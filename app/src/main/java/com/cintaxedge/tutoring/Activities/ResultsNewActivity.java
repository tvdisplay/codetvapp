package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.Qlist;
import com.cintaxedge.tutoring.Models.RightAnswers;
import com.cintaxedge.tutoring.Models.StoryQuestionsAnswers;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;

public class ResultsNewActivity extends AppCompatActivity {
    String catId, lessonId;
    TextView catname, lessonname, date, ttlQ, ttlAns, ttlCRAns, ttlWrAns, scores;
    WebView storywv, questionwv, op1wv, op2wv, op3wv, op4wv, op5wv, explainwv;
    private static final String TAG = "ResultsActivity";
    ImageView previmg, nextimg;
    RelativeLayout prev, next, explainrltv;
    static RightAnswers result;
    int questionNo = 0;
    RelativeLayout wrongbtn;
    ProgressBar pbar;
    ImageView back;

    ArrayList<Qlist> AnswersArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_new);
        getIncomingIntent();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionNo < AnswersArr.size() ) {
                    questionNo = questionNo + 1;
                    setQuestions();
                }

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionNo > 0) {
                    questionNo = questionNo - 1;
                    setQuestions();
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
        pbar = findViewById(R.id.progbar);

        storywv = findViewById(R.id.storywebview);
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

        ResultsNewActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pbar.setVisibility(View.VISIBLE);
            }
        });
        WebAPI.getResults(getApplicationContext(), catId, lessonId, (status, res) -> {
            ResultsNewActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pbar.setVisibility(View.GONE);
                }
            });
            if (status) {
                result = res;
                if (result != null) {

                    if (result.getReport().getCat_id().equals("19") || result.getReport().getCat_id().equals("88")) {
                        for (Qlist story : result.getQlist()) {
                            for (StoryQuestionsAnswers question : story.getQuestions()) {
                                story.setStory(story.getStory());
                                AnswersArr.add(story);
                                setData();
                            }
                        }
                    } else {
                        AnswersArr = result.getQlist();
                        setData();
                    }
                }

            }
        });
    }


    public void setData() {

        ResultsNewActivity.this.runOnUiThread(new Runnable() {
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
                setQuestions();
            }
        });
    }



    public void setQuestions() {
        setIcons(result);
        clearAllWebviews();
        String story = "", question = "", opt1 = "", opt2 = "", opt3 = "", opt4 = "", opt5 = "", explain = "", serial = "";
        if (result.getReport().getCat_id().equals("13") || result.getReport().getCat_id().equals("88")) {
            story = AnswersArr.get(questionNo).getStory();
        }
        question = AnswersArr.get(questionNo).getQuestion();
        opt1 = AnswersArr.get(questionNo).getAnswers().get(0);
        opt2 = AnswersArr.get(questionNo).getAnswers().get(1);
        opt3 = AnswersArr.get(questionNo).getAnswers().get(2);
        opt4 = AnswersArr.get(questionNo).getAnswers().get(3);


        if (AnswersArr.get(questionNo).getAnswers().size() == 5) {
            opt5 = AnswersArr.get(questionNo).getAnswers().get(4);
            op5wv.setVisibility(View.VISIBLE);
        } else {
            op5wv.setVisibility(View.GONE);
        }
        explain = AnswersArr.get(questionNo).getExplanation();
        serial = String.valueOf(AnswersArr.get(questionNo).getSrno());

        if (!story.equals("")){
            putInWebview(story, storywv);
        }
        String ques = serial + " " + question;
        putInWebview(ques, questionwv);
        putInWebview(opt1, op1wv);
        putInWebview(opt2, op2wv);
        putInWebview(opt3, op3wv);
        putInWebview(opt4, op4wv);
        putInWebview(opt5, op5wv);
        putInWebview(explain, explainwv);
        setBackgroundWebview();

    }


    public void putInWebview(String data, WebView wv) {
        String htmlstring = "<head><meta name='viewport' content='width=device-width, shrink-to-fit=YES' user-scalable='no'><script type=\"text/x-mathjax-config\">MathJax.Hub.Config({tex2jax: {inlineMath: [['\\(','\\)']],processEscapes: true},\"HTML-CSS\": { linebreaks: { automatic: true, width: \"container\" } } } )</script><script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js\" ></script></head>" +
                "<body style=\"font-size:100%; font-family: Arial\">" + data + "</body></html>";
        String qstnencodedHtml = Base64.encodeToString(htmlstring.getBytes(), Base64.NO_PADDING);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadData(qstnencodedHtml, "text/html", "base64");

    }


    public void setIcons(RightAnswers result) {

        if (questionNo == 0) {
            previmg.setImageResource(R.drawable.previousgray);
        } else {
            previmg.setImageResource(R.drawable.previousblack);
        }


        if (catId.equals("19") || catId.equals("88")) {

            if (questionNo == AnswersArr.size() - 1) {
                nextimg.setImageResource(R.drawable.nextgray);
            } else {
                nextimg.setImageResource(R.drawable.nextblack);
            }
        } else {
            if (questionNo == result.getQlist().size() - 1) {
                nextimg.setImageResource(R.drawable.nextgray);
            } else {
                nextimg.setImageResource(R.drawable.nextblack);
            }
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
        String currentString = "";
        if (catId.equals("19") || catId.equals("88")) {
            if (AnswersArr.get(questionNo) != null) {
                currentString = AnswersArr.get(questionNo).getCorrect_answer();
            }
        } else {
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