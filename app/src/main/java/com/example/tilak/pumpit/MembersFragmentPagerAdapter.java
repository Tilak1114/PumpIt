package com.example.tilak.pumpit;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MembersFragmentPagerAdapter extends FragmentPagerAdapter {
    public MembersFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new OverViewFragment();
        } else
            return new FeedFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Overview";
        }
        else if (position == 1)
        {
            title = "Feed";
        }
        return title;
    }

}
