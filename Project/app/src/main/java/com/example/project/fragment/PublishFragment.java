package com.example.project.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PublishFragment extends Fragment {
    private Bitmap img;
    private File file;

    private EditText title;
    private EditText content;
    private Spinner spinner;

    public static PublishFragment newInstance() {
        return new PublishFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_publish, container, false);
        Button upload_btn = root.findViewById(R.id.upload_btn);
        Button publish_btn = root.findViewById(R.id.publish_btn);
        Button video_btn = root.findViewById(R.id.video_btn);

        title = root.findViewById(R.id.title);
        content = root.findViewById(R.id.content);
        spinner = root.findViewById(R.id.theme);

        String[] data = {"building", "computer", "chemistry", "physics", "music"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, data);
        spinner.setAdapter(adapter);

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it, 100);
            }
        });

        video_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("video/*");
                startActivityForResult(it, 100);
            }
        });

        publish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTitle = title.getText().toString();
                String strContent = content.getText().toString();
                String strTheme = spinner.getSelectedItem().toString();

                HashMap<String, String> p = new HashMap<>();
                p.put("theme", strTheme);
                p.put("title", strTitle);
                p.put("content", strContent);

                Callback callback = new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()) {
                            if(file != null) {
                                //文件上传
                                int id = 0;
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    id = jsonObject.getInt("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Callback c = new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        if(response.isSuccessful()) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getActivity(), "文章发表成功！", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                };
                                HttpReq.uploadImg("/article/upload", c, id, file);
                            }
                        }
                        else {
                            System.out.println(response.code());
                        }
                    }
                };
                HttpReq.sendOkHttpPostRequest("/article/article", callback, p);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);

                file = new File(getActivity().getCacheDir(), "tmp");
                if(file.exists()) {
                    file.delete();
                    file = new File(getActivity().getCacheDir(), "tmp");
                }
                file.createNewFile();

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                byte[] buffers = new byte[inputStream.available()];
                bufferedOutputStream.write(buffers, 0, inputStream.read(buffers));

                inputStream.close();
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
//                img = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
//                file = new File(getActivity().getCacheDir(), "tmp");
//                if(file.exists()) {
//                    file.delete();
//                    file = new File(getActivity().getCacheDir(), "tmp");
//                }
//                file.createNewFile();
//
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                img.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//                byte[] bitmapdata = bos.toByteArray();
//
//                // write the bytes in file
//                FileOutputStream fos = new FileOutputStream(file);
//                fos.write(bitmapdata);
//                fos.flush();
//                fos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
