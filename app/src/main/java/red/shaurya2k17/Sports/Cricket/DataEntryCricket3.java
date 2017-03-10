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

import java.util.ArrayList;

import red.shaurya2k17.Admin.DataEntryActivity;
import red.shaurya2k17.R;


public class DataEntryCricket3 extends Fragment implements AdapterView.OnItemSelectedListener{

    Spinner cbowler;
    Spinner onStrike;
    Spinner otherBats;
    EditText currOver;
    EditText currScore;
    String bowler_name;
    String striker_name;
    String non_striker_name;
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
        mRef = database.getReference(); // yet to be decided

        currOver=(EditText)view.findViewById(R.id.curr_over_dec_3);
        currScore=(EditText)view.findViewById(R.id.curr_score_dec_3);

        currOver.setText( ((DataEntryActivity)getActivity()).curr_over);

        if( ((DataEntryActivity)getActivity()).t1_status.equals("batting"))
        {
            currScore.setText( ((DataEntryActivity)getActivity()).t1s);
        }
        else
        {
            currScore.setText( ((DataEntryActivity)getActivity()).t2s);
        }

        currScore.setEnabled(false);
        currOver.setEnabled(false);







        if(((DataEntryActivity)getActivity()).t1_status.equals("bowling"))
        {
            String parray=((DataEntryActivity)getActivity()).t1+"_Cricket_Players";
            int array_id =getContext().getResources().getIdentifier(parray,"array",
                    getContext().getPackageName());


            cbowler = (Spinner) view.findViewById(R.id.curr_bowler_dec_3);


            ArrayAdapter<CharSequence> adapter_cbowler = ArrayAdapter.createFromResource(getContext(),
                    array_id, android.R.layout.simple_spinner_item);

            adapter_cbowler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cbowler.setAdapter(adapter_cbowler);
            cbowler.setOnItemSelectedListener(this);

        } else
        {
            String parray=((DataEntryActivity)getActivity()).t2+"_Cricket_Players";
            int array_id=getContext().getResources().getIdentifier(parray,"array",
                    getContext().getPackageName());

            cbowler = (Spinner) view.findViewById(R.id.curr_bowler_dec_3);

            ArrayAdapter<CharSequence> adapter_cbowler = ArrayAdapter.createFromResource(getContext(),
                   array_id, android.R.layout.simple_spinner_item);

            adapter_cbowler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cbowler.setAdapter(adapter_cbowler);
            cbowler.setOnItemSelectedListener(this);

        }

        if(((DataEntryActivity)getActivity()).t1_status.equals("batting"))
        {

            String parray=((DataEntryActivity)getActivity()).t1+"_Cricket_Players";
            onStrike = (Spinner) view.findViewById(R.id.on_strike_bats_dec_3);
            ArrayAdapter<CharSequence> adapter_onStrike = ArrayAdapter.createFromResource(
                    getContext(), getContext().getResources().getIdentifier(parray,"array",
                            getContext().getPackageName()) , android.R.layout.simple_spinner_item);

            adapter_onStrike.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            onStrike.setAdapter(adapter_onStrike);
            onStrike.setOnItemSelectedListener(this);


            otherBats = (Spinner) view.findViewById(R.id.other_bats_dec_3);
            ArrayAdapter<CharSequence> adapter_otherBats = ArrayAdapter.createFromResource(
                    getContext(), getContext().getResources().getIdentifier(parray,"array",
                            getContext().getPackageName())     , android.R.layout.simple_spinner_item);


            adapter_otherBats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            otherBats.setAdapter(adapter_otherBats);
            otherBats.setOnItemSelectedListener(this);
        }else {

            String parray=((DataEntryActivity)getActivity()).t2+"_Cricket_Players";

            onStrike = (Spinner) view.findViewById(R.id.on_strike_bats_dec_3);
            ArrayAdapter<CharSequence> adapter_onStrike = ArrayAdapter.createFromResource(
                    getContext(), getContext().getResources().getIdentifier(parray,"array",
                            getContext().getPackageName()), android.R.layout.simple_spinner_item);



            adapter_onStrike.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            onStrike.setAdapter(adapter_onStrike);
            onStrike.setOnItemSelectedListener(this);


            otherBats = (Spinner) view.findViewById(R.id.other_bats_dec_3);
            ArrayAdapter<CharSequence> adapter_otherBats = ArrayAdapter.createFromResource(
                    getContext(), getContext().getResources().getIdentifier(parray,"array",
                            getContext().getPackageName())   , android.R.layout.simple_spinner_item);
            adapter_otherBats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            otherBats.setAdapter(adapter_otherBats);
            otherBats.setOnItemSelectedListener(this);
        }


        next=(Button)view.findViewById(R.id.next_3);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.next_3)
                {
                    next_3();
                }
            }
        });

    }


    void next_3()
    {
        // update the toss won details in ongoing match obj

        ((DataEntryActivity)getActivity()).curr_bowler=bowler_name;
        ((DataEntryActivity)getActivity()).curr_striker= striker_name;
        ((DataEntryActivity)getActivity()).curr_non_striker= non_striker_name;

        mRef.child("ongoing").child("Cricket").child( ((DataEntryActivity)getActivity()).mat_name)
                .child("curr_bowler").setValue(bowler_name);
        mRef.child("ongoing").child("Cricket").child( ((DataEntryActivity)getActivity()).mat_name)
                .child("curr_striker").setValue(striker_name);
        mRef.child("ongoing").child("Cricket").child( ((DataEntryActivity)getActivity()).mat_name)
                .child("curr_non_striker").setValue(non_striker_name);


        // Bowler details ................................................................

        if((((DataEntryActivity)getActivity()).completed_bowlers != null)
                &&((DataEntryActivity)getActivity()).completed_bowlers.containsKey(bowler_name))
        {
            if(((DataEntryActivity)getActivity()).t1_status.equals("batting"))
            {
                ArrayList<String> details=((DataEntryActivity)getActivity())
                        .completed_bowlers.get(bowler_name);

                String wickets=details.get(1);
                int overs=Integer.parseInt(details.get(0));
                 overs=overs+1;

                ArrayList<String> updated_details=new ArrayList<>();
                updated_details.add(Integer.toString(overs));
                updated_details.add(wickets);



                ((DataEntryActivity)getActivity())
                        .completed_bowlers.put(bowler_name,updated_details);

                mRef.child("bowlers").child(((DataEntryActivity) getActivity()).t2).
                        child(((DataEntryActivity)getActivity()).mat_name).child(bowler_name).
                        child("overs").setValue(((DataEntryActivity)getActivity())
                        .completed_bowlers.get(bowler_name));

            }
            else {

                ArrayList<String> details=((DataEntryActivity)getActivity())
                        .completed_bowlers.get(bowler_name);

                String wickets=details.get(1);
                int overs=Integer.parseInt(details.get(0));
                overs=overs+1;

                ArrayList<String> updated_details=new ArrayList<>();
                updated_details.add(Integer.toString(overs));
                updated_details.add(wickets);



                ((DataEntryActivity)getActivity())
                        .completed_bowlers.put(bowler_name,updated_details);
                mRef.child("bowlers").child(((DataEntryActivity) getActivity()).t1).
                        child(((DataEntryActivity)getActivity()).mat_name).child(bowler_name).
                        child("overs").setValue(((DataEntryActivity)getActivity())
                        .completed_bowlers.get(bowler_name).get(0));


            }

        }
        else {

            Bowler current_bowler=new Bowler();
            current_bowler.setOvers("1");
            current_bowler.setName(bowler_name);
            current_bowler.setWickets("0");


            ArrayList<String> details=new ArrayList<>();
            details.add("1");
            details.add("0");

            ((DataEntryActivity)getActivity())
                    .completed_bowlers.put(bowler_name,details);

            if(((DataEntryActivity)getActivity()).t1_status.equals("batting"))
            {

                current_bowler.setTeam(((DataEntryActivity) getActivity()).t2);

                mRef.child("bowlers").child(((DataEntryActivity) getActivity()).t2).
                        child(((DataEntryActivity)getActivity()).mat_name).child(bowler_name).
                        setValue(current_bowler);
            }
            else {

                current_bowler.setTeam(((DataEntryActivity) getActivity()).t1);

                mRef.child("bowlers").child(((DataEntryActivity) getActivity()).t1).
                        child(((DataEntryActivity)getActivity()).mat_name).child(bowler_name)
                        .setValue(current_bowler);

            }

        }

        // Striker details ................................................................

        if((((DataEntryActivity)getActivity()).completed_batsmen != null)
                &&!((DataEntryActivity)getActivity()).completed_batsmen.containsKey(striker_name))
        {
            if(((DataEntryActivity)getActivity()).t1_status.equals("batting")) {
                Batsmen current_striker = new Batsmen();
                current_striker.setTeam(((DataEntryActivity) getActivity()).t1);
                current_striker.setName(striker_name);
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
                                                         put(striker_name, striker_details);

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t1).
                        child(((DataEntryActivity) getActivity()).mat_name).child(striker_name).
                        setValue(current_striker);
            }
            else
            {
                Batsmen current_striker = new Batsmen();
                current_striker.setTeam(((DataEntryActivity) getActivity()).t2);
                current_striker.setName(striker_name);
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
                        put(striker_name, striker_details);

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t2).
                        child(((DataEntryActivity) getActivity()).mat_name).child(striker_name).
                        setValue(current_striker);

            }

        }

        // Non Striker details ................................................................


        if((((DataEntryActivity)getActivity()).completed_batsmen != null)
                &&!((DataEntryActivity)getActivity()).completed_batsmen.containsKey(non_striker_name))
        {
            if(((DataEntryActivity)getActivity()).t1_status.equals("batting")) {
                Batsmen current_non_striker = new Batsmen();
                current_non_striker.setTeam(((DataEntryActivity) getActivity()).t1);
                current_non_striker.setName(non_striker_name);
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
                        put(non_striker_name, non_striker_details);

                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t1).
                        child(((DataEntryActivity) getActivity()).mat_name).child(non_striker_name).
                        setValue(current_non_striker);
            }
            else
            {
                Batsmen current_non_striker = new Batsmen();
                current_non_striker.setTeam(((DataEntryActivity) getActivity()).t2);
                current_non_striker.setName(non_striker_name);
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
                        put(non_striker_name, non_striker_details);


                mRef.child("batsmen").child(((DataEntryActivity) getActivity()).t2).
                        child(((DataEntryActivity) getActivity()).mat_name).child(non_striker_name).
                        setValue(current_non_striker);

            }

        }

        //TODO: Check the working and think for more possible conditions

        ((DataEntryActivity)getActivity()).curr_ball="1";

        ((DataEntryActivity)getActivity()).replaceFragments(DataEntryOversMain.class,false,null);
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
                    striker_name =onStrike.getItemAtPosition(i).toString();
                break;
            case R.id.other_bats_dec_3:
                if(i>0)
                    non_striker_name =otherBats.getItemAtPosition(i).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
