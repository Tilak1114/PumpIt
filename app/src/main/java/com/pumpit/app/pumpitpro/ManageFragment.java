package com.pumpit.app.pumpitpro;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ManageFragment extends Fragment {
    private TabLayout dbtabs;
    private ViewPager dbviewPager;
    private int[] tabIcons = {
            R.drawable.membicon1,
            R.drawable.plansicon,
            R.drawable.arm
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View manageFragmentView = inflater.inflate(R.layout.fragment_manage, container, false);
        dbtabs = manageFragmentView.findViewById(R.id.dashboardtabs);
        dbviewPager = manageFragmentView.findViewById(R.id.dashboardPagerContainer);
        return manageFragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DashBoardFragmentPagerAdapter dashBoardFragmentPagerAdapter = new
                DashBoardFragmentPagerAdapter(getChildFragmentManager());
        dbviewPager.setAdapter(dashBoardFragmentPagerAdapter);
        dbtabs.setupWithViewPager(dbviewPager);
        setupTabIcons();
    }
    private void setupTabIcons() {
        dbtabs.getTabAt(0).setIcon(tabIcons[0]);
        dbtabs.getTabAt(1).setIcon(tabIcons[1]);
        dbtabs.getTabAt(2).setIcon(tabIcons[2]);
    }
}
