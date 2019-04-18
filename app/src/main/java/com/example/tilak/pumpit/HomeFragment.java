package com.example.tilak.pumpit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

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
