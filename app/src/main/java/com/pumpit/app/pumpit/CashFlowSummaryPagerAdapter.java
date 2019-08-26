package com.pumpit.app.pumpit;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class CashFlowSummaryPagerAdapter extends FragmentPagerAdapter {
    public CashFlowSummaryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ThisMonthFragment();
        } else
            return new LastMonthFragment();
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
            title = "This Month";
        }
        else if (position == 1)
        {
            title = "Last Month";
        }
        return title;
    }

}
