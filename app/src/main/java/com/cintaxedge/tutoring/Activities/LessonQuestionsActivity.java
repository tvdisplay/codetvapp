package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

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

import com.cintaxedge.tutoring.Models.PracticeTestQuestion;
import com.cintaxedge.tutoring.Models.homework_questions;

import com.cintaxedge.tutoring.Models.ViewLesson;
import com.cintaxedge.tutoring.R;

import java.net.URLDecoder;
import java.util.ArrayList;

import io.github.kexanie.library.MathView;

public class LessonQuestionsActivity extends AppCompatActivity {

    ViewLesson lessonobj;
    TextView title, rightAnswer, questionNumber;
    WebView myWebView1, opt1, opt2, opt3, opt4,opt5,storyWebview;
    WebView explainwebview;
    String lessonname;
    int questionNo = 0;
    ImageView back;
    Button viewAnswer;
    RelativeLayout answerReltv;
    RelativeLayout prev, next;
    TextView explainantiontv;
    ImageView previmg, nextimg;
    ArrayList<homework_questions> homeworkquestionsarr = new ArrayList<>();
    ArrayList<homework_questions> homeworkStoryquestionsarr = new ArrayList<>();
    RelativeLayout rltvoption1,rltvoption2,rltvoption3,rltvoption4,rltvoption5,rltvlyte;
    private static final String TAG = "LessonQuestionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_questions);
        init();

        viewAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerReltv.setVisibility(View.VISIBLE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionNo < lessonobj.getHomework_total() - 1) {
                    questionNo = questionNo + 1;
                    if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")  ){
                        setStroyQuestion(questionNo);
                    }else {
                        setQuestion(questionNo);
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionNo > 0) {
                    questionNo = questionNo - 1;
                    if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")  ){
                        setStroyQuestion(questionNo);
                    }else {
                        setQuestion(questionNo);
                    }
                }
            }
        });
    }

    public void init() {
        title = findViewById(R.id.title);
        opt1 = findViewById(R.id.webview2);
        opt2 = findViewById(R.id.webview3);
        opt3 = findViewById(R.id.webview4);
        opt4 = findViewById(R.id.webview5);
        opt5 = findViewById(R.id.webviewe);
        back = findViewById(R.id.backimg);
        explainwebview = findViewById(R.id.webview6);
        prev = findViewById(R.id.previousbtn);
        next = findViewById(R.id.nextbutton);
        previmg = findViewById(R.id.previmg);
        storyWebview = findViewById(R.id.storywebview);
        nextimg = findViewById(R.id.nextimg);
        questionNumber = findViewById(R.id.questionnumber);
        rightAnswer = findViewById(R.id.answerright);
        rltvlyte = findViewById(R.id.rltvlyte);
        lessonobj = (ViewLesson) getIntent().getSerializableExtra("lessonobj");

        explainantiontv = findViewById(R.id.explainantion);

        myWebView1 = findViewById(R.id.webview1);
        viewAnswer = findViewById(R.id.submit);
        answerReltv = findViewById(R.id.answerrelative);
        if (lessonobj.getCat_id().equals("19") || lessonobj.getCat_id().equals("88")  ){
            for ( homework_questions story : lessonobj.getHomework_list() ){
                for (homework_questions question: story.getQuestions() ){
                    Log.d(TAG, "init:story "+story.getStory());
                    Log.d(TAG, "init: questions:"+question.getQuestion());

                    question.setStory(story.getStory());
                    homeworkStoryquestionsarr.add(question);

                }
            }
            setStroyQuestion( questionNo );


        }else {
            setQuestion(questionNo);
        }

    }




    public void setStroyQuestion(int i){

        setIcons();
        clearAllWebviews();
        answerReltv.setVisibility(View.GONE);
        //homeworkquestionsarr = lessonobj.getHomework_questions();
        questionNumber.setText(i + 1 + "\\" + lessonobj.getHomework_total());



        String question = "", story = "", answer1, answer2, answer3, answer4,answer5="", explainantion, correctAnswer;
        story =  homeworkStoryquestionsarr.get(i).getStory();
        question = homeworkStoryquestionsarr.get(i).getQuestion();
        answer1 = homeworkStoryquestionsarr.get(i).getAnswers().get(0).getAnswer();
        answer2 = homeworkStoryquestionsarr.get(i).getAnswers().get(1).getAnswer();
        answer3 = homeworkStoryquestionsarr.get(i).getAnswers().get(2).getAnswer();
        answer4 = homeworkStoryquestionsarr.get(i).getAnswers().get(3).getAnswer();
        if (homeworkStoryquestionsarr.get(i).getAnswers().size() == 5){
            answer5 = homeworkStoryquestionsarr.get(i).getAnswers().get(4).getAnswer();
            rltvlyte.setVisibility(View.VISIBLE);
        }else {
            rltvlyte.setVisibility(View.GONE);
        }

        explainantion = homeworkStoryquestionsarr.get(i).getQuestion_explanation();
        correctAnswer = homeworkStoryquestionsarr.get(i).getCorrect_answer();
        rightAnswer.setText("Answer: " + correctAnswer);



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
        String answer5htmlstring = "";
        if (answer5 != null ){
             answer5htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                    "<body style=\"font-size:100%; font-family: Arial\">" + answer5 + "</body></html>";
        }


        String res=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            res=Html.fromHtml(explainantion,Html.FROM_HTML_MODE_COMPACT).toString();
        }
        else{
            res=Html.fromHtml(explainantion).toString();
        }
        if (res.equals("")){
            explainantiontv.setVisibility(View.GONE);
        }

        String explainantionhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + res + "</body></html>";

        String storyencodedHtml = Base64.encodeToString(storyhtmlstring.getBytes(), Base64.NO_PADDING);
        storyWebview.getSettings().setJavaScriptEnabled(true);
        storyWebview.loadData(storyencodedHtml, "text/html", "base64");


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

        String encodedHtmle = Base64.encodeToString(answer5htmlstring.getBytes(), Base64.NO_PADDING);
        opt5.getSettings().setJavaScriptEnabled(true);
        opt5.loadData(encodedHtmle, "text/html", "base64");



        String encodedHtml5 = Base64.encodeToString(explainantionhtmlstring.getBytes(), Base64.NO_PADDING);
        explainwebview.getSettings().setJavaScriptEnabled(true);
        explainwebview.loadData(encodedHtml5, "text/html", "base64");


    }
    public void setQuestion(int i) {

        setIcons();
        clearAllWebviews();
        answerReltv.setVisibility(View.GONE);
        homeworkquestionsarr = lessonobj.getHomework_questions();
        questionNumber.setText(i + 1 + "\\" + lessonobj.getHomework_total());

        String question = "", answer1, answer2, answer3, answer4,answer5="", explainantion, correctAnswer;
        question = homeworkquestionsarr.get(i).getQuestion();
        answer1 = homeworkquestionsarr.get(i).getAnswers().get(0).getAnswer();
        answer2 = homeworkquestionsarr.get(i).getAnswers().get(1).getAnswer();
        answer3 = homeworkquestionsarr.get(i).getAnswers().get(2).getAnswer();
        answer4 = homeworkquestionsarr.get(i).getAnswers().get(3).getAnswer();
        if ( homeworkquestionsarr.get(i).getAnswers().size() == 5){
            answer5 = homeworkquestionsarr.get(i).getAnswers().get(4).getAnswer();
            rltvlyte.setVisibility(View.VISIBLE);
        }else {
            rltvlyte.setVisibility(View.GONE);
        }



        explainantion = homeworkquestionsarr.get(i).getQuestion_explanation();
        correctAnswer = homeworkquestionsarr.get(i).getCorrect_answer();
         rightAnswer.setText("Answer: " + correctAnswer);



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

        String res=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            res=Html.fromHtml(explainantion,Html.FROM_HTML_MODE_COMPACT).toString();
        }
        else{
            res=Html.fromHtml(explainantion).toString();
        }
        if (res.equals("")){
            explainantiontv.setVisibility(View.GONE);
        }
        String explainantionhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + res + "</body></html>";


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


        String encodedHtmle = Base64.encodeToString(answer5htmlstring.getBytes(), Base64.NO_PADDING);
        opt5.getSettings().setJavaScriptEnabled(true);
        opt5.loadData(encodedHtmle, "text/html", "base64");


        String encodedHtml5 = Base64.encodeToString(explainantionhtmlstring.getBytes(), Base64.NO_PADDING);
      //  Html.fromHtml((String) encodedHtml5).toString();
        explainwebview.getSettings().setJavaScriptEnabled(true);
        explainwebview.loadData(encodedHtml5, "text/html", "base64");



    }

    private void clearAllWebviews() {
        opt1.clearCache(true);
        opt2.clearCache(true);
        opt3.clearCache(true);
        opt4.clearCache(true);
        myWebView1.clearCache(true);
        explainwebview.clearCache(true);
    }

    public void setIcons() {
        if (questionNo == 0) {
            previmg.setImageResource(R.drawable.previousgray);
        } else {
            previmg.setImageResource(R.drawable.previousblack);
        }
        if (questionNo == lessonobj.getHomework_total() - 1) {
            nextimg.setImageResource(R.drawable.nextgray);
        } else {
            nextimg.setImageResource(R.drawable.nextblack);
        }
    }
}