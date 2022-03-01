package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cintaxedge.tutoring.R;

public class TermandConditionsActivity extends AppCompatActivity {

    String from = "";
    TextView title;
    ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termand_conditions);
        WebView browser = (WebView) findViewById(R.id.webview);
        title = findViewById(R.id.titletext);
        backbtn = findViewById(R.id.backimg);


        from = getIntent().getStringExtra("from");
        if (from.equals("termandcondition")){
            title.setText("Terms and Conditions");
            browser.loadUrl("https://www.asvab-tutoring.com/online-study/information/terms-and-conditions");
        }else if (from.equals("about")) {
            title.setText("About Us");
            browser.loadUrl("file:///android_asset/aboutus.html");
        }else {
            title.setText("Privacy Policy");
            browser.loadUrl("https://www.asvab-tutoring.com/online-study/information/privacy-policy");
        }



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}