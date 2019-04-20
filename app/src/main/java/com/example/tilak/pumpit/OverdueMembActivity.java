package com.example.tilak.pumpit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OverdueMembActivity extends AppCompatActivity implements MemberAdapter.ItemclickListener{
    RecyclerView odRv;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference odmemberref;
    ImageView cancel;

    TextView odmembcnt;

    TelephonyManager telephonyManager;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    MemberAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overdue_memb);
        odRv = findViewById(R.id.odmembRecyclerview);
        GymName = user.getDisplayName();
        odmembcnt = findViewById(R.id.odmmbcnt);
        cancel = findViewById(R.id.closeOdmemb);
        Log.d("GymNameActive", GymName);
        odmemberref = db.collection("Gyms/"+GymName+"/Members");

        DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/MetaData/members");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String cnt = documentSnapshot.getString("overduemembcount");
                odmembcnt.setText(cnt+" Members");
            }
        });
        setUpActiveRecyclerView();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setUpActiveRecyclerView() {
        Query query = odmemberref.whereEqualTo("payment", "Payment Pending");
        FirestoreRecyclerOptions<Member> options = new FirestoreRecyclerOptions.Builder<Member>().setQuery(query, Member.class).build();
        adapter = new MemberAdapter(options, getApplicationContext(), this);
        odRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        odRv.setAdapter(adapter);
        Log.d("ActiveRv", "finished rv");
    }

    @Override
    public void onItemClick(String name, String plan, String payment, String profileurl, String phone, String start_date,
                            String end_date, String email) {
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
