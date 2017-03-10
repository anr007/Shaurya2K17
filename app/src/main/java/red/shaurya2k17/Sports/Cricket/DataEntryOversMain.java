package red.shaurya2k17.Sports.Cricket;

import android.content.res.Resources;
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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import red.shaurya2k17.Admin.DataEntryActivity;
import red.shaurya2k17.R;

import static java.lang.Integer.parseInt;


public class DataEntryOversMain extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner onStrikeRuns;
    Spinner onStrike;
    Spinner otherBatsmen;
    Spinner extras;
    Spinner ballResult;
    Spinner wicketBy;
    Spinner runout;

    EditText totalScore;
    EditText completedOvers;
    EditText byes;

    String on_Strike_runs;
    String on_Strike;
    String other_Batsmen;
    String Extras;
    String ball_Result;
    String wicket_by;
    String run_out;

    Button next;
    DatabaseReference mRef;
    FirebaseDatabase database;



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
        mRef = database.getReference();
        Resources res = getResources();// yet to be decided


        totalScore = (EditText) view.findViewById(R.id.tot_score_overs_main);
        totalScore.setEnabled(false);
        completedOvers = (EditText) view.findViewById(R.id.comp_over_overs_main);
        completedOvers.setEnabled(false);
        byes = (EditText) view.findViewById(R.id.bye_runs_overs_main);
        byes.setEnabled(false);

        if( ((DataEntryActivity)getActivity()).t1_status.equals("batting"))
        {
            totalScore.setText( ((DataEntryActivity)getActivity()).t1s);
        }
        else
        {
            totalScore.setText( ((DataEntryActivity)getActivity()).t2s);
        }

        completedOvers.setText(((DataEntryActivity)getActivity()).curr_over+"."+
                ((DataEntryActivity)getActivity()).curr_ball);

        if (((DataEntryActivity) getActivity()).t1_status.equals("batting")) {

            String parray = ((DataEntryActivity) getActivity()).t1 + "_Cricket_Players";
            onStrike = (Spinner) view.findViewById(R.id.on_strike_bats_overs_main);

            int id = getContext().getResources().getIdentifier(parray,
                    "array", getContext().getPackageName());
            String[] on_strike = res.getStringArray(id);
            on_strike[0] = "Select On Strike Batsmen";
            ArrayAdapter<String> adapter_onStrike = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, on_strike);
            adapter_onStrike.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            onStrike.setAdapter(adapter_onStrike);
            onStrike.setOnItemSelectedListener(this);


            otherBatsmen = (Spinner) view.findViewById(R.id.other_bats_overs_main);
            String[] non_striker = res.getStringArray(id);
            non_striker[0] = "Select Non Striker";
            ArrayAdapter<String> adapter_otherBats = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, non_striker);
            adapter_otherBats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            otherBatsmen.setAdapter(adapter_otherBats);
            otherBatsmen.setOnItemSelectedListener(this);

        } else {

            String parray = ((DataEntryActivity) getActivity()).t2 + "_Cricket_Players";

            onStrike = (Spinner) view.findViewById(R.id.on_strike_bats_overs_main);
            int id = getContext().getResources().getIdentifier(parray,
                    "array", getContext().getPackageName());
            String[] on_strike = res.getStringArray(id);
            on_strike[0] = "Select On Strike Batsmen";
            ArrayAdapter<String> adapter_onStrike = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, on_strike);
            adapter_onStrike.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            onStrike.setAdapter(adapter_onStrike);
            onStrike.setOnItemSelectedListener(this);


            otherBatsmen = (Spinner) view.findViewById(R.id.other_bats_overs_main);
            String[] non_striker = res.getStringArray(id);
            non_striker[0] = "Select Non Striker";
            ArrayAdapter<String> adapter_otherBats = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, non_striker);
            adapter_otherBats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            otherBatsmen.setAdapter(adapter_otherBats);
            otherBatsmen.setOnItemSelectedListener(this);
        }


        onStrikeRuns = (Spinner) view.findViewById(R.id.runs_overs_main);
        ArrayAdapter<CharSequence> adapter_onStrikeRuns = ArrayAdapter.createFromResource(getContext(),
                R.array.Runs, android.R.layout.simple_spinner_item);
        adapter_onStrikeRuns.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        onStrikeRuns.setAdapter(adapter_onStrikeRuns);
        onStrikeRuns.setOnItemSelectedListener(this);


        extras = (Spinner) view.findViewById(R.id.extras_overs_main);
        ArrayAdapter<CharSequence> adapter_extras = ArrayAdapter.createFromResource(getContext(),
                R.array.Extras, android.R.layout.simple_spinner_item);

        adapter_extras.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        extras.setAdapter(adapter_extras);
        extras.setOnItemSelectedListener(this);


        ballResult = (Spinner) view.findViewById(R.id.ball_result_overs_main);
        ArrayAdapter<CharSequence> adapter_ballResult = ArrayAdapter.createFromResource(getContext(),
                R.array.Result, android.R.layout.simple_spinner_item);
        adapter_ballResult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ballResult.setAdapter(adapter_ballResult);
        ballResult.setOnItemSelectedListener(this);


        if (((DataEntryActivity) getActivity()).t1_status.equals("bowling")) {
            String parray = ((DataEntryActivity) getActivity()).t1 + "_Cricket_Players";
            int array_id = getContext().getResources().getIdentifier(parray, "array",
                    getContext().getPackageName());

            wicketBy = (Spinner) view.findViewById(R.id.wicket_by_overs_main);
            String[] cbowler = res.getStringArray(array_id);
            cbowler[0] = "Select any Fielder (Caught/Run Out)";
            ArrayAdapter<String> adapter_cbowler = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, cbowler);
            adapter_cbowler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            wicketBy.setAdapter(adapter_cbowler);
            wicketBy.setOnItemSelectedListener(this);

        } else {
            String parray = ((DataEntryActivity) getActivity()).t2 + "_Cricket_Players";
            int array_id = getContext().getResources().getIdentifier(parray, "array",
                    getContext().getPackageName());
            wicketBy = (Spinner) view.findViewById(R.id.wicket_by_overs_main);
            String[] cbowler = res.getStringArray(array_id);
            cbowler[0] = "Select any Fielder (Caught/Run Out)";
            ArrayAdapter<String> adapter_cbowler = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_item, cbowler);
            adapter_cbowler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            wicketBy.setAdapter(adapter_cbowler);
            wicketBy.setOnItemSelectedListener(this);

        }
        wicketBy.setEnabled(false);


        runout = (Spinner) view.findViewById(R.id.run_out_overs_main);
        ArrayList<String> batsmen = new ArrayList<>();
        batsmen.add("Select Batsmen (Run Out)");
        //batsmen.add(on_Strike);
        //batsmen.add(other_Batsmen);
        ArrayAdapter<String> run_out = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, batsmen);
        run_out.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        runout.setAdapter(run_out);
        runout.setOnItemSelectedListener(this);
        runout.setEnabled(false);


        next = (Button) view.findViewById(R.id.next_overs_main);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.next_overs_main) {
                    next_overs_main();
                }
            }
        });

    }

    void next_overs_main() {


        //TODO: if batsmen object is not there u should create it and add runs



        //............ Batsmen object not exists create it ......................................

        //........................... Striker ...................................................

        if ((((DataEntryActivity) getActivity()).completed_batsmen != null)
                && !((DataEntryActivity) getActivity()).completed_batsmen.containsKey(on_Strike)) {
            if (((DataEntryActivity) getActivity()).t1_status.equals("batting")) {
                Batsmen current_striker = new Batsmen();
                current_striker.setTeam(((DataEntryActivity) getActivity()).t1);
                current_striker.setName(on_Strike);
                current_striker.setRuns("0");
                current_striker.setFours("0");
                current_striker.setSixes("0");
                current_striker.setBalls("0");

                ArrayList<String> striker_details=new ArrayList<>();
                striker_details.add("0");
                striker_details.add("0");
                striker_details.add("0");
                striker_details.add("0");

                ((DataEntryActivity)getActivity()).completed_batsmen.
                        put(on_Strike, striker_details);



                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t1).
                        child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                        setValue(current_striker);
            } else {
                Batsmen current_striker = new Batsmen();
                current_striker.setTeam(((DataEntryActivity) getActivity()).t2);
                current_striker.setName(on_Strike);
                current_striker.setRuns("0");
                current_striker.setFours("0");
                current_striker.setSixes("0");
                current_striker.setBalls("0");

                ArrayList<String> striker_details=new ArrayList<>();
                striker_details.add("0");
                striker_details.add("0");
                striker_details.add("0");
                striker_details.add("0");

                ((DataEntryActivity)getActivity()).completed_batsmen.
                        put(on_Strike, striker_details);

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t2).
                        child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                        setValue(current_striker);
            }

        }

        //........................ non Striker .................................................

        if((((DataEntryActivity)getActivity()).completed_batsmen != null)
                &&!((DataEntryActivity)getActivity()).completed_batsmen.containsKey(other_Batsmen))
        {
            if(((DataEntryActivity)getActivity()).t1_status.equals("batting")) {
                Batsmen current_non_striker = new Batsmen();
                current_non_striker.setTeam(((DataEntryActivity) getActivity()).t1);
                current_non_striker.setName(other_Batsmen);
                current_non_striker.setRuns("0");
                current_non_striker.setFours("0");
                current_non_striker.setSixes("0");
                current_non_striker.setBalls("0");

                ArrayList<String> non_striker_details=new ArrayList<>();
                non_striker_details.add("0");
                non_striker_details.add("0");
                non_striker_details.add("0");
                non_striker_details.add("0");

                ((DataEntryActivity)getActivity()).completed_batsmen.
                        put(other_Batsmen, non_striker_details);

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t1).
                        child(((DataEntryActivity) getActivity()).mat_name).child(other_Batsmen).
                        setValue(current_non_striker);
            }
            else
            {
                Batsmen current_non_striker = new Batsmen();
                current_non_striker.setTeam(((DataEntryActivity) getActivity()).t2);
                current_non_striker.setName(other_Batsmen);
                current_non_striker.setRuns("0");
                current_non_striker.setFours("0");
                current_non_striker.setSixes("0");
                current_non_striker.setBalls("0");

                ArrayList<String> non_striker_details=new ArrayList<>();
                non_striker_details.add("0");
                non_striker_details.add("0");
                non_striker_details.add("0");
                non_striker_details.add("0");

                ((DataEntryActivity)getActivity()).completed_batsmen.
                        put(other_Batsmen, non_striker_details);

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t2).
                        child(((DataEntryActivity) getActivity()).mat_name).child(other_Batsmen).
                        setValue(current_non_striker);

            }

        }

        //.......................................................................................

        //................... Update the score in t1 or t2 main score and on strike score...........

        //TODO: after getting the present score set the updated score to local object
        // TODO: and the database also - update: completed **** citation needed *****

        //TODO: Manage the runs extras , wickets , updating the score screens below button ,
        //TODO: Main thing manage curr over in local and online data base and current ball also
        //TODO: Loop here 6 times and not to count extras after go to de3 again. think for any missings ....

        if (((DataEntryActivity) getActivity()).t1_status.equals("batting")) {


            // ......................... team 1 score ++ ......................................

            int cScore = parseInt(((DataEntryActivity) getActivity()).t1s);
            cScore = cScore + parseInt(on_Strike_runs);
            ((DataEntryActivity) getActivity()).t1s = Integer.toString(cScore);

            mRef.child("ongoing").child("Cricket").child(((DataEntryActivity) getActivity()).
                    mat_name).child("team1Score").setValue(cScore);



            // ....................... onstrike score ++ ......................................

            ArrayList<String> striker_details=((DataEntryActivity)getActivity())
                    .completed_batsmen.get(on_Strike);
            int strikerRuns= parseInt(striker_details.get(0));
            int strikerSixes= parseInt(striker_details.get(2));
            int strikerFours= parseInt(striker_details.get(1));
            int strikerBallsPlayed= parseInt(striker_details.get(3));

            strikerRuns= strikerRuns + parseInt(on_Strike_runs);
            strikerBallsPlayed=strikerBallsPlayed+1;



                   //................... local db .....................

            ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                    set(0,Integer.toString(strikerRuns));
            ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                    set(3,Integer.toString(strikerBallsPlayed));


            if(parseInt(on_Strike_runs)==6)
            {
                strikerSixes=strikerSixes+1;
                ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                        set(2,Integer.toString(strikerSixes));

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t1).
                        child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                        child("sixes").setValue(Integer.toString(strikerSixes));


            }

            if (parseInt(on_Strike_runs)==4) {
                strikerFours = strikerFours + 1;
                ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                        set(1, Integer.toString(strikerFours));

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t1).
                        child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                        child("fours").setValue(Integer.toString(strikerFours));


            }


                  //................. firebase ........................

            mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t1).
                    child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                    child("runs").setValue(Integer.toString(strikerRuns));

            mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t1).
                    child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                    child("balls").setValue(Integer.toString(strikerBallsPlayed));





        // ......................... </team 1 score ++> ...........................................


        } else {


        // ......................... team 2 score ++ ...........................................


            int cScore = parseInt(((DataEntryActivity) getActivity()).t2s);
            cScore = cScore + parseInt(on_Strike_runs);
            ((DataEntryActivity) getActivity()).t2s = Integer.toString(cScore);

            mRef.child("ongoing").child("Cricket").child(((DataEntryActivity) getActivity()).
                    mat_name).child("team2Score").setValue(cScore);



            // ....................... onstrike score ++ ......................................

            ArrayList<String> striker_details=((DataEntryActivity)getActivity())
                    .completed_batsmen.get(on_Strike);

            int strikerRuns= parseInt(striker_details.get(0));
            int strikerSixes= parseInt(striker_details.get(2));
            int strikerFours= parseInt(striker_details.get(1));
            int strikerBallsPlayed= parseInt(striker_details.get(3));

            strikerRuns= strikerRuns + parseInt(on_Strike_runs);
            strikerBallsPlayed=strikerBallsPlayed+1;



            //................... local db .....................

            ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                    set(0,Integer.toString(strikerRuns));
            ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                    set(3,Integer.toString(strikerBallsPlayed));


            if(parseInt(on_Strike_runs)==6)
            {
                strikerSixes=strikerSixes+1;
                ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                        set(2,Integer.toString(strikerSixes));

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t2).
                        child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                        child("sixes").setValue(Integer.toString(strikerSixes));


            }

            if (parseInt(on_Strike_runs)==4) {
                strikerFours = strikerFours + 1;
                ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                        set(1, Integer.toString(strikerFours));

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t2).
                        child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                        child("fours").setValue(Integer.toString(strikerFours));

            }


            //................. firebase ........................

            mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t2).
                    child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                    child("runs").setValue(Integer.toString(strikerRuns));

            mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t2).
                    child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                    child("balls").setValue(Integer.toString(strikerBallsPlayed));


        }




        if(Extras.toLowerCase().equals("byes"))
        {
            if(((DataEntryActivity) getActivity()).t1_status.equals("batting")) {

                int bye = Integer.parseInt(byes.getText().toString());
                int score = Integer.parseInt(((DataEntryActivity) getActivity()).t1s);
                score = score + bye;
                ((DataEntryActivity) getActivity()).t1s = Integer.toString(score);
                mRef.child("ongoing").child("Cricket").
                        child(((DataEntryActivity) getActivity()).mat_name).child("team1Score").
                        setValue(Integer.toString(score));

            }
            else {

                int bye = Integer.parseInt(byes.getText().toString());
                int score = Integer.parseInt(((DataEntryActivity) getActivity()).t2s);
                score = score + bye;
                ((DataEntryActivity) getActivity()).t2s = Integer.toString(score);
                mRef.child("ongoing").child("Cricket").
                        child(((DataEntryActivity) getActivity()).mat_name).child("team2Score").
                        setValue(Integer.toString(score));

            }

            if(Extras.toLowerCase().equals("over throw")){

                if(((DataEntryActivity) getActivity()).t1_status.equals("batting")) {
                    ArrayList<String> striker_details = ((DataEntryActivity) getActivity())
                            .completed_batsmen.get(on_Strike);

                    int strikerRuns = parseInt(striker_details.get(0));
                    strikerRuns = strikerRuns + parseInt(byes.getText().toString());

                    ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                            set(0, Integer.toString(strikerRuns));

                    mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t1).
                            child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                            child("runs").setValue(Integer.toString(strikerRuns));
                }
                else {
                    ArrayList<String> striker_details = ((DataEntryActivity) getActivity())
                            .completed_batsmen.get(on_Strike);

                    int strikerRuns = parseInt(striker_details.get(0));
                    strikerRuns = strikerRuns + parseInt(byes.getText().toString());

                    ((DataEntryActivity) getActivity()).completed_batsmen.get(on_Strike).
                            set(0, Integer.toString(strikerRuns));

                    mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t2).
                            child(((DataEntryActivity) getActivity()).mat_name).child(on_Strike).
                            child("runs").setValue(Integer.toString(strikerRuns));

                }
            }

            if(!ball_Result.equals("none"))
            {
                if(ball_Result.toLowerCase().equals("bowled"))
                {
                    //TODO: design logic....

                    if(((DataEntryActivity)getActivity()).t1_status.equals("batting"))
                    {
                        ArrayList<String> details=((DataEntryActivity)getActivity())
                                .completed_bowlers
                                .get(((DataEntryActivity) getActivity()).curr_bowler);

                        String overs=details.get(0);
                        int wickets=Integer.parseInt(details.get(1));
                        wickets=wickets+1;


                        ArrayList<String> updated_details=new ArrayList<>();
                        updated_details.add(overs);
                        updated_details.add(Integer.toString(wickets));



                        //Updating wickets in bowler field

                        ((DataEntryActivity)getActivity()).completed_bowlers
                              .put(((DataEntryActivity) getActivity()).curr_bowler,updated_details);

                        mRef.child("bowlers").child(((DataEntryActivity) getActivity()).t2)
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child(((DataEntryActivity) getActivity()).curr_bowler)
                                .child("wickets").setValue(((DataEntryActivity)getActivity())
                                .completed_bowlers
                                .get(((DataEntryActivity) getActivity()).curr_bowler).get(1));

                        int tot_wickets=Integer
                                .parseInt(((DataEntryActivity)getActivity()).t1_wickets);

                        tot_wickets=tot_wickets+1;

                        ((DataEntryActivity)getActivity()).t1_wickets=Integer.toString(tot_wickets);

                        mRef.child("ongoing").child("Cricket")
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child("team1Wickets")
                                .setValue(((DataEntryActivity)getActivity()).t1_wickets);


                    }
                    else {

                        ArrayList<String> details=((DataEntryActivity)getActivity())
                                .completed_bowlers
                                .get(((DataEntryActivity) getActivity()).curr_bowler);

                        String overs=details.get(0);
                        int wickets=Integer.parseInt(details.get(1));
                        wickets=wickets+1;


                        ArrayList<String> updated_details=new ArrayList<>();
                        updated_details.add(overs);
                        updated_details.add(Integer.toString(wickets));



                        ((DataEntryActivity)getActivity()).completed_bowlers
                                .put(((DataEntryActivity) getActivity())
                                        .curr_bowler,updated_details);

                        mRef.child("bowlers").child(((DataEntryActivity) getActivity()).t1)
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child(((DataEntryActivity) getActivity()).curr_bowler)
                                .child("wickets").setValue(((DataEntryActivity)getActivity())
                                .completed_bowlers
                                .get(((DataEntryActivity) getActivity()).curr_bowler).get(1));

                        int tot_wickets=Integer
                                .parseInt(((DataEntryActivity)getActivity()).t2_wickets);

                        tot_wickets=tot_wickets+1;

                        ((DataEntryActivity)getActivity()).t2_wickets=Integer.toString(tot_wickets);

                        mRef.child("ongoing").child("Cricket")
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child("team2Wickets")
                                .setValue(((DataEntryActivity)getActivity()).t2_wickets);

                    }

                }
                else if(ball_Result.toLowerCase().equals("caught out"))
                {


                    if(((DataEntryActivity)getActivity()).t1_status.equals("batting"))
                    {
                        ArrayList<String> details=((DataEntryActivity)getActivity())
                                .completed_bowlers
                                .get(((DataEntryActivity) getActivity()).curr_bowler);

                        String overs=details.get(0);
                        int wickets=Integer.parseInt(details.get(1));
                        wickets=wickets+1;


                        ArrayList<String> updated_details=new ArrayList<>();
                        updated_details.add(overs);
                        updated_details.add(Integer.toString(wickets));



                        //Updating wickets in bowler field

                        ((DataEntryActivity)getActivity()).completed_bowlers
                                .put(((DataEntryActivity) getActivity()).curr_bowler,updated_details);

                        mRef.child("bowlers").child(((DataEntryActivity) getActivity()).t2)
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child(((DataEntryActivity) getActivity()).curr_bowler)
                                .child("wickets").setValue(((DataEntryActivity)getActivity())
                                .completed_bowlers
                                .get(((DataEntryActivity) getActivity()).curr_bowler).get(1));

                        int tot_wickets=Integer
                                .parseInt(((DataEntryActivity)getActivity()).t1_wickets);

                        tot_wickets=tot_wickets+1;

                        ((DataEntryActivity)getActivity()).t1_wickets=Integer.toString(tot_wickets);

                        mRef.child("ongoing").child("Cricket")
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child("team1Wickets")
                                .setValue(((DataEntryActivity)getActivity()).t1_wickets);


                    }
                    else {

                        ArrayList<String> details=((DataEntryActivity)getActivity())
                                .completed_bowlers
                                .get(((DataEntryActivity) getActivity()).curr_bowler);

                        String overs=details.get(0);
                        int wickets=Integer.parseInt(details.get(1));
                        wickets=wickets+1;


                        ArrayList<String> updated_details=new ArrayList<>();
                        updated_details.add(overs);
                        updated_details.add(Integer.toString(wickets));



                        ((DataEntryActivity)getActivity()).completed_bowlers
                                .put(((DataEntryActivity) getActivity())
                                        .curr_bowler,updated_details);

                        mRef.child("bowlers").child(((DataEntryActivity) getActivity()).t1)
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child(((DataEntryActivity) getActivity()).curr_bowler)
                                .child("wickets").setValue(((DataEntryActivity)getActivity())
                                .completed_bowlers
                                .get(((DataEntryActivity) getActivity()).curr_bowler).get(1));

                        int tot_wickets=Integer
                                .parseInt(((DataEntryActivity)getActivity()).t2_wickets);

                        tot_wickets=tot_wickets+1;

                        ((DataEntryActivity)getActivity()).t2_wickets=Integer.toString(tot_wickets);

                        mRef.child("ongoing").child("Cricket")
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child("team2Wickets")
                                .setValue(((DataEntryActivity)getActivity()).t2_wickets);

                    }


                    //TODO: think something for caught by implementation

                }
                else if(ball_Result.toLowerCase().equals("run out"))
                {


                    if(((DataEntryActivity)getActivity()).t1_status.equals("batting"))
                    {

                        int tot_wickets=Integer
                                .parseInt(((DataEntryActivity)getActivity()).t1_wickets);

                        tot_wickets=tot_wickets+1;

                        ((DataEntryActivity)getActivity()).t1_wickets=Integer.toString(tot_wickets);

                        mRef.child("ongoing").child("Cricket")
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child("team1Wickets")
                                .setValue(((DataEntryActivity)getActivity()).t1_wickets);

                    }
                    else
                    {
                        int tot_wickets=Integer
                                .parseInt(((DataEntryActivity)getActivity()).t2_wickets);

                        tot_wickets=tot_wickets+1;

                        ((DataEntryActivity)getActivity()).t2_wickets=Integer.toString(tot_wickets);

                        mRef.child("ongoing").child("Cricket")
                                .child(((DataEntryActivity)getActivity()).mat_name)
                                .child("team2Wickets")
                                .setValue(((DataEntryActivity)getActivity()).t2_wickets);

                    }

                    //TODO: think something for run out by implementation


                }

            }


        }


        if(((DataEntryActivity)getActivity()).curr_ball.equals("6"))
        {
            int over=Integer.parseInt(((DataEntryActivity)getActivity()).curr_over);
            over=over+1;
            ((DataEntryActivity) getActivity()).curr_over=Integer.toString(over);

            mRef.child("ongoing").child("Cricket").
                    child(((DataEntryActivity) getActivity()).mat_name).child("curr_over").
                    setValue(Integer.toString(over));

            if(((DataEntryActivity)getActivity()).curr_over
                    .equals(((DataEntryActivity)getActivity()).tovers))
            {
                ((DataEntryActivity)getActivity())
                        .replaceFragments(DataEntryInningsSummary.class,false,null);
            }
            else {
                ((DataEntryActivity)getActivity())
                        .replaceFragments(DataEntryCricket3.class,false,null);
            }
        }


        if(Extras.toLowerCase().equals("none") || Extras.toLowerCase().equals("byes")
                || Extras.toLowerCase().equals("over throw"))
        {
            int ball=Integer.parseInt(((DataEntryActivity) getActivity()).curr_ball);
            ball=ball+1;
            ((DataEntryActivity) getActivity()).curr_ball=Integer.toString(ball);

            mRef.child("ongoing").child("Cricket").
                    child(((DataEntryActivity) getActivity()).mat_name).child("curr_ball").
                    setValue(Integer.toString(ball));
        }


        Toast.makeText(getContext(),"Data Updated",Toast.LENGTH_SHORT).show();

        ((DataEntryActivity)getActivity()).replaceFragments(DataEntryOversMain.class,false,null);


        //TODO: faulty code in on item select spinners



    }


            @Override
            public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){
                // i is position

                switch (adapterView.getId()) {
                    case R.id.on_strike_bats_overs_main:
                        if (i > 0)
                            on_Strike = onStrike.getItemAtPosition(i).toString();
                        break;
                    case R.id.other_bats_overs_main:
                        if (i > 0) {

                            other_Batsmen = otherBatsmen.getItemAtPosition(i).toString();
                        }
                        break;
                    case R.id.runs_overs_main:
                        if (i > 0)
                            on_Strike_runs = onStrikeRuns.getItemAtPosition(i).toString();
                        break;
                    case R.id.extras_overs_main:
                        if (i > 0) {
                            Extras = extras.getItemAtPosition(i).toString();
                            if (Extras.toLowerCase().equals("byes")) {
                                    byes.setEnabled(true);
                            } else {
                                byes.setEnabled(false);
                            }
                        }
                        break;

                    case R.id.wicket_by_overs_main:
                        if (i > 0)
                            wicket_by = wicketBy.getItemAtPosition(i).toString();
                        break;
                    case R.id.ball_result_overs_main:
                        if (i > 0) {
                                ball_Result = ballResult.getItemAtPosition(i).toString();
                                if (ball_Result.toLowerCase().equals("run out")) {
                                    runout.setEnabled(true);

                                    ArrayList<String> batsmen = new ArrayList<>();
                                    batsmen.add("Select Batsmen (Run Out)");
                                    batsmen.add(on_Strike);
                                    batsmen.add(other_Batsmen);
                                    ArrayAdapter<String> run_out = new ArrayAdapter<>(getContext(),
                                            android.R.layout.simple_spinner_item, batsmen);
                                    run_out.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    runout.setAdapter(run_out);

                                } else {
                                    runout.setEnabled(false);
                                }
                                if (ball_Result.toLowerCase().equals("caught out")
                                    || ball_Result.toLowerCase().equals("run out")) {

                                        wicketBy.setEnabled(true);
                                } else {
                                    wicketBy.setEnabled(false);
                                }
                        }
                        break;
                    case R.id.run_out_overs_main:
                        if (i > 0)
                            run_out = runout.getItemAtPosition(i).toString();
                        break;
                }
            }

            @Override
            public void onNothingSelected (AdapterView < ? > adapterView) {

            }



}
