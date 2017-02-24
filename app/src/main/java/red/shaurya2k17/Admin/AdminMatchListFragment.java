package red.shaurya2k17.Admin;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import red.shaurya2k17.Adapters.MatchRecyclerViewAdapter;
import red.shaurya2k17.Adapters.MatchRecyclerViewOnClickListener;
import red.shaurya2k17.Events.MatchDetailFragment;
import red.shaurya2k17.Home;
import red.shaurya2k17.Match;
import red.shaurya2k17.R;


public class AdminMatchListFragment extends Fragment {


    MatchRecyclerViewAdapter adapter;
    FirebaseDatabase database;
    DatabaseReference mRef;
    private View mProgressView;
    private View mAllListView;
    private View empty;
    private View prog_lay;
    RecyclerView recyclerView;
    NetworkTask networkTask;
    ArrayList<Match> matchList;
    String cat;
    SwipeRefreshLayout swipeContainer;
    private Paint p;
    ValueEventListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cat = getArguments().getString("id");
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("matches").child(cat);
        networkTask=new NetworkTask();
        networkTask.execute();
        recyclerView = (RecyclerView) view.findViewById(R.id.all_rlist);
        mProgressView = view.findViewById(R.id.all_list_pgbar);
        empty = view.findViewById(R.id.empty);
        prog_lay=view.findViewById(R.id.pg_view);
        mAllListView=recyclerView;
        matchList=new ArrayList<>();
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.srMatchList);
        p = new Paint();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settingUpThings();
        swipeRefresh();
        initSwipe();
        if(networkTask.getStatus() != AsyncTask.Status.FINISHED)
        {
            swipeContainer.setEnabled(false);
            showProgress(true);
        }
    }

    void settingUpThings()
    {

        adapter = new MatchRecyclerViewAdapter(matchList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 5;
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new MatchRecyclerViewOnClickListener(getContext(), recyclerView,
                new MatchRecyclerViewOnClickListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        Match d = matchList.get(position);
                        HashMap<String,String> map=new HashMap<>();
                        map.put("mat_name",d.getMat_nam());
                        map.put("id",cat);
                        ((Home) getActivity()).replaceFragments(MatchDetailFragment.class,map);
                    }

                    public void onLongClickItem(View v, int position) {

                    }
                }));
    }

    void swipeRefresh()
    {


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetworkTask networkTask=new NetworkTask();
                networkTask.execute();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.mgreen,
                R.color.mblue, R.color.morange, R.color.mred);
    }

    class NetworkTask extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {


            listener=new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                    matchList.clear();
                    while (iterator.hasNext()) {
                        Match value = iterator.next().getValue(Match.class);
                        matchList.add(value);
                    }
                    Log.d("me","do in background");
                    System.out.println(matchList.size() +"  is this");
                    swipeContainer.setEnabled(true);
                    showProgress(false);
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(adapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
                    empty.setVisibility(adapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
                    if
                            (swipeContainer.isRefreshing())
                    {

                        swipeContainer.setRefreshing(false);
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());
                }
            };


            mRef.addValueEventListener(listener);

            return null;
        }

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAllListView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAllListView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAllListView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            prog_lay.setVisibility(show ? View.VISIBLE : View.GONE);
            prog_lay.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            prog_lay.setVisibility(show ? View.VISIBLE : View.GONE);
            mAllListView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    Match d = matchList.get(position);
                    deleteDialogue(position,d);
                    adapter.notifyDataSetChanged();


                } else {
                    //removeView();
                    //edit_position = position;
                    //alertDialog.setTitle("Edit Country");
                    //et_country.setText(countries.get(position));
                    //alertDialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#f44336"));  //"#388E3C"
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#4caf50")); //"#D32F2F"
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeView(View view){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void deleteDialogue(final int position,final Match d)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Delete Confirmation");
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage("Do you want to delete this");
        alertDialog.setIcon(R.drawable.ic_delete_black_48dp);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                mRef.child(d.getMat_nam()).removeValue();
                mRef.getRoot().child("stats").child(d.getWin())
                        .child("won")
                        .child(d.getMat_nam()).removeValue();
                if(d.getWin().equals(d.getT1()))
                {
                    mRef.getRoot().child("stats").child(d.getT2())
                            .child("lost")
                            .child(d.getMat_nam()).removeValue();
                }
                else {
                    mRef.getRoot().child("stats").child(d.getT1())
                            .child("lost")
                            .child(d.getMat_nam()).removeValue();
                }

            }

        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRef.removeEventListener(listener);
        networkTask.cancel(true);

    }

}
