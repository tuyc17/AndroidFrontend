package com.example.project.adapter.component;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.activity.DetailsActivity;
import com.example.project.component.Summary;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    private List<Summary> Summaries;
    private Context context;

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

    public SummaryAdapter(Context context, List<Summary> summaries) {
        this.context = context;
        this.Summaries = summaries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Summary summary = Summaries.get(position);
        holder.avatar.setImageResource(summary.imageId);
        holder.username.setText(summary.username);
        holder.date.setText(summary.date);
        holder.title.setText(summary.title);
        holder.content.setText(summary.content);

        holder.content.setHeight(holder.content.getLineHeight() * 2);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent 需要携带extraData才知道去获取哪个消息的具体信息
                Intent it = new Intent(context, DetailsActivity.class);
                it.putExtra("avatar", summary.imageId);
                it.putExtra("username", summary.username);
                it.putExtra("date", summary.date);
                it.putExtra("title", summary.title);
                it.putExtra("content", summary.content);
                it.putExtra("id", summary.id);
                it.putExtra("authorId", summary.authorId);
                context.startActivity(it);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Summaries.size();
    }
}
