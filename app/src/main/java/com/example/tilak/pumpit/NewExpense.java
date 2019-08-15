package com.example.tilak.pumpit;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class NewExpense extends AppCompatActivity {
    RelativeLayout rentlay, billslay,
            insurancelay, salarylay, newequiplay,
            repairlay, marketinglay, misclay;
    EditText expenseName, expenseValue;
    RelativeLayout finishBtn;
    ImageView back;
    String selectedCate = "";
    DocumentReference expenseref;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        GymName = user.getDisplayName();

        rentlay = findViewById(R.id.rentlay);
        billslay = findViewById(R.id.billslay);
        back = findViewById(R.id.cancelexp);
        insurancelay = findViewById(R.id.insurancelay);
        salarylay = findViewById(R.id.salarylay);
        repairlay = findViewById(R.id.repairlay);
        marketinglay = findViewById(R.id.marketinglay);
        misclay = findViewById(R.id.misclay);
        newequiplay = findViewById(R.id.newequiplay);

        expenseName = findViewById(R.id.newExpName);
        expenseValue = findViewById(R.id.newExpVal);

        finishBtn = findViewById(R.id.finishnewexp);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
                selectedCate = "+Equipment";
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

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expenseName.getText().toString().isEmpty()){
                    expenseName.setError("Required");
                }
                if(expenseValue.getText().toString().isEmpty()){
                    expenseValue.setError("Required");
                }
                if(selectedCate.isEmpty()){
                    Toasty.error(getApplicationContext(), "Choose a category", Toasty.LENGTH_LONG).show();
                }
                if(!expenseName.getText().toString().isEmpty()&&!expenseValue.getText().toString().isEmpty()&&!selectedCate.isEmpty()){
                    Toasty.normal(getApplicationContext(), selectedCate, Toasty.LENGTH_LONG).show();
                    String expName = expenseName.getText().toString();
                    String expVal = expenseValue.getText().toString();
                    String category = selectedCate;

                    String timeStamp = new SimpleDateFormat("MMMM dd, yyyy HH:mm").format(new Date());
                    Long timeLong = new Date().getTime();
                    expenseref = FirebaseFirestore.getInstance()
                            .document("Gyms/"+GymName+"/Cashflow/"+expName+expVal+category);

                    Map<String, Object> data= new HashMap<String, Object>();
                    data.put("inorout", "out");
                    data.put("title", expName);
                    data.put("category", selectedCate);
                    data.put("amount", expVal);
                    data.put("timedate", timeStamp);
                    data.put("timelong", timeLong);


                    expenseref.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finish();
                        }
                    });
                }
            }
        });


    }
}
