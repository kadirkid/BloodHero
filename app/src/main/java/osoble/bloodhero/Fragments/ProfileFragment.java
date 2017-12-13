package osoble.bloodhero.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import osoble.bloodhero.Activities.MainActivity;
import osoble.bloodhero.Models.User;
import osoble.bloodhero.R;
import osoble.bloodhero.Utils.Utils;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    TextView name, email, bloodType;
    RadioButton publicButton, privateButton;
    ImageView profilePicture;
    Button update, logout;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference childref;
    private User user;
    private ProgressDialog progressDialog;
    private StorageReference mStorageReference;
    private StorageReference degreeRef;
    private Toolbar profileToolBar;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new Utils().getLoggedInUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile, container, false);
        configureToolBar(view);

        //------------------------------------Widgets---------------------------------------------//
        name = view.findViewById(R.id.profile_name);
        email = view.findViewById(R.id.profile_email);
        bloodType = view.findViewById(R.id.profile_blood);

        publicButton = view.findViewById(R.id.profile_public);
        privateButton = view.findViewById(R.id.profile_private);

        profilePicture = view.findViewById(R.id.profile_picture);

        logout = view.findViewById(R.id.profile_logout);
        logout.setOnClickListener(this);

        //---------------------------------Database and Storage-----------------------------------//
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        degreeRef = mStorageReference.child("User").child(firebaseUser.getUid()).child("/profile.jpg");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("User");

        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("---------------------", "---------------------");
                Log.i("Util OnDataChange", "Was called");

                user = dataSnapshot.child(firebaseUser.getUid()).getValue(User.class);
                Log.i("---------------------", "---------------------");
                Log.i("User name", user.getName());

                Log.i("---------------------", "---------------------");
                Log.i(user.getName(), user.getPassword());
                Log.i(user.getEmail(), user.getBloodType());
                String status = String.valueOf(user.isPrivacy());
                Log.i(status, "Status");
                Log.i("---------------------", "---------------------");

                name.setText(user.getName());
                email.setText(user.getEmail());
                bloodType.setText(user.getBloodType());

                Glide
                        .with(getActivity())
                        .using(new FirebaseImageLoader())
                        .load(degreeRef)
                        .into(profilePicture);

                if (user.isPrivacy()) {
                    privateButton.toggle();
                } else {
                    publicButton.toggle();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        childref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.i("---------------------", "---------------------");
//                Log.i("OnDataChange", "Called!!");
//                Log.i("---------------------", "---------------------");
//
//                user = dataSnapshot.child(firebaseUser.getUid()).getValue(User.class);
//
//                Log.i("---------------------", "---------------------");
//                Log.i(user.getName(), user.getPassword());
//                Log.i(user.getEmail(), user.getBloodType());
//                String status = String.valueOf(user.isPrivacy());
//                Log.i(status, "Status");
//                Log.i("---------------------", "---------------------");
//
//                name.setText(user.getName());
//                email.setText(user.getEmail());
//                bloodType.setText(user.getBloodType());
//
//                Glide
//                        .with(getActivity())
//                        .using(new FirebaseImageLoader())
//                        .load(degreeRef)
//                        .into(profilePicture);
//
//                if (user.isPrivacy()) {
//                    privateButton.toggle();
//                } else {
//                    publicButton.toggle();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        return view;
    }

    public void configureToolBar(View view){
        profileToolBar = view.findViewById(R.id.toolbar);
        profileToolBar.setTitle("Profile");
        profileToolBar.setTitleTextColor(Color.WHITE);
        profileToolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        profileToolBar.setNavigationOnClickListener(this);
    }

    //------------------------------------------METHODS-------------------------------------------//

    //------------------------------------------ON CLICK------------------------------------------//

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_logout:
                auth.signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            default:
                getActivity().onBackPressed();
                break;
        }
    }


    public void changeFragment(Fragment f) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.right_out, R.anim.right_enter, R.anim.right_out)
                .replace(R.id.profile_layout, f)
                .addToBackStack("Profile");

        fragmentTransaction.commit();
    }

}
