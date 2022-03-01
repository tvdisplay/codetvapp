package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.Prefrences;
import com.cintaxedge.tutoring.R;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    EditText etemail,etmobile;
    TextView etname;
    Button update;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();

            }
        });
    }

    public void setData(){
        etname.setText(Prefrences.getName(getApplicationContext()));
        etemail.setText(Prefrences.getEmail(getApplicationContext()));
        etmobile.setText(Prefrences.getMobile(getApplicationContext()));
    }
    public void init(){
        etname = findViewById(R.id.etname);
        etemail = findViewById(R.id.etemail);
        etmobile = findViewById(R.id.etmobile);
        update = findViewById(R.id.submit);
        progressBar = findViewById(R.id.pbar);
        setData();
    }

    public void validate(){
        String  useremail = etemail.getText().toString();
        String usermobile = etmobile.getText().toString();


        if (useremail.isEmpty()) {
            etemail.setError(getString(R.string.input_error_email));
            etemail.requestFocus();
            return;
        }

        if (usermobile.isEmpty()){
            etmobile.setError(getString(R.string.input_error_mobile));
            etmobile.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        WebAPI.updateProfile(getApplicationContext(),useremail,usermobile,(status,err)->{
            if (status.equals("true")){
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Prefrences.setEmail(getApplicationContext(),useremail);
                        Prefrences.setMobile(getApplicationContext(),usermobile);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, err, Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                ProfileActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, err, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}