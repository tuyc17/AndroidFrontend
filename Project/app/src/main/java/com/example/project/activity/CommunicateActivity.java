package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.project.R;
import com.example.project.adapter.component.MessageAdapter;
import com.example.project.component.Message;

import java.util.ArrayList;
import java.util.List;

public class CommunicateActivity extends AppCompatActivity {
    private List<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        initMessages();

        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MessageAdapter adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMessages() {
        Message message1 = new Message("你好", 0);
        messages.add(message1);
        Message message2 = new Message("我要起飞了", 0);
        messages.add(message2);
        Message message3 = new Message("？？？", 1);
        messages.add(message3);
        Message message4 = new Message("李在赣神魔", 1);
        messages.add(message4);
        Message message5 = new Message("飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞", 1);
        messages.add(message5);
        Message message6 = new Message("李时珍滴皮，我金牌港c接下来将一次不死并且超神！", 0);
        messages.add(message6);
    }
}
