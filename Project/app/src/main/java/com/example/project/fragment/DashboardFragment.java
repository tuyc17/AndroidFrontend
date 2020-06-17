package com.example.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.project.R;
import com.example.project.adapter.DashboardPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DashboardFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        DashboardPagerAdapter dashboardPagerAdapter = new DashboardPagerAdapter(this, getFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.dashViewPager);
        viewPager.setAdapter(dashboardPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.dashTabs);
        tabs.setupWithViewPager(viewPager);

        return root;
    }


}
