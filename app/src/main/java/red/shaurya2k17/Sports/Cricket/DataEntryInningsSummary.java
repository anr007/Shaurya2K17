package red.shaurya2k17.Sports.Cricket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import red.shaurya2k17.R;

public class DataEntryInningsSummary extends Fragment {


    public DataEntryInningsSummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_entry_innings_summary, container, false);
    }

    //TODO: Create layout of this frag , write code for changing all the require things like
    //TODO: rest overs , reset curr_balls , reset completed batsmen and bowlers list
    //TODO: change curr innings number , swap t1,t2 status , both on local and firebase



}
