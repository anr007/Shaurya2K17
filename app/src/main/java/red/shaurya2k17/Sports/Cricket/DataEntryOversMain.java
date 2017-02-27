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


public class DataEntryOversMain extends Fragment implements AdapterView.OnItemSelectedListener{

    Spinner onStrikeRuns;
    Spinner onStrike;
    Spinner otherBatsmen;
    Spinner extras;
    Spinner ballResult;
    Spinner wicketBy;

    EditText totalScore;
    EditText completedOvers;

    String on_Strike_runs;
    String on_Strike;
    String other_Batsmen;
    String Extras;
    String ball_Result;
    String wicket_by;

    Button next;
    DatabaseReference mRef;
    FirebaseDatabase database;

    String mat_name;
    String t1;
    String t2;



    public DataEntryOversMain() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_entry_overs_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference(); // yet to be decided

        mat_name=getArguments().getString("mat_name");
        t1=getArguments().getString("t1");
        t2=getArguments().getString("t2");



        totalScore=(EditText)view.findViewById(R.id.tot_score_overs_main);
        completedOvers=(EditText)view.findViewById(R.id.comp_over_overs_main);

        onStrike = (Spinner) view.findViewById(R.id.on_strike_bats_overs_main);
        ArrayAdapter<CharSequence> adapter_onStrike = ArrayAdapter.createFromResource(getContext(),
                R.array.Selection      , android.R.layout.simple_spinner_item);

        //change the array above

        adapter_onStrike.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        onStrike.setAdapter(adapter_onStrike);
        onStrike.setOnItemSelectedListener(this);


        onStrikeRuns = (Spinner) view.findViewById(R.id.runs_overs_main);
        ArrayAdapter<CharSequence> adapter_onStrikeRuns = ArrayAdapter.createFromResource(getContext(),
                R.array.Selection      , android.R.layout.simple_spinner_item);

        //change the array above

        adapter_onStrikeRuns.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        onStrikeRuns.setAdapter(adapter_onStrikeRuns);
        onStrikeRuns.setOnItemSelectedListener(this);


        otherBatsmen = (Spinner) view.findViewById(R.id.other_bats_overs_main);
        ArrayAdapter<CharSequence> adapter_otherBatsmen = ArrayAdapter.createFromResource(getContext(),
                R.array.Selection      , android.R.layout.simple_spinner_item);

        //change the array above

        adapter_otherBatsmen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otherBatsmen.setAdapter(adapter_otherBatsmen);
        otherBatsmen.setOnItemSelectedListener(this);

        extras = (Spinner) view.findViewById(R.id.extras_overs_main);
        ArrayAdapter<CharSequence> adapter_extras = ArrayAdapter.createFromResource(getContext(),
                R.array.Selection      , android.R.layout.simple_spinner_item);

        //change the array above

        adapter_extras.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        extras.setAdapter(adapter_extras);
        extras.setOnItemSelectedListener(this);


        ballResult = (Spinner) view.findViewById(R.id.ball_result_overs_main);
        ArrayAdapter<CharSequence> adapter_ballResult = ArrayAdapter.createFromResource(getContext(),
                R.array.Selection      , android.R.layout.simple_spinner_item);

        //change the array above

        adapter_ballResult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ballResult.setAdapter(adapter_ballResult);
        ballResult.setOnItemSelectedListener(this);


        wicketBy= (Spinner) view.findViewById(R.id.wicket_by_overs_main);
        ArrayAdapter<CharSequence> adapter_wicketBy = ArrayAdapter.createFromResource(getContext(),
                R.array.Selection      , android.R.layout.simple_spinner_item);

        //change the array above

        adapter_wicketBy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wicketBy.setAdapter(adapter_wicketBy);
        wicketBy.setOnItemSelectedListener(this);





        next=(Button)view.findViewById(R.id.next_3);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.next_start)
                {
                    next_overs_main();
                }
            }
        });

    }

    void next_overs_main()
    {

        mRef.child("ongoing").child("Cricket").child(mat_name).child("team1Score")
                .setValue(on_Strike_runs);
        //mRef.child("players").child(t1).child("")




    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // i is position

        switch(adapterView.getId()){
            case R.id.on_strike_bats_overs_main:
                if(i>0)
                    on_Strike=onStrike.getItemAtPosition(i).toString();
                break;
            case R.id.other_bats_overs_main:
                if(i>0)
                    other_Batsmen=otherBatsmen.getItemAtPosition(i).toString();
                break;
            case R.id.runs_overs_main:
                if(i>0)
                    on_Strike_runs=onStrikeRuns.getItemAtPosition(i).toString();
                break;
            case R.id.extras_overs_main:
                if(i>0)
                    Extras=extras.getItemAtPosition(i).toString();
                break;

            case R.id.wicket_by_overs_main:
                if(i>0)
                    wicket_by=wicketBy.getItemAtPosition(i).toString();
                break;
            case R.id.ball_result_overs_main:
                if(i>0)
                    ball_Result=ballResult.getItemAtPosition(i).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
