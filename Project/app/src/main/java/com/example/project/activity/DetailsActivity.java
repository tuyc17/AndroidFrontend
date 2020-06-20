package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.VideoView;

import com.example.project.R;
import com.example.project.adapter.component.CommendAdapter;
import com.example.project.component.Commend;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private List<Commend> commends = new ArrayList<>();

    private ImageView img;
    private SurfaceView video;

    private boolean isUp = false;
    private boolean isCollect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        img = findViewById(R.id.image);
        video = findViewById(R.id.video);
        img.setVisibility(View.INVISIBLE);
        video.setVisibility(View.INVISIBLE);

        initCommends();

        //todo
        //实现recycler的吸顶效果

        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CommendAdapter adapter = new CommendAdapter(this, commends);
        recyclerView.setAdapter(adapter);

        final RadioButton upbtn = findViewById(R.id.upBtn);
        final RadioButton collectbtn = findViewById(R.id.collectBtn);

        upbtn.setChecked(isUp);
        collectbtn.setChecked(isCollect);

        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isUp) {
                    upbtn.setChecked(true);
                    isUp = true;
                }
                else {
                    upbtn.setChecked(false);
                    isUp = false;
                }
            }
        });

        collectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCollect) {
                    collectbtn.setChecked(true);
                    isCollect = true;
                }
                else {
                    collectbtn.setChecked(false);
                    isCollect = false;
                }
            }
        });

        ImageView avatar = findViewById(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DetailsActivity.this, ProfileActivity.class);
                startActivity(it);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCommends() {
        String content = "飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞";
        for (int i=0; i<10; i++) {
            Commend commend = new Commend(R.drawable.avatar2, "用户名称", "6-18", content);
            commends.add(commend);
        }
    }

}
