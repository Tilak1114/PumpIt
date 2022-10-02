package com.pumpit.app.pumpitpro;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


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
