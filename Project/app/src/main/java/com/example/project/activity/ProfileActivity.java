package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    private boolean isFollow = false;
    private int authorId;

    private ImageView avatarView;
    private TextView usernameView;
    private TextView titleView;
    private TextView num_praise;
    private TextView num_collect;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent it = getIntent();
        authorId = it.getIntExtra("authorId", -1);

        avatarView = findViewById(R.id.avatar);
        usernameView = findViewById(R.id.username);
        titleView = findViewById(R.id.title);
        num_praise = findViewById(R.id.num_praise);
        num_collect = findViewById(R.id.num_collect);

        initUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        btn = findViewById(R.id.btn_follow);

        //Todo 自己的情况取消显示关注按钮显示
        initBtn();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFollow) {

                    HashMap<String, String> p = new HashMap<>();
                    p.put("friendId", String.valueOf(authorId));

                    Callback callback = new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
                                //System.out.println(response.code());
                                System.out.println("关注关注");
                            }
                            else {
                                //System.out.println(response.code());
                            }
                        }
                    };

                    HttpReq.sendOkHttpPostRequest("/friend/follow", callback, p);

                    btn.setText("已关注");
                    isFollow = true;
                }
                else {

                    HashMap<String, String> p = new HashMap<>();
                    p.put("friendId", String.valueOf(authorId));

                    Callback callback = new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
                                //System.out.println(response.code());
                                System.out.println("未关注关注");
                            }
                            else {
                                //System.out.println(response.code());
                            }
                        }
                    };

                    HttpReq.sendOkHttpPostRequest("/friend/follow", callback, p);

                    btn.setText("关注");
                    isFollow = false;
                }
            }
        });

        //Todo
        //该用户发表过的文章

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initBtn() {
        SharedPreferences preferences = getSharedPreferences("metadata", MODE_PRIVATE);
        int id = preferences.getInt("userId", -2);

        if(id == authorId) {
            btn.setVisibility(View.GONE);
            return;
        }

        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {

                    if(response.code() == 201) {
                        isFollow = true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn.setText("已关注");
                            }
                        });
                    }
                    else {
                        isFollow = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn.setText("关注");
                            }
                        });
                    }
                }
                else {
                    System.out.println(response.code());
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/friend/follow?friendId="+authorId,callback);
    }

    private void initUser() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String userdata = response.body().string();
                    try {
                        JSONObject jsonData = new JSONObject(userdata);
                        JSONObject jsonInfo = new JSONObject(jsonData.get("info").toString());

                        final int avatar = jsonInfo.getInt("avatar");
                        final String nickname = jsonInfo.getString("nickname");
                        final int praiseCount = jsonInfo.getInt("praisecount");
                        final int favoriteCount = jsonInfo.getInt("favoritecount");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avatarView.setImageResource(avatar);
                                usernameView.setText(nickname);
                                num_praise.setText(""+praiseCount);
                                num_collect.setText(""+favoriteCount);
                            }
                        });

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println(response.code());
                }

            }
        };

        HttpReq.sendOkHttpGetRequest("/user/info?id="+authorId, callback);
    }
}
