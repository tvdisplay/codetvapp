package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.cintaxedge.tutoring.Models.ViewLesson;
import com.cintaxedge.tutoring.R;

public class WebviewActivity extends AppCompatActivity {

    ViewLesson lessonobj;
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();

    }
    private void init() {
        webview = findViewById(R.id.webviewfull);
        lessonobj = (ViewLesson) getIntent().getSerializableExtra("lessonobj");
        lessonobj.getLesson().getOverview();
        webview.loadUrl(lessonobj.getLesson().getOverview());
    }
}