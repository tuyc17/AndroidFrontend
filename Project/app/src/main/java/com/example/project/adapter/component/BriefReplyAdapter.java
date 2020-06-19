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
import com.example.project.activity.CommunicateActivity;
import com.example.project.activity.ReplyDetailActivity;
import com.example.project.component.BriefReply;

import java.util.List;

public class BriefReplyAdapter extends RecyclerView.Adapter<BriefReplyAdapter.ViewHolder> {
    private List<BriefReply> replies;
    private Context context;
    private int index;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username;
        TextView content;

        public ViewHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            username = view.findViewById(R.id.username);
            content = view.findViewById(R.id.content);
        }
    }

    public BriefReplyAdapter(Context context, int position, List<BriefReply> replies) {
        this.replies = replies;
        this.context = context;
        this.index = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_brief_reply, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BriefReply reply = replies.get(position);
        holder.avatar.setImageResource(reply.imageId);
        holder.username.setText(reply.username);
        holder.content.setText(reply.content);

        if(index == 0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(context, ReplyDetailActivity.class);
                    context.startActivity(it);
                }
            });
        }
        else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(context, CommunicateActivity.class);
                    context.startActivity(it);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }
}
