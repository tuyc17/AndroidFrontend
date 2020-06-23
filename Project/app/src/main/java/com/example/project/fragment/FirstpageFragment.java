package com.example.project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.project.R;
import com.example.project.activity.SearchActivity;
import com.example.project.adapter.FirstpagePagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FirstpageFragment extends Fragment {

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

        SearchView searchView = root.findViewById(R.id.search);
        searchView.setImeOptions(3);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent it = new Intent(getActivity(), SearchActivity.class);
                it.putExtra("query", query);
                startActivity(it);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return root;
    }
}
