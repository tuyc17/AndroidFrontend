package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.activity.ActionActivity;
import com.example.project.activity.CollectActivity;
import com.example.project.activity.FollowActivity;
import com.example.project.activity.VerifyActivity;
import com.example.project.activity.SettingActivity;

import com.example.project.R;
import com.example.project.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        CardView card_follow = root.findViewById(R.id.card_follow);
        CardView card_collect = root.findViewById(R.id.card_collect);
        CardView card_action = root.findViewById(R.id.card_action);

        CardView recent = root.findViewById(R.id.recent);
        CardView apply = root.findViewById(R.id.apply);
        CardView settings = root.findViewById(R.id.settings);

        ImageView avatar = root.findViewById(R.id.avatar);
        avatar.setImageResource(R.drawable.avatar6);

        card_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), FollowActivity.class);
                startActivity(it);
            }
        });

        card_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), CollectActivity.class);
                startActivity(it);
            }
        });

        card_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), ActionActivity.class);
                startActivity(it);
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), VerifyActivity.class);
                startActivity(it);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), SettingActivity.class);
                startActivity(it);
            }
        });

        //动态设置字符串
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
