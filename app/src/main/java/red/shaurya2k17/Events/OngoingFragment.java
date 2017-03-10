package red.shaurya2k17.Events;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import red.shaurya2k17.Adapters.OngoingRecyclerViewAdapter;
import red.shaurya2k17.Match;
import red.shaurya2k17.R;


public class OngoingFragment extends Fragment {

    OngoingRecyclerViewAdapter adapter;
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
    ValueEventListener listener;


    public OngoingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ongoing, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cat = getArguments().getString("id");
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("ongoing").child(cat);
        networkTask=new NetworkTask();
        networkTask.execute();
        recyclerView = (RecyclerView) view.findViewById(R.id.all_ongoing_rlist);
        mProgressView = view.findViewById(R.id.all_list_pgbar_ongoing);
        empty = view.findViewById(R.id.empty);
        prog_lay=view.findViewById(R.id.pg_ongoing_view);
        mAllListView=recyclerView;
        matchList=new ArrayList<>();
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.srOngoingMatchList);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settingUpThings();
        swipeRefresh();
        if(networkTask.getStatus() != AsyncTask.Status.FINISHED)
        {
            swipeContainer.setEnabled(false);
            showProgress(true);
        }
    }

    void settingUpThings()
    {

        adapter = new OngoingRecyclerViewAdapter(matchList,getContext());
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


    @Override
    public void onPause() {
        super.onPause();
        mRef.removeEventListener(listener);
        networkTask.cancel(true);

    }

}
