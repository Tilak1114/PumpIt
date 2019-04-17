package com.example.tilak.pumpit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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

import java.util.ArrayList;

public class AllMembActivity extends AppCompatActivity implements MemberAdapter.ItemclickListener{
    RelativeLayout addNewMembFab;
    RecyclerView recyclerView;
    SearchView searchView;
    TextView membcount;
    TelephonyManager telephonyManager;

    ArrayList<String> planNameList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MemberAdapter adapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    CollectionReference memberref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_memb);
        GymName = user.getDisplayName();

        Log.d("GymMetainfo_MMF", GymName);

        memberref = db.collection("Gyms/"+GymName+"/Members");

        //FragmentManager fm =
        //getFragmentManager().beginTransaction()
        // .replace(R.id.newMemb_container, new NewMembFrag1()).commit();

        addNewMembFab = findViewById(R.id.activityaddMembfab);
        searchView = findViewById(R.id.activitysearchmemb);
        membcount = findViewById(R.id.activitymngmmbcnt);
        recyclerView = findViewById(R.id.activitymembRecyclerview);

        DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/MetaData/members");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String cnt = documentSnapshot.getString("allmembcount");
                membcount.setText(cnt+" Members");
            }
        });

        setUpRecyclerView();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                if (dy > 0 && addNewMembFab.getVisibility() == View.VISIBLE) {
                    addNewMembFab.setVisibility(View.INVISIBLE);
                } else if (dy < 0 && addNewMembFab.getVisibility() != View.VISIBLE) {
                    addNewMembFab.setVisibility(View.VISIBLE);
                }
            }
        });

        addNewMembFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewMemberActivity.class));
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFilter(query);
                adapter.startListening();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilter(newText);
                adapter.startListening();
                return false;
            }
        });
    }
    private void searchFilter(String query) {
        Query searchQ = memberref.orderBy("fullname_lc").startAt(query.toLowerCase()).endAt(query.toLowerCase() + "\uf8ff");
        FirestoreRecyclerOptions<Member> options = new FirestoreRecyclerOptions.Builder<Member>().setQuery(searchQ, Member.class).build();
        adapter = new MemberAdapter(options, getApplicationContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setUpRecyclerView() {
        Query query = memberref;
        FirestoreRecyclerOptions<Member> options = new FirestoreRecyclerOptions.Builder<Member>().setQuery(query, Member.class).build();
        adapter = new MemberAdapter(options, getApplicationContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(adapter);
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

    @Override
    public void onResume() {
        super.onResume();
        DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/MetaData/members");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String cnt = documentSnapshot.getString("allmembcount");
                membcount.setText(cnt+" Members");
            }
        });
    }

    @Override
    public void onItemClick(String name, String plan, String payment, String profileurl, String phone,
                            String start_date, String end_date, String email) {
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
}
