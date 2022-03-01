package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.ChangePassResponse;
import com.cintaxedge.tutoring.R;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etpassword,etconfirmpassword;
    Button submit;
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
        setContentView(R.layout.activity_change_password);
        init();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
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
        cnfrmeyelyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cnfrmshowpassword) {
                    etconfirmpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showpassword = false;
                    cnfrmeyeoffimg.setVisibility(View.GONE);
                    cnfrmeyeimg.setVisibility(View.VISIBLE);
                } else {
                    etconfirmpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    cnfrmshowpassword = true;
                    cnfrmeyeoffimg.setVisibility(View.VISIBLE);
                    cnfrmeyeimg.setVisibility(View.GONE);
                }
            }
        });

    }
    public void init(){
        etpassword = findViewById(R.id.password);
        etconfirmpassword = findViewById(R.id.cnfrmpassword);
        submit = findViewById(R.id.submit);
        pbar = findViewById(R.id.pbar);

        eyelyt = findViewById(R.id.eyerltv);
        eyeimg = findViewById(R.id.eyeimage);
        eyeoffimg = findViewById(R.id.eyeoffimage);

        cnfrmeyelyt = findViewById(R.id.cnfrmeyerltv);
        cnfrmeyeimg = findViewById(R.id.cnfrmeyeimage);
        cnfrmeyeoffimg = findViewById(R.id.cnfrmeyeoffimage);
    }

    private void validate(){
        if(!etpassword.getText().toString().equals(etconfirmpassword.getText().toString())) {
            etpassword.setError("confirm Password not match");
            return;
        }
        if (TextUtils.isEmpty(etpassword.getText().toString())) {
            etpassword.setError("Password Required");
            return;
        }

        if (TextUtils.isEmpty(etconfirmpassword.getText().toString())) {
            etconfirmpassword.setError("Password Required");
            return;
        }

        if (etpassword.getText().toString().length() < 6) {
            etpassword.setError("minimum 6 strength required");
            return;
        }


        pbar.setVisibility(View.VISIBLE);
        WebAPI.changePassword(getApplicationContext(),etpassword.getText().toString(),(status,err)->{
            if (status) {
                ChangePasswordActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pbar.setVisibility(View.GONE);
                        etconfirmpassword.setText("");
                        etpassword.setText("");
                        Toast.makeText(ChangePasswordActivity.this, err, Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                pbar.setVisibility(View.GONE);
                Toast.makeText(ChangePasswordActivity.this, err, Toast.LENGTH_SHORT).show();
            }
        });

    }
}