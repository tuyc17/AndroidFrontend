package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.adapter.component.BriefUserAdapter;
import com.example.project.component.BriefUser;
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


public class FollowActivity extends AppCompatActivity {

    private List<BriefUser> users = new ArrayList<>();
    private BriefUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BriefUserAdapter(this, users);
        recyclerView.setAdapter(adapter);

        initUsers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUsers() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String data = response.body().string();
                    try{
                        JSONObject jsonData = new JSONObject(data);
                        JSONArray jsonFriends = new JSONArray(jsonData.get("following").toString());

                        for (int i=0; i<jsonFriends.length(); i++) {
                            JSONObject friend = jsonFriends.getJSONObject(i);
                            String nickname = friend.getString("nickName");
                            int avatar = friend.getInt("avatar");
                            int id = friend.getInt("id");

                            BriefUser briefUser = new BriefUser(avatar, nickname, id);
                            users.add(briefUser);
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

                }
                else {
                    System.out.println(response.code());
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/friend/following", callback);

//        for(int i=0; i<10; i++) {
//            BriefUser user = new BriefUser(R.drawable.avatar1, "用户名称");
//            users.add(user);
//        }
    }
}
