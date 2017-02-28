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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import red.shaurya2k17.Admin.DataEntryActivity;
import red.shaurya2k17.R;

import static red.shaurya2k17.R.id.toss_won_pref_dec_2;


public class DataEntryCricket2 extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner toss_won;
    Spinner toss_won_pref;
    String tw;
    String twp;


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


        String parray=((DataEntryActivity)getActivity()).t1+"_Cricket_Players";
        Toast.makeText(getContext(),parray,Toast.LENGTH_LONG).show();

        ArrayList<String> teams=new ArrayList<>();
        teams.add(deflt);
        teams.add(((DataEntryActivity)getActivity()).t1);
        teams.add(((DataEntryActivity)getActivity()).t2);

        toss_won = (Spinner) view.findViewById(R.id.toss_won_dec_2);
        ArrayAdapter<String> adapter_toss_won = new ArrayAdapter<>(getContext(),
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


        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Save Confirmation");
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setMessage("Do you want to Save this Data");
        alertDialog.setIcon(R.drawable.ic_save_teal_500_48dp);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

        mRef.child("Cricket").child(((DataEntryActivity)getActivity()).mat_name)
                .child("tossWon").setValue(tw);
        mRef.child("Cricket").child(((DataEntryActivity)getActivity()).mat_name)
                .child("tossWonPref").setValue(twp);


        ((DataEntryActivity)getActivity()).toss_won=tw;
        ((DataEntryActivity)getActivity()).tosswon_pref=twp;
        if(((DataEntryActivity)getActivity()).toss_won.
                equals(((DataEntryActivity)getActivity()).t1))
        {
            ((DataEntryActivity)getActivity()).t1_status=twp;
            if(twp.equals("batting"))
            ((DataEntryActivity)getActivity()).t2_status="bowling";
            else
                ((DataEntryActivity)getActivity()).t2_status="batting";
        }
        else
        {
            ((DataEntryActivity)getActivity()).t2_status=twp;
            if(twp.equals("batting"))
                ((DataEntryActivity)getActivity()).t1_status="bowling";
            else
                ((DataEntryActivity)getActivity()).t1_status="batting";

        }



        if( ((DataEntryActivity)getActivity()).t1_status.equals("batting"))
        {
            ((DataEntryActivity)getActivity()).t1s="0";
        }
        else
        {
            ((DataEntryActivity)getActivity()).t2s="0";
        }

         ((DataEntryActivity)getActivity()).replaceFragments(DataEntryCricket3.class,false,null);


       }
      });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"No",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.cancel();}
      });
        alertDialog.show();

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
