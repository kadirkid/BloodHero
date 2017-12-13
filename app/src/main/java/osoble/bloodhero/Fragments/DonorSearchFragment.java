package osoble.bloodhero.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import osoble.bloodhero.Adapters.DonorAdapter;
import osoble.bloodhero.Models.User;
import osoble.bloodhero.R;


public class DonorSearchFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = DonorSearchFragment.class.getSimpleName();
//    public static String BLOODTYPE = "Blood Type";
//    public static String REGION = "Region";
//
//    private String bloodtype, region;
    private RecyclerView donorRecyclerView;
    private DonorAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    private ProgressDialog progress;
    private DividerItemDecoration mDividerItemDecoration;
    private SnapHelper snapHelper;
    private Toolbar searchToolBar;

    public DonorSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.donor_search, container, false);
        Log.i(TAG, "Was Opened");
        configureToolBar(view);

        //-------------------------------------Widgets--------------------------------------------//
        donorRecyclerView = view.findViewById(R.id.rDonorSearch);
        donorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progress = new ProgressDialog(getContext());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();

        //----------------------------------Database and Storage----------------------------------//
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.keepSynced(true);
        childRef = mDatabaseRef.child("User");

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

        //--------------------------------------Getting Listing Criteria--------------------------//
//        if(getArguments() != null){
//            bloodtype = getArguments().getString(BLOODTYPE);
//            region = getArguments().getString(REGION);
//
//            Query ref;
//            if(bloodtype.equals("Select Blood Type") && region.equals("Select Region")){
//                 ref = childRef.orderByChild("privacy").equalTo(true);
//            }else
//                ref = childRef.orderByChild("privacy").equalTo(true);
//
//            mAdapter = new DonorAdapter(User.class, R.layout.donor_row,
//                    DonorAdapter.DonorViewHolder.class, ref, getContext());
//        }
//        else{
//            mAdapter = new DonorAdapter(User.class, R.layout.donor_row,
//                    DonorAdapter.DonorViewHolder.class, childRef, getContext());
//        }

        //--------------------------------RecyclerView Setup--------------------------------------//
        Query ref = childRef.orderByChild("privacy").equalTo(true);
        mAdapter = new DonorAdapter(User.class, R.layout.donor_row,
                    DonorAdapter.DonorViewHolder.class, ref, getContext());
        mDividerItemDecoration = new DividerItemDecoration(
                donorRecyclerView.getContext(), LinearLayout.HORIZONTAL);
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(donorRecyclerView);
        donorRecyclerView.setAdapter(mAdapter);
        donorRecyclerView.setHasFixedSize(true);
        donorRecyclerView.addItemDecoration(mDividerItemDecoration);

        Log.i("-----------------------", "-----------------------");
        Log.i("User RV", "Complete");
        Log.i("-----------------------", "-----------------------");

        return view;
    }

    public void configureToolBar(View view){
        searchToolBar = view.findViewById(R.id.toolbar);
        searchToolBar.setTitle("Search Donors");
        searchToolBar.setTitleTextColor(Color.WHITE);
        searchToolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        searchToolBar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        getActivity().onBackPressed();
    }
}
