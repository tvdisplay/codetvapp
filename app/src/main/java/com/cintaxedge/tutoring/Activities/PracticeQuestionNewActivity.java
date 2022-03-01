package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.cintaxedge.tutoring.Models.PracticeTestQuestion;
import com.cintaxedge.tutoring.Models.ViewLesson;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PracticeQuestionNewActivity extends AppCompatActivity implements View.OnClickListener {

    ViewLesson lessonobj;
    TextView title, rightAnswer, questionNumber;
    WebView myWebView1, opt1, opt2, opt3, opt4, opt5, storywebView;
    RelativeLayout opt1rltv, opt2rltv, opt3rltv, opt4rltv, opt5rltv;
    ImageView check1, check2, check3, check4, check5;
    ImageView checkfill1, checkfill2, checkfill3, checkfill4, checkfill5;
    ImageView previmg, nextimg;
    RelativeLayout prev, next;
    ImageView back;
    Button submit;
    int questionNo = 0;
    int answerno = 0;
    TextView remainingQuestions;
    ArrayList<Integer> answerQuestions = new ArrayList<>();
    ArrayList<Integer> unAnswerQuestions = new ArrayList<>();
    ArrayList<PracticeTestQuestion> practicequestionsarr = new ArrayList<>();
    HashMap<Integer, Integer> answreshasmap = new HashMap<Integer, Integer>();
    private static final String TAG = "PracticeQuestionNewActi";

    ArrayList<String> quesIds = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_question_new);
        getIncomingContent();

    }

    public void getIncomingContent() {
        lessonobj = (ViewLesson) getIntent().getSerializableExtra("lessonobj");
        String catId = getIntent().getStringExtra("catId");
        String lessonId = getIntent().getStringExtra("lessonId");

        if (lessonobj == null) {
            WebAPI.getAllTestPracticeQues(getApplicationContext(), catId, lessonId, (status, lessonobject) -> {

                lessonobj = lessonobject;
                if (lessonobject.getMessage() != null) {
                    if (lessonobject.getMessage().equals("Its paid lesson.Upgrade your membership to get all paid lessons active by paying  $0 USD for 0 days.")) {
                        Intent intent = new Intent(PracticeQuestionNewActivity.this, SubscribeActivity.class);
                        startActivity(intent);
                    } else {
                        init();
                    }

                } else {
                    init();
                }


            });
        } else {
            init();
        }
    }

    public void init() {

        opt1rltv = findViewById(R.id.rltvlyt1);
        opt2rltv = findViewById(R.id.rltvlyt2);
        opt3rltv = findViewById(R.id.rltvlyt3);
        opt4rltv = findViewById(R.id.rltvlyt4);
        opt5rltv = findViewById(R.id.rltvlyt5);

        questionNumber = findViewById(R.id.questionnumber);
        storywebView = findViewById(R.id.storywebview);
        myWebView1 = findViewById(R.id.webview1);

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

        remainingQuestions = findViewById(R.id.remainingtv);
        submit = findViewById(R.id.submit);

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


        opt1rltv.setOnClickListener(this);
        opt2rltv.setOnClickListener(this);
        opt3rltv.setOnClickListener(this);
        opt4rltv.setOnClickListener(this);
        opt5rltv.setOnClickListener(this);


        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);



        if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {

            for (PracticeTestQuestion story : lessonobj.getPractice_test_questions()) {
                for (PracticeTestQuestion question : story.getPractice_test_questions()) {
                    question.setStory(story.getStory());
                    practicequestionsarr.add(question);
                }
            }
            Log.d(TAG, "setStoryQuesion2: "+practicequestionsarr.size());
            setStoryQuesion(questionNo);
        } else {
            setQuestion(questionNo);
        }
    }


    public void setStoryQuesion(int i) {
        Log.d(TAG, "setStoryQuesion1: "+practicequestionsarr.size());
        answerno = 0;
        clearallchecks();
        clearAllWebviews();
        questionNumber.setText(i + 1 + "\\" + lessonobj.getPractice_test_total());
        setIcons();
        String story, question, answer1, answer2, answer3, answer4, answer5 = "";
        story = practicequestionsarr.get(i).getStory();
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
        String question, answer1, answer2, answer3, answer4, answer5 = "";

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
            checkunAnswere();
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

    private void clearAllWebviews() {
        opt1.clearCache(true);
        opt2.clearCache(true);
        opt3.clearCache(true);
        opt4.clearCache(true);
        myWebView1.clearCache(true);

    }

    public void checkunAnswere() {
        unAnswerQuestions.clear();
        for (int i = 1; i < lessonobj.getPractice_test_total() + 1; i++) {
            if (!answerQuestions.contains(i)) {
                unAnswerQuestions.add(i);
            }
        }

    }

    public void submitAnswer(String quesion_id, String answer_no, int total, int nextQuestionIndex, Boolean shouldLoadNextQuestion) {

        WebAPI.submitAnswer(getApplicationContext(), lessonobj.getCat_id(), lessonobj.lesson_id, quesion_id, answer_no, String.valueOf(total), (status, err) -> {
            if (status) {
                if (shouldLoadNextQuestion) {
                    PracticeQuestionNewActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            remainingQuestions.setText(err);
                            if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                                setStoryQuesion(questionNo);
                            } else {
                                setQuestion(questionNo);
                            }
                        }
                    });

                } else {
                    Intent intent = new Intent(PracticeQuestionNewActivity.this, ResultsActivity.class);
                    intent.putExtra("catId", lessonobj.getCat_id());
                    intent.putExtra("lessonId", lessonobj.getLesson_id());
                    startActivity(intent);

                }

            } else {
                PracticeQuestionNewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PracticeQuestionNewActivity.this, err, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rltvlyt1:
                answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 1);
                answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                clearallchecks();
                answerno = 1;
                checkfill1.setVisibility(View.VISIBLE);
                check1.setVisibility(View.GONE);
                break;

            case R.id.rltvlyt2:

                answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 2);
                answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                answerno = 2;
                clearallchecks();
                checkfill2.setVisibility(View.VISIBLE);
                check2.setVisibility(View.GONE);
                break;

            case R.id.rltvlyt3:
                answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 3);
                answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                clearallchecks();
                answerno = 3;
                checkfill3.setVisibility(View.VISIBLE);
                check3.setVisibility(View.GONE);
                break;

            case R.id.rltvlyt4:

                answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 4);
                answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                clearallchecks();
                answerno = 4;
                checkfill4.setVisibility(View.VISIBLE);
                check4.setVisibility(View.GONE);
                break;


            case R.id.rltvlyt5:
                answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 5);
                answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                clearallchecks();
                answerno = 5;
                checkfill5.setVisibility(View.VISIBLE);
                check5.setVisibility(View.GONE);
                break;

            case R.id.nextbutton:
                Boolean shouldloadnext = false;

                if (questionNo < lessonobj.getPractice_test_total() - 1) {
                    questionNo = questionNo + 1;
                    shouldloadnext = true;
                    String questionid = practicequestionsarr.get(questionNo).getId();
                    quesIds.add(questionid);
                    if (answerno != 0) {
                        submitAnswer(questionid, String.valueOf(answerno), lessonobj.getPractice_test_total(), questionNo + 1, shouldloadnext);
                    } else {
                        if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                            setStoryQuesion(questionNo);
                        } else {
                            setQuestion(questionNo);
                        }
                    }
                    break;
                }

            case R.id.previousbtn:

                if (questionNo > 0) {
                    questionNo = questionNo - 1;
                    if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                        setStoryQuesion(questionNo);
                    } else {
                        setQuestion(questionNo);
                    }
                }
                break;

            case R.id.backimg:
                onBackPressed();
                break;

            case R.id.submit:
                Log.d(TAG, "setStoryQuesion3: "+practicequestionsarr.size());
                StringBuilder stringBuilder = new StringBuilder();
                if (unAnswerQuestions.size() <= 1) {
                    String questionid = practicequestionsarr.get(questionNo).getId();
                    quesIds.add(questionid);
                    for(int i=0;i<quesIds.size();i++){
                        Log.d(TAG, "onClick:queids:: "+quesIds.get(i));
                    }

                    shouldloadnext = false;
                    if (answerno != 0) {
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
                    AlertDialog.Builder alert = new AlertDialog.Builder(PracticeQuestionNewActivity.this);
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
        }
    }
}



