package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NewMembFrag2 extends Fragment {
    RelativeLayout next;
    ProgressDialog progressDialog;
    NextBtnListener nextBtnListener;
    EditText email, pwd, cpwd;

    PlanSelAdapter adapter;

    String membName;

    RecyclerView planRv;
    FirebaseAuth mAuth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    public interface NextBtnListener{
        void onNewMembBtnClicked2(Boolean result, String planPrice, String planName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View NewMembView1 = inflater.inflate(R.layout.newmemb_frag2, container, false);
        membName = getArguments().getString("membName");
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        planRv = NewMembView1.findViewById(R.id.planSelectorRecyclerview);
        next = NewMembView1.findViewById(R.id.newMembNext2);

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_nmf2", GymName);

        return NewMembView1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        setupRecyclerView();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adapter.getPlanSel().isEmpty()&&adapter.getPlanRate().isEmpty()){
                    Toast.makeText(getContext(), "Select a Plan", Toast.LENGTH_SHORT).show();
                }
                else{
                    String startDate, endDate, edateyr, sdateyr;

                    Calendar sdate = Calendar.getInstance();
                    Calendar edate = Calendar.getInstance();

                    String duration = adapter.getPlanSel().replaceAll("[^\\d.]", "");

                    startDate = sdate.getTime().toString().substring(4, 10);
                    sdateyr = sdate.getTime().toString().substring(29, 34);
                    startDate = startDate + sdateyr;

                    edate.add(Calendar.MONTH, Integer.parseInt(duration));

                    endDate = edate.getTime().toString().substring(4, 10);
                    edateyr = edate.getTime().toString().substring(29, 34);
                    endDate = endDate + edateyr;

                    DocumentReference documentReference = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Members/"+membName);

                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("membPlan", adapter.getPlanSel());
                    data.put("payment", "Fees Paid");// later move this to frag three
                    data.put("start_date", startDate);
                    data.put("end_date", endDate);
                    data.put("email", "");
                    documentReference.set(data, SetOptions.merge());
                    nextBtnListener.onNewMembBtnClicked2(true, adapter.getPlanRate(), adapter.getPlanName());
                }
            }
        });
    }
    private void setupRecyclerView() {
        CollectionReference planCollection = FirebaseFirestore.getInstance()
                .collection("Gyms/"+GymName+"/Plans");
        Query query = planCollection;
        FirestoreRecyclerOptions<Plan> options = new FirestoreRecyclerOptions.Builder<Plan>().
                setQuery(query, Plan.class).build();
        adapter = new PlanSelAdapter(options, getContext());
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

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }
}
