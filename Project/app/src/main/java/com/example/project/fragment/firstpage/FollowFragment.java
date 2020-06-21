package com.example.project.fragment.firstpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.R;
import com.example.project.component.Summary;
import com.example.project.adapter.component.SummaryAdapter;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FollowFragment extends Fragment {

    private List<Summary> summaryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SummaryAdapter adapter;

    public static FollowFragment newInstance(int index) {
        FollowFragment fragment = new FollowFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_page_follow, container, false);
        recyclerView = root.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SummaryAdapter(getActivity(), summaryList);
        recyclerView.setAdapter(adapter);

        initSummary();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initSummary() {

        HashMap<String, String> p = new HashMap<>();
        p.put("theme", "软件学院");

        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String data = response.body().string();
                    try {
                        JSONObject body = new JSONObject(data);
                        final JSONArray json = new JSONArray(body.get("articles").toString());
                        for (int i=0; i< json.length(); i++) {
                            JSONObject jsonObject = json.getJSONObject(i);

                            final int id = jsonObject.getInt("id");
                            final String title = jsonObject.getString("articlename");
                            final String content = jsonObject.getString("content");
                            final String publishtime = jsonObject.getString("publishtime").split("T")[0];
                            final int authorid = jsonObject.getInt("authorid");

                            Callback c = new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    if(response.isSuccessful()) {
                                        String userdata = response.body().string();
                                        try {
                                            JSONObject temp = new JSONObject(userdata);
                                            JSONObject j = new JSONObject(temp.get("info").toString());

                                            int avatar = j.getInt("avatar");
                                            String nickname = j.getString("nickname");
                                            Summary summary = new Summary(avatar, nickname, publishtime, title, content, id, authorid);
                                            summaryList.add(summary);

                                            if(summaryList.size() == json.length()) {
                                               getActivity().runOnUiThread(new Runnable() {
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
                            HttpReq.sendOkHttpGetRequest("/user/info?id=" + authorid, c);
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

        HttpReq.sendOkHttpGetRequest("/article/theme?theme=软件学院", callback);
    }
}
