package com.example.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.project.R;
import com.example.project.adapter.NotificationsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class NotificationsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        NotificationsPagerAdapter notificationsPagerAdapter = new NotificationsPagerAdapter(this, getFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.notificationsViewPager);
        viewPager.setAdapter(notificationsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.notificationsTabs);
        tabs.setupWithViewPager(viewPager);

        return root;
    }
}
