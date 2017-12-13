package osoble.bloodhero.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import osoble.bloodhero.Fragments.LoginFragment;
import osoble.bloodhero.R;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(this, HomeActivity.class));
        }
        else{
            changeFragment(new LoginFragment());
        }
    }

    public void changeFragment(Fragment f){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.right_out, R.anim.right_enter, R.anim.right_out)
                .replace(R.id.mainframe, f);

        fragmentTransaction.commit();
    }
}