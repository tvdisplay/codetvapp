package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.FlashCard;
import com.cintaxedge.tutoring.R;

public class FlashCardsActivity extends AppCompatActivity implements View.OnClickListener {

    WebView quesWebview, explainwebview;
    Button submit;
    RelativeLayout explainrltv, next, prev;
    int questionNo = 0;
    TextView questnNo;
    ImageView previmg, nextimg;
    FlashCard flashData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_cards);
        init();

    }

    public void init() {
        quesWebview = findViewById(R.id.webview1);
        submit = findViewById(R.id.submit);
        explainrltv = findViewById(R.id.answerrelative);
        explainwebview = findViewById(R.id.webview6);
        questnNo = findViewById(R.id.questionnumber);
        next = findViewById(R.id.nextbutton);
        prev = findViewById(R.id.previousbtn);
        nextimg = findViewById(R.id.nextimg);
        previmg = findViewById(R.id.previmg);
        next.setOnClickListener(this::onClick);
        prev.setOnClickListener(this::onClick);
        submit.setOnClickListener(this);

        WebAPI.getflashCardsdata(getApplicationContext(), (status, fData) -> {
            flashData = fData;
            FlashCardsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setData( );
                }
            });

        });

    }

    public void setData() {
        setIcons();
        clearAllWebViews();
        String question = flashData.getQuestions().get(questionNo).getQuestion();
        String question_explanation = flashData.getQuestions().get(questionNo).getQuestion_explanation();
        questnNo.setText(questionNo+1 + "/" + flashData.getQuestions().size());
        String questonhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + question + "</body></html>";

        String encodedHtml = Base64.encodeToString(questonhtmlstring.getBytes(), Base64.NO_PADDING);
        quesWebview.getSettings().setJavaScriptEnabled(true);
        quesWebview.loadData(encodedHtml, "text/html", "base64");


        String res=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            res= Html.fromHtml(question_explanation,Html.FROM_HTML_MODE_COMPACT).toString();
        }
        else{
            res=Html.fromHtml(question_explanation).toString();
        }
//        if (res.equals("")){
//            explainrltv.setVisibility(View.GONE);
//        }

        String explainantionhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + res + "</body></html>";

        String encodedquestnexplainHtml = Base64.encodeToString(explainantionhtmlstring.getBytes(), Base64.NO_PADDING);
        explainwebview.getSettings().setJavaScriptEnabled(true);
        explainwebview.loadData(encodedquestnexplainHtml, "text/html", "base64");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                explainrltv.setVisibility(View.VISIBLE);
                break;

            case R.id.previousbtn:
                if (questionNo>0){
                    questionNo = questionNo - 1;
                    setData( );
                }
                break;
            case R.id.nextbutton:
                if (questionNo < flashData.getQuestions().size() - 1) {
                    questionNo = questionNo + 1;
                    setData( );
                }
                break;

        }
    }

    public void setIcons() {
        if (questionNo == 0) {
            previmg.setImageResource(R.drawable.previousgray);
        } else {
            previmg.setImageResource(R.drawable.previousblack);
        }
        if (questionNo == flashData.getQuestions().size() - 1) {
            nextimg.setImageResource(R.drawable.nextgray);
        } else {
            nextimg.setImageResource(R.drawable.nextblack);
        }
    }

    public void clearAllWebViews(){
        quesWebview.clearCache(true);
        explainwebview.clearCache(true);
        explainrltv.setVisibility(View.GONE);
    }
}