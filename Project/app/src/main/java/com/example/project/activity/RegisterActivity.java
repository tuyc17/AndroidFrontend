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
import okhttp3.Response;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText id;
    private EditText password1;
    private EditText password2;

    private SharedPreferences mPreferences;
    // private String sharedPrefFile = "com.example.project.activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        id = findViewById(R.id.id);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strUsername = username.getText().toString();
                final String strId = id.getText().toString();
                final String strPassword1 = password1.getText().toString();
                final String strPassword2 = password2.getText().toString();

                // 判断是否全填入
                if(strUsername.equals("") || strId.equals("") || strPassword1.equals("") || strPassword2.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请将以上信息填写完整！", Toast.LENGTH_SHORT).show();
                }
                else if(!strPassword1.equals(strPassword2)) {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
                }
                else {
                    // 将id,username,password 存入mPreferences
                    mPreferences = getSharedPreferences("metadata", MODE_PRIVATE);
                    SharedPreferences.Editor saveEditor = mPreferences.edit();
                    saveEditor.putString("studentId", strId);
                    saveEditor.putString("username", strUsername);
                    saveEditor.putString("password", strPassword1);
                    saveEditor.putInt("avatar", R.drawable.avatar1);
                    saveEditor.apply();

                    HashMap<String, String> params = new HashMap<>();
                    params.put("studentId", strId);
                    params.put("username", strUsername);
                    params.put("password", strPassword1);

                    Callback call = new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {

                                HashMap<String, String> p = new HashMap<>();
                                p.put("username", strId);
                                p.put("password", strPassword1);

                                Callback c = new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        if(response.isSuccessful()) {
                                            Intent it = new Intent(RegisterActivity.this, MainPageActivity.class);
                                            startActivity(it);
                                        }
                                        else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(RegisterActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                };

                                HttpReq.sendOkHttpPostRequest("/login", c, p);
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    };

                    HttpReq.sendOkHttpPostRequest("/user/logon", call, params);
                }
            }
        });
    }

}
