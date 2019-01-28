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

public class ManagePlansFragment extends Fragment {
    ScrollView scrollView;
    LinearLayout planListLL;
    Dialog addplanDialog;
    RelativeLayout addplanfab;
    ImageView cancelpopup;
    LinearLayout.LayoutParams layoutparams;
    Button addplanbtn;
    EditText planDur, planPrice;
    TextView newPlan, newPlanMembers;
    CardView cardView;
    Context context= getContext();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View mngplans = inflater.inflate(R.layout.fragment_manage_plans, container, false);
        scrollView = mngplans.findViewById(R.id.plansscroll);
        addplanDialog = new Dialog(mngplans.getContext(), android.R.style.Theme_Light_NoTitleBar);
        addplanDialog.setContentView(R.layout.addplan_popup);
        planListLL = mngplans.findViewById(R.id.planlistll);
        planDur = addplanDialog.findViewById(R.id.plnmonths);
        planPrice = addplanDialog.findViewById(R.id.planPrice);
        cancelpopup = addplanDialog.findViewById(R.id.cancelplanpopup);
        addplanbtn = addplanDialog.findViewById(R.id.addnewplan);
        addplanfab = mngplans.findViewById(R.id.addplanfab);

        return mngplans;
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
                createDynamicCardView(PlanDur+" Months Plan", "23 Members");
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
