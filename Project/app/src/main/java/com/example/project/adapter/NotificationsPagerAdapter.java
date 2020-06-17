package com.example.project.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.project.R;
import com.example.project.fragment.NotificationsFragment;
import com.example.project.fragment.notifications.CommunicateFragment;
import com.example.project.fragment.notifications.ReplyFragment;

public class NotificationsPagerAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.notifications_tab_text_1, R.string.notifications_tab_text_2};
    private final NotificationsFragment mContext;

    public NotificationsPagerAdapter(NotificationsFragment context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            return ReplyFragment.newInstance(position);
        }
        else
        {
            return CommunicateFragment.newInstance(position);
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
