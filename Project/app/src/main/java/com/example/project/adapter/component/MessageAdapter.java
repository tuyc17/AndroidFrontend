package com.example.project.adapter.component;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.component.Message;

import java.util.List;

import com.example.project.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> messages;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout left_layout;
        LinearLayout right_layout;
        TextView left_msg;
        TextView right_msg;

        public ViewHolder (View view) {
            super(view);
            left_layout = view.findViewById(R.id.left_layout);
            right_layout = view.findViewById(R.id.right_layout);
            left_msg = view.findViewById(R.id.left_msg);
            right_msg = view.findViewById(R.id.right_msg);
        }
    }

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);
        if(message.type == 0) {
            holder.left_layout.setVisibility(View.VISIBLE);
            holder.right_layout.setVisibility(View.GONE);
            holder.left_msg.setText(message.content);
        }
        else {
            holder.left_layout.setVisibility(View.GONE);
            holder.right_layout.setVisibility(View.VISIBLE);
            holder.right_msg.setText(message.content);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

}
