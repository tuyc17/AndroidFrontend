package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    // Unique tag for the intent reply
    public static final int TEXT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        Button btn_headicon = findViewById(R.id.change_headicon);
        btn_headicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SettingActivity.this, ChangeAvatarActivity.class);
                startActivity(it);
            }
        });

        Button btn_nickname = findViewById(R.id.change_nickname);
        btn_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SettingActivity.this, ChangeNicknameActivity.class);
                startActivityForResult(it, TEXT_REQUEST);
            }
        });

        Button btn_password = findViewById(R.id.change_password);
        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivityForResult(it, TEXT_REQUEST);
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
}
