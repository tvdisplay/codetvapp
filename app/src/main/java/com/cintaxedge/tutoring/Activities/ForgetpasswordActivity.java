package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.R;

public class ForgetpasswordActivity extends AppCompatActivity {

    Button submit;
    ImageView backbtn;
    ProgressBar pbar;
    EditText emailet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        init();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
//                Intent intent = new Intent(ForgetpasswordActivity.this,LoginActivity.class);
//                startActivity(intent);
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void init(){
        submit = findViewById(R.id.submit);
        backbtn = findViewById(R.id.backimg);
        emailet = findViewById(R.id.etemail);
        pbar = findViewById(R.id.pbar);

    }

    public void validate(){
        String  useremail = emailet.getText().toString();
        if (useremail.isEmpty()) {
            emailet.setError(getString(R.string.input_error_email));
            emailet.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            emailet.setError(getString(R.string.input_error_email_invalid));
            emailet.requestFocus();
            return;
        }
        pbar.setVisibility(View.VISIBLE);

        WebAPI.forgotpassword(useremail,(status) -> {

            if (status.equals("A new password has beeen sent your email id.")){

                ForgetpasswordActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pbar.setVisibility(View.GONE);
                        Toast.makeText(ForgetpasswordActivity.this, status, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgetpasswordActivity.this, LoginActivity.class);
                        intent.putExtra("email",useremail);
                        startActivity(intent);
                    }
                });
            }else {
                ForgetpasswordActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pbar.setVisibility(View.GONE);
                        Toast.makeText(ForgetpasswordActivity.this, status, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }
}