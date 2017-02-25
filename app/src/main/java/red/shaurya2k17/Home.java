package red.shaurya2k17;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import red.shaurya2k17.Admin.AdminHomeFragment;
import red.shaurya2k17.Events.AllEventsFragment;

import static red.shaurya2k17.Utils.isLollipop;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public View frag_view;
    public View home_view;
    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mListener;
    ValueEventListener listener;
    public HashMap<String,String> wData= new HashMap<>();
    public HashMap<String,String> lData= new HashMap<>();
    public HashMap<String,String> tData= new HashMap<>();

    SharedPreferences status;
    Context context;

    MenuItem stats_nav;
    MenuItem admin_nav;
    MenuItem logout_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        context=this;
        mFirebaseAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setFitsSystemWindows(false);
        Menu menuNav=navigationView.getMenu();
        stats_nav = menuNav.findItem(R.id.stats_nav);
        admin_nav= menuNav.findItem(R.id.admin_nav);
        logout_nav=menuNav.findItem(R.id.log_out_nav);

        if(mFirebaseAuth.getCurrentUser().getEmail().equals("admin@shaurya.com")) {
            admin_nav.setVisible(true);
            logout_nav.setVisible(true);
        }
        else {
            logout_nav.setVisible(true);
        }

        mListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if(!user.getEmail().equals("admin@shaurya.com"))
                    {
                        admin_nav.setVisible(false);
                    }

                } else {
                    Intent i=new Intent(Home.this,UserLoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };




        frag_view=findViewById(R.id.frag_view_home);
        home_view=findViewById(R.id.home_view);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("stats");

        getStats getStats= new getStats();
        getStats.execute();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_nav) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.home_nav)
        {
            frag_view.setVisibility(View.GONE);
            home_view.setVisibility(View.VISIBLE);
            setTitle("Home");


        }else if (id == R.id.all_matches_nav) {

            replaceFragments(AllEventsFragment.class,false);
            setTitle("Events");

            /*
            home_view.setVisibility(View.GONE);
            frag_view.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("id", "Cricket");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            MatchListFragment frag=new MatchListFragment();
            frag.setArguments(bundle);
            ft.replace(R.id.frag_view_home, frag);
            ft.commit();*/

            /*Intent i=new Intent(Home.this,MatchList.class);
            i.putExtra("id","Cricket");
            startActivity(i);*/

        } else if (id == R.id.time_table_nav) {


        } else if (id == R.id.admin_nav) {

            if(mFirebaseAuth.getCurrentUser()!=null && mFirebaseAuth.getCurrentUser().getEmail()
                    .equals("admin@shaurya.com"))
            {
                replaceFragments(AdminHomeFragment.class,false);
            }
            else {
                replaceFragments(AdminHomeFragment.class, false);
            }
            setTitle("Admin");

        } else if (id == R.id.stats_nav) {

            replaceFragments(StatsFragment.class,"stats");
            setTitle("Fest Stats");
            // while changing title here see get stats async task
        }else if(id == R.id.about_nav)
        {



        } else if (id == R.id.log_out_nav) {

            status = context.getSharedPreferences("status", MODE_PRIVATE);
            status.edit().putBoolean("in",false).apply();
            mFirebaseAuth.signOut();
            /*Intent i=new Intent(Home.this,UserLoginActivity.class);
            startActivity(i);
            finish();*/

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void replaceFragments(Class fragmentClass,Boolean addToBackStack) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if(!getSupportFragmentManager().beginTransaction().isEmpty()) {
                getSupportFragmentManager().beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.frag_view_home)).commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        home_view.setVisibility(View.GONE);
        frag_view.setVisibility(View.VISIBLE);
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(isLollipop()) {
            fragment.setEnterTransition(new Slide(Gravity.RIGHT));
            fragment.setExitTransition(new Explode());
            fragment.setAllowEnterTransitionOverlap(true);
        }
        fragmentManager.popBackStackImmediate();
        if(addToBackStack) {
            fragmentManager.beginTransaction().replace(R.id.frag_view_home, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction().replace(R.id.frag_view_home, fragment)
                    .commit();
        }
    }

    public void replaceFragments(Class fragmentClass,String tag) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if(!getSupportFragmentManager().beginTransaction().isEmpty()) {
                getSupportFragmentManager().beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.frag_view_home)).commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        home_view.setVisibility(View.GONE);
        frag_view.setVisibility(View.VISIBLE);
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction().replace(R.id.frag_view_home, fragment,tag)
                .commit();
    }

    public void showDialog(Context context,String team) {


        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.score_summary_dialog,null);

        TextView total_score = (TextView)dialogView.findViewById(R.id.tot_dia);
        TextView won =(TextView)dialogView.findViewById(R.id.won_dia);
        TextView lost =(TextView)dialogView.findViewById(R.id.lost_dia);


        total_score.setText(tData.get(team));
        won.setText(wData.get(team));
        lost.setText(lData.get(team));


        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        dialog.setView(dialogView);
        dialog.setTitle(team);
        dialog.setIcon(R.drawable.ic_info_outline_teal_500_48dp);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.cancel();
            }
        });
        dialog.show();

    }


    public void replaceFragments(Class fragmentClass, HashMap<String,String> map)
    {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();

        Set<Map.Entry<String, String>> set = map.entrySet();
        for(Map.Entry<String, String> data : set){
            bundle.putString(data.getKey(),data.getValue());
        }
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frag_view_home, fragment)
                .addToBackStack(null)
                .commit();

    }

    class getStats extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            listener=new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                    wData.clear();
                    lData.clear();
                    tData.clear();

                    while (iterator.hasNext()) {
                        DataSnapshot data = iterator.next();
                        long winCount=data.child("won").getChildrenCount();
                        long lostCount=data.child("lost").getChildrenCount();
                        long totCount=winCount+lostCount;

                        if(winCount != 0)
                            wData.put(data.getKey(),Integer.toString(((int) winCount)));

                        lData.put(data.getKey(),Integer.toString((int)lostCount));
                        tData.put(data.getKey(),Integer.toString((int)totCount));

                    }
                    stats_nav.setEnabled(true);
                    StatsFragment fragment =(StatsFragment) getSupportFragmentManager()
                            .findFragmentByTag("stats");
                    if(fragment !=null && fragment.isVisible())
                        fragment.refreshData();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mRef.addValueEventListener(listener);

            return null;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListener != null) {
            mFirebaseAuth.removeAuthStateListener(mListener);
        }
    }

}


