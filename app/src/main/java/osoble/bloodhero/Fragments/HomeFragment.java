package osoble.bloodhero.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import osoble.bloodhero.Adapters.AppointmentAdapter;
import osoble.bloodhero.Models.Appointment;
import osoble.bloodhero.R;


public class HomeFragment extends Fragment {
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private RecyclerView appointmentRecyclerView;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    private Query ref;
    private DividerItemDecoration mDividerItemDecoration;
    private SnapHelper snapHelper;
    private AppointmentAdapter mAdapter;
    private FirebaseUser firebaseUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //-------------------------------------Widgets--------------------------------------------//
        appointmentRecyclerView = view.findViewById(R.id.r_appointment);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        //---------------------------------------Database-----------------------------------------//

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.keepSynced(true);
        childRef = mDatabaseRef.child("Appointment").child(firebaseUser.getUid());
        ref = childRef.orderByChild("date").limitToFirst(10);

        Log.i("---------------------", "---------------------");
        Log.i("Home UserID", firebaseUser.getUid());
        Log.i("---------------------", "---------------------");

        childRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                progress.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //--------------------------------RecyclerView Setup--------------------------------------//

        mDividerItemDecoration = new DividerItemDecoration(
                appointmentRecyclerView.getContext(), LinearLayout.HORIZONTAL);

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(appointmentRecyclerView);
        mAdapter = new AppointmentAdapter(Appointment.class, R.layout.appointment_row,
                AppointmentAdapter.AppointmentViewHolder.class, ref, getContext());

        appointmentRecyclerView.setAdapter(mAdapter);
        appointmentRecyclerView.addItemDecoration(mDividerItemDecoration);

        return view;
    }
    
}
