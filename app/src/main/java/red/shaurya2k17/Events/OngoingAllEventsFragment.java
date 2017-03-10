package red.shaurya2k17.Events;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import red.shaurya2k17.Adapters.ExpandableListAdapter;
import red.shaurya2k17.Home;
import red.shaurya2k17.R;

public class OngoingAllEventsFragment extends Fragment {

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    public OngoingAllEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ongoing_all_events, container, false);
    }





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get the listview
        expandableListView = (ExpandableListView) view.findViewById(R.id.ongoing_all_events_elv);

        // preparing list data
        prepareListData();

        expandableListAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0,true);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                    ((Home)getActivity()).replaceFragments(OngoingFragment.class,map);

                }
                return false;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add("Sports");


        List<String> Sports = new ArrayList<>();
        Sports.add("Cricket");
        Sports.add("Football");
        Sports.add("Volleyball");
        Sports.add("Badminton");


        listDataChild.put(listDataHeader.get(0), Sports);
    }

}
