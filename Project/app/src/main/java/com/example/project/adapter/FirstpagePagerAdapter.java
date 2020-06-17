package com.example.project.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project.R;

import com.example.project.fragment.FirstpageFragment;
import com.example.project.fragment.firstpage.FollowFragment;
import com.example.project.fragment.firstpage.HotFragment;
import com.example.project.fragment.firstpage.RecommendFragment;

public class FirstpagePagerAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.first_page_tab_text_1, R.string.first_page_tab_text_2, R.string.first_page_tab_text_3};
    private final FirstpageFragment mContext;

    public FirstpagePagerAdapter(FirstpageFragment context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            return FollowFragment.newInstance(position);
        }
        else if(position == 1)
        {
            return RecommendFragment.newInstance(position);
        }
        else
        {
            return HotFragment.newInstance(position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
