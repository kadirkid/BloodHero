package osoble.bloodhero.Fragments;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

import osoble.bloodhero.Activities.HomeActivity;
import osoble.bloodhero.Adapters.BloodBankAdapter;
import osoble.bloodhero.Models.Appointment;
import osoble.bloodhero.Models.BloodBank;
import osoble.bloodhero.R;

public class BloodBankAppointmentFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = BloodBankAppointmentFragment.class.getSimpleName();

    private CalendarView mCalendarView;
    private RecyclerView bloodbankRecyclerView;
    private DividerItemDecoration mDividerItemDecoration;
    private BloodBankAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    private ProgressDialog progress;
    private SnapHelper snapHelper;
    private Button setAppointment;
    private Appointment appointment;
    private String date, time, bloodBankID, userID, bloodBankName;
    private Toolbar appoitnmentToolBar;

    public BloodBankAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.blood_bank_appointment, container, false);
        Log.i(TAG, "Was Opened");
        configureToolBar(view);

        date = time = bloodBankID = userID = bloodBankName = "";

        mCalendarView = view.findViewById(R.id.appointment_calendar);
        bloodbankRecyclerView = view.findViewById(R.id.r_blood_bank_search);
        bloodbankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, true));

        setAppointment = view.findViewById(R.id.set_appointment);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        setAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date.equals("") || time.equals(""))
                    Toast.makeText(getContext(), "Please select a Date and Time",
                            Toast.LENGTH_SHORT).show();
                else{
                    appointment = new Appointment(UUID.randomUUID().toString(), bloodBankID, bloodBankName
                            , date, time);

                    Log.i("BLOODBANK----", "-----BLOODBANK");
                    Log.i(appointment.getBloodbank_name(), appointment.getDate());
                    addToDatabase(appointment);
                }
            }
        });

        mCalendarView.setMinDate(mCalendarView.getDate());
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = Integer.toString(i2) + "/" + Integer.toString(i1) + "/" + Integer.toString(i);
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        time = Integer.toString(i) + ":" + Integer.toString(i1);
                        Log.i("Appointment time is: ", time);
                        Toast.makeText(getContext(), "Date and Time has been set",
                                Toast.LENGTH_SHORT).show();
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        progress = new ProgressDialog(getContext());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
//        progress.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.keepSynced(true);
        childRef = mDatabaseRef.child("Blood Bank");

        childRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progress.dismiss();
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
                bloodbankRecyclerView.getContext(), LinearLayout.HORIZONTAL);
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(bloodbankRecyclerView);
        mAdapter = new BloodBankAdapter(BloodBank.class, R.layout.blood_bank_row,
                BloodBankAdapter.BloodBankViewHolder.class, childRef, getContext());

        mAdapter.setCallback(new BloodBankAdapter.Callback() {
            @Override
            public void onItemClick(String id, String name) {
                bloodBankID = id;
                bloodBankName = name;
            }
        });
        bloodbankRecyclerView.setHasFixedSize(true);
        bloodbankRecyclerView.addItemDecoration(mDividerItemDecoration);
        bloodbankRecyclerView.setAdapter(mAdapter);


        return view;
    }

    public void configureToolBar(View view){
        appoitnmentToolBar = view.findViewById(R.id.toolbar);
        appoitnmentToolBar.setTitle("Schedule Appointment");
        appoitnmentToolBar.setTitleTextColor(Color.WHITE);
        appoitnmentToolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        appoitnmentToolBar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        getActivity().onBackPressed();
    }

    //------------------------------------------METHODS-------------------------------------------//

    private void addToDatabase(Appointment appointment) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Setting Appointment...");
        progressDialog.show();

        childRef = mDatabaseRef.child("Appointment").child(userID);
        childRef.child(appointment.getID()).setValue(appointment)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Appointment has been set",
                                    Toast.LENGTH_SHORT);
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }
                        else{
                            Toast.makeText(getContext(), "Failed to make Appointment",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
