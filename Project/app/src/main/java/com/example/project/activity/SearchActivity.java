package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.project.R;
import com.example.project.adapter.component.ResultAdapter;
import com.example.project.component.Result;
import com.example.project.component.Summary;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kotlin.collections.IntIterator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    private List<Result> results = new ArrayList<>();
    private ResultAdapter adapter;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent it = getIntent();
        query = it.getStringExtra("query");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ResultAdapter(this, results);
        recyclerView.setAdapter(adapter);

        initResult();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initResult() {
        initUser();
        initSum();
    }

    private void initUser() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    try{
                        JSONObject jsonData = new JSONObject(response.body().string());
                        JSONArray jsonUser = new JSONArray(jsonData.get("users").toString());

                        for(int i=0; i<jsonUser.length(); i++) {
                            JSONObject user = jsonUser.getJSONObject(i);
                            String nickname = user.getString("nickName");
                            int avatar = user.getInt("avatar");
                            int id = user.getInt("id");
                            Result result = new Result(avatar, nickname, "", "", "", id, -1);
                            results.add(result);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("----------------------------");
                }

            }
        };
        HttpReq.sendOkHttpGetRequest("/user/search?target="+query, callback);

    }

    private void initSum() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    try {
                        JSONObject jsonData = new JSONObject(response.body().string());
                        final JSONArray jsonArticles = new JSONArray(jsonData.get("articles").toString());

                        System.out.println(jsonArticles);

                        for (int i=0; i<jsonArticles.length(); i++) {
                            JSONObject article = jsonArticles.getJSONObject(i);

                            final int id = article.getInt("id");
                            final String title = article.getString("articleName");
                            final String content = article.getString("content");
                            final String publishtime = article.getString("publishTime").split("T")[0];
                            final int authorid = article.getInt("authorId");

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

                                            Result result = new Result(avatar, nickname, publishtime, title, content, id, authorid);
                                            results.add(result);

                                            if(results.size() == jsonArticles.length()) {
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("失败");
                    System.out.println(response.code());
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/article/fakesearch?target="+query, callback);
    }

}
