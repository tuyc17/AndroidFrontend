package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CollectActivity extends AppCompatActivity {
    private List<Summary> summaries = new ArrayList<>();
    private SummaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        initSummary();

        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SummaryAdapter(this, summaries);
        recyclerView.setAdapter(adapter);
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
                    String data = response.body().string();
                    try {
                        JSONObject body = new JSONObject(data);
                        final JSONArray json = new JSONArray(body.get("articles").toString());
                        for (int i=0; i< json.length(); i++) {
                            JSONObject jsonObject = json.getJSONObject(i);

                            final int id = jsonObject.getInt("id");
                            final String title = jsonObject.getString("articleName");
                            final String content = jsonObject.getString("content");
                            final String publishtime = jsonObject.getString("publishTime").split("T")[0];
                            final int authorid = jsonObject.getInt("authorId");

                            Callback c = new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    if(response.isSuccessful()) {
                                        String userdata = response.body().string();
                                        try {
                                            JSONObject temp = new JSONObject(userdata);
                                            JSONObject j = new JSONObject(temp.get("info").toString());

                                            int avatar = j.getInt("avatar");
                                            String nickname = j.getString("nickname");
                                            Summary summary = new Summary(avatar, nickname, publishtime, title, content, id, authorid);
                                            summaries.add(summary);

                                            if(summaries.size() == json.length()) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                        }catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else {
                                        System.out.println(response.code());
                                    }

                                }
                            };
                            HttpReq.sendOkHttpGetRequest("/user/info?id=" + authorid, c);
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println(response.code());
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/article/favorite", callback);

//        for (int i=0; i<5; i++) {
//            Summary summary = new Summary(R.drawable.avatar3, "用户名称", "6-20", "标题", "简介");
//            summaries.add(summary);
//        }
    }
}
