package com.example.project.fragment.notifications;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.R;
import com.example.project.component.BriefReply;
import com.example.project.adapter.component.BriefReplyAdapter;
import com.example.project.web.HttpReq;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommunicateFragment extends Fragment {
    private List<BriefReply> replies = new ArrayList<>();
    private BriefReplyAdapter adapter;

    private List<BriefReply> replies1 = new ArrayList<>();
    private BriefReplyAdapter adapter1;

    private int position;
    public CommunicateFragment(int index) {
        this.position = index;
    }

    public static CommunicateFragment newInstance(int index) {
        CommunicateFragment fragment = new CommunicateFragment(index);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications_communicate, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BriefReplyAdapter(getActivity(), position, replies);
        recyclerView.setAdapter(adapter);

        RecyclerView recyclerView1 = root.findViewById(R.id.recycler1);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager1);
        adapter1 = new BriefReplyAdapter(getActivity(), position, replies1);
        recyclerView1.setAdapter(adapter1);

        initReply();

        return root;
    }

    private void initReply() {
        Callback callback1 = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String data = response.body().string();
                    try{
                        JSONObject jsonData = new JSONObject(data);
                        JSONArray jsonFriends = new JSONArray(jsonData.get("following").toString());

                        for (int i=0; i<jsonFriends.length(); i++) {
                            JSONObject friend = jsonFriends.getJSONObject(i);
                            String nickname = friend.getString("nickName");
                            int avatar = friend.getInt("avatar");
                            int id = friend.getInt("id");

                            BriefReply reply = new BriefReply(avatar, nickname, id);
                            replies.add(reply);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    System.out.println("初始化消息列表失败1");
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/friend/following", callback1);

        Callback callback2 = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String data = response.body().string();
                    try{
                        JSONObject jsonData = new JSONObject(data);
                        JSONArray jsonFriends = new JSONArray(jsonData.get("follower").toString());

                        for (int i=0; i<jsonFriends.length(); i++) {
                            JSONObject friend = jsonFriends.getJSONObject(i);
                            String nickname = friend.getString("nickName");
                            int avatar = friend.getInt("avatar");
                            int id = friend.getInt("id");

                            BriefReply reply = new BriefReply(avatar, nickname, id);
                            replies1.add(reply);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter1.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    System.out.println("初始化消息列表失败2");
                }
            }
        };

        HttpReq.sendOkHttpGetRequest("/friend/follower", callback2);
    }
}
