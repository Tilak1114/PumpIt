package com.pumpit.app.pumpit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TransactionDetails extends AppCompatActivity {
    TextView maincate, transname, transamt, transdate, incorexp;
    RelativeLayout cateimglay;
    ImageView cateimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        maincate = findViewById(R.id.maincatename);
        incorexp = findViewById(R.id.expenseorinctv);
        transamt = findViewById(R.id.transmoneyval);
        transname = findViewById(R.id.transnameval);
        transdate = findViewById(R.id.transdateval);
        cateimglay = findViewById(R.id.detailslogo);
        cateimg = findViewById(R.id.detailslogoimg);

        Intent intent = getIntent();
        maincate.setText(intent.getStringExtra("category"));
        transdate.setText(intent.getStringExtra("timestamp"));
        transname.setText(intent.getStringExtra("name"));
        transamt.setText(intent.getStringExtra("amount"));

        if(intent.getStringExtra("inorout").equals("in")){
            cateimglay.setBackgroundResource(R.drawable.circleinc);
            incorexp.setText("Income");
            transamt.setTextColor(getColor(android.R.color.holo_green_dark));
        }
        else if(intent.getStringExtra("inorout").equals("out")){
            cateimglay.setBackgroundResource(R.drawable.circleexp);
            incorexp.setText("Expense");
            transamt.setTextColor(getColor(android.R.color.holo_red_dark));
        }

        switch (intent.getStringExtra("category")){
            case "Rent":
                cateimg.setBackgroundResource(R.drawable.gymrent);
                break;
            case "Bills":
                cateimg.setBackgroundResource(R.drawable.billicn);
                break;
            case "Salaries":
                cateimg.setBackgroundResource(R.drawable.salaryicn);
                break;
            case "Insurance":
                cateimg.setBackgroundResource(R.drawable.insuranceicn);
                break;
            case "Repairs":
                cateimg.setBackgroundResource(R.drawable.repairicn);
                break;
            case "+Equipment":
                cateimg.setBackgroundResource(R.drawable.equipnew);
                break;
            case "Marketing":
                cateimg.setBackgroundResource(R.drawable.ads);
                break;
            case "Misc":
                cateimg.setBackgroundResource(R.drawable.empty);
                break;
        }
        if(intent.getStringExtra("category").equals("Rent")){

        }
    }
}
