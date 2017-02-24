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
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import red.shaurya2k17.R;


public class DataEntryCricket3 extends Fragment implements AdapterView.OnItemSelectedListener{

    Spinner cbowler;
    Spinner onStrike;
    Spinner otherBats;
    EditText currOver;
    EditText currScore;
    String bowler_name;
    String onStrike_name;
    String otherBats_name;
    Button next;
    DatabaseReference mRef;
    FirebaseDatabase database;


    public DataEntryCricket3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_entry_cricket3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("stats"); // yet to be decided

        currOver=(EditText)view.findViewById(R.id.curr_over_dec_3);
        currScore=(EditText)view.findViewById(R.id.curr_score_dec_3);



        cbowler = (Spinner) view.findViewById(R.id.curr_bowler_dec_3);
        ArrayAdapter<CharSequence> adapter_cbowler = ArrayAdapter.createFromResource(getContext(),
                R.array.Selection      , android.R.layout.simple_spinner_item);

        //change the array above

        adapter_cbowler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbowler.setAdapter(adapter_cbowler);
        cbowler.setOnItemSelectedListener(this);


        onStrike = (Spinner) view.findViewById(R.id.on_strike_bats_dec_3);
        ArrayAdapter<CharSequence> adapter_onStrike = ArrayAdapter.createFromResource(getContext(),
                R.array.Selection      , android.R.layout.simple_spinner_item);

        //change the array above

        adapter_onStrike.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        onStrike.setAdapter(adapter_onStrike);
        onStrike.setOnItemSelectedListener(this);


        otherBats = (Spinner) view.findViewById(R.id.other_bats_dec_3);
        ArrayAdapter<CharSequence> adapter_otherBats = ArrayAdapter.createFromResource(getContext(),
                R.array.Selection      , android.R.layout.simple_spinner_item);

        //change the array above

        adapter_otherBats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otherBats.setAdapter(adapter_otherBats);
        otherBats.setOnItemSelectedListener(this);

        next=(Button)view.findViewById(R.id.next_3);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.next_start)
                {
                    next_3();
                }
            }
        });

    }


    void next_3()
    {
        // update the toss won details in ongoing match obj

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // i is position

        switch(adapterView.getId()){
            case R.id.curr_bowler_dec_3:
                if(i>0)
                    bowler_name=cbowler.getItemAtPosition(i).toString();
                break;
            case R.id.on_strike_bats_dec_3:
                if(i>0)
                    onStrike_name=onStrike.getItemAtPosition(i).toString();
                break;
            case R.id.other_bats_dec_3:
                if(i>0)
                    otherBats_name=otherBats.getItemAtPosition(i).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
