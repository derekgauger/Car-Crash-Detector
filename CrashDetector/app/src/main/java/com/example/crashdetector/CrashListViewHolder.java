package com.example.crashdetector;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CrashListViewHolder extends RecyclerView.ViewHolder {

    TextView timeView;
    TextView typeView;
    TextView speedView;
    TextView accelerationView;

    public CrashListViewHolder(@NonNull View itemView) {
        super(itemView);
        timeView = itemView.findViewById(R.id.time);
        typeView = itemView.findViewById(R.id.type);
        speedView = itemView.findViewById(R.id.speed);
        accelerationView = itemView.findViewById(R.id.acceleration);
    }
}
