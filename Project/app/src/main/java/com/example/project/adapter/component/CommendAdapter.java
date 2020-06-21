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
import com.example.project.activity.ProfileActivity;
import com.example.project.component.Commend;

import java.util.List;

public class CommendAdapter extends RecyclerView.Adapter<CommendAdapter.ViewHolder> {
    private List<Commend> commends;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username;
        TextView date;
        TextView content;
        TextView btn;

        public ViewHolder (View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            username = view.findViewById(R.id.username);
            date = view.findViewById(R.id.date);
            content = view.findViewById(R.id.content);
            btn = view.findViewById(R.id.btn);
        }
    }

    public CommendAdapter(Context context, List<Commend> commends) {
        this.commends = commends;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_commend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Commend commend = commends.get(position);
        holder.avatar.setImageResource(commend.ImageId);
        holder.username.setText(commend.username);
        holder.date.setText(commend.date);
        holder.content.setText(commend.content);

        holder.content.setHeight(holder.content.getLineHeight());
        holder.content.post(new Runnable() {
            @Override
            public void run() {
                holder.btn.setVisibility(holder.content.getLineCount() > 1 ? View.VISIBLE : View.INVISIBLE);
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btn.getText().equals("展开")) {
                    holder.content.setHeight(holder.content.getLineCount() * holder.content.getLineHeight());
                    holder.btn.setText("收起");
                }
                else {
                    holder.content.setHeight(holder.content.getLineHeight());
                    holder.btn.setText("展开");
                }
            }
        });

//        holder.avatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //跳转到用户详情页
//                Intent it = new Intent(context, ProfileActivity.class);
//                context.startActivity(it);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return commends.size();
    }
}
