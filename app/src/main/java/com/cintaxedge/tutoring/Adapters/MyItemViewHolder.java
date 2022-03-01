package com.cintaxedge.tutoring.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cintaxedge.tutoring.R;

class MyItemViewHolder extends RecyclerView.ViewHolder {
    public final TextView tvItem;

    public MyItemViewHolder(View itemView) {
        super(itemView);

        tvItem = (TextView) itemView.findViewById(R.id.tvItem);
    }
}