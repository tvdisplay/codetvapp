package com.cintaxedge.tutoring.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.cintaxedge.tutoring.Activities.FlashCardsActivity;
import com.cintaxedge.tutoring.Activities.LessonsActivity;
import com.cintaxedge.tutoring.Models.CategoryItem;
import com.cintaxedge.tutoring.R;

import java.util.ArrayList;



public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{


    private ArrayList<CategoryItem> name = new ArrayList<>();
    private Context mContext;

    public HomeAdapter(ArrayList<CategoryItem> name, Context mContext) {
        this.name = name;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (position == 0 || position == 3 || position == 4 || position == 7 || position ==8 || position == 11 || position ==12 ) {
            holder.parentLayout.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.blue));
        }
        else {
            holder.parentLayout.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.orngered));
        }
        Glide.with(mContext)
                .asBitmap()
                .load(name.get(position).getIcon())
                .into(holder.mImage);
        holder.name.setText(name.get(position).getCat_name());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "catid"+name.get(position).getCat_id(), Toast.LENGTH_SHORT).show();
                if (name.get(position).getCat_id().equals("71")){
                    Intent intent = new Intent(mContext, FlashCardsActivity.class);
                    intent.putExtra("id",name.get(position).getCat_id());
                    intent.putExtra("catname",name.get(position).getCat_name());
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, LessonsActivity.class);
                    intent.putExtra("id",name.get(position).getCat_id());
                    intent.putExtra("catname",name.get(position).getCat_name());
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView mImage;
        TextView name;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage= itemView.findViewById(R.id.imagemain);
            name = itemView.findViewById(R.id.txttitle);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
