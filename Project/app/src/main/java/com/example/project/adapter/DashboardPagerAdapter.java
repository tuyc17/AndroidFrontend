package com.example.project.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project.R;
import com.example.project.fragment.DashboardFragment;
import com.example.project.fragment.dashboard.ClassFragment;
import com.example.project.fragment.dashboard.DepartmentFragment;

public class DashboardPagerAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.dash_tab_text_1, R.string.dash_tab_text_2};
    private final DashboardFragment mContext;

    public DashboardPagerAdapter(DashboardFragment context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            return DepartmentFragment.newInstance(position);
        }
        else
        {
            return ClassFragment.newInstance(position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
