package com.cintaxedge.tutoring.Adapters;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cintaxedge.tutoring.Activities.LessonQuestionsActivity;
import com.cintaxedge.tutoring.Activities.LessonsActivity;
import com.cintaxedge.tutoring.Activities.PracticeQuestionActivity;
import com.cintaxedge.tutoring.Activities.PracticeQuestionNewActivity;
import com.cintaxedge.tutoring.Activities.WebviewActivity;
import com.cintaxedge.tutoring.Models.CategoryItem;
import com.cintaxedge.tutoring.Models.ViewLesson;
import com.cintaxedge.tutoring.R;

import java.io.Serializable;
import java.util.ArrayList;


public class LessonDashboardAdapter extends RecyclerView.Adapter<LessonDashboardAdapter.ViewHolder> {


    ArrayList<String> lessonname = new ArrayList<>();
    ViewLesson lessonobj;
    private Context mContext;
    private static final String TAG = "LessonDashboardAdapter";

    public LessonDashboardAdapter(ArrayList<String> lessonname, ViewLesson lessonobj, Context mContext) {
        this.lessonname = lessonname;
        this.lessonobj = lessonobj;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lessondashboard, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (position == 0 || position == 3) {
            holder.parentLayout.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.blue));
        } else if (position == 1) {
            holder.parentLayout.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.orngered));
        } else {
            holder.parentLayout.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.greenlight));
        }
        Glide.with(mContext)
                .asBitmap()
                .load(R.drawable.orangetestprep)
                .into(holder.mImage);
        holder.name.setText(lessonname.get(position));
        if (lessonname.get(position).equals("Over View")) {
            //  holder.count.setText("Total Count"+String.valueOf(lessonobj.()));
        } else if (lessonname.get(position).equals("Home Work")) {
            Log.d(TAG, "onBindViewHolder: lessonobj" + lessonobj.getHomework_total());
            holder.count.setText("Total Count:" + String.valueOf(lessonobj.getHomework_total()));
        } else if (lessonname.get(position).equals("Practice Test")) {
            holder.count.setText("Total Count:" + String.valueOf(lessonobj.getPractice_test_total()));
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lessonname.get(position).equals("Home Work")){
                    if (lessonobj.getHomework_total() > 0){
                        Intent intent = new Intent(mContext, LessonQuestionsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("lessonobj",  lessonobj);
                        intent.putExtra("from",lessonname.get(position));
                        mContext.startActivity(intent);
                    }

                }else if(lessonname.get(position).equals("Practice Test")){
                    if (lessonobj.getPractice_test_total() > 0){
                        Intent intent = new Intent(mContext, PracticeQuestionNewActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("lessonobj",  lessonobj);
                        intent.putExtra("from",lessonname.get(position));
                        mContext.startActivity(intent);
                    }

                }else if (lessonname.get(position).equals("Over View") || lessonname.get(position).equals("Instruction") ){
                    Intent intent = new Intent(mContext, WebviewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("lessonobj",  lessonobj);
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView mImage;
        TextView name, count;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.imagemain);
            name = itemView.findViewById(R.id.txttitle);
            count = itemView.findViewById(R.id.txtcount);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
