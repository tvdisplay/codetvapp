package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.cintaxedge.tutoring.Models.Prefrences;
import com.cintaxedge.tutoring.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Prefrences.isLoggedin(getApplicationContext())){


                    Intent i = new Intent(SplashActivity.this, BottomNavigaionAcivity.class);
                    startActivity(i);
                    finish();

                }else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }




            }
        }, 3000);
    }
}