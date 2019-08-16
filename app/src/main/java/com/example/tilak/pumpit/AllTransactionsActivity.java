package com.example.tilak.pumpit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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

public class AllTransactionsActivity extends AppCompatActivity implements TransactionsAdapter.ItemclickListener{
    RecyclerView recyclerView;
    ImageView cancel;
    TransactionsAdapter adapter;
    TextView noOftrans;
    String GymName;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CollectionReference transref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transactions);
        recyclerView = findViewById(R.id.alltransrv);
        cancel = findViewById(R.id.cancelexp);
        noOftrans = findViewById(R.id.nooftranstv);

        GymName = user.getDisplayName();

        transref = FirebaseFirestore.getInstance().collection("Gyms/"+GymName+"/Cashflow");

        setupTransactionRv();

        transref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                    noOftrans.setText(task.getResult().size()+" Transactions");
                else
                    noOftrans.setText("No Transactions");
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void setupTransactionRv() {
        Query query = transref.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Transaction> options = new FirestoreRecyclerOptions.Builder<Transaction>().setQuery(query, Transaction.class).build();
        adapter = new TransactionsAdapter(options, getApplicationContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }
}
