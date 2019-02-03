package com.example.tilak.pumpit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ManagePlansFragment extends Fragment {
    ScrollView scrollView;
    LinearLayout planListLL;
    Dialog addplanDialog;
    RelativeLayout addplanfab;
    ImageView cancelpopup;
    LinearLayout.LayoutParams layoutparams;
    Button addplanbtn;
    EditText planDur, planPrice, planDesc;
    TextView newPlan, newPlanMembers;
    CardView cardView;
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View mngplans = inflater.inflate(R.layout.fragment_manage_plans, container, false);
        context = mngplans.getContext();
        scrollView = mngplans.findViewById(R.id.plansscroll);
        addplanDialog = new Dialog(mngplans.getContext(), android.R.style.Theme_Light_NoTitleBar);
        addplanDialog.setContentView(R.layout.addplan_popup);
        planListLL = mngplans.findViewById(R.id.planlistll);
        planDur = addplanDialog.findViewById(R.id.plnmonths);
        planDesc = addplanDialog.findViewById(R.id.planDesc);
        planPrice = addplanDialog.findViewById(R.id.planPrice);
        cancelpopup = addplanDialog.findViewById(R.id.cancelplanpopup);
        addplanbtn = addplanDialog.findViewById(R.id.addnewplan);
        addplanfab = mngplans.findViewById(R.id.addplanfab);
        defaultPlans();
        return mngplans;
    }

    private void defaultPlans() {
        DocumentReference planDoc1 = FirebaseFirestore.getInstance().document("Gyms/MalakaFitness"+
                "/Plans/Plan1");
        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("PlanName", "Plan1");
        data1.put("PlanDuration", "3 months");
        data1.put("PlanFeatures", "Cardio, Strength");
        planDoc1.set(data1);

        DocumentReference planDoc2 = FirebaseFirestore.getInstance().document("Gyms/MalakaFitness"+
                "/Plans/Plan2");
        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("PlanName", "Plan2");
        data2.put("PlanDuration", "6 months");
        data2.put("PlanFeatures", "Cardio, Strength");
        planDoc2.set(data2);

        DocumentReference planDoc3 = FirebaseFirestore.getInstance().document("Gyms/MalakaFitness"+
                "/Plans/Plan3");
        Map<String, Object> data3 = new HashMap<String, Object>();
        data3.put("PlanName", "Plan3");
        data3.put("PlanDuration", "12 months");
        data3.put("PlanFeatures", "Cardio, Strength");
        planDoc3.set(data3);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        addplanfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data from dialog and then
                addplanDialog.show();
            }
        });

        addplanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PlanDur = planDur.getText().toString();
                String PlanPrice = planPrice.getText().toString();
                String PlanDesc = planDesc.getText().toString();
                DocumentReference newPlan = FirebaseFirestore.getInstance().document("Gyms/MalakaFitness"+
                        "/Plans/Plan4");
                Map<String, Object> newData = new HashMap<String, Object>();
                newData.put("PlanName", "Plan4");
                newData.put("PlanDuration", PlanDur);
                newData.put("PlanFeatures", PlanDesc);
                newData.put("Price", PlanPrice);
                newPlan.set(newData);
                createDynamicCardView(PlanDur+" Months Plan", "23 Members");
                addplanDialog.dismiss();
            }
        });

        cancelpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addplanDialog.dismiss();
            }
        });
    }

    private void createDynamicCardView(String planName, String planMembers) {
        cardView = new CardView(context);

        layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardView.setLayoutParams(layoutparams);

        newPlan = new TextView(context);
        newPlanMembers = new TextView(context);

        newPlan.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        newPlanMembers.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        newPlan.setText(planName);
        newPlanMembers.setText(planMembers);

        newPlanMembers.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        newPlan.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        newPlan.setTextColor(Color.WHITE);


        newPlan.setGravity(Gravity.BOTTOM);

        cardView.addView(newPlan);

        planListLL.addView(cardView);
    }
}
