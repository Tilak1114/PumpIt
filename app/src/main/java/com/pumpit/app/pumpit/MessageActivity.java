package com.pumpit.app.pumpit;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MessageActivity extends AppCompatActivity {
    ImageView back;
    private TabLayout comptabs;
    private ViewPager comppager;
    private int[] tabIcons = {
            R.drawable.msgoption,
            R.drawable.whatsappop,
            R.drawable.mailoption
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        back = findViewById(R.id.bckmsg);
        comppager = findViewById(R.id.composecontainer);
        comptabs = findViewById(R.id.composetabs);

        ComposePagerAdapter composePagerAdapter = new
                ComposePagerAdapter(getSupportFragmentManager());
        comppager.setAdapter(composePagerAdapter);
        comptabs.setupWithViewPager(comppager);
        setupTabIcons();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setupTabIcons() {
        comptabs.getTabAt(0).setIcon(tabIcons[0]);
        comptabs.getTabAt(1).setIcon(tabIcons[1]);
        comptabs.getTabAt(2).setIcon(tabIcons[2]);
    }
}
