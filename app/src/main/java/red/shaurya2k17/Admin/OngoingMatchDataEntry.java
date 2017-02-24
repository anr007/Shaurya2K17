package red.shaurya2k17.Admin;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import red.shaurya2k17.Match;
import red.shaurya2k17.R;


public class OngoingMatchDataEntry extends Fragment implements AdapterView.OnItemSelectedListener {


    EditText win;
    EditText t1s;
    EditText mat_nam;
    EditText t2s;
    EditText com;
    Button s;
    Button u;
    Button f;
    Button e;
    Spinner cat;
    String cate;
    Spinner t1;
    Spinner t2;
    String team1;
    String team2;

    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseAuth mFirebaseAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ongoing_match_data_entry, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("ongoing");





        cat = (Spinner) view.findViewById(R.id.cat_2team);

        ArrayAdapter<CharSequence> adapter_cat = ArrayAdapter.createFromResource(getContext(),
                R.array.categories_2teams, android.R.layout.simple_spinner_item);
        adapter_cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cat.setAdapter(adapter_cat);
        cat.setOnItemSelectedListener(this);


        t1 = (Spinner) view.findViewById(R.id.t1);
        ArrayAdapter<CharSequence> adapter_team1 = ArrayAdapter.createFromResource(getContext(),
                R.array.Team1, android.R.layout.simple_spinner_item);
        adapter_team1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        t1.setAdapter(adapter_team1);
        t1.setOnItemSelectedListener(this);


        t2 = (Spinner) view.findViewById(R.id.t2);
        ArrayAdapter<CharSequence> adapter_team2 = ArrayAdapter.createFromResource(getContext(),
                R.array.Team2, android.R.layout.simple_spinner_item);
        adapter_team2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        t2.setAdapter(adapter_team2);
        t2.setOnItemSelectedListener(this);




        mat_nam = (EditText) view.findViewById(R.id.match_nam);
        mat_nam.requestFocus();
        win = (EditText) view.findViewById(R.id.win);
        win.setEnabled(false);
        t1s = (EditText) view.findViewById(R.id.t1_Score);
        t1s.setEnabled(false);
        t2s = (EditText) view.findViewById(R.id.t2_Score);
        t2s.setEnabled(false);
        com = (EditText) view.findViewById(R.id.comm);
        com.setEnabled(false);

        s = (Button) view.findViewById(R.id.s);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        u = (Button)view.findViewById(R.id.update_ode);
        u.setEnabled(false);
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        f=(Button) view.findViewById(R.id.finish_ode);
        f.setEnabled(false);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        e=(Button)view.findViewById(R.id.edit_ode);
        e.setEnabled(false);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });




    }


    public void save()
    {


        Match match=new Match();
        match.setMat_nam(mat_nam.getText().toString());
        match.setT1(team1);
        match.setT2(team2);
        match.setT1s("");
        match.setT2s("");
        match.setCom("");
        match.setWin("");


        mRef.child(cate).child(mat_nam.getText().toString()).setValue(match);

        e.setEnabled(true);
        u.setEnabled(true);
        mat_nam.setEnabled(false);
        t1.setEnabled(false);
        t2.setEnabled(false);
        cat.setEnabled(false);
        t1s.setEnabled(true);
        t2s.setEnabled(true);
        win.setEnabled(true);
        com.setEnabled(true);



        /*
        mRef.child(mat_nam.getText().toString()).child("Team:1").setValue();
        mRef.();
        mRef.child("Team:2").setValue(t2.getText().toString());
        mRef.child("Winner").setValue(win.getText().toString());
        mRef.child("Team:1-Score").setValue(t1s.getText().toString());
        mRef.child("Team:2-Score").setValue(t2s.getText().toString());
        mRef.child("Comments").setValue(com.getText().toString());
        */
    }


    private void edit()
    {

        mRef.child(cate).child(mat_nam.getText().toString()).removeValue();

        mat_nam.setEnabled(true);
        t1.setEnabled(true);
        t2.setEnabled(true);
        cat.setEnabled(true);
        t1s.setEnabled(false);
        t2s.setEnabled(false);
        win.setEnabled(false);
        com.setEnabled(false);

    }




    private void update()
    {
        Match match=new Match();
        match.setMat_nam(mat_nam.getText().toString());
        match.setT1(team1);
        match.setT2(team2);
        match.setT1s(t1s.getText().toString());
        match.setT2s(t2s.getText().toString());
        match.setCom(com.getText().toString());
        match.setWin("");

        mRef.child(cate).child(mat_nam.getText().toString()).setValue(match);
        f.setEnabled(true);


    }

    private void finish()
    {
        Match match=new Match();
        match.setMat_nam(mat_nam.getText().toString());
        match.setT1(team1);
        match.setT2(team2);
        match.setT1s(t1s.getText().toString());
        match.setT2s(t2s.getText().toString());
        match.setCom(com.getText().toString());
        match.setWin(win.getText().toString());

        mRef.child(cate).child(mat_nam.getText().toString()).removeValue();

        mRef = database.getReference("matches");

        mRef.child(cate).child(mat_nam.getText().toString()).setValue(match);

        mRef.getRoot().child("stats").child(match.getWin())
                .child("won")
                .child(match.getMat_nam()).setValue(cate);
        if(match.getWin().equals(match.getT1()))
        {
            mRef.getRoot().child("stats").child(match.getT2())
                    .child("lost")
                    .child(match.getMat_nam()).setValue(cate);
        }
        else {
            mRef.getRoot().child("stats").child(match.getT1())
                    .child("lost")
                    .child(match.getMat_nam()).setValue(cate);
        }

        t1s.setEnabled(false);
        t2s.setEnabled(false);
        win.setEnabled(false);
        com.setEnabled(false);

        Toast.makeText(getContext(),"Entry Success",Toast.LENGTH_SHORT).show();

    }





    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // i is position

        switch(adapterView.getId()){
            case R.id.cat_2team:
                if(i>0)
                    cate=cat.getItemAtPosition(i).toString();
                break;
            case R.id.t1:
                if(i>0)
                    team1=t1.getItemAtPosition(i).toString();
                break;
            case R.id.t2:
                if(i>0)
                    team2=t2.getItemAtPosition(i).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }


}


