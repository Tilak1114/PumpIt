package com.pumpit.app.pumpit;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView crtdyn, accdyn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.ViewPagerContainer);
        crtdyn = findViewById(R.id.tvcreate);
        accdyn = findViewById(R.id.tvnc);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    crtdyn.setText("Create a");
                    accdyn.setText("New Account");
                }
                else if(tab.getPosition()==1){
                    crtdyn.setText("Login to an");
                    accdyn.setText("Existing Account");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
