package osoble.bloodhero.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import osoble.bloodhero.Activities.HomeActivity;
import osoble.bloodhero.Models.User;
import osoble.bloodhero.R;


public class SignUpFragment extends Fragment implements View.OnClickListener {
    public static final int GALLERY_INTENT = 2;
    private String[] bloodTypes, country;
    private ArrayAdapter<String> adapterB, adapterC;
    private Spinner bloodTypeSpinner, countrySpinner;
    private EditText password, email, confirmPassword, name;
    private CheckBox age;
    private Button signupButton, uploadImage;
    private ImageView haveAccount;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private DatabaseReference childref;
    private User user;
    private ProgressDialog progressDialog;
    private FirebaseUser firebaseUser;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sign_up, container, false);

        name = view.findViewById(R.id.nameSignUp);
        email = view.findViewById(R.id.emailSignUp);
        password = view.findViewById(R.id.passwordSignUp);
        confirmPassword = view.findViewById(R.id.confirmPasswordSignUp);
        age = view.findViewById(R.id.confirmAge);

        bloodTypeSpinner = view.findViewById(R.id.bloodTypeSpinner);
        countrySpinner = view.findViewById(R.id.countrySpinner);

        signupButton = view.findViewById(R.id.signupButton);
        uploadImage = view.findViewById(R.id.upload_image);
        haveAccount = view.findViewById(R.id.haveAccount);

        haveAccount.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        uploadImage.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("User");

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading Picture...");
        setupSpinner();

        signupButton.setOnClickListener(this);

        return view;
    }

    //------------------------------------------ON CLICK------------------------------------------//

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signupButton:
                if (check()) {
                    signup();
                }
                break;

            case R.id.haveAccount:
                changeFragment(new LoginFragment());
                break;
        }
    }

    //------------------------------------------METHODS-------------------------------------------//

    public void setupSpinner() {
        bloodTypes = new String[]{"Select Blood Type", "------------------------------", "O-", "O+", "A+",
                "A-", "B+", "B-", "AB+", "AB-"};
        adapterB = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                bloodTypes);
        bloodTypeSpinner.setAdapter(adapterB);

        country = new String[]{"Select Region", "------------------------------", "Sharjah", "Dubai", "Abu Dhabi",
                "Ras Al Khaimah", "Umm Al Quwain", "Fujairah", "Ajman"};
        adapterC = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,
                country);
        countrySpinner.setAdapter(adapterC);
    }

    private void signup() {
        final String namestr = name.getText().toString().trim();
        final String passwordstr = password.getText().toString().trim();
        final String emailstr = email.getText().toString().trim();
        final String blood = bloodTypeSpinner.getSelectedItem().toString();
        final String country_str = countrySpinner.getSelectedItem().toString();

        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(emailstr, passwordstr)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            firebaseUser = auth.getCurrentUser();
                            user = new User(namestr, passwordstr, emailstr, blood, firebaseUser.getUid(), country_str);
                            addToDatabase(user);
                        } else {
                            Toast.makeText(getContext(), "Sign Up failed " + task.getException()
                                    , Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

    }

    private void addToDatabase(User user) {
        firebaseUser = auth.getCurrentUser();
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        childref.child(firebaseUser.getUid()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Snackbar.make(getView(), "Login Successful", Snackbar.LENGTH_SHORT);
                            startActivity(new Intent(getActivity(), HomeActivity.class));
//                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "Failed to add User to the database",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean check() {
        String passwordstr = password.getText().toString().trim();
        String confirmstr = confirmPassword.getText().toString().trim();
        String emailstr = email.getText().toString().trim();

        if (!passwordstr.equals(confirmstr)) {
            Toast.makeText(getContext(), "Make sure the passwords match", Toast.LENGTH_SHORT)
                    .show();
            return false;
        } else if (!emailstr.contains("@")) {
            Toast.makeText(getContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!age.isChecked()) {
            Toast.makeText(getContext(), "Confirm your age by ticking the checkbox",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void changeFragment(Fragment f){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.right_out, R.anim.right_enter, R.anim.right_out)
                .replace(R.id.mainframe, f)
                .addToBackStack("SignUpFragment");

        fragmentTransaction.commit();
    }
}
