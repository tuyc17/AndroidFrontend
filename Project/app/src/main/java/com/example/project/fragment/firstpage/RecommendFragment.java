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

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends Fragment {

    private List<Summary> summaryList = new ArrayList<>();

    public static RecommendFragment newInstance(int index) {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initSummary();

        View root = inflater.inflate(R.layout.fragment_main_page_recommend, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        SummaryAdapter adapter = new SummaryAdapter(getActivity(), summaryList);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void initSummary() {
        Summary summary1 = new Summary(R.drawable.ic_launcher_background, "用户姓名" , "6-17", "标题", "内容简介");
        summaryList.add(summary1);
        Summary summary2 = new Summary(R.drawable.ic_launcher_background, "用户姓名" , "6-17", "标题", "内容简介");
        summaryList.add(summary2);
        Summary summary3 = new Summary(R.drawable.ic_launcher_background, "用户姓名" , "6-17", "标题", "内容简介");
        summaryList.add(summary3);
        Summary summary4 = new Summary(R.drawable.ic_launcher_background, "用户姓名" , "6-17", "标题", "内容简介");
        summaryList.add(summary4);
        Summary summary5 = new Summary(R.drawable.ic_launcher_background, "用户姓名" , "6-17", "标题", "内容简介");
        summaryList.add(summary5);
        Summary summary6 = new Summary(R.drawable.ic_launcher_background, "用户姓名" , "6-17", "标题", "内容简介");
        summaryList.add(summary6);
    }
}
