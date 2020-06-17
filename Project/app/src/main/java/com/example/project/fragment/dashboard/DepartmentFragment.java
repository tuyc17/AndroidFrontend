package com.example.project.fragment.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.project.R;

public class DepartmentFragment extends Fragment {

    public static DepartmentFragment newInstance(int index) {
        DepartmentFragment fragment = new DepartmentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_department, container, false);
    }

}
