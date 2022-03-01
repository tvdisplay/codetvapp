package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.CountUpTimer;
import com.cintaxedge.tutoring.Models.PracticeTestQuestion;
import com.cintaxedge.tutoring.Models.ViewLesson;

import com.cintaxedge.tutoring.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PracticeQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    ViewLesson lessonobj;
    String lessonname;
    RelativeLayout prev, next;
    ImageView back;
    TextView timertv;
    TextView title, rightAnswer, questionNumber;
    ImageView previmg, nextimg;
    int questionNo = 0;
    RelativeLayout opt1rltv, opt2rltv, opt3rltv, opt4rltv, opt5rltv;
    WebView myWebView1, opt1, opt2, opt3, opt4, opt5, storywebView;
    ImageView check1, check2, check3, check4, check5;
    ImageView checkfill1, checkfill2, checkfill3, checkfill4, checkfill5;
    ArrayList<PracticeTestQuestion> practicequestionsarr = new ArrayList<>();
    ArrayList<Integer> answerQuestions = new ArrayList<>();
    ArrayList<Integer> unAnswerQuestions = new ArrayList<>();
    private static final String TAG = "PracticeQuestionActivit";
    boolean answered = false;
    Button submit;
    String question = "";
    String story = "";
    TextView remainingQuestions;
    int answerno = 0;
    HashMap<Integer, Integer> answreshasmap = new HashMap<Integer, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_question);
        hitApi();
        addtimer();
    }

    private void hitApi() {
        lessonobj = (ViewLesson) getIntent().getSerializableExtra("lessonobj");
        String catId = getIntent().getStringExtra("catId");
        String lessonId = getIntent().getStringExtra("lessonId");

        if (lessonobj == null) {
            WebAPI.getAllTestPracticeQues(getApplicationContext(), catId, lessonId, (status, lessonobject) -> {

                lessonobj = lessonobject;
                if (lessonobject.getMessage() != null) {
                    if (lessonobject.getMessage().equals("Its paid lesson.Upgrade your membership to get all paid lessons active by paying  $0 USD for 0 days.")) {
                        Intent intent = new Intent(PracticeQuestionActivity.this, SubscribeActivity.class);
                        startActivity(intent);
                    } else {
                        PracticeQuestionActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                init();
                            }
                        });
                    }

                } else {
                    PracticeQuestionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            init();
                        }
                    });
                }


            });
        } else {
            init();
        }
    }

    private void init() {


        title = findViewById(R.id.title);
        opt1 = findViewById(R.id.webview2);
        opt2 = findViewById(R.id.webview3);
        opt3 = findViewById(R.id.webview4);
        opt4 = findViewById(R.id.webview5);
        opt5 = findViewById(R.id.webview7);
        back = findViewById(R.id.backimg);
        prev = findViewById(R.id.previousbtn);
        next = findViewById(R.id.nextbutton);
        previmg = findViewById(R.id.previmg);
        nextimg = findViewById(R.id.nextimg);
        questionNumber = findViewById(R.id.questionnumber);
        myWebView1 = findViewById(R.id.webview1);

        submit = findViewById(R.id.submit);
        storywebView = findViewById(R.id.storywebview);

        opt1rltv = findViewById(R.id.rltvlyt1);
        opt2rltv = findViewById(R.id.rltvlyt2);
        opt3rltv = findViewById(R.id.rltvlyt3);
        opt4rltv = findViewById(R.id.rltvlyt4);
        opt5rltv = findViewById(R.id.rltvlyt5);


        opt1rltv.setOnClickListener(this);
        opt2rltv.setOnClickListener(this);
        opt3rltv.setOnClickListener(this);
        opt4rltv.setOnClickListener(this);
        opt5rltv.setOnClickListener(this);


        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);

        check1 = findViewById(R.id.chk1);
        check2 = findViewById(R.id.chk2);
        check3 = findViewById(R.id.chk3);
        check4 = findViewById(R.id.chk4);
        check5 = findViewById(R.id.chk5);


        checkfill1 = findViewById(R.id.chkfill1);
        checkfill2 = findViewById(R.id.chkfill2);
        checkfill3 = findViewById(R.id.chkfill3);
        checkfill4 = findViewById(R.id.chkfill4);
        checkfill5 = findViewById(R.id.chkfill5);


        remainingQuestions = findViewById(R.id.remainingtv);


        if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {

            for (PracticeTestQuestion story : lessonobj.getPractice_test_questions()) {
                for (PracticeTestQuestion question : story.getPractice_test_questions()) {
                    Log.d(TAG, "init:story " + story.getStory());
                    Log.d(TAG, "init: questions:" + question.getQuestion());

                    question.setStory(story.getStory());
                    practicequestionsarr.add(question);
                }
            }
            setStoryQuesion(practicequestionsarr, questionNo);
        } else {
            setQuestion(questionNo);
        }

    }


    public void addtimer() {
        CountUpTimer timer = new CountUpTimer(300000000) {
            public void onTick(int second) {
                final int MINUTES_IN_AN_HOUR = 60;
                final int SECONDS_IN_A_MINUTE = 60;

                int seconds = second % SECONDS_IN_A_MINUTE;
                int totalMinutes = second / SECONDS_IN_A_MINUTE;
                int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
                int hours = totalMinutes / MINUTES_IN_AN_HOUR;
                String hourstr = String.valueOf(hours), minstr = String.valueOf(minutes), secstr = String.valueOf(seconds);
                if (hours < 10) {
                    hourstr = "0" + hours;
                }
                if (minutes < 10) {
                    minstr = "0" + minutes;
                }
                if (seconds < 10) {
                    secstr = "0" + seconds;
                }
                timertv = findViewById(R.id.timetextview);
                timertv.setText(String.valueOf(hourstr + " : " + minstr + " : " + secstr));
            }
        };

        timer.start();
    }

    public void setIcons() {
        if (questionNo == 0) {
            previmg.setImageResource(R.drawable.previousgray);
        } else {
            previmg.setImageResource(R.drawable.previousblack);
        }
        if (questionNo == lessonobj.getPractice_test_total() - 1) {
            checkunAnswere();
            nextimg.setImageResource(R.drawable.nextgray);
            submit.setVisibility(View.VISIBLE);
        } else {
            submit.setVisibility(View.GONE);
            nextimg.setImageResource(R.drawable.nextblack);
        }

        if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
            if (answerQuestions.contains(practicequestionsarr.get(questionNo).getSerial_no())) {

                for (Map.Entry m : answreshasmap.entrySet()) {

                    int questionno = (int) m.getKey();
                    int rightoption = (int) m.getValue();
                    if (questionno == practicequestionsarr.get(questionNo).getSerial_no()) {
                        if (rightoption == 1) {
                            clearallchecks();
                            checkfill1.setVisibility(View.VISIBLE);
                        } else if (rightoption == 2) {
                            clearallchecks();
                            checkfill2.setVisibility(View.VISIBLE);
                        } else if (rightoption == 3) {
                            clearallchecks();
                            checkfill3.setVisibility(View.VISIBLE);
                        } else if (rightoption == 4) {
                            clearallchecks();
                            checkfill4.setVisibility(View.VISIBLE);
                        } else if (rightoption == 5) {
                            clearallchecks();
                            checkfill5.setVisibility(View.VISIBLE);
                        } else {
                            clearallchecks();
                        }
                    }
                }
            }
        } else {
            if (answerQuestions.contains(practicequestionsarr.get(questionNo).getSerial_no())) {
                for (Map.Entry m : answreshasmap.entrySet()) {
                    int questionno = (int) m.getKey();
                    int rightoption = (int) m.getValue();
                    if (questionno == practicequestionsarr.get(questionNo).getSerial_no()) {
                        if (rightoption == 1) {
                            clearallchecks();
                            checkfill1.setVisibility(View.VISIBLE);
                        } else if (rightoption == 2) {
                            clearallchecks();
                            checkfill2.setVisibility(View.VISIBLE);
                        } else if (rightoption == 3) {
                            clearallchecks();
                            checkfill3.setVisibility(View.VISIBLE);
                        } else if (rightoption == 4) {
                            clearallchecks();
                            checkfill4.setVisibility(View.VISIBLE);
                        } else if (rightoption == 5) {
                            clearallchecks();
                            checkfill5.setVisibility(View.VISIBLE);
                        } else {
                            clearallchecks();
                        }
                    }
                }
            }
        }


    }

    public void setStoryQuesion(ArrayList<PracticeTestQuestion> questionsarray, int i) {
        answerno = 0;
        clearallchecks();
        clearAllWebviews();
      //   practicequestionsarr = lessonobj.getPractice_test_questions();
        questionNumber.setText(i + 1 + "\\" + lessonobj.getPractice_test_total());
        setIcons();


        String answer1, answer2, answer3, answer4, answer5 = "";
        story = questionsarray.get(i).getStory();
        question = questionsarray.get(i).getQuestion();
        answer1 = questionsarray.get(i).getAnswers().get(0).getAnswer();
        answer2 = questionsarray.get(i).getAnswers().get(1).getAnswer();
        answer3 = questionsarray.get(i).getAnswers().get(2).getAnswer();
        answer4 = questionsarray.get(i).getAnswers().get(3).getAnswer();
        if (questionsarray.get(i).getAnswers().size() == 5) {
            answer5 = questionsarray.get(i).getAnswers().get(4).getAnswer();
            opt5rltv.setVisibility(View.VISIBLE);
        } else {
            opt5.setVisibility(View.GONE);
            opt5rltv.setVisibility(View.GONE);
        }


        String storyhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + story + "</body></html>";
        String questonhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + question + "</body></html>";
        String answer1htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer1 + "</body></html>";
        String answer2htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer2 + "</body></html>";
        String answer3htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer3 + "</body></html>";
        String answer4htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer4 + "</body></html>";

        String answer5htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer5 + "</body></html>";


        String encodedHtml0 = Base64.encodeToString(storyhtmlstring.getBytes(), Base64.NO_PADDING);
        storywebView.getSettings().setJavaScriptEnabled(true);
        storywebView.loadData(encodedHtml0, "text/html", "base64");

        String encodedHtml = Base64.encodeToString(questonhtmlstring.getBytes(), Base64.NO_PADDING);
        myWebView1.getSettings().setJavaScriptEnabled(true);
        myWebView1.loadData(encodedHtml, "text/html", "base64");

        String encodedHtml1 = Base64.encodeToString(answer1htmlstring.getBytes(), Base64.NO_PADDING);
        opt1.getSettings().setJavaScriptEnabled(true);
        opt1.loadData(encodedHtml1, "text/html", "base64");


        String encodedHtml2 = Base64.encodeToString(answer2htmlstring.getBytes(), Base64.NO_PADDING);
        opt2.getSettings().setJavaScriptEnabled(true);
        opt2.loadData(encodedHtml2, "text/html", "base64");


        String encodedHtml3 = Base64.encodeToString(answer3htmlstring.getBytes(), Base64.NO_PADDING);
        opt3.getSettings().setJavaScriptEnabled(true);
        opt3.loadData(encodedHtml3, "text/html", "base64");


        String encodedHtml4 = Base64.encodeToString(answer4htmlstring.getBytes(), Base64.NO_PADDING);
        opt4.getSettings().setJavaScriptEnabled(true);
        opt4.loadData(encodedHtml4, "text/html", "base64");

        String encodedHtml5 = Base64.encodeToString(answer5htmlstring.getBytes(), Base64.NO_PADDING);
        opt5.getSettings().setJavaScriptEnabled(true);
        opt5.loadData(encodedHtml5, "text/html", "base64");


    }

    public void setQuestion(int i) {
        answerno = 0;
        clearallchecks();
        clearAllWebviews();
        practicequestionsarr = lessonobj.getPractice_test_questions();
        questionNumber.setText(i + 1 + "\\" + lessonobj.getPractice_test_total());
        setIcons();
        String answer1, answer2, answer3, answer4, answer5 = "";
        question = practicequestionsarr.get(i).getQuestion();
        answer1 = practicequestionsarr.get(i).getAnswers().get(0).getAnswer();
        answer2 = practicequestionsarr.get(i).getAnswers().get(1).getAnswer();
        answer3 = practicequestionsarr.get(i).getAnswers().get(2).getAnswer();
        answer4 = practicequestionsarr.get(i).getAnswers().get(3).getAnswer();
        if (practicequestionsarr.get(i).getAnswers().size() == 5) {
            answer5 = practicequestionsarr.get(i).getAnswers().get(3).getAnswer();
            opt5rltv.setVisibility(View.VISIBLE);
        } else {
            opt5rltv.setVisibility(View.GONE);
        }


        String questonhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + question + "</body></html>";

        String answer1htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer1 + "</body></html>";
        String answer2htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer2 + "</body></html>";
        String answer3htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer3 + "</body></html>";
        String answer4htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer4 + "</body></html>";

        String answer5htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + answer5 + "</body></html>";


        String encodedHtml = Base64.encodeToString(questonhtmlstring.getBytes(), Base64.NO_PADDING);
        myWebView1.getSettings().setJavaScriptEnabled(true);
        myWebView1.loadData(encodedHtml, "text/html", "base64");

        String encodedHtml1 = Base64.encodeToString(answer1htmlstring.getBytes(), Base64.NO_PADDING);
        opt1.getSettings().setJavaScriptEnabled(true);
        opt1.loadData(encodedHtml1, "text/html", "base64");


        String encodedHtml2 = Base64.encodeToString(answer2htmlstring.getBytes(), Base64.NO_PADDING);
        opt2.getSettings().setJavaScriptEnabled(true);
        opt2.loadData(encodedHtml2, "text/html", "base64");


        String encodedHtml3 = Base64.encodeToString(answer3htmlstring.getBytes(), Base64.NO_PADDING);
        opt3.getSettings().setJavaScriptEnabled(true);
        opt3.loadData(encodedHtml3, "text/html", "base64");


        String encodedHtml4 = Base64.encodeToString(answer4htmlstring.getBytes(), Base64.NO_PADDING);
        opt4.getSettings().setJavaScriptEnabled(true);
        opt4.loadData(encodedHtml4, "text/html", "base64");

        String encodedHtml5 = Base64.encodeToString(answer5htmlstring.getBytes(), Base64.NO_PADDING);
        opt5.getSettings().setJavaScriptEnabled(true);
        opt5.loadData(encodedHtml5, "text/html", "base64");

    }

    private void clearAllWebviews() {
        opt1.clearCache(true);
        opt2.clearCache(true);
        opt3.clearCache(true);
        opt4.clearCache(true);
        myWebView1.clearCache(true);

    }

    public void clearallchecks() {
        checkfill1.setVisibility(View.GONE);
        checkfill2.setVisibility(View.GONE);
        checkfill3.setVisibility(View.GONE);
        checkfill4.setVisibility(View.GONE);
        checkfill5.setVisibility(View.GONE);


        check1.setVisibility(View.VISIBLE);
        check2.setVisibility(View.VISIBLE);
        check3.setVisibility(View.VISIBLE);
        check4.setVisibility(View.VISIBLE);
        check5.setVisibility(View.VISIBLE);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rltvlyt1:
                if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 1);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                } else {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 1);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                }

                clearallchecks();
                answerno = 1;
                checkfill1.setVisibility(View.VISIBLE);
                check1.setVisibility(View.GONE);
                break;

            case R.id.rltvlyt2:
                if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 2);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                } else {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 2);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                }

                answerno = 2;
                clearallchecks();
                checkfill2.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                break;

            case R.id.rltvlyt3:
                if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 3);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                } else {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 3);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                }

                clearallchecks();
                answerno = 3;
                checkfill3.setVisibility(View.VISIBLE);
                check3.setVisibility(View.GONE);
                break;

            case R.id.rltvlyt4:
                if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 4);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                } else {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 4);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                }

                clearallchecks();
                answerno = 4;
                checkfill4.setVisibility(View.VISIBLE);
                check4.setVisibility(View.GONE);
                break;

            case R.id.rltvlyt5:
                if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 5);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                } else {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 5);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                }

                clearallchecks();
                answerno = 5;
                checkfill5.setVisibility(View.VISIBLE);
                check5.setVisibility(View.GONE);
                break;


            case R.id.nextbutton:
                Boolean shouldloadnext = false;

                if (questionNo < lessonobj.getPractice_test_total() - 1) {
                    questionNo = questionNo + 1;
                    //   setQuestion(questionNo);
                    shouldloadnext = true;

                    if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                        String questionid = practicequestionsarr.get(questionNo).getId();
                        if (answerno != 0) {
                            Log.d(TAG, "onClick:idcheckstry "+questionid+" : "+ String.valueOf(answerno)+" : "+lessonobj.getPractice_test_total()+" : "+ questionNo + 1+" : "+ shouldloadnext);

                            submitAnswer(questionid, String.valueOf(answerno), lessonobj.getPractice_test_total(), questionNo + 1, shouldloadnext);
                        } else {
                            setStoryQuesion(practicequestionsarr, questionNo);
                        }
                    } else {

                        String questionid = practicequestionsarr.get(questionNo).getId();
                        if (answerno != 0) {
                            Log.d(TAG, "onClick:idchecksmpl "+questionid+" : "+ String.valueOf(answerno)+" : "+lessonobj.getPractice_test_total()+" : "+ questionNo + 1+" : "+ shouldloadnext);

                            submitAnswer(questionid, String.valueOf(answerno), lessonobj.getPractice_test_total(), questionNo + 1, shouldloadnext);
                        } else {
                            setQuestion(questionNo);
                        }

                    }


                }


                break;

            case R.id.previousbtn:

                if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                    if (questionNo > 0) {
                        questionNo = questionNo - 1;
                        setStoryQuesion(practicequestionsarr, questionNo);
                    }
                    break;
                } else {
                    if (questionNo > 0) {
                        questionNo = questionNo - 1;
                        setQuestion(questionNo);
                    }
                    break;
                }


            case R.id.backimg:
                onBackPressed();
                break;

            case R.id.submit:
                StringBuilder stringBuilder = new StringBuilder();
                if (unAnswerQuestions.size() <= 1) {
                    String questionid = "";
                    if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                        questionid = practicequestionsarr.get(questionNo).getId();
                    } else {
                        questionid = practicequestionsarr.get(questionNo).getId();
                    }
                    shouldloadnext = false;
                    if (answerno != 0) {
                        Log.d(TAG, "onClick:idcheck "+questionid+" : "+ String.valueOf(answerno)+" : "+lessonobj.getPractice_test_total()+" : "+ questionNo + 1+" : "+ shouldloadnext);
                        submitAnswer(questionid, String.valueOf(answerno), lessonobj.getPractice_test_total(), questionNo + 1, shouldloadnext);
                    } else {
                        clearallchecks();
                        Toast.makeText(this, "Option not selected", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    for (int i = 0; i < unAnswerQuestions.size(); i++) {
                        if (unAnswerQuestions.size() < 2 || i == unAnswerQuestions.size() - 1) {
                            stringBuilder.append(unAnswerQuestions.get(i));
                        } else {
                            stringBuilder.append(unAnswerQuestions.get(i) + ",");
                        }
                    }
                    String joinedString = stringBuilder.toString();
                    AlertDialog.Builder alert = new AlertDialog.Builder(PracticeQuestionActivity.this);
                    alert.setTitle("please answer all the questions. Missing answers are ");
                    alert.setMessage(joinedString);
                    alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
                break;
            default:
                //who knows what button was pressed
        }
    }

    public void checkunAnswere() {
        unAnswerQuestions.clear();
        for (int i = 1; i < lessonobj.getPractice_test_total() + 1; i++) {
            if (!answerQuestions.contains(i)) {
                unAnswerQuestions.add(i);
            }
        }
        Log.d(TAG, "checkunAnswere: " + unAnswerQuestions);
    }


    public void submitAnswer(String quesion_id, String answer_no, int total, int nextQuestionIndex, Boolean shouldLoadNextQuestion) {

        Log.d(TAG, "submitAnswer: " + getApplicationContext() + "/n" + lessonobj.getCat_id() + "/n" + lessonobj.lesson_id + "/n" + quesion_id + "/n" + answer_no + "/n" + String.valueOf(total));
        WebAPI.submitAnswer(getApplicationContext(), lessonobj.getCat_id(), lessonobj.lesson_id, quesion_id, answer_no, String.valueOf(total), (status, err) -> {
            if (status) {

                if (shouldLoadNextQuestion) {
                    PracticeQuestionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            remainingQuestions.setText(err);
                            Log.d(TAG, "run:remaining1: "+   err);
                            if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                                setStoryQuesion(practicequestionsarr, questionNo);
                            } else {
                                setQuestion(questionNo);
                            }
                        }
                    });

                } else {
                    PracticeQuestionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            remainingQuestions.setText(err);
                            Log.d(TAG, "run:remaining "+   err);
                        }
                    });

                    Intent intent = new Intent(PracticeQuestionActivity.this, ResultsActivity.class);
                    intent.putExtra("catId", lessonobj.getCat_id());
                    intent.putExtra("lessonId", lessonobj.getLesson_id());
                    startActivity(intent);
                    // Toast.makeText(this, "submit and show score", Toast.LENGTH_SHORT).show();
                }

            } else {
                PracticeQuestionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: error in submit");
                        Toast.makeText(PracticeQuestionActivity.this, err, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }

}