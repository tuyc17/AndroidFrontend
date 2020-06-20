package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.project.R;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangeAvatarActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        ImageView avatar1 = findViewById(R.id.avatar1);
        ImageView avatar2 = findViewById(R.id.avatar2);
        ImageView avatar3 = findViewById(R.id.avatar3);
        ImageView avatar4 = findViewById(R.id.avatar4);
        ImageView avatar5 = findViewById(R.id.avatar5);
        ImageView avatar6 = findViewById(R.id.avatar6);

        mPreferences = getSharedPreferences("metadata", MODE_PRIVATE);
        final SharedPreferences.Editor saveEditor = mPreferences.edit();

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditor.putInt("avatar", R.drawable.avatar1);
                saveEditor.apply();
                upload();
                finish();
            }
        });

        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditor.putInt("avatar", R.drawable.avatar2);
                saveEditor.apply();
                upload();
                finish();
            }
        });

        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditor.putInt("avatar", R.drawable.avatar3);
                saveEditor.apply();
                upload();
                finish();
            }
        });

        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditor.putInt("avatar", R.drawable.avatar4);
                saveEditor.apply();
                upload();
                finish();
            }
        });

        avatar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditor.putInt("avatar", R.drawable.avatar5);
                saveEditor.apply();
                upload();
                finish();
            }
        });

        avatar6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditor.putInt("avatar", R.drawable.avatar6);
                saveEditor.apply();
                upload();
                finish();
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

    private void upload() {
        HashMap<String, String> p = new HashMap<>();
        int i = mPreferences.getInt("avatar", R.drawable.avatar1);
        p.put("avatar", String.valueOf(i));
        Callback call = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    System.out.println("yes");
                }
                else {
                    System.out.println(response.code());
                }
            }
        };
        HttpReq.sendOkHttpPostRequest("/user/avatar", call, p);
    }


}
