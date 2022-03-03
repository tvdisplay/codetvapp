package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.cintaxedge.tutoring.Models.CountUpTimer;
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
    ImageView previmg, nextimg;
    RelativeLayout prev, next;
    ImageView back;
    Button submit;
    int questionNo = 0;
    int answerno = 0;
    TextView remainingQuestions;
    TextView timertv;
    ArrayList<Integer> answerQuestions = new ArrayList<>();
    ArrayList<Integer> unAnswerQuestions = new ArrayList<>();
    ArrayList<PracticeTestQuestion> practicequestionsarr = new ArrayList<>();
    HashMap<Integer, Integer> answreshasmap = new HashMap<Integer, Integer>();
    private static final String TAG = "PracticeQuestionNewActi";
    ProgressDialog dialog;
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
        addtimer();
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


        opt1rltv.setOnClickListener(this);
        opt2rltv.setOnClickListener(this);
        opt3rltv.setOnClickListener(this);
        opt4rltv.setOnClickListener(this);
        opt5rltv.setOnClickListener(this);


        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        back.setOnClickListener(this);
        submit.setOnClickListener(this);


        dialog = new ProgressDialog(PracticeQuestionNewActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);


        if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {

            for (PracticeTestQuestion story : lessonobj.getPractice_test_questions()) {
                for (PracticeTestQuestion question : story.getPractice_test_questions()) {
                    question.setStory(story.getStory());

                    practicequestionsarr.add(question);
                }
            }
            Log.d(TAG, "setStoryQuesion2: " + practicequestionsarr.size());
            setStoryQuesion(questionNo);
        } else {
            setQuestion(questionNo);
        }
    }


    public void setStoryQuesion(int i) {

        PracticeQuestionNewActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "setStoryQuesion1: " + practicequestionsarr.size());
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

                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });


    }

    public void setQuestion(int i) {


        PracticeQuestionNewActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

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
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
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
                            check1.setBackgroundResource(R.drawable.check_fill);

                        } else if (rightoption == 2) {
                            clearallchecks();
                            check2.setBackgroundResource(R.drawable.check_fill);

                        } else if (rightoption == 3) {
                            clearallchecks();
                            check3.setBackgroundResource(R.drawable.check_fill);

                        } else if (rightoption == 4) {
                            clearallchecks();
                            check4.setBackgroundResource(R.drawable.check_fill);

                        } else if (rightoption == 5) {
                            clearallchecks();
                            check5.setBackgroundResource(R.drawable.check_fill);

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
                            check1.setBackgroundResource(R.drawable.check_fill);
                        } else if (rightoption == 2) {
                            clearallchecks();
                            check2.setBackgroundResource(R.drawable.check_fill);
                        } else if (rightoption == 3) {
                            clearallchecks();
                            check3.setBackgroundResource(R.drawable.check_fill);
                        } else if (rightoption == 4) {
                            clearallchecks();
                            check4.setBackgroundResource(R.drawable.check_fill);
                        } else if (rightoption == 5) {
                            clearallchecks();
                            check5.setBackgroundResource(R.drawable.check_fill);
                        } else {
                            clearallchecks();
                        }
                    }
                }
            }
        }


    }

    public void clearallchecks() {
        PracticeQuestionNewActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                check1.setBackgroundResource(R.drawable.check);
                check2.setBackgroundResource(R.drawable.check);
                check3.setBackgroundResource(R.drawable.check);
                check4.setBackgroundResource(R.drawable.check);
                check5.setBackgroundResource(R.drawable.check);
            }
        });

    }

    private void clearAllWebviews() {
        PracticeQuestionNewActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                opt1.clearCache(true);
                opt2.clearCache(true);
                opt3.clearCache(true);
                opt4.clearCache(true);
                myWebView1.clearCache(true);
            }
        });

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
                    Intent intent = new Intent(PracticeQuestionNewActivity.this, ResultsNewActivity.class);
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
                if (practicequestionsarr.get(questionNo) != null) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 1);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                    clearallchecks();
                    answerno = 1;
                    check1.setBackgroundResource(R.drawable.check_fill);
                }
                break;

            case R.id.rltvlyt2:

                if (practicequestionsarr.get(questionNo) != null) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 2);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                    answerno = 2;
                    clearallchecks();
                    check2.setBackgroundResource(R.drawable.check_fill);

                }

                break;

            case R.id.rltvlyt3:
                if (practicequestionsarr.get(questionNo) != null) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 3);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                    clearallchecks();
                    answerno = 3;
                    check3.setBackgroundResource(R.drawable.check_fill);
                }
                break;

            case R.id.rltvlyt4:
                if (practicequestionsarr.get(questionNo) != null) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 4);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                    clearallchecks();
                    answerno = 4;
                    check4.setBackgroundResource(R.drawable.check_fill);
                }
                break;


            case R.id.rltvlyt5:
                if (practicequestionsarr.get(questionNo) != null) {
                    answreshasmap.put(practicequestionsarr.get(questionNo).getSerial_no(), 5);
                    answerQuestions.add(practicequestionsarr.get(questionNo).getSerial_no());
                    clearallchecks();
                    answerno = 5;
                    check1.setBackgroundResource(R.drawable.check_fill);
                }
                break;

            case R.id.nextbutton:
                Boolean shouldloadnext = false;

                if (questionNo != lessonobj.getPractice_test_total() - 1) {
                    questionNo = questionNo + 1;
                    shouldloadnext = true;
                    Log.d(TAG, "2onClick:questionNo "+questionNo);
                    String questionid = practicequestionsarr.get(questionNo -1).getId();
                    quesIds.add(questionid);
                    if (answerno != 0) {
                        dialog.show();
                        submitAnswer(questionid, String.valueOf(answerno), lessonobj.getPractice_test_total(), questionNo + 1, shouldloadnext);

                    } else {


                        if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")) {
                            setStoryQuesion(questionNo);
                        } else {
                            setQuestion(questionNo);
                        }
                    }
                }
                break;

            case R.id.previousbtn:

                if (questionNo != 0) {
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
                Log.d(TAG, "setStoryQuesion3: " + practicequestionsarr.size());
                StringBuilder stringBuilder = new StringBuilder();
                if (unAnswerQuestions.size() <= 1) {
                    Log.d(TAG, "1onClick:questionNo "+questionNo);
                    String questionid = practicequestionsarr.get(questionNo).getId();
                    quesIds.add(questionid);
                    for (int i = 0; i < quesIds.size(); i++) {
                        Log.d(TAG, "onClick:queids:: " + quesIds.get(i));
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
}



