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

public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View homeFragView = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = homeFragView.findViewById(R.id.membtabs);
        viewPager = homeFragView.findViewById(R.id.membPagerContainer);
        return homeFragView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MembersFragmentPagerAdapter membersFragmentPagerAdapter = new
                MembersFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(membersFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        super.onViewCreated(view, savedInstanceState);
    }

}
