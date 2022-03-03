package com.cintaxedge.tutoring.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cintaxedge.tutoring.Activities.FlashCardsActivity;
import com.cintaxedge.tutoring.Activities.LessonDashboardActivity;
import com.cintaxedge.tutoring.Activities.LessonsActivity;

import com.cintaxedge.tutoring.Activities.ResultsNewActivity;
import com.cintaxedge.tutoring.Models.CategoryItem;
import com.cintaxedge.tutoring.Models.PredictionResult;
import com.cintaxedge.tutoring.Models.Result;
import com.cintaxedge.tutoring.Models.ResultAll;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {


    public static final int ITEM_TYPE_pred = 0;
    public static final int ITEM_TYPE_result = 1;

    private ArrayList<Result> result = new ArrayList<>();
    private PredictionResult presult;
    private Context mContext;
    private static final String TAG = "ResultAdapter";

    public ResultAdapter(ArrayList<Result> result, PredictionResult presult, Context mContext) {
        this.result = result;
        this.presult = presult;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_pred) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_prediction_results, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_results, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final int itemType = getItemViewType(position);
        if (itemType == ITEM_TYPE_pred) {
            if (presult.getAfqt_report().getMath_Skills() != null) {
                holder.Math.setText(presult.getAfqt_report().getMath_Skills());
            }
            if (presult.getAfqt_report().getReading_Comprehension() != null) {
                holder.Comprehension.setText(presult.getAfqt_report().getReading_Comprehension());
            }
            if (presult.getAfqt_report().getMechanical_Comprehension() != null) {
                holder.Mechanical.setText(presult.getAfqt_report().getMechanical_Comprehension());
            }
            if (presult.getAfqt_report().getSimple_Drawings() != null) {
                holder.Drawings.setText(presult.getAfqt_report().getSimple_Drawings());
            }
            if (presult.getAfqt_report().getHidden_Figures() != null) {
                holder.Hidden.setText(presult.getAfqt_report().getHidden_Figures());
            }
            if (presult.getAfqt_report().getArmy_Aviation_Information() != null) {
                holder.Aviation.setText(presult.getAfqt_report().getArmy_Aviation_Information());
            }
            if (presult.getAfqt_report().getSpatial_Apperception() != null) {
                holder.Spatial.setText(presult.getAfqt_report().getSpatial_Apperception());
            }
            if (presult.getAfqt_score() != null) {
                holder.siftscore.setText("SIFT Score: " + presult.getAfqt_score());
            }
        } else {

            if (result.get(position).getCategory() != null) {
                holder.catname.setText(result.get(position).getCategory());
            }
            if (result.get(position).getLesson_name() != null) {
                holder.lessonname.setText(result.get(position).getLesson_name());
            }
            if (result.get(position).getDate() != null) {
                holder.date.setText(result.get(position).getDate());
            }
            if (result.get(position).getTotal_questions() != null) {
                holder.ttlQ.setText(result.get(position).getTotal_questions());
            }
            if (result.get(position).getTotal_questions() != null) {
                holder.ttlAns.setText(result.get(position).getTotal_questions());
            }
            if (result.get(position).getCorrect() != null) {
                holder.ttlCRAns.setText(result.get(position).getCorrect());
            }
            if (result.get(position).getWrong() != null) {
                holder.ttlWrAns.setText(result.get(position).getWrong());
            }

            if (result.get(position).getScore() != null) {
                holder.scores.setText(result.get(position).getScore());
            }

            holder.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ResultsNewActivity.class);
                    intent.putExtra("catId", result.get(position).getCat_id());
                    intent.putExtra("lessonId", result.get(position).getLesson_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return result.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == result.size()) {
            return ITEM_TYPE_pred;
        } else {
            return ITEM_TYPE_result;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parentLayout;
        Button submit;
        TextView catname, lessonname, date, ttlQ, ttlAns, ttlCRAns, ttlWrAns, scores;
        TextView Math, Comprehension, Mechanical, Drawings, Hidden, Aviation, Spatial, siftscore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            catname = itemView.findViewById(R.id.catname);
            lessonname = itemView.findViewById(R.id.lessonname);
            date = itemView.findViewById(R.id.date);
            ttlQ = itemView.findViewById(R.id.tvtotalquestns);
            ttlAns = itemView.findViewById(R.id.totalans);
            ttlCRAns = itemView.findViewById(R.id.totalcrtans);
            ttlWrAns = itemView.findViewById(R.id.totalrongans);
            scores = itemView.findViewById(R.id.scores);
            submit = itemView.findViewById(R.id.submit);

            Math = itemView.findViewById(R.id.totalans);
            Comprehension = itemView.findViewById(R.id.totalcrtans);
            Mechanical = itemView.findViewById(R.id.totalrongans);
            Drawings = itemView.findViewById(R.id.scores);
            Hidden = itemView.findViewById(R.id.hiden);
            Aviation = itemView.findViewById(R.id.armyav);
            Spatial = itemView.findViewById(R.id.spcial);
            siftscore = itemView.findViewById(R.id.siftscoretv);

        }
    }
}
