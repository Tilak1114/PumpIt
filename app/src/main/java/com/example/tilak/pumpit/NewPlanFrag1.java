package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewPlanFrag1 extends Fragment {

    RelativeLayout next;
    ProgressDialog progressDialog;
    EditText planTitle, planDur, planPrice;
    NewPlanFrag1.NextBtnListener nextBtnListener;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;


    public interface NextBtnListener{
        void onNewPlanBtnClicked1(Boolean result, String title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View NewPlanView1 = inflater.inflate(R.layout.newplan_frag1, container, false);
        progressDialog = new ProgressDialog(getContext());
        planDur = NewPlanView1.findViewById(R.id.plnmonths);
        planPrice = NewPlanView1.findViewById(R.id.planPrice);
        planTitle = NewPlanView1.findViewById(R.id.plntitle);
        next = NewPlanView1.findViewById(R.id.newPlanNext);

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_npf1", GymName);

        return NewPlanView1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PlanDur = planDur.getText().toString()+" Months Plan";
                final String PlanTitle = planTitle.getText().toString();
                String PlanPrice = planPrice.getText().toString();

                progressDialog.setTitle("Creating New Plan");
                progressDialog.setMessage("Adding Data...");
                progressDialog.show();

                DocumentReference newPlan = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                        "/Plans/"+PlanTitle);
                Map<String, Object> data1 = new HashMap<String, Object>();
                data1.put("planName", PlanTitle);
                data1.put("planDuration", PlanDur);
                data1.put("planPrice", PlanPrice);
                data1.put("planFeatures", "Cardio, Strength");
                newPlan.set(data1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nextBtnListener.onNewPlanBtnClicked1(true, PlanTitle);
                        progressDialog.dismiss();
                    }
                });
            }
        });
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
