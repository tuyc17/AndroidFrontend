package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        //////////////////////
        SharedPreferences mPreferences = getSharedPreferences("metadata", MODE_PRIVATE);
        String pUsername = mPreferences.getString("studentId", "defValue");
        String pPassword = mPreferences.getString("password", "defValue");
        if(!pUsername.equals("defValue")) {
            HashMap<String, String> params = new HashMap<>();
            params.put("username", pUsername);
            params.put("password", pPassword);

            Callback call = new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()) {
                        Intent it = new Intent(LoginActivity.this, MainPageActivity.class);
                        startActivity(it);
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "用户名或密码错误，登录失败！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            };

            HttpReq.sendOkHttpPostRequest("/login", call, params);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strUsername = username.getText().toString();
                final String strPassword = password.getText().toString();

                //连接后端检查用户名和密码是否匹配
                //检查是否存在该用户

                HashMap<String, String> params = new HashMap<>();
                params.put("username", strUsername);
                params.put("password", strPassword);

                Callback call = new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            Intent it = new Intent(LoginActivity.this, MainPageActivity.class);
                            startActivity(it);
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "用户名或密码错误，登录失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                };

                HttpReq.sendOkHttpPostRequest("/login", call, params);
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
