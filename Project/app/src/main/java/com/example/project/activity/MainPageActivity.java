package com.example.project.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.project.R;
import com.example.project.web.HttpReq;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_firstpage, R.id.navigation_publish)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        initUser();
    }

    private void initUser() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String userdata = response.body().string();
                    try {
                        JSONObject jsonData = new JSONObject(userdata);
                        JSONObject jsonInfo = new JSONObject(jsonData.get("info").toString());

                        // 将id,username,password 存入mPreferences

                        int id = jsonInfo.getInt("id");
                        String name = jsonInfo.getString("nickname");
                        int avatar = jsonInfo.getInt("avatar");

                        SharedPreferences preferences = getSharedPreferences("metadata", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("userId", id);
                        editor.putString("username", name);
                        editor.putInt("avatar", avatar);
                        editor.apply();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        HttpReq.sendOkHttpGetRequest("/user/info?id=-1", callback);
    }

}
