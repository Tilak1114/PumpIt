package com.pumpit.app.pumpitpro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ActiveMembActivity extends AppCompatActivity implements MemberAdapter.ItemclickListener{
    RecyclerView activeRv;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference actmemberref;

    TextView activemembcnt;

    ImageView close;

    TelephonyManager telephonyManager;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    MemberAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_memb);
        activeRv = findViewById(R.id.activemembRecyclerview);
        activemembcnt = findViewById(R.id.actmmbcnt);
        GymName = user.getDisplayName();
        close = findViewById(R.id.closeActiveMemb);
        actmemberref = db.collection("Gyms/"+GymName+"/Members");
        setUpActiveRecyclerView();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setUpActiveRecyclerView() {
        Query query = actmemberref.whereEqualTo("payment", "Fees Paid");
        FirestoreRecyclerOptions<Member> options = new FirestoreRecyclerOptions.Builder<Member>().setQuery(query, Member.class).build();
        adapter = new MemberAdapter(options, getApplicationContext(), this);
        activeRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activeRv.setAdapter(adapter);
        Log.d("ActiveRv", "finished rv");
    }

    @Override
    public void onItemClick(String name, String plan, String payment, String profileurl, String phone, String start_date, String end_date, String email) {
        Intent i = new Intent(getApplicationContext(), MemberDetails.class);
        i.putExtra("name", name);
        i.putExtra("plan", plan);
        i.putExtra("payment", payment);
        i.putExtra("profileurl", profileurl);
        i.putExtra("start_date", start_date);
        i.putExtra("end_date", end_date);
        i.putExtra("phone", phone);
        i.putExtra("email", email);
        startActivity(i);
    }

    @Override
    public void callItem(String phone) {
        String phonenum = String.format("tel: %s", phone);
        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        // Set the data for the intent as the phone number.
        dialIntent.setData(Uri.parse(phonenum));
        startActivity(dialIntent);
        // If package resolves to an app, send intent.
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
