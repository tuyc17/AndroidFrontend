package com.example.project.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.project.viewmodel.PublishViewModel;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PublishFragment extends Fragment {
    private Bitmap img;

    private PublishViewModel mViewModel;
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
                Intent it = new Intent(Intent.ACTION_PICK);
                it.setType("image/*");
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "文章发表成功！", Toast.LENGTH_SHORT).show();
                                }
                            });
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PublishViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && data != null) {
            Uri uri = data.getData();
            try {
                img = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
