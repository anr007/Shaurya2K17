package red.shaurya2k17.Sports.Cricket;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import red.shaurya2k17.R;

public class DataEntryOversSummary extends Fragment {


    public DataEntryOversSummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_entry_overs_summary, container, false);
    }

}
