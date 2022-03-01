package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.PracticeTestQuestion;
import com.cintaxedge.tutoring.Models.Qlist;
import com.cintaxedge.tutoring.Models.RightAnswers;
import com.cintaxedge.tutoring.Models.StoryQuestionsAnswers;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    String catId, lessonId;
    TextView catname, lessonname, date, ttlQ, ttlAns, ttlCRAns, ttlWrAns, scores;
    WebView questionwv, op1wv, op2wv, op3wv, op4wv, op5wv, explainwv;
    private static final String TAG = "ResultsActivity";
    ImageView previmg, nextimg;
    RelativeLayout prev, next,explainrltv;
    RightAnswers result;
    int questionNo = 0;
    RelativeLayout wrongbtn;
    ImageView back;
    ArrayList<StoryQuestionsAnswers> storyQuestionsAnswersArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getIncomingIntent();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionNo < result.getQlist().size() - 1) {
                    questionNo = questionNo + 1;
                    setquestion(result, questionNo);
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionNo > 0) {
                    questionNo = questionNo - 1;
                    setquestion(result, questionNo);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    public void getIncomingIntent() {
        init();
        catId = getIntent().getStringExtra("catId");
        lessonId = getIntent().getStringExtra("lessonId");
        getResults();
    }

    public void init() {
        catname = findViewById(R.id.catname);
        lessonname = findViewById(R.id.lessonname);
        date = findViewById(R.id.date);
        ttlQ = findViewById(R.id.tvtotalquestns);
        ttlAns = findViewById(R.id.totalans);
        ttlCRAns = findViewById(R.id.totalcrtans);
        ttlWrAns = findViewById(R.id.totalrongans);
        scores = findViewById(R.id.scores);
        back = findViewById(R.id.backarrow);
        explainrltv = findViewById(R.id.answerrelative);

        questionwv = findViewById(R.id.questionwebview);
        op1wv = findViewById(R.id.op1webview);
        op2wv = findViewById(R.id.op2webview);
        op3wv = findViewById(R.id.op3webview);
        op4wv = findViewById(R.id.op4webview);
        op5wv = findViewById(R.id.op5webview);
        explainwv = findViewById(R.id.explainwebview);

        previmg = findViewById(R.id.previmg);
        nextimg = findViewById(R.id.nextimg);
        prev = findViewById(R.id.previousbtn);
        next = findViewById(R.id.nextbutton);
        wrongbtn = findViewById(R.id.wronganswerbtn);


    }

    public void getResults() {
        WebAPI.getResults(getApplicationContext(), catId, lessonId, (status, res) -> {
            if (status) {
                result = res;
                Log.d(TAG, "getResults:catId " + catId);
                if (catId.equals("19") || catId.equals("88")) {
//                    Log.d(TAG, "getResults:size res" + res.getQlist().get(0));
//                    Log.d(TAG, "getResults: res1:" + res.getQlist().toString());
                    setStory(result);
                } else {
                    setData(result);
                }
            }
        });
    }

    public void setStory(RightAnswers result) {
        ResultsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    catname.setText(result.getReport().getCat_name());
                    lessonname.setText(result.getReport().getLesson_name());
                    date.setText(result.getReport().getDate());
                    ttlQ.setText(result.getReport().getTotal_questions());
                    ttlAns.setText(result.getReport().getTotal_questions());
                    ttlCRAns.setText(result.getReport().getCorrect());
                    ttlWrAns.setText(result.getReport().getWrong());
                    scores.setText(result.getReport().getScore());


                    for (Qlist story : result.getQlist()) {
                        for (StoryQuestionsAnswers question : story.getQuestions()) {
                            Log.d(TAG, "init:story " + story.getStory());
                            Log.d(TAG, "init: questions:" + question.getQuestion());

                            question.setStory(story.getStory());
                            storyQuestionsAnswersArr.add(question);

                        }
                    }
                    setStoryquestion(result, questionNo);
                } catch (Exception e) {

                }
            }
        });

    }

    public void setData(RightAnswers result) {

        ResultsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                catname.setText(result.getReport().getCat_name());
                lessonname.setText(result.getReport().getLesson_name());
                date.setText(result.getReport().getDate());
                ttlQ.setText(result.getReport().getTotal_questions());
                ttlAns.setText(result.getReport().getTotal_questions());
                ttlCRAns.setText(result.getReport().getCorrect());
                ttlWrAns.setText(result.getReport().getWrong());
                scores.setText(result.getReport().getScore());
                setquestion(result, questionNo);
            }
        });
    }

    public void setStoryquestion(RightAnswers result, int questionno) {
        setIcons(result);
        clearAllWebviews();
        String question, opt1, opt2, opt3, opt4, opt5 = "", explain, serial;

        explain = storyQuestionsAnswersArr.get(questionno).getExplanation();
        question = storyQuestionsAnswersArr.get(questionno).getStory();
        opt1 = storyQuestionsAnswersArr.get(questionno).getAnswers().get(0);
        opt2 = storyQuestionsAnswersArr.get(questionno).getAnswers().get(1);
        opt3 = storyQuestionsAnswersArr.get(questionno).getAnswers().get(2);
        opt4 = storyQuestionsAnswersArr.get(questionno).getAnswers().get(3);
        if (storyQuestionsAnswersArr.get(questionno).getAnswers().size() == 5) {
            opt5 = storyQuestionsAnswersArr.get(questionno).getAnswers().get(4);
            op5wv.setVisibility(View.VISIBLE);
        } else {
            op5wv.setVisibility(View.GONE);
        }



        if (explain.equals("")){
            explainrltv.setVisibility(View.GONE);
        }else {
            explainrltv.setVisibility(View.VISIBLE);
        }

        serial = String.valueOf(result.getQlist().get(questionno).getSrno());

        String questionhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + serial + " " + question + "</body></html>";
        String opt1htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "A. " + opt1 + "</body></html>";
        String opt2htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "B. " + opt2 + "</body></html>";
        String opt3htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "C. " + opt3 + "</body></html>";
        String opt4htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "D. " + opt4 + "</body></html>";
        String opt5htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "E. " + opt5 + "</body></html>";

        String explainhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + explain + "</body></html>";


        ResultsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String qstnencodedHtml = Base64.encodeToString(questionhtmlstring.getBytes(), Base64.NO_PADDING);
                questionwv.getSettings().setJavaScriptEnabled(true);
                questionwv.loadData(qstnencodedHtml, "text/html", "base64");

                String opt1encodedHtml = Base64.encodeToString(opt1htmlstring.getBytes(), Base64.NO_PADDING);
                op1wv.getSettings().setJavaScriptEnabled(true);
                op1wv.loadData(opt1encodedHtml, "text/html", "base64");

                String opt2encodedHtml = Base64.encodeToString(opt2htmlstring.getBytes(), Base64.NO_PADDING);
                op2wv.getSettings().setJavaScriptEnabled(true);
                op2wv.loadData(opt2encodedHtml, "text/html", "base64");

                String opt3encodedHtml = Base64.encodeToString(opt3htmlstring.getBytes(), Base64.NO_PADDING);
                op3wv.getSettings().setJavaScriptEnabled(true);
                op3wv.loadData(opt3encodedHtml, "text/html", "base64");

                String opt4encodedHtml = Base64.encodeToString(opt4htmlstring.getBytes(), Base64.NO_PADDING);
                op4wv.getSettings().setJavaScriptEnabled(true);
                op4wv.loadData(opt4encodedHtml, "text/html", "base64");


                String opt5encodedHtml = Base64.encodeToString(opt5htmlstring.getBytes(), Base64.NO_PADDING);
                op5wv.getSettings().setJavaScriptEnabled(true);
                op5wv.loadData(opt5encodedHtml, "text/html", "base64");


                String explainencodedHtml = Base64.encodeToString(explainhtmlstring.getBytes(), Base64.NO_PADDING);
                explainwv.getSettings().setJavaScriptEnabled(true);
                explainwv.loadData(explainencodedHtml, "text/html", "base64");

                setBackgroundWebview();
            }
        });


    }

    public void setquestion(RightAnswers result, int questionno) {
        setIcons(result);
        clearAllWebviews();
        String question, opt1, opt2, opt3, opt4, opt5 = "", explain, serial;

        question = result.getQlist().get(questionno).getQuestion();
        opt1 = result.getQlist().get(questionno).getAnswers().get(0);
        opt2 = result.getQlist().get(questionno).getAnswers().get(1);
        opt3 = result.getQlist().get(questionno).getAnswers().get(2);
        opt4 = result.getQlist().get(questionno).getAnswers().get(3);
        if (result.getQlist().get(questionno).getAnswers().size() == 5) {
            opt5 = result.getQlist().get(questionno).getAnswers().get(4);
            op5wv.setVisibility(View.VISIBLE);
        } else {
            op5wv.setVisibility(View.GONE);
        }

        explain = result.getQlist().get(questionno).getExplanation();
        Log.d(TAG, "setquestion: " + result.getQlist().get(questionno).getSrno());
        serial = String.valueOf(result.getQlist().get(questionno).getSrno());

        String questionhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + serial + " " + question + "</body></html>";
        String opt1htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "A. " + opt1 + "</body></html>";
        String opt2htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "B. " + opt2 + "</body></html>";
        String opt3htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "C. " + opt3 + "</body></html>";
        String opt4htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "D. " + opt4 + "</body></html>";
        String opt5htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + "E. " + opt5 + "</body></html>";

        String explainhtmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + explain + "</body></html>";


        ResultsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String qstnencodedHtml = Base64.encodeToString(questionhtmlstring.getBytes(), Base64.NO_PADDING);
                questionwv.getSettings().setJavaScriptEnabled(true);
                questionwv.loadData(qstnencodedHtml, "text/html", "base64");

                String opt1encodedHtml = Base64.encodeToString(opt1htmlstring.getBytes(), Base64.NO_PADDING);
                op1wv.getSettings().setJavaScriptEnabled(true);
                op1wv.loadData(opt1encodedHtml, "text/html", "base64");

                String opt2encodedHtml = Base64.encodeToString(opt2htmlstring.getBytes(), Base64.NO_PADDING);
                op2wv.getSettings().setJavaScriptEnabled(true);
                op2wv.loadData(opt2encodedHtml, "text/html", "base64");

                String opt3encodedHtml = Base64.encodeToString(opt3htmlstring.getBytes(), Base64.NO_PADDING);
                op3wv.getSettings().setJavaScriptEnabled(true);
                op3wv.loadData(opt3encodedHtml, "text/html", "base64");

                String opt4encodedHtml = Base64.encodeToString(opt4htmlstring.getBytes(), Base64.NO_PADDING);
                op4wv.getSettings().setJavaScriptEnabled(true);
                op4wv.loadData(opt4encodedHtml, "text/html", "base64");


                String opt5encodedHtml = Base64.encodeToString(opt5htmlstring.getBytes(), Base64.NO_PADDING);
                op5wv.getSettings().setJavaScriptEnabled(true);
                op5wv.loadData(opt5encodedHtml, "text/html", "base64");


                String explainencodedHtml = Base64.encodeToString(explainhtmlstring.getBytes(), Base64.NO_PADDING);
                explainwv.getSettings().setJavaScriptEnabled(true);
                explainwv.loadData(explainencodedHtml, "text/html", "base64");

                setBackgroundWebview();
            }
        });
    }


    public void setIcons(RightAnswers result) {
        if (questionNo == 0) {
            previmg.setImageResource(R.drawable.previousgray);
        } else {
            previmg.setImageResource(R.drawable.previousblack);
        }
        if (questionNo == result.getQlist().size() - 1) {
            nextimg.setImageResource(R.drawable.nextgray);
        } else {
            nextimg.setImageResource(R.drawable.nextblack);
        }
    }


    private void clearAllWebviews() {
        questionwv.clearCache(true);
        op1wv.clearCache(true);
        op1wv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        op2wv.clearCache(true);
        op2wv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        op3wv.clearCache(true);
        op3wv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        op4wv.clearCache(true);
        op4wv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        explainwv.clearCache(true);
        op5wv.clearCache(true);
        op5wv.setBackgroundColor(Color.parseColor("#FFFFFF"));

    }

    public void setBackgroundWebview() {
        String currentString="";
        if (catId.equals("19") || catId.equals("88")){
            if (storyQuestionsAnswersArr.get(questionNo) != null){
                currentString = storyQuestionsAnswersArr.get(questionNo).getCorrect_answer();
            }
        }else {
            currentString = result.getQlist().get(questionNo).getCorrect_answer();
        }

        String[] separated = currentString.split("|");
        String first = separated[0];

        if (!currentString.contains("Correct")) {

            wrongbtn.setVisibility(View.VISIBLE);
            if (first.trim().equals("A")) {
                op1wv.setBackgroundColor(Color.parseColor("#00FF00"));
            } else if (first.trim().equals("B")) {
                op2wv.setBackgroundColor(Color.parseColor("#00FF00"));
            } else if (first.trim().equals("C")) {
                op3wv.setBackgroundColor(Color.parseColor("#00FF00"));
            } else if (first.trim().equals("D")) {
                op4wv.setBackgroundColor(Color.parseColor("#00FF00"));
            } else if (first.trim().equals("E")) {
                op5wv.setBackgroundColor(Color.parseColor("#00FF00"));
            }

            if (separated[19].trim().equals("A")) {
                op1wv.setBackgroundColor(Color.parseColor("#FF0000"));
            } else if (separated[19].trim().equals("B")) {
                op2wv.setBackgroundColor(Color.parseColor("#FF0000"));
            } else if (separated[19].trim().equals("C")) {
                op3wv.setBackgroundColor(Color.parseColor("#FF0000"));
            } else if (separated[19].trim().equals("D")) {
                op4wv.setBackgroundColor(Color.parseColor("#FF0000"));
            } else if (separated[19].trim().equals("E")) {
                op5wv.setBackgroundColor(Color.parseColor("#FF0000"));
            }
        } else {
            wrongbtn.setVisibility(View.GONE);
            if (first.contains("A")) {
                op1wv.setBackgroundColor(Color.parseColor("#00FF00"));
            } else if (first.contains("B")) {
                op2wv.setBackgroundColor(Color.parseColor("#00FF00"));
            } else if (first.contains("C")) {
                op3wv.setBackgroundColor(Color.parseColor("#00FF00"));
            } else if (first.contains("D")) {
                op4wv.setBackgroundColor(Color.parseColor("#00FF00"));
            } else if (first.contains("E")) {
                op5wv.setBackgroundColor(Color.parseColor("#00FF00"));
            }
        }

    }
}