package com.pumpit.app.pumpitpro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OverDueMembFrag extends Fragment implements  MemberAdapter.ItemclickListener{
    RecyclerView odRv;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference odmemberref;

    TextView odmembcnt;

    TelephonyManager telephonyManager;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    MemberAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View odFragV = inflater.inflate(R.layout.fragment_odmemb, container, false);
        odRv = odFragV.findViewById(R.id.odmembRecyclerview);
        GymName = user.getDisplayName();
        odmembcnt = odFragV.findViewById(R.id.odmmbcnt);
        Log.d("GymNameActive", GymName);
        odmemberref = db.collection("Gyms/"+GymName+"/Members");
        return odFragV;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setUpActiveRecyclerView();
    }

    private void setUpActiveRecyclerView() {
        Query query = odmemberref.whereEqualTo("payment", "Payment Pending");
        FirestoreRecyclerOptions<Member> options = new FirestoreRecyclerOptions.Builder<Member>().setQuery(query, Member.class).build();
        adapter = new MemberAdapter(options, getContext(), this);
        odRv.setLayoutManager(new LinearLayoutManager(getContext()));
        odRv.setAdapter(adapter);
        Log.d("ActiveRv", "finished rv");
    }

    @Override
    public void onItemClick(String name, String plan, String payment, String profileurl, String phone, String start_date,
    String end_date, String email) {
        Intent i = new Intent(getActivity(), MemberDetails.class);
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
        telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
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
