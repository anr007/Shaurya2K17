package red.shaurya2k17.Sports.Cricket;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import red.shaurya2k17.Admin.DataEntryActivity;
import red.shaurya2k17.R;

import static red.shaurya2k17.R.id.toss_won_pref_dec_2;


public class DataEntryCricket2 extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner toss_won;
    Spinner toss_won_pref;
    String tw;
    String twp;
    String mat_name;
    String t1;
    String t2;

    String deflt="Toss Won By";
    Button next;
    DatabaseReference mRef;
    FirebaseDatabase database;

    public DataEntryCricket2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_entry_cricket2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("ongoing"); // yet to be decided

        mat_name=getArguments().getString("mat_name");
        t1=getArguments().getString("t1");
        t2=getArguments().getString("t2");

        ArrayList<String> teams=new ArrayList<String>();
        teams.add(deflt);
        teams.add(t1);
        teams.add(t2);
        //get values of t1 , t2 from db

        toss_won = (Spinner) view.findViewById(R.id.toss_won_dec_2);
        ArrayAdapter<String> adapter_toss_won = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,teams);

        adapter_toss_won.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toss_won.setAdapter(adapter_toss_won);
        toss_won.setOnItemSelectedListener(this);




        toss_won_pref = (Spinner) view.findViewById(toss_won_pref_dec_2);
        ArrayAdapter<CharSequence> adapter_toss_won_pref = ArrayAdapter.createFromResource(
                getContext(),R.array.Selection, android.R.layout.simple_spinner_item);
        adapter_toss_won_pref.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toss_won_pref.setAdapter(adapter_toss_won_pref);
        toss_won_pref.setOnItemSelectedListener(this);




        next=(Button)view.findViewById(R.id.next_2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.next_2)
                {
                    next_2();
                }
            }
        });




    }

    void next_2()
    {
        // update the toss won details in ongoing match obj
        //replace with 3

        mRef.child("Cricket").child(mat_name).child("tossWon").setValue(tw);
        mRef.child("Cricket").child(mat_name).child("tossWonPref").setValue(twp);
        HashMap<String,String> map=new HashMap<>();
        map.put("mat_name",mat_name);
        map.put("t1",t1);
        map.put("t2",t2);
        ((DataEntryActivity)getActivity()).replaceFragments(DataEntryCricket3.class,false,map);

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // i is position

        switch(adapterView.getId()){
            case R.id.toss_won_dec_2:
                if(i>0)
                    tw=toss_won.getItemAtPosition(i).toString();
                break;
            case toss_won_pref_dec_2:
                if(i>0)
                    twp=toss_won_pref.getItemAtPosition(i).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
