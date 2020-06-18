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

import java.util.ArrayList;
import java.util.List;

public class CommunicateFragment extends Fragment {
    private List<BriefReply> replies = new ArrayList<>();

    public static CommunicateFragment newInstance(int index) {
        CommunicateFragment fragment = new CommunicateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initReply();

        View root = inflater.inflate(R.layout.fragment_notifications_communicate, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BriefReplyAdapter adapter = new BriefReplyAdapter(replies);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void initReply() {
        for (int i=0; i< 10; i++)
        {
            BriefReply reply1 = new BriefReply(R.drawable.ic_launcher_background, "用户名称", "消息简介");
            replies.add(reply1);
        }
    }
}
