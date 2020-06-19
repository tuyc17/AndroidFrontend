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

import com.example.project.activity.DetailsActivity;
import com.example.project.activity.ProfileActivity;
import com.example.project.component.Result;
import com.example.project.R;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<Result> results;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username;
        TextView date;
        TextView title;
        TextView content;

        public ViewHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            username = view.findViewById(R.id.username);
            date = view.findViewById(R.id.date);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
        }
    }

    public ResultAdapter(Context context, List<Result>results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_summary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.avatar.setImageResource(result.imageId);
        holder.username.setText(result.username);
        holder.date.setText(result.date);

        if(result.title.equals("")) {
            holder.title.setVisibility(View.GONE);
        }
        else {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(result.title);
        }
        if(result.content.equals("")) {
            holder.content.setVisibility(View.GONE);
        }
        else {
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText(result.content);
        }

        if(result.title.equals("")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到用户详情页
                    Intent it = new Intent(context, ProfileActivity.class);
                    context.startActivity(it);
                }
            });
        }
        else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到消息详情页
                    Intent it = new Intent(context, DetailsActivity.class);
                    context.startActivity(it);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
