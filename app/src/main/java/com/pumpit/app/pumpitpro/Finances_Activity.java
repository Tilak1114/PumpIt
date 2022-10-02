package com.pumpit.app.pumpitpro;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Finances_Activity extends AppCompatActivity implements TransactionsAdapter.ItemclickListener{
    TabLayout tabLayout;
    RelativeLayout expFab;
    TextView nodatatitle, nodatasubtitle, viewall;
    ImageView nodatavector;
    ImageView cancelCashflow;
    RecyclerView transRv;
    RelativeLayout summCashLay;
    ViewPager viewPager;

    CollectionReference transref;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    private TransactionsAdapter transadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashflow);
        viewall = findViewById(R.id.alltranscshflow);
        summCashLay = findViewById(R.id.netchslay);
        tabLayout = findViewById(R.id.summcashtabs);
        expFab = findViewById(R.id.expenseFab);
        viewPager = findViewById(R.id.cshpagercontainer);
        transRv = findViewById(R.id.transRv);
        cancelCashflow = findViewById(R.id.cancelcshflow);
        nodatasubtitle = findViewById(R.id.nodatasubexp);
        nodatatitle = findViewById(R.id.nodatatvexp);
        nodatavector = findViewById(R.id.emptyvector);

        GymName = user.getDisplayName();

        transref = FirebaseFirestore.getInstance().collection("Gyms/"+GymName+"/Cashflow");

        transRv.setVisibility(View.INVISIBLE);

        setupTransactionRv();

        cancelCashflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        transRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                if (dy > 0 && expFab.getVisibility() == View.VISIBLE) {
                    expFab.setVisibility(View.INVISIBLE);
                } else if (dy < 0 && expFab.getVisibility() != View.VISIBLE) {
                    expFab.setVisibility(View.VISIBLE);
                }
            }
        });


        FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Cashflow")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(Objects.requireNonNull(task.getResult()).isEmpty()){
                    nodatasubtitle.setVisibility(View.VISIBLE);
                    nodatatitle.setVisibility(View.VISIBLE);
                    nodatavector.setVisibility(View.VISIBLE);
                }
                else{
                    transRv.setVisibility(View.VISIBLE);
                    nodatasubtitle.setVisibility(View.INVISIBLE);
                    nodatatitle.setVisibility(View.INVISIBLE);
                    nodatavector.setVisibility(View.INVISIBLE);
                }
            }
        });

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

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AllTransactionsActivity.class));
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

    private void setupTransactionRv() {
        Query query = transref.limit(5).orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Transaction> options = new FirestoreRecyclerOptions.Builder<Transaction>().setQuery(query, Transaction.class).build();
        transadapter = new TransactionsAdapter(options, getApplicationContext(), this);
        transRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        transRv.setAdapter(transadapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        transadapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        transadapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        transadapter.startListening();
    }

    @Override
    public void onItemClick(String name, String timestamp, String category, String amount, String inOrOut) {
        Intent intent = new Intent(getApplicationContext(), TransactionDetails.class);
        intent.putExtra("name", name);
        intent.putExtra("timestamp", timestamp);
        intent.putExtra("category", category);
        intent.putExtra("amount", amount);
        intent.putExtra("inorout", inOrOut);
        startActivity(intent);
    }
}
