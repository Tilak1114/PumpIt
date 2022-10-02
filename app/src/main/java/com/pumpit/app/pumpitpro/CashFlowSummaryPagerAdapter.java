package com.pumpit.app.pumpitpro;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


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
