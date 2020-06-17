package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button register = findViewById(R.id.register);
        Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();

                //连接后端检查用户名和密码是否匹配
                //检查是否存在该用户

                Intent it = new Intent();
                it.setClass(LoginActivity.this, MainPageActivity.class);
                startActivity(it);

//                if(true)
//                {
//                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    //跳转到首页
//                    //保存用户信息下一次打开app直接跳转到首页
//                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
            }
        });
    }
}
