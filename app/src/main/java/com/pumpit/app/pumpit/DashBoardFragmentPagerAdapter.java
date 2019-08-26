package com.pumpit.app.pumpit;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class DashBoardFragmentPagerAdapter extends FragmentPagerAdapter {
    public DashBoardFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ManageMembersFragment();
        }
        else if(position == 1)
            return new ManagePlansFragment();
        else
            return new ManageTrainersFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


}
