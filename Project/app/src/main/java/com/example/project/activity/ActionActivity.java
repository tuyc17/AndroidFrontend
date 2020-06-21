package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.project.R;
import com.example.project.adapter.component.SummaryAdapter;
import com.example.project.component.Summary;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActionActivity extends AppCompatActivity {
    private List<Summary> summaries = new ArrayList<>();
    private int avatarId;
    private int userId;
    private String username;
    private SummaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        SharedPreferences preferences = getSharedPreferences("metadata", MODE_PRIVATE);
        avatarId = preferences.getInt("avatar", R.drawable.avatar1);
        username = preferences.getString("username", "");
        userId = preferences.getInt("userId", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SummaryAdapter(this, summaries);
        recyclerView.setAdapter(adapter);

        initSummary();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSummary() {

        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    try {
                        JSONObject jsonData = new JSONObject(response.body().string());
                        JSONArray jsonArticles = new JSONArray(jsonData.get("articles").toString());
                        for(int i=0; i< jsonArticles.length(); i++) {
                            JSONObject article = jsonArticles.getJSONObject(i);
                            String title = article.getString("articlename");
                            String content = article.getString("content");
                            String time = article.getString("publishtime").split("T")[0];
                            int id = article.getInt("id");

                            Summary summary = new Summary(avatarId, username, time, title, content, id, userId);
                            summaries.add(summary);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/article/person?id=-1", callback);
//        for (int i=0; i<5; i++) {
//            Summary summary = new Summary(R.drawable.avatar4, "用户名称", "6-20", "标题", "简介");
//            summaries.add(summary);
//        }
    }
}
