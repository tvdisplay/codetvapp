package com.cintaxedge.tutoring.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cintaxedge.tutoring.Adapters.LessonsAdapter;
import com.cintaxedge.tutoring.Api.WebAPI;
import com.cintaxedge.tutoring.Models.Lesson;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;

public class LessonsActivity extends AppCompatActivity {

    String catid,catname;
    ImageView back;
    private static final String TAG = "LessonsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void init(){
        catid = getIntent().getStringExtra("id");
        catname = getIntent().getStringExtra("catname");
        getLessonsData();
        back = findViewById(R.id.backarrow);
    }

    public void getLessonsData(){
        WebAPI.allLessons(getApplicationContext(),catid,(arr,status)->{


            if (status){
                LessonsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        initrecycler(arr);
                    }
                });

            }else {
                LessonsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LessonsActivity.this, "No Data to show", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }
    public void initrecycler(ArrayList<Lesson> arrlesson) {

        Log.d(TAG, "initrecycler: arrlsn"+arrlesson.get(1).getName()

        );
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lessonlistrecycler);
        LessonsAdapter adapter = new LessonsAdapter(catid,arrlesson, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}