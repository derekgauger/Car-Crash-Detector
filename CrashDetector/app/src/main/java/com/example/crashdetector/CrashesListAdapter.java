package com.example.crashdetector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrashesListAdapter extends RecyclerView.Adapter<CrashListViewHolder> {

    Context context;
    List<Crash> crashes;

    public CrashesListAdapter(Context context, List<Crash> crashes) {
        this.context = context;
        this.crashes = crashes;
    }

    @NonNull
    @Override
    public CrashListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CrashListViewHolder(LayoutInflater.from(context).inflate(R.layout.crash_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CrashListViewHolder holder, int position) {
        holder.timeView.setText(crashes.get(position).time);
        holder.typeView.setText(crashes.get(position).type);
        holder.speedView.setText(String.valueOf(crashes.get(position).speed));
        holder.accelerationView.setText(String.valueOf(crashes.get(position).acceleration));
    }

    @Override
    public int getItemCount() {
        return crashes.size();
    }
}
