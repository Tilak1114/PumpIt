package com.pumpit.app.pumpitpro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class LeadsActivity extends AppCompatActivity implements LeadsAdapter.ItemclickListener{
    RelativeLayout addEnq;
    ImageView cancelleads;
    RecyclerView leadRv;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    TelephonyManager telephonyManager;

    private LeadsAdapter adapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    CollectionReference memberref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leads_activity);
        addEnq = findViewById(R.id.addenqfab);
        leadRv = findViewById(R.id.leadsrv);
        cancelleads = findViewById(R.id.cancelleadsmain);

        GymName = user.getDisplayName();

        //Log.d("GymMetainfo_MMF", GymName);

        memberref = db.collection("Gyms/"+GymName+"/Leads");

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
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        Query query = memberref;
        FirestoreRecyclerOptions<Lead> options = new FirestoreRecyclerOptions.Builder<Lead>().setQuery(query, Lead.class).build();
        adapter = new LeadsAdapter(options, getApplicationContext(), this);
        leadRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        leadRv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(String name, String gender, String email, String phone, String date, String enquiry) {

    }

    @Override
    public void callItem(String phone) {
        String phonenum = String.format("tel: %s", phone);
        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        // Set the data for the intent as the phone number.
        dialIntent.setData(Uri.parse(phonenum));
        startActivity(dialIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
