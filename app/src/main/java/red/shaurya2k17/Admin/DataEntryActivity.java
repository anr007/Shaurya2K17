package red.shaurya2k17.Admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import red.shaurya2k17.Adapters.ExpandableListAdapter;
import red.shaurya2k17.R;
import red.shaurya2k17.Sports.Cricket.DataEntryCricketStart;

import static red.shaurya2k17.Utils.isLollipop;

public class DataEntryActivity extends AppCompatActivity {


    String cMatName;
    View HomeView;
    View FragView;


    //****************************** <Cricket> *****************

    public String mat_name;
    public String t1;
    public String t1s;
    public String t2;
    public String t2s;
    public String tovers;
    public String curr_over;
    public String curr_striker;
    public String curr_non_striker;
    public String curr_bowler;
    public String curr_ball;
    public String t1_status;
    public String t2_status;
    public String toss_won;
    public String tosswon_pref;
    public String comment;

    public HashMap<String,ArrayList<String>> completed_bowlers=new HashMap<>();
                                                    // list[0]=overs; list[1]=wickets;
    public HashMap<String,ArrayList<String>> completed_batsmen=new HashMap<>();
                                                  // list[0]=runs; list[1]=fours;
                                                 // list[2]=sixes; list[3]=balls_played;
















    //**************************** <\Cricket> ********************
    

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);


        HomeView=findViewById(R.id.data_entry_home);
        FragView=findViewById(R.id.frag_view_Cricket);

        //elv............................................................
        expandableListView = (ExpandableListView) findViewById(R.id.all_events_elv);
        prepareListData();
        expandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if(listDataHeader.get(groupPosition).equals("Sports"))
                {
                    HashMap<String,String> map=new HashMap<>();
                    map.put("id",listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition));
                    replaceFragments(DataEntryCricketStart.class,true,map);

                }
                return false;
            }
        });


    }


    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Athletics");
        listDataHeader.add("Sports");
        listDataHeader.add("Games");

        // Adding child data
        List<String> Athletics = new ArrayList<>();
        Athletics.add("Shotput");


        List<String> Sports = new ArrayList<>();
        Sports.add("Cricket");
        Sports.add("Football");
        Sports.add("Volleyball");
        Sports.add("Badminton");


        List<String> Games = new ArrayList<>();
        Games.add("Chess");


        listDataChild.put(listDataHeader.get(0), Athletics); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Sports);
        listDataChild.put(listDataHeader.get(2), Games);


    }





    public void replaceFragments(Class fragmentClass,Boolean addToBackStack,
                                 HashMap<String,String> map) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if(!getSupportFragmentManager().beginTransaction().isEmpty()) {
                getSupportFragmentManager().beginTransaction().
                        remove(getSupportFragmentManager()
                                .findFragmentById(R.id.frag_view_Cricket)).commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HomeView.setVisibility(View.GONE);
        FragView.setVisibility(View.VISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(isLollipop()) {
            try{

                fragment.setEnterTransition(new Slide(Gravity.END));
                fragment.setExitTransition(new Fade());
                fragment.setAllowEnterTransitionOverlap(true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        //fragmentManager.popBackStackImmediate();


        if(map!= null) {
            Bundle bundle = new Bundle();

            Set<Map.Entry<String, String>> set = map.entrySet();
            for (Map.Entry<String, String> data : set) {
                bundle.putString(data.getKey(), data.getValue());
            }

            fragment.setArguments(bundle);
        }


        if(addToBackStack) {
            fragmentManager.beginTransaction().replace(R.id.frag_view_Cricket, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            fragmentManager.beginTransaction().replace(R.id.frag_view_Cricket, fragment)
                    .commit();
        }
    }


    @Override
    public void onBackPressed() {


        if(HomeView.getVisibility()==View.GONE)
        {
            HomeView.setVisibility(View.VISIBLE);
            FragView.setVisibility(View.GONE);
            return;
        }

        super.onBackPressed();


    }
}



