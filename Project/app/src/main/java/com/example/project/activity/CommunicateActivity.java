package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.adapter.component.MessageAdapter;
import com.example.project.component.Message;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommunicateActivity extends AppCompatActivity {
    private List<Message> messages = new ArrayList<>();
    private int receiverId;
    private MessageAdapter adapter;
    private EditText input;
    private RecyclerView recyclerView;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);

        Intent it = getIntent();
        receiverId = it.getIntExtra("recevierId", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(it.getStringExtra("username"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);

        input = findViewById(R.id.input);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, final int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    final String strInput = input.getText().toString();
                    input.setText("");
                    HashMap<String, String> p = new HashMap<>();
                    p.put("content", strInput);
                    p.put("friendId", String.valueOf(receiverId));

                    Callback callback = new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
                                Message message = new Message(strInput, 1);
                                messages.add(message);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                        recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
                                    }
                                });
                            }
                            else {
                                System.out.println("发送消息错误");
                            }
                        }
                    };

                    HttpReq.sendOkHttpPostRequest("/friend/msg", callback, p);
                }
                return false;
            }
        });

        initMessages();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                initMessages();
            }
        }, 1000, 2000);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            timer.cancel();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMessages() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String data = response.body().string();
                    try {
                        JSONObject jsonData = new JSONObject(data);
                        JSONArray jsonMsg = new JSONArray(jsonData.get("chatlist").toString());

                        if(jsonMsg.length() == messages.size()) {
                            return;
                        }

                        messages.clear();
                        for (int i=0; i<jsonMsg.length(); i++) {
                            JSONObject msg = jsonMsg.getJSONObject(i);
                            //int sender_id = msg.getInt("senderid");
                            int receiver_id = msg.getInt("receiverid");
                            String content = msg.getString("content");

                            Message message;
                            if(receiver_id == receiverId) {
                                //我发给别人的消息
                                message = new Message(content, 1);
                            }
                            else {
                                //别人发给我的消息
                                message = new Message(content, 0);
                            }
                            messages.add(message);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("初始化消息列表错误");
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/friend/msg?friendId=" + receiverId, callback);
    }
}
