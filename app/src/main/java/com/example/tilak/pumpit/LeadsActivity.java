package com.example.tilak.pumpit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LeadsActivity extends AppCompatActivity {
    RelativeLayout addEnq;
    ImageView cancelleads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leads_activity);
        addEnq = findViewById(R.id.addenqfab);
        cancelleads = findViewById(R.id.cancelleadsmain);

        addEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewLeadActivity.class));
            }
        });

        cancelleads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
