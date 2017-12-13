package osoble.bloodhero.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import osoble.bloodhero.Activities.HomeActivity;
import osoble.bloodhero.R;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText email, password;
    private Button loginButton;
    private ImageView noAccount;
    private FirebaseAuth auth;
    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;
    private FragmentTransaction fragmentTransaction;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login, container, false);

        email = view.findViewById(R.id.emailTextView);
        password = view.findViewById(R.id.passwordTextView);
        loginButton = view.findViewById(R.id.loginButton);
        noAccount = view.findViewById(R.id.noAccount);

        loginButton.setOnClickListener(this);
        noAccount.setOnClickListener(this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loggin in...");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();

        return view;
    }

    //------------------------------------------ON CLICK------------------------------------------//

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.loginButton:
                if(check()){
                    login();
                }
                break;

            case R.id.noAccount:
                changeFragment(new SignUpFragment());
                break;
        }
    }

    //------------------------------------------METHODS-------------------------------------------//

    public boolean check(){
        String emailstr = email.getText().toString().trim();
        String passwordstr = password.getText().toString().trim();

        if(!emailstr.contains("@")){
            Toast.makeText(getContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }else if (passwordstr.length() < 6){
            Toast.makeText(getContext(), "Minimum password length is 6.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    public void login(){
        String emailstr = email.getText().toString().trim();
        String passwordstr = password.getText().toString().trim();

        Log.i("------------------ ", "------------------");
        Log.i("Login Function ", "Accessed");
        Log.i("------------------ ", "------------------");
        progressDialog.show();
        auth.signInWithEmailAndPassword(emailstr, passwordstr)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();
                Log.i("------------------ ", "------------------");
                Log.i("OnComplete ", "Accessed");
                Log.i("------------------ ", "------------------");

                if(task.isSuccessful()){

                    Log.i("------------------ ", "------------------");
                    Log.i("OnComplete ", "Done");
                    Log.i("------------------ ", "------------------");
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }
                else{
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    Log.i("Login Failed ", task.toString());
                }
            }
        });
    }

    public void changeFragment(Fragment f){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.right_out, R.anim.right_enter, R.anim.right_out)
                .replace(R.id.mainframe, f);

        fragmentTransaction.commit();
    }
}
