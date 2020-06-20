package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
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

public class ChangeNicknameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nickname);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用

        final EditText new_nickname = findViewById(R.id.new_nickname);

        Button btn_ensure_nickname = findViewById(R.id.ensure_nickname);

        btn_ensure_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strNewNickname = new_nickname.getText().toString();

                ////////////////////////////////////////
                HashMap<String, String> params = new HashMap<>();
                params.put("username", strNewNickname);

                Callback call = new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            // 正常返回
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangeNicknameActivity.this, "用户昵称修改成功！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            // 错误返回
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangeNicknameActivity.this, "用户昵称修改失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                };
                HttpReq.sendOkHttpPostRequest("/user/username", call, params);

                //////////////////////////////////
                // Toast.makeText(ChangeNicknameActivity.this, "新昵称为："+ new_nickname.getText(), Toast.LENGTH_SHORT).show();
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
