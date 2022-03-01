package com.cintaxedge.tutoring.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cintaxedge.tutoring.Models.AllTest;
import com.cintaxedge.tutoring.Models.SectionAlltests;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;


public class MathSkillsAdapter extends RecyclerView.Adapter<MathSkillsAdapter.ViewHolder> {


    ArrayList<SectionAlltests> sectionList;
    private ArrayList<AllTest> name = new ArrayList<>();
    private Context mContext;
    private static final String TAG = "MathSkillsAdapter";

    public MathSkillsAdapter(ArrayList<SectionAlltests> sectionList, Context mContext) {
        this.sectionList = sectionList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        SectionAlltests namess = sectionList.get(position);
        String sectionName = namess.getSectionName();
        ArrayList<AllTest> items = namess.getSectionItem();
        holder.sectionnametv.setText(sectionName);

        Log.d(TAG, "onBindViewHolder: itemss:"+items.size());
        ChildSectionAdapter childSectionAdapter = new ChildSectionAdapter(items,mContext);
        holder.childRecyclerview.setAdapter(childSectionAdapter);
        holder.childRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
//        holder.parentLayout.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.blueposhish));
//        Glide.with(mContext)
//                .asBitmap()
//                .load(name.get(position).getIcon())
//                .into(holder.mImage);
//
//        holder.name.setText(name.get(position).getName());
//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(mContext, SubCateory.class);
////                mContext.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView mImage;
        TextView name;
        CardView parentLayout;
        RecyclerView childRecyclerview;
        TextView sectionnametv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            mImage = itemView.findViewById(R.id.imagemain);
//            name = itemView.findViewById(R.id.txttitle);
//            parentLayout = itemView.findViewById(R.id.parent_layout);
            sectionnametv = itemView.findViewById(R.id.headingtext);
            childRecyclerview = itemView.findViewById(R.id.sectionrecycler);


        }
    }


}
