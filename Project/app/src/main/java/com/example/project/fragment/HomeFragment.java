package com.example.project.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.activity.ActionActivity;
import com.example.project.activity.CollectActivity;
import com.example.project.activity.FollowActivity;
import com.example.project.activity.RecentActivity;
import com.example.project.activity.VerifyActivity;
import com.example.project.activity.SettingActivity;

import com.example.project.R;
import com.example.project.viewmodel.HomeViewModel;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private TextView username;
    private TextView userTitle;
    private ImageView avatar;

    private TextView followText;
    private TextView collectText;
    private TextView actionText;

    private SharedPreferences mPreferences;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mPreferences = getActivity().getSharedPreferences("metadata", MODE_PRIVATE);

        avatar = root.findViewById(R.id.avatar);
        username = root.findViewById(R.id.username);
        userTitle = root.findViewById(R.id.title);

        followText = root.findViewById(R.id.num_follow);
        collectText = root.findViewById(R.id.num_collect);
        actionText = root.findViewById(R.id.num_action);

        CardView card_follow = root.findViewById(R.id.card_follow);
        CardView card_collect = root.findViewById(R.id.card_collect);
        CardView card_action = root.findViewById(R.id.card_action);

        CardView recent = root.findViewById(R.id.recent);
        CardView apply = root.findViewById(R.id.apply);
        CardView settings = root.findViewById(R.id.settings);

        ImageView img_recent = root.findViewById(R.id.img_recent);
        ImageView img_setting = root.findViewById(R.id.img_setting);
        img_recent.setImageResource(R.drawable.ic_history);
        img_setting.setImageResource(R.drawable.ic_setting);

        apply.setVisibility(View.GONE);

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

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), RecentActivity.class);
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

        //upload();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        mPreferences = getActivity().getSharedPreferences("metadata", MODE_PRIVATE);
        SharedPreferences.Editor saveEditor = mPreferences.edit();
        int img = mPreferences.getInt("avatar", R.drawable.avatar1);
        String name = mPreferences.getString("username", "新用户");
        String user_title = mPreferences.getString("userTitle", "");

        avatar.setImageResource(img);
        username.setText(name);
        userTitle.setText(user_title);

        setFollow();
        setCollect();
        setAction();
    }

    private void upload() {
        HashMap<String, String> p = new HashMap<>();
        int i = mPreferences.getInt("avatar", R.drawable.avatar1);
        p.put("avatar", String.valueOf(i));
        Callback call = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    System.out.println("yes");
                }
            }
        };
        HttpReq.sendOkHttpPostRequest("/user/avatar", call, p);
    }

    private void setFollow() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try{
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        final int num = jsonObject.getInt("count");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                followText.setText(String.valueOf(num));
                            }
                        });
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        HttpReq.sendOkHttpGetRequest("/friend/followingcount", callback);
    }

    private void setCollect() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try{
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        final int num = jsonObject.getInt("count");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                collectText.setText(String.valueOf(num));
                            }
                        });
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        HttpReq.sendOkHttpGetRequest("/article/favoritecount", callback);
    }

    private void setAction() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try{
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        final int num = jsonObject.getInt("count");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                actionText.setText(String.valueOf(num));
                            }
                        });
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        HttpReq.sendOkHttpGetRequest("/article/articlecount", callback);
    }
}
