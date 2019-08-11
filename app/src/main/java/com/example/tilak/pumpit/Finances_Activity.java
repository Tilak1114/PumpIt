package com.example.tilak.pumpit;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Finances_Activity extends AppCompatActivity {
    TabLayout tabLayout;
    RelativeLayout expFab;
    RelativeLayout summCashLay;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashflow);
        summCashLay = findViewById(R.id.netchslay);
        tabLayout = findViewById(R.id.summcashtabs);
        expFab = findViewById(R.id.expenseFab);
        viewPager = findViewById(R.id.cshpagercontainer);

        CashFlowSummaryPagerAdapter cashFlowSummaryPagerAdapter =
                new CashFlowSummaryPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(cashFlowSummaryPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        expFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewExpense.class));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    summCashLay.setBackground(getResources().getDrawable(R.drawable.stats_bck_rect_primarydark));
                } else {
                    summCashLay.setBackground(getResources().getDrawable(R.drawable.stats_bck_rect));
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
