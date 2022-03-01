package com.cintaxedge.tutoring.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cintaxedge.tutoring.Activities.ui.alltests.AlltestsFragment;
import com.cintaxedge.tutoring.Activities.ui.home.HomeFragment;
import com.cintaxedge.tutoring.Activities.ui.results.ResultsFragment;
import com.cintaxedge.tutoring.Activities.ui.tutorrequest.TutorrequestFragment;
import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.Prefrences;
import com.cintaxedge.tutoring.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

public class BottomNavigaionAcivity extends AppCompatActivity {
    ImageView drawer;
    DrawerLayout drawerlyt;
    Toolbar toolbar;
    ActionBarDrawerToggle t;
    NavigationView nv;
    private static final String TAG = "BottomNavigaionAcivity";
    RelativeLayout homelyt, changepasslyt, subsnowlyt, profilelyt, resultslyt;
    RelativeLayout sharelyt, aboutuslyt, privacypolicylyt, termsconditionslyt, logoutlyt;
    static TextView titletv;
    TextView emailtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigaion_acivity);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        init();

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlyt.openDrawer(GravityCompat.START);
            }
        });

        homelyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerlyt.closeDrawers();
            }
        });
        changepasslyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BottomNavigaionAcivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        subsnowlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BottomNavigaionAcivity.this, SubscribeActivity.class);
                startActivity(intent);
            }
        });
        profilelyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BottomNavigaionAcivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        resultslyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BottomNavigaionAcivity.this, ResultsActivity.class);
                startActivity(intent);
            }
        });
        logoutlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(BottomNavigaionAcivity.this);
                alert.setTitle("Logout");
                alert.setMessage("Are you sure you want to logout?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Prefrences.logout(getApplicationContext());
                        Intent intent = new Intent(BottomNavigaionAcivity.this, LoginActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();



            }
        });
        termsconditionslyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BottomNavigaionAcivity.this, TermandConditionsActivity.class);
                intent.putExtra("from", "termandcondition");
                startActivity(intent);
            }
        });

        aboutuslyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BottomNavigaionAcivity.this, TermandConditionsActivity.class);
                intent.putExtra("from", "about");
                startActivity(intent);
            }
        });

        privacypolicylyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BottomNavigaionAcivity.this, TermandConditionsActivity.class);
                intent.putExtra("from", "privacypolicy");
                startActivity(intent);
            }
        });
        sharelyt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StringFormatInvalid")
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.invitation_title));
                    shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.invitation_text),
                            getApplicationContext().getPackageName()));
                    startActivity(Intent.createChooser(shareIntent, "Share using.."));
                } catch (Exception ignored) {
                }
            }
        });


//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_alltest, R.id.navigation_results,R.id.navigation_tutorrequest)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
     //    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }





    private void init() {

        drawer = findViewById(R.id.iv_drawer);
        drawerlyt = findViewById(R.id.container);
        nv = findViewById(R.id.sidenav_view);
        titletv = findViewById(R.id.titletext);

        homelyt = findViewById(R.id.homerltv);
        changepasslyt = findViewById(R.id.chngpassltv);
        subsnowlyt = findViewById(R.id.subcrltv);
        profilelyt = findViewById(R.id.profilerltv);
        resultslyt = findViewById(R.id.resultrltv);

        sharelyt = findViewById(R.id.sharerltv);
        aboutuslyt = findViewById(R.id.aboutusrltv);
        privacypolicylyt = findViewById(R.id.privacyrltv);
        termsconditionslyt = findViewById(R.id.termsrltv);
        logoutlyt = findViewById(R.id.logoutrltv);
        emailtext = findViewById(R.id.emailtext);

        emailtext.setText(Prefrences.getEmail(getApplicationContext()));

    }

    public static void setitle(String title) {
        titletv.setText(title);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;

                case R.id.navigation_alltest:
                    fragment = new AlltestsFragment();
                    break;

                case R.id.navigation_results:
                    fragment = new ResultsFragment();
                    break;
                case R.id.navigation_tutorrequest:
                    fragment = new TutorrequestFragment();
                    break;

            }

            return loadFragment(fragment);
        }

    };

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}