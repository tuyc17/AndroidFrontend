package com.example.project.adapter.component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.component.BriefReply;

import java.util.List;

public class BriefReplyAdapter extends RecyclerView.Adapter<BriefReplyAdapter.ViewHolder> {
    private List<BriefReply> replies;

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

    public BriefReplyAdapter(List<BriefReply> replies) {
        this.replies = replies;
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
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }
}
