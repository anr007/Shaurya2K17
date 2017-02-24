package red.shaurya2k17.Admin;

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


public class AdminAllEventsFragment extends Fragment {



    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_events, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get the listview
        expandableListView = (ExpandableListView) view.findViewById(R.id.all_events_elv);

        // preparing list data
        prepareListData();

        expandableListAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(expandableListAdapter);
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
                    ((Home)getActivity()).replaceFragments(AdminMatchListFragment.class,map);

                }
                return false;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Athletics");
        listDataHeader.add("Sports");
        listDataHeader.add("Games");

        // Adding child data
        List<String> Athletics = new ArrayList<String>();
        Athletics.add("Shotput");


        List<String> Sports = new ArrayList<String>();
        Sports.add("Cricket");
        Sports.add("Football");
        Sports.add("Volleyball");
        Sports.add("Badminton");


        List<String> Games = new ArrayList<String>();
        Games.add("Chess");


        listDataChild.put(listDataHeader.get(0), Athletics); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Sports);
        listDataChild.put(listDataHeader.get(2), Games);
    }

}
