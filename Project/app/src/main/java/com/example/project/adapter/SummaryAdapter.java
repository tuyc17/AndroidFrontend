package com.example.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.component.Summary;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    private List<Summary> Summaries;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username;
        TextView date;
        TextView title;
        TextView content;

        public ViewHolder (View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            username = view.findViewById(R.id.username);
            date = view.findViewById(R.id.date);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
        }

    }

    public SummaryAdapter(List<Summary> summaries) {
        Summaries = summaries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Summary summary = Summaries.get(position);
        holder.avatar.setImageResource(summary.imageId);
        holder.username.setText(summary.username);
        holder.date.setText(summary.date);
        holder.title.setText(summary.title);
        holder.content.setText(summary.content);
    }

    @Override
    public int getItemCount() {
        return Summaries.size();
    }
}
