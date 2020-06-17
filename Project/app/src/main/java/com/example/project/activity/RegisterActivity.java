package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;

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
                String strUsername = username.getText().toString();
                String strId = id.getText().toString();
                String strPassword1 = password1.getText().toString();
                String strPassword2 = password2.getText().toString();

                //检查4个是否为空

                if(strPassword1.equals(strPassword2))
                {
                    //发送到后端创建用户
                    //转到首页
                    Toast.makeText(RegisterActivity.this, "用户创建成功", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
