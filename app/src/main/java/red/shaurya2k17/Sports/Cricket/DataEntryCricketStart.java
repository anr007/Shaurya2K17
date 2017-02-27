package red.shaurya2k17.Sports.Cricket;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import red.shaurya2k17.Admin.DataEntryActivity;
import red.shaurya2k17.R;


public class DataEntryCricketStart extends Fragment implements AdapterView.OnItemSelectedListener {



    EditText mat_nam;
    EditText overs;

    String cate;

    Spinner t1;
    Spinner t2;
    String team1;
    String team2;
    Button next;
    DatabaseReference mRef;
    FirebaseDatabase database;



    public DataEntryCricketStart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_entry_cricket_start, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cate = getArguments().getString("id");
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("ongoing"); // yet to be decided


        t1 = (Spinner) view.findViewById(R.id.t1_dec_st);
        ArrayAdapter<CharSequence> adapter_team1 = ArrayAdapter.createFromResource(getContext(),
                R.array.Team1, android.R.layout.simple_spinner_item);
        adapter_team1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        t1.setAdapter(adapter_team1);
        t1.setOnItemSelectedListener(this);


        t2 = (Spinner) view.findViewById(R.id.t2_dec_st);
        ArrayAdapter<CharSequence> adapter_team2 = ArrayAdapter.createFromResource(getContext(),
                R.array.Team2, android.R.layout.simple_spinner_item);
        adapter_team2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        t2.setAdapter(adapter_team2);
        t2.setOnItemSelectedListener(this);

        mat_nam = (EditText) view.findViewById(R.id.match_nam_dec_st);
        overs=(EditText) view.findViewById(R.id.tot_overs_dec_st);


        next=(Button)view.findViewById(R.id.next_start);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.next_start)
                {
                    next_start();
                }
            }
        });


    }


    void next_start()
    {
        // we have to first create a object in ongoing match
        // enable card view in home layout
        // ongoing match obj should contain entries like
        // overs completed , runs scored , wickets taken , runrate etc..
        // then replace with second frag

        //mRef.child(cate).child(mat_nam.getText().toString()).setValue(match);


            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Save Confirmation");
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.setMessage("Do you want to Save this Data");
            alertDialog.setIcon(R.drawable.ic_save_teal_500_48dp);
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    CMatch cMatch=new CMatch();

                    cMatch.setMatchName(mat_nam.getText().toString());
                    cMatch.setOvers(overs.getText().toString());
                    cMatch.setTeam1(team1);
                    cMatch.setTeam2(team2);
                    cMatch.setTeam2Score("");
                    cMatch.setTeam1Score("");
                    cMatch.setComments("");
                    cMatch.setTossWon("");
                    cMatch.setWinner("");
                    cMatch.setTossWonPref("");
                    mRef.child(cate).child(mat_nam.getText().toString()).setValue(cMatch);

                    ((DataEntryActivity)getActivity()).mat_name=mat_nam.getText().toString();
                    ((DataEntryActivity)getActivity()).t1=team1;
                    ((DataEntryActivity)getActivity()).t2=team2;
                    ((DataEntryActivity)getActivity()).tovers=overs.getText().toString();


                    //TODO: Think for other ways
                    mRef= database.getReference("summary");
                    mRef.child(mat_nam.getText().toString()).child("0").
                            child("Status").setValue("Match Started");



                    ((DataEntryActivity)getActivity()).
                            replaceFragments(DataEntryCricket2.class,false,null);


                }

            });
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"No",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    alertDialog.cancel();
                }
            });
            alertDialog.show();
        }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // i is position

        switch(adapterView.getId()){
            case R.id.t1_dec_st:
                if(i>0)
                    team1=t1.getItemAtPosition(i).toString();
                break;
            case R.id.t2_dec_st:
                if(i>0)
                    team2=t2.getItemAtPosition(i).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
