package osoble.bloodhero.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import osoble.bloodhero.Fragments.AboutFragment;
import osoble.bloodhero.Fragments.BloodBankAppointmentFragment;
import osoble.bloodhero.Fragments.HomeFragment;
import osoble.bloodhero.Fragments.LearnFragment;
import osoble.bloodhero.Fragments.ProfileFragment;
import osoble.bloodhero.Fragments.SearchFragment;
import osoble.bloodhero.R;

public class HomeActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView nav;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        configureToolBar();
        configureDrawerNavigation();
        configureFloatingActionButton();

        changeFragment(new HomeFragment());

    }

    public void configureToolBar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void configureDrawerNavigation(){
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        nav = (NavigationView) findViewById(R.id.navigationView);
        fragmentManager = getSupportFragmentManager();

        nav.getMenu().findItem(R.id.nav_home).setChecked(true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        break;
                    case R.id.nav_profile:
                        changeFragment(new ProfileFragment());
                        break;
                    case R.id.nav_donor:
                        changeFragment(new SearchFragment());
                        break;

                    case R.id.nav_appointment:
                        changeFragment(new BloodBankAppointmentFragment());
                        break;

                    case R.id.nav_Learn:
                        changeFragment(new LearnFragment());
                        break;

                    case R.id.nav_about:
                        changeFragment(new AboutFragment());
                        break;

                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                }

                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        fragmentHolder(new HomeFragment());
    }

    public void configureFloatingActionButton(){
        FloatingActionButton setAppointment = findViewById(R.id.action_a);
        setAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new BloodBankAppointmentFragment());
            }
        });

        FloatingActionButton searchDonors = findViewById(R.id.action_b);
        searchDonors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new SearchFragment());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        if (item.getItemId() == R.id.home_profile){
            changeFragment(new ProfileFragment());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setTitle("Exit App")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            dialogInterface.dismiss();
                            moveTaskToBack(true);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }

    public void changeFragment(Fragment f){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.right_out, R.anim.right_enter, R.anim.right_out)
                .replace(R.id.home_activity_layout, f)
                .addToBackStack("HomeActivity");

        fragmentTransaction.commit();
    }

    public void fragmentHolder(Fragment f){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.homeframe, f);
    }
}
