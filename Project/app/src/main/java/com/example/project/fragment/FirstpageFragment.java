package com.example.project.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.R;
import com.example.project.adapter.FirstpagePagerAdapter;
import com.example.project.viewmodel.FirstpageViewModel;
import com.google.android.material.tabs.TabLayout;

public class FirstpageFragment extends Fragment {

    private FirstpageViewModel mViewModel;

    public static FirstpageFragment newInstance() {
        return new FirstpageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_firstpage, container, false);

        FirstpagePagerAdapter firstpagePagerAdapter = new FirstpagePagerAdapter(this, getFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.firstpageViewPager);
        viewPager.setAdapter(firstpagePagerAdapter);
        TabLayout tabs = root.findViewById(R.id.firstpageTabs);
        tabs.setupWithViewPager(viewPager);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FirstpageViewModel.class);
        // TODO: Use the ViewModel
    }

}
