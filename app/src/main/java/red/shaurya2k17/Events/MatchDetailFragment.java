package red.shaurya2k17.Events;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import red.shaurya2k17.Match;
import red.shaurya2k17.R;


public class MatchDetailFragment extends Fragment {

    TextView match_name_ip;
    TextView t1_ip;
    TextView t2_ip;
    TextView t1s_ip;
    TextView t2s_ip;
    TextView win_ip;
    TextView comm_ip;
    TextView t1_name;
    TextView t2_name;
    String cat;
    String mat_name;
    ProgressDialog mProgressDialog;
    FirebaseDatabase database;
    DatabaseReference mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_detail, container, false);



    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingThings(view);
        showProgressDialog("","Loading...");
    }

    private void settingThings(View view)
    {
        match_name_ip = (TextView) view.findViewById(R.id.match_name_ip);
        t1_ip = (TextView) view.findViewById(R.id.t1_ip);
        t1_name = (TextView) view.findViewById(R.id.t1_name_score);
        t2_ip = (TextView) view.findViewById(R.id.t2_ip);
        t2_name = (TextView) view.findViewById(R.id.t2_name_score);
        t1s_ip = (TextView) view.findViewById(R.id.t1_score_ip);
        t2s_ip = (TextView) view.findViewById(R.id.t2_score_ip);
        win_ip = (TextView) view.findViewById(R.id.win_ip);
        comm_ip = (TextView) view.findViewById(R.id.comment_ip);

        mat_name= getArguments().getString("mat_name");
        cat = getArguments().getString("id");
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("matches").child(cat).child(mat_name);



        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Match obj= dataSnapshot.getValue(Match.class);
                set_values(obj);
                hideProgressDialog();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }


    private void set_values(Match obj)
    {
        match_name_ip.setText(obj.getMat_nam());
        t1_ip.setText(obj.getT1());
        t2_ip.setText(obj.getT2());
        t1s_ip.setText(obj.getT1s());
        t2s_ip.setText(obj.getT2s());
        t1_name.setText(obj.getT1());
        t2_name.setText(obj.getT2());
        win_ip.setText(obj.getWin());
        comm_ip.setText(obj.getCom());

    }

    private void showProgressDialog(String title, String message) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.setMessage(message);
        else
            mProgressDialog = ProgressDialog.show(getContext(), title, message,true,true);
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


}

  /*

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        t = (TextView) view.findViewById(R.id.t);
        b = (Button) view.findViewById(R.id.b);
    }


    // This method is called when the fragment is no longer connected to the Activity
    // Any references saved in onAttach should be nulled out here to prevent memory leaks.

    @Override
    public void onDetach() {
        super.onDetach();
    }


    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    /*



    private void closefragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }


    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        return fragment;
    }




    /*public interface OnFragmentInteractionListener {

    }*/
