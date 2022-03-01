package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.RegisterResponse;
import com.cintaxedge.tutoring.R;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    TextView login;
    Button submit;
    EditText fnameet, lnamert, emailet, passwordet, confirmpasswordet,etmobile;
    ProgressBar pbar;

    RelativeLayout eyelyt;
    ImageView eyeimg, eyeoffimg;
    boolean showpassword = false;

    RelativeLayout cnfrmeyelyt;
    ImageView cnfrmeyeimg, cnfrmeyeoffimg;
    boolean cnfrmshowpassword = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        eyelyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showpassword) {
                    passwordet.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showpassword = false;
                    eyeoffimg.setVisibility(View.GONE);
                    eyeimg.setVisibility(View.VISIBLE);
                } else {
                    passwordet.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showpassword = true;
                    eyeoffimg.setVisibility(View.VISIBLE);
                    eyeimg.setVisibility(View.GONE);
                }
            }
        });
        cnfrmeyelyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cnfrmshowpassword) {
                    confirmpasswordet.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showpassword = false;
                    cnfrmeyeoffimg.setVisibility(View.GONE);
                    cnfrmeyeimg.setVisibility(View.VISIBLE);
                } else {
                    confirmpasswordet.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    cnfrmshowpassword = true;
                    cnfrmeyeoffimg.setVisibility(View.VISIBLE);
                    cnfrmeyeimg.setVisibility(View.GONE);
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    public void validate() {

        String mobile = etmobile.getText().toString();
        String firstname = fnameet.getText().toString();
        String lastname = lnamert.getText().toString();
        String useremail = emailet.getText().toString();
        String userpass = passwordet.getText().toString();
        String confirmuserpass = confirmpasswordet.getText().toString();

        if (firstname.isEmpty()) {
            fnameet.setError(getString(R.string.input_error_fname));
            return;
        }
        if (lastname.isEmpty()) {
            lnamert.setError(getString(R.string.input_error_lname));
            lnamert.requestFocus();
            return;

        }

        if (useremail.isEmpty()) {
            emailet.setError(getString(R.string.input_error_email));
            emailet.requestFocus();
            return;

        }
        if (userpass.isEmpty()) {
            passwordet.setError(getString(R.string.input_error_password));
            passwordet.requestFocus();
            return;

        }
        if (confirmuserpass.isEmpty()) {
            confirmpasswordet.setError(getString(R.string.input_error_password));
            confirmpasswordet.requestFocus();
            return;

        }
        if (userpass.length() < 6) {
            passwordet.setError(getString(R.string.input_length_error_password));
            passwordet.requestFocus();
            return;
        }

        if (!userpass.equals(confirmuserpass)){
            confirmpasswordet.setError(getString(R.string.nomatchs_password));
            confirmpasswordet.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            emailet.setError(getString(R.string.input_error_email_invalid));
            emailet.requestFocus();
            return;
        }
        pbar.setVisibility(View.VISIBLE);
        WebAPI.Register(getApplicationContext(),firstname,lastname,useremail,mobile,userpass,(status,message)->{

            if (status.equals("true")){
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pbar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);

                loginApi(useremail,userpass);
            }else {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pbar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void init() {
        login = findViewById(R.id.logintv);
        fnameet = findViewById(R.id.etfname);
        lnamert = findViewById(R.id.etlname);
        emailet = findViewById(R.id.etemail);
        passwordet = findViewById(R.id.password);
        confirmpasswordet = findViewById(R.id.confirmpassword);
        submit = findViewById(R.id.submit);
        etmobile = findViewById(R.id.etmobile);
        pbar = findViewById(R.id.pbar);

        eyelyt = findViewById(R.id.eyerltv);
        eyeimg = findViewById(R.id.eyeimage);
        eyeoffimg = findViewById(R.id.eyeoffimage);

        cnfrmeyelyt = findViewById(R.id.cnfrmeyerltv);
        cnfrmeyeimg = findViewById(R.id.cnfrmeyeimage);
        cnfrmeyeoffimg = findViewById(R.id.cnfrmeyeoffimage);
    }

    public void loginApi(String useremail,String userpass){
        WebAPI.login(getApplicationContext(),useremail,userpass,(status,message)->{
            if (status.equals("true")){

//                LoginResponse loginResponse = new LoginResponse();
//                Gson gson = new Gson();
//                loginResponse = gson.fromJson(message,LoginResponse.class);
//                Prefrences.saveUserDetails(LoginActivity.this,loginResponse);

                Intent intent = new Intent(RegisterActivity.this, BottomNavigaionAcivity.class);
                startActivity(intent);

                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pbar.setVisibility(View.GONE);
                    }
                });
            }else {
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pbar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}