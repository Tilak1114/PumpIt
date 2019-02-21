package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NewMembFrag2 extends Fragment {
    RelativeLayout next;
    ProgressDialog progressDialog;
    NextBtnListener nextBtnListener;
    EditText email, pwd, cpwd;

    PlanSelAdapter adapter;

    RecyclerView planRv;
    FirebaseAuth mAuth;

    public interface NextBtnListener{
        void onNewMembBtnClicked2(Boolean result);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View NewMembView1 = inflater.inflate(R.layout.newmemb_frag2, container, false);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        planRv = NewMembView1.findViewById(R.id.planSelectorRecyclerview);
        next = NewMembView1.findViewById(R.id.newMembNext2);

        return NewMembView1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setupRecyclerView();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtnListener.onNewMembBtnClicked2(true);
            }
        });
    }
    private void setupRecyclerView() {
        CollectionReference planCollection = FirebaseFirestore.getInstance()
                .collection("Gyms/EvolveFitness/Plans");
        Query query = planCollection;
        FirestoreRecyclerOptions<Plan> options = new FirestoreRecyclerOptions.Builder<Plan>().
                setQuery(query, Plan.class).build();
        adapter = new PlanSelAdapter(options);
        planRv.setAdapter(adapter);
        planRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NextBtnListener){
            nextBtnListener = (NextBtnListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        nextBtnListener = null;
    }
}
