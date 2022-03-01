package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cintaxedge.tutoring.Adapters.LessonDashboardAdapter;
import com.cintaxedge.tutoring.Adapters.LessonsAdapter;
import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.Lesson;
import com.cintaxedge.tutoring.Models.LessonDashboardName;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;

public class LessonDashboardActivity extends AppCompatActivity {

    String catid,lessonId,titlename;
    private static final String TAG = "LessonDashboardActivity";
    ArrayList<String> lessonDashboardNames = new ArrayList<>();
    TextView title;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_dashboard);
        init();

        lessonDashboardNames.add("Instruction");
        lessonDashboardNames.add("Over View");
        lessonDashboardNames.add("Home Work");
        lessonDashboardNames.add("Practice Test");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void init(){

        catid = getIntent().getStringExtra("catid");
        lessonId = getIntent().getStringExtra("lessonid");
        title = findViewById(R.id.titletext);
        titlename = getIntent().getStringExtra("name");
        Log.d(TAG, "init: catid"+catid+lessonId);
        getAllLessonData();
        title.setText(titlename);
        back = findViewById(R.id.backimg);
    }
    public void getAllLessonData(){
        WebAPI.viewLesson(getApplicationContext(),catid,lessonId,(lessonobj,status)->{
            if (status){
                LessonDashboardActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lessondashboardlistrecycler);
//                        if (lessonobj.getHomework_list().size() == 0 || lessonobj.getHomework_questions().size() == 0){
//                            lessonDashboardNames.remove(1);
//                        }
                     //   Toast.makeText(LessonDashboardActivity.this, catid, Toast.LENGTH_SHORT).show();
                        if (catid.equals("93")){
                        }else {
                            lessonDashboardNames.remove(lessonDashboardNames.indexOf("Over View"));
                        }

                        //instruction
                        if (catid.equals("87") || catid.equals("88") || catid.equals("90")){

                        }else {
                            lessonDashboardNames.remove(lessonDashboardNames.indexOf("Instruction"));
                        }
                        //homework
                        if (catid.equals("87") || catid.equals("88") || catid.equals("90")){
                            lessonDashboardNames.remove(lessonDashboardNames.indexOf("Home Work"));
                        }

                        LessonDashboardAdapter adapter = new LessonDashboardAdapter(lessonDashboardNames,lessonobj, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

                    }
                });


            }
        });
    }
}