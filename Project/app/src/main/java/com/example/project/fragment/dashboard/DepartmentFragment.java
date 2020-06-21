package com.example.project.fragment.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.example.project.activity.DepartmentActivity;

public class DepartmentFragment extends Fragment {
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;

    public static DepartmentFragment newInstance(int index) {
        DepartmentFragment fragment = new DepartmentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard_department, container, false);
        imageView1 = root.findViewById(R.id.imageView1);
        imageView2 = root.findViewById(R.id.imageView2);
        imageView3 = root.findViewById(R.id.imageView3);
        imageView4 = root.findViewById(R.id.imageView4);
        imageView5 = root.findViewById(R.id.imageView5);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), DepartmentActivity.class);
                it.putExtra("theme", "building");
                startActivity(it);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), DepartmentActivity.class);
                it.putExtra("theme", "computer");
                startActivity(it);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), DepartmentActivity.class);
                it.putExtra("theme", "chemistry");
                startActivity(it);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), DepartmentActivity.class);
                it.putExtra("theme", "physics");
                startActivity(it);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), DepartmentActivity.class);
                it.putExtra("theme", "music");
                startActivity(it);
            }
        });

        return root;
    }

}
