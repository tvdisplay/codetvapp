package com.cintaxedge.tutoring.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cintaxedge.tutoring.Activities.LessonDashboardActivity;
import com.cintaxedge.tutoring.Activities.SubscribeActivity;
import com.cintaxedge.tutoring.Models.Lesson;
import com.cintaxedge.tutoring.Models.Prefrences;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;


public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {


    String cat_id;
    private ArrayList<Lesson> lesson = new ArrayList<>();
    private Context mContext;

    public LessonsAdapter(String cat_id, ArrayList<Lesson> lesson, Context mContext) {
        this.cat_id = cat_id;
        this.lesson = lesson;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lessons, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(lesson.get(position).getIcon())
                .into(holder.mImage);


        if (lesson.get(position).getPaid() == 1) {
            holder.lock.setVisibility(View.VISIBLE);
        }

        holder.name.setText(lesson.get(position).getName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "lessonid:"+lesson.get(position).getLesson_id(), Toast.LENGTH_SHORT).show();
                if (lesson.get(position).getPaid() == 0) {
                    Intent intent = new Intent(mContext, LessonDashboardActivity.class);
                    intent.putExtra("catid", cat_id);
                    intent.putExtra("name", lesson.get(position).getName());
                    intent.putExtra("lessonid", lesson.get(position).getLesson_id());
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, SubscribeActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lesson.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView mImage, lock;
        TextView name;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.imagemain);
            name = itemView.findViewById(R.id.txttitle);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            lock = itemView.findViewById(R.id.lock);

        }
    }
}
