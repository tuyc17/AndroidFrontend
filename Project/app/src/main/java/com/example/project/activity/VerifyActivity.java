package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.fragment.HomeFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class VerifyActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        mPreferences = getSharedPreferences("metadata", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用

        final Spinner department = findViewById(R.id.department_spinner);

        String[] data = {"软件学院", "计算机系", "交叉信息学院", "自动化系", "经管学院", "考古专业", "其它专业"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, data);
        department.setAdapter(adapter);

        Button submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 读取spinner的字符串
                final String personal_title =  department.getSelectedItem().toString();
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putString("userTitle", personal_title);
                editor.apply();
                finish();
                // Toast.makeText(VerifyActivity.this, "认证成功!", Toast.LENGTH_SHORT).show();
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
