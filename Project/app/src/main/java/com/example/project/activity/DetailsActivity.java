package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.adapter.component.CommendAdapter;
import com.example.project.component.Commend;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.Headers;
import okhttp3.internal.http2.Header;


public class DetailsActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private List<Commend> commends = new ArrayList<>();
    private SharedPreferences preferences;
    private CommendAdapter adapter;

    private SurfaceView video;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private SurfaceHolder surfaceHolder;
    private Button play_btn;
    private Button stop_btn;
    private Button replay_btn;
    private Button change_btn;

    private ImageView img;
    private ImageView avatar;
    private TextView username;
    private TextView date;
    private TextView title;
    private TextView content;
    private RecyclerView recyclerView;
    private EditText input;
    private RadioButton upbtn;
    private RadioButton collectbtn;

    private boolean isUp = false;
    private boolean isCollect = false;

    private int id = -1;
    private int authorId= -1;
    private int speed = 1;
    private String path;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent it = getIntent();

        preferences = getSharedPreferences("metadata", MODE_PRIVATE);

        avatar = findViewById(R.id.avatar);
        username = findViewById(R.id.username);
        date = findViewById(R.id.date);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        input = findViewById(R.id.input);

        avatar.setImageResource(it.getIntExtra("avatar", R.drawable.avatar1));
        username.setText(it.getStringExtra("username"));
        date.setText(it.getStringExtra("date"));
        title.setText(it.getStringExtra("title"));
        content.setText(it.getStringExtra("content"));

        id = it.getIntExtra("id", -1);
        authorId = it.getIntExtra("authorId", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        img = findViewById(R.id.image);
        video = findViewById(R.id.video);
        img.setVisibility(View.GONE);
        video.setVisibility(View.GONE);

        play_btn = findViewById(R.id.play_btn);
        stop_btn = findViewById(R.id.stop_btn);
        replay_btn = findViewById(R.id.replay_btn);
        change_btn = findViewById(R.id.change_btn);

        play_btn.setVisibility(View.GONE);
        stop_btn.setVisibility(View.GONE);
        replay_btn.setVisibility(View.GONE);
        change_btn.setVisibility(View.GONE);

        surfaceHolder = video.getHolder();
        surfaceHolder.addCallback(this);

        initSurface();
        initImg();
        initVideo();

        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommendAdapter(this, commends);
        recyclerView.setAdapter(adapter);

        initCommends();

        upbtn = findViewById(R.id.upBtn);
        collectbtn = findViewById(R.id.collectBtn);

        initBtn();

        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isUp) {
                    HashMap<String, String> p = new HashMap<>();
                    p.put("articleId", String.valueOf(id));

                    Callback callback = new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
//                                if(response.code() == 200) {
//
//                                }
                            }
                            else {
                                System.out.println(response.code());
                            }
                        }
                    };

                    HttpReq.sendOkHttpPostRequest("/article/praiseArticle", callback, p);

                    upbtn.setChecked(true);
                    isUp = true;
                }
                else {
                    HashMap<String, String> p = new HashMap<>();
                    p.put("articleId", String.valueOf(id));

                    Callback callback = new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
//                                if(response.code() == 201) {
//                                    System.out.println("no");
//                                }
                            }
                            else {
                                System.out.println(response.code());
                            }
                        }
                    };

                    HttpReq.sendOkHttpPostRequest("/article/praiseArticle", callback, p);

                    upbtn.setChecked(false);
                    isUp = false;
                }
            }
        });

        collectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCollect) {
                    HashMap<String, String> p = new HashMap<>();
                    p.put("articleId", String.valueOf(id));

                    Callback callback = new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
//                                if(response.code() == 200) {
//                                    System.out.println("yes");
//                                }
                            }
                            else {
                                System.out.println(response.code());
                            }
                        }
                    };

                    HttpReq.sendOkHttpPostRequest("/article/favorite", callback, p);

                    collectbtn.setChecked(true);
                    isCollect = true;
                }
                else {
                    HashMap<String, String> p = new HashMap<>();
                    p.put("articleId", String.valueOf(id));

                    Callback callback = new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
                                if(response.code() == 201) {
                                    System.out.println("no");
                                }
                            }
                            else {
                                System.out.println(response.code());
                            }
                        }
                    };

                    HttpReq.sendOkHttpPostRequest("/article/favorite", callback, p);

                    collectbtn.setChecked(false);
                    isCollect = false;
                }
            }
        });

        ImageView avatar = findViewById(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DetailsActivity.this, ProfileActivity.class);
                it.putExtra("authorId", authorId);
                startActivity(it);
            }
        });

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    final String strComment = input.getText().toString();

                    HashMap<String, String> p = new HashMap<>();
                    p.put("articleId", String.valueOf(id));
                    p.put("content", strComment);

                    Callback callback = new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
                                int avatar = preferences.getInt("avatar", R.drawable.avatar1);
                                String username = preferences.getString("username", "");
                                Calendar calendar = Calendar.getInstance();
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH)+1;
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                String date = year+"-"+month+"-"+day;
                                Commend commend = new Commend(avatar, username, date, strComment, -1);
                                commends.add(commend);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                            else {
                                System.out.println(response.code());
                            }

                        }
                    };

                    HttpReq.sendOkHttpPostRequest("/article/comment", callback, p);
                }
                return false;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mediaPlayer = MediaPlayer.create(this, uri);
            mediaPlayer.setDisplay(surfaceHolder);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initImg() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    try {
                        InputStream inputStream = response.body().byteStream();
                        final Bitmap bitmap= BitmapFactory.decodeStream(inputStream);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                img.setImageBitmap(bitmap);
                                img.setVisibility(View.VISIBLE);
                            }
                        });

                        inputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println(response.code());
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/article/download?articleId="+id, callback);
    }

    private void initSurface() {
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                play_btn.setEnabled(false);
                stop_btn.setEnabled(true);
                replay_btn.setEnabled(true);
                change_btn.setEnabled(true);
            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                play_btn.setEnabled(true);
                stop_btn.setEnabled(false);
                replay_btn.setEnabled(true);
                change_btn.setEnabled(true);
            }
        });

        replay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_btn.setEnabled(false);
                stop_btn.setEnabled(true);
                replay_btn.setEnabled(true);
                change_btn.setEnabled(true);

                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(DetailsActivity.this, uri);
                mediaPlayer.setDisplay(surfaceHolder);
                mediaPlayer.start();
            }
        });

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(speed == 1) {
                    mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed((float)2.0));
                    speed = 2;
                }
                else if(speed == 2) {
                    mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed((float)0.5));
                    speed = 3;
                }
                else if(speed == 3) {
                    mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed((float)1.0));
                    speed = 1;
                }
            }
        });
    }

    private void initVideo() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    try {
                        InputStream inputStream = response.body().byteStream();

                        File file = new File(getCacheDir(), "temp.mp4");
                        if(file.exists()) {
                            file.delete();
                            file = new File(getCacheDir(), "temp.mp4");
                        }
                        file.createNewFile();
                        path = file.getAbsolutePath();
                        uri = Uri.fromFile(file);

                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        byte[] bytes = new byte[1024];
                        do {
                            int num = inputStream.read(bytes);
                            if(num <= 0) {
                                break;
                            }
                            bufferedOutputStream.write(bytes, 0, num);
                        } while (true);

                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                video.setVisibility(View.VISIBLE);
                                play_btn.setVisibility(View.VISIBLE);
                                stop_btn.setVisibility(View.VISIBLE);
                                replay_btn.setVisibility(View.VISIBLE);
                                change_btn.setVisibility(View.VISIBLE);

                                play_btn.setEnabled(false);
                                stop_btn.setEnabled(true);
                                replay_btn.setEnabled(true);
                                change_btn.setEnabled(true);

                                mediaPlayer.start();
                            }
                        });

                        inputStream.close();
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else {
                    System.out.println(response.code());
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/article/download?articleId="+id, callback);
    }

    private void initCommends() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String data = response.body().string();
                    try{
                        JSONObject jsonData = new JSONObject(data);
                        final JSONArray jsonComments = new JSONArray(jsonData.get("comments").toString());
                        for(int i=0; i<jsonComments.length(); i++) {
                            JSONObject jsonComment = jsonComments.getJSONObject(i);
                            final String publishTime = jsonComment.getString("publish_time").split("T")[0];
                            final String content = jsonComment.getString("content");
                            final int authorId = jsonComment.getInt("authorid");

                            Callback c = new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    if(response.isSuccessful()) {
                                        String userdata = response.body().string();
                                        try{
                                            JSONObject jsonUser = new JSONObject(userdata);
                                            JSONObject jsonInfo = new JSONObject(jsonUser.get("info").toString());

                                            int avatar = jsonInfo.getInt("avatar");
                                            String nickname = jsonInfo.getString("nickname");

                                            Commend commend = new Commend(avatar, nickname, publishTime, content, authorId);
                                            commends.add(commend);

                                            if(commends.size() == jsonComments.length()) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                });
                                            }

                                        }catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else {
                                        System.out.println(response.code());
                                    }
                                }
                            };
                            HttpReq.sendOkHttpGetRequest("/user/info?id=" + authorId, c);
                        }

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println(response.code());
                }
            }
        };
        HttpReq.sendOkHttpGetRequest("/article/read?articleId=" + id,callback);

//        String content = "飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞飞";
//        for (int i=0; i<10; i++) {
//            Commend commend = new Commend(R.drawable.avatar2, "用户名称", "6-18", content);
//            commends.add(commend);
//        }
    }

    private void initBtn() {
        Callback callback1 = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = 0;
                try {
                    JSONObject data = new JSONObject(response.body().string());
                    code = data.getInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(code == 200) {
                    isUp = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            upbtn.setChecked(isUp);
                        }
                    });
                }
                else {
                    isUp = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            upbtn.setChecked(isUp);
                        }
                    });
                }
            }
        };
        HttpReq.sendOkHttpGetRequest("/article/ispraised?articleId="+id, callback1);


        Callback callback2 = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = 0;
                try {
                    JSONObject data = new JSONObject(response.body().string());
                    code = data.getInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(code == 200) {
                    isCollect = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            collectbtn.setChecked(isCollect);
                        }
                    });
                }
                else {
                    isCollect = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            collectbtn.setChecked(isCollect);
                        }
                    });
                }
                //System.out.println(response.code());
            }
        };
        HttpReq.sendOkHttpGetRequest("/article/isfavorited?articleId="+id, callback2);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
}
