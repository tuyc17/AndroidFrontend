package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class ChangePasswordActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mPreferences = getSharedPreferences("metadata", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        final EditText old_password = findViewById(R.id.old_password);
        final EditText new_password = findViewById(R.id.new_password);

        Button ensure_password = findViewById(R.id.ensure_password);

        ensure_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strOldPassword = old_password.getText().toString();
                final String strNewPassword = new_password.getText().toString();

                // TODO: 判断old password是否正确
                String old = mPreferences.getString("password", "");
                if (!strOldPassword.equals(old)) {
                    Toast.makeText(ChangePasswordActivity.this, "原密码密码不正确！", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("password", strNewPassword);

                Callback call = new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {

                            SharedPreferences.Editor saveEditor = mPreferences.edit();
                            saveEditor.putString("password", strNewPassword);
                            saveEditor.apply();
                            // 正常返回
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangePasswordActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            // 错误返回
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangePasswordActivity.this, "密码修改失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                };
                HttpReq.sendOkHttpPostRequest("/user/password", call, params);
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
