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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ManageTrainersFragment extends Fragment implements TrainerAdapter.ItemclickListener{
    RelativeLayout fab;
    RecyclerView trainerRv;
    TrainerAdapter adapter;
    SearchView searchView;
    TelephonyManager telephonyManager;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    CollectionReference trainerref;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View mngtrainers = inflater.inflate(R.layout.fragment_manage_trainers, container, false);
        fab = mngtrainers.findViewById(R.id.addTrainerfab);
        GymName = user.getDisplayName();
        trainerref = db.collection("Gyms/"+GymName+"/Trainers");
        trainerRv = mngtrainers.findViewById(R.id.trainerRecyclerview);
        searchView = mngtrainers.findViewById(R.id.trainersearch);
        return mngtrainers;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setUpRecyclerView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TrainerActivity.class));
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
        Query searchQ = trainerref.orderBy("fullname_lc").startAt(query.toLowerCase()).endAt(query.toLowerCase() + "\uf8ff");
        FirestoreRecyclerOptions<Trainer> options = new FirestoreRecyclerOptions.Builder<Trainer>().setQuery(searchQ, Trainer.class).build();
        adapter = new TrainerAdapter(options, getContext(), this);
        trainerRv.setLayoutManager(new LinearLayoutManager(getContext()));
        trainerRv.setAdapter(adapter);
    }

    private void setUpRecyclerView() {
        Query query = trainerref;
        FirestoreRecyclerOptions<Trainer> options = new FirestoreRecyclerOptions.Builder<Trainer>().setQuery(query, Trainer.class).build();
        adapter = new TrainerAdapter(options, getContext(), this);
        trainerRv.setLayoutManager(new LinearLayoutManager(getContext()));
        trainerRv.setAdapter(adapter);
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
    public void onItemClick(String name, String profileurl, String phone, String email, String trainees, String trainerSpecs, String salaryStatus, Integer salary) {

    }

    @Override
    public void callItem(String phone) {
        String phonenum = String.format("tel: %s", phone);
        telephonyManager = (TelephonyManager) Objects.requireNonNull(getActivity()).getSystemService(Context.TELEPHONY_SERVICE);
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        // Set the data for the intent as the phone number.
        dialIntent.setData(Uri.parse(phonenum));
        startActivity(dialIntent);
    }
}
