package red.shaurya2k17.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import red.shaurya2k17.Home;
import red.shaurya2k17.R;


public class AdminHomeFragment extends Fragment {


    Button all_events_admin;
    Button ongoing_data_entry;
    Button data_entry;
    Button data_entry_cric;
    Button Logout;
    FirebaseAuth mFirebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        all_events_admin=(Button)view.findViewById(R.id.all_events_but);
        ongoing_data_entry=(Button)view.findViewById(R.id.ongoing_data_entry_but);
        Logout=(Button)view.findViewById(R.id.Logout_but);
        data_entry=(Button)view.findViewById(R.id.data_entry_but);
        data_entry_cric =(Button)view.findViewById(R.id.data_entry_cric_but);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();

        all_events_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((Home) getActivity()).replaceFragments(AdminAllEventsFragment.class,true);

            }
        });

        ongoing_data_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((Home) getActivity()).replaceFragments(OngoingMatchDataEntry.class,true);
            }
        });

        data_entry_cric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DataEntryActivity.class);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                Toast.makeText(getContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                closefragment();
                ((Home)getActivity()).frag_view.setVisibility(View.GONE);
                ((Home)getActivity()).home_view.setVisibility(View.VISIBLE);
                getActivity().setTitle("Home");

            }
        });

        data_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home)getActivity()).replaceFragments(MatchDataEntryFragment.class,true);

            }
        });




    }


    private void closefragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }



}
