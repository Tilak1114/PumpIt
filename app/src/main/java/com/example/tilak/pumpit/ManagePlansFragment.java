package com.example.tilak.pumpit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagePlansFragment extends Fragment {

    Dialog addplanDialog;
    RelativeLayout addplanfab;
    ImageView cancelpopup;
    TextView plancount;
    RecyclerView planRv;

    Button addplanbtn;
    EditText planDur, planPrice, planDesc, planTitle;
    Context context;

    PlanAdapter adapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View mngplans = inflater.inflate(R.layout.fragment_manage_plans, container, false);
        context = mngplans.getContext();

        addplanDialog = new Dialog(mngplans.getContext(), android.R.style.Theme_Light_NoTitleBar);
        addplanDialog.setContentView(R.layout.addplan_popup);

        planDur = addplanDialog.findViewById(R.id.plnmonths);
        planDesc = addplanDialog.findViewById(R.id.planDesc);
        planPrice = addplanDialog.findViewById(R.id.planPrice);
        planTitle = addplanDialog.findViewById(R.id.plntitle);
        cancelpopup = addplanDialog.findViewById(R.id.cancelplanpopup);
        addplanbtn = addplanDialog.findViewById(R.id.addnewplan);
        addplanfab = mngplans.findViewById(R.id.addplanfab);
        plancount = mngplans.findViewById(R.id.mngplncnt);
        planRv = mngplans.findViewById(R.id.newplanrv);

        GymName = user.getDisplayName();

        FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Plans")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                    plancount.setText(task.getResult().size()+" Active Plans");
                else
                    plancount.setText("0 Active Plans");
            }
        });
        return mngplans;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupRecyclerView();
        addplanfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewPlanActivity.class));
            }
        });

    }

    private void setupRecyclerView() {
        CollectionReference planCollection = FirebaseFirestore.getInstance()
                .collection("Gyms/"+GymName+"/Plans");
        Query query = planCollection;
        FirestoreRecyclerOptions<Plan> options = new FirestoreRecyclerOptions.Builder<Plan>().
                setQuery(query, Plan.class).build();
        adapter = new PlanAdapter(options, getContext());
        planRv.setAdapter(adapter);
        planRv.setLayoutManager(new LinearLayoutManager(getContext()));

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
