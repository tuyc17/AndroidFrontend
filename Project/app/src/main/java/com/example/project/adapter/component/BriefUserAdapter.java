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

import com.example.project.activity.ProfileActivity;
import com.example.project.component.BriefUser;

import com.example.project.R;

import java.util.List;

public class BriefUserAdapter extends RecyclerView.Adapter<BriefUserAdapter.ViewHolder> {
    private List<BriefUser> users;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView username;

        public ViewHolder (View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            username = view.findViewById(R.id.username);
        }
    }

    public BriefUserAdapter(Context context, List<BriefUser> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_brief_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BriefUser user = users.get(position);
        holder.avatar.setImageResource(user.imageId);
        holder.username.setText(user.username);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, ProfileActivity.class);
                it.putExtra("authorId", user.id);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
