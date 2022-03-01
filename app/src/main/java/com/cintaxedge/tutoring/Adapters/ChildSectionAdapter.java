package com.cintaxedge.tutoring.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cintaxedge.tutoring.Activities.PracticeQuestionActivity;
import com.cintaxedge.tutoring.Activities.PracticeQuestionNewActivity;
import com.cintaxedge.tutoring.Models.AllTest;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;


public class ChildSectionAdapter extends RecyclerView.Adapter<ChildSectionAdapter.ViewHolder> {


    ArrayList<AllTest> items;
    private static final String TAG = "ChildSectionAdapter";
    private ArrayList<AllTest> name = new ArrayList<>();
    private Context mContext;

    public ChildSectionAdapter(ArrayList<AllTest> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mathskills, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // holder.name.setText(items.get(position));
         holder.parentLayout.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.blueposhish));
        Glide.with(mContext)
                .asBitmap()
                .load(items.get(position).getIcon())
                .into(holder.mImage);

         holder.name.setText(items.get(position).getName());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+"c"+items.get(position).getCat_id()+"lessonId"+items.get(position).getLesson_id());
                Intent intent = new Intent(mContext, PracticeQuestionNewActivity.class);
                intent.putExtra("catId",items.get(position).getCat_id());
                intent.putExtra("lessonId",items.get(position).getLesson_id());
                mContext.startActivity(intent);
            }
        });
        
        
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView mImage;
        TextView name;
        CardView parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.imagemain);
            name = itemView.findViewById(R.id.txttitle);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }


}
