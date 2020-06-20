package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText username;
    private EditText id;
    private EditText password1;
    private EditText password2;

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
                    HashMap<String, String> params = new HashMap<>();
                    params.put("studentid", strId);
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
                                Intent it = new Intent(RegisterActivity.this, MainPageActivity.class);
                                it.putExtra("studentId", strId);
                                it.putExtra("username", strUsername);
                                it.putExtra("password", strPassword1);
                                startActivity(it);
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
                // Post();
                //检查4个是否为空

//                if(strPassword1.equals(strPassword2))
//                {
//                    //发送到后端创建用户
//                    //转到首页
//                    Toast.makeText(RegisterActivity.this, "用户创建成功", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

//    private void Post() {
//        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
//        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
//
//        formBody.add("username","bobo");//传递键值对参数
//        formBody.add("password","123456");//传递键值对参数
//        formBody.add("studentid","2017013582");//传递键值对参数
//
//        Request request = new Request.Builder()//创建Request 对象。
//                .url("http://101.37.67.13:8080/user/logon")
//                .post(formBody.build())//传递请求体
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println("---------------");
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if(response.isSuccessful()) {
//                    System.out.println("yes");
//                }
//
//            }
//        });
//
//    }
}
