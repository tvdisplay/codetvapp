package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.LoginResponse;
import com.cintaxedge.tutoring.Models.Prefrences;
import com.cintaxedge.tutoring.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    TextView register,forget;
    Button submit;
    EditText etemail, etpassword;
    RelativeLayout eyelyt;
    ImageView eyeimg, eyeoffimg;
    boolean showpassword = false;
    ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetpasswordActivity.class);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
//                Intent intent = new Intent(LoginActivity.this, BottomNavigaionAcivity.class);
//                startActivity(intent);
            }
        });
        eyelyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showpassword) {
                    etpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showpassword = false;
                    eyeoffimg.setVisibility(View.GONE);
                    eyeimg.setVisibility(View.VISIBLE);
                } else {
                    etpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showpassword = true;
                    eyeoffimg.setVisibility(View.VISIBLE);
                    eyeimg.setVisibility(View.GONE);
                }
            }
        });
    }

    public void init() {
        submit = findViewById(R.id.submit);
        register = findViewById(R.id.register);
        eyelyt = findViewById(R.id.eyerltv);
        eyeimg = findViewById(R.id.eyeimage);
        eyeoffimg = findViewById(R.id.eyeoffimage);
        etemail = findViewById(R.id.etemail);
        forget = findViewById(R.id.forgetpass);
        etpassword = findViewById(R.id.password);
        pbar = findViewById(R.id.pbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void validate(){

        String  useremail = etemail.getText().toString();
        String userpass = etpassword.getText().toString();


        if (useremail.isEmpty()) {
            etemail.setError(getString(R.string.input_error_email));
            etemail.requestFocus();
            return;
        }

        if (userpass.isEmpty()){
            etpassword.setError(getString(R.string.input_error_password));
            etpassword.requestFocus();
            return;
        }

        pbar.setVisibility(View.VISIBLE);
        WebAPI.login(getApplicationContext(),useremail,userpass,(status,message)->{
            if (status.equals("true")){

//                LoginResponse loginResponse = new LoginResponse();
//                Gson gson = new Gson();
//                loginResponse = gson.fromJson(message,LoginResponse.class);
//                Prefrences.saveUserDetails(LoginActivity.this,loginResponse);

                Intent intent = new Intent(LoginActivity.this, BottomNavigaionAcivity.class);
                startActivity(intent);

                LoginActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pbar.setVisibility(View.GONE);
                    }
                });
            }else {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pbar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}