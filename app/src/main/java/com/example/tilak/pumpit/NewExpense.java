package com.example.tilak.pumpit;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class NewExpense extends AppCompatActivity {
    RelativeLayout rentlay, billslay,
            insurancelay, salarylay, newequiplay,
            repairlay, marketinglay, misclay;
    String selectedCate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        rentlay = findViewById(R.id.rentlay);
        billslay = findViewById(R.id.billslay);
        insurancelay = findViewById(R.id.insurancelay);
        salarylay = findViewById(R.id.salarylay);
        repairlay = findViewById(R.id.repairlay);
        marketinglay = findViewById(R.id.marketinglay);
        misclay = findViewById(R.id.misclay);
        newequiplay = findViewById(R.id.newequiplay);

        // radio group logic

        rentlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentlay.setBackgroundResource(R.drawable.stats_bck_rect_org);
                billslay.setBackgroundResource(R.drawable.stats_bck_rect);
                insurancelay.setBackgroundResource(R.drawable.stats_bck_rect);
                salarylay.setBackgroundResource(R.drawable.stats_bck_rect);
                repairlay.setBackgroundResource(R.drawable.stats_bck_rect);
                marketinglay.setBackgroundResource(R.drawable.stats_bck_rect);
                newequiplay.setBackgroundResource(R.drawable.stats_bck_rect);
                misclay.setBackgroundResource(R.drawable.stats_bck_rect);
                selectedCate = "Rent";
            }
        });

        billslay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentlay.setBackgroundResource(R.drawable.stats_bck_rect);
                billslay.setBackgroundResource(R.drawable.stats_bck_rect_org);
                insurancelay.setBackgroundResource(R.drawable.stats_bck_rect);
                salarylay.setBackgroundResource(R.drawable.stats_bck_rect);
                repairlay.setBackgroundResource(R.drawable.stats_bck_rect);
                marketinglay.setBackgroundResource(R.drawable.stats_bck_rect);
                newequiplay.setBackgroundResource(R.drawable.stats_bck_rect);
                misclay.setBackgroundResource(R.drawable.stats_bck_rect);
                selectedCate = "Bills";
            }
        });

        salarylay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentlay.setBackgroundResource(R.drawable.stats_bck_rect);
                billslay.setBackgroundResource(R.drawable.stats_bck_rect);
                insurancelay.setBackgroundResource(R.drawable.stats_bck_rect);
                salarylay.setBackgroundResource(R.drawable.stats_bck_rect_org);
                repairlay.setBackgroundResource(R.drawable.stats_bck_rect);
                marketinglay.setBackgroundResource(R.drawable.stats_bck_rect);
                newequiplay.setBackgroundResource(R.drawable.stats_bck_rect);
                misclay.setBackgroundResource(R.drawable.stats_bck_rect);
                selectedCate = "Salary";
            }
        });

        insurancelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentlay.setBackgroundResource(R.drawable.stats_bck_rect);
                billslay.setBackgroundResource(R.drawable.stats_bck_rect);
                insurancelay.setBackgroundResource(R.drawable.stats_bck_rect_org);
                salarylay.setBackgroundResource(R.drawable.stats_bck_rect);
                repairlay.setBackgroundResource(R.drawable.stats_bck_rect);
                marketinglay.setBackgroundResource(R.drawable.stats_bck_rect);
                newequiplay.setBackgroundResource(R.drawable.stats_bck_rect);
                misclay.setBackgroundResource(R.drawable.stats_bck_rect);
                selectedCate = "Insurance";
            }
        });

        newequiplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentlay.setBackgroundResource(R.drawable.stats_bck_rect);
                billslay.setBackgroundResource(R.drawable.stats_bck_rect);
                insurancelay.setBackgroundResource(R.drawable.stats_bck_rect);
                salarylay.setBackgroundResource(R.drawable.stats_bck_rect);
                repairlay.setBackgroundResource(R.drawable.stats_bck_rect);
                marketinglay.setBackgroundResource(R.drawable.stats_bck_rect);
                newequiplay.setBackgroundResource(R.drawable.stats_bck_rect_org);
                misclay.setBackgroundResource(R.drawable.stats_bck_rect);
                selectedCate = "newEquipment";
            }
        });

        repairlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentlay.setBackgroundResource(R.drawable.stats_bck_rect);
                billslay.setBackgroundResource(R.drawable.stats_bck_rect);
                insurancelay.setBackgroundResource(R.drawable.stats_bck_rect);
                salarylay.setBackgroundResource(R.drawable.stats_bck_rect);
                repairlay.setBackgroundResource(R.drawable.stats_bck_rect_org);
                marketinglay.setBackgroundResource(R.drawable.stats_bck_rect);
                newequiplay.setBackgroundResource(R.drawable.stats_bck_rect);
                misclay.setBackgroundResource(R.drawable.stats_bck_rect);
                selectedCate = "Repair";
            }
        });

        marketinglay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentlay.setBackgroundResource(R.drawable.stats_bck_rect);
                billslay.setBackgroundResource(R.drawable.stats_bck_rect);
                insurancelay.setBackgroundResource(R.drawable.stats_bck_rect);
                salarylay.setBackgroundResource(R.drawable.stats_bck_rect);
                repairlay.setBackgroundResource(R.drawable.stats_bck_rect);
                marketinglay.setBackgroundResource(R.drawable.stats_bck_rect_org);
                newequiplay.setBackgroundResource(R.drawable.stats_bck_rect);
                misclay.setBackgroundResource(R.drawable.stats_bck_rect);
                selectedCate = "Marketing";
            }
        });

        misclay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentlay.setBackgroundResource(R.drawable.stats_bck_rect);
                billslay.setBackgroundResource(R.drawable.stats_bck_rect);
                insurancelay.setBackgroundResource(R.drawable.stats_bck_rect);
                salarylay.setBackgroundResource(R.drawable.stats_bck_rect);
                repairlay.setBackgroundResource(R.drawable.stats_bck_rect);
                marketinglay.setBackgroundResource(R.drawable.stats_bck_rect);
                newequiplay.setBackgroundResource(R.drawable.stats_bck_rect);
                misclay.setBackgroundResource(R.drawable.stats_bck_rect_org);
                selectedCate = "Misc";
            }
        });
    }
}
