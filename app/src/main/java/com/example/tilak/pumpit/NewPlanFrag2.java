package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class NewPlanFrag2 extends Fragment {
    RelativeLayout next;
    GridView gridView;
    String PlanTitle;
    NewPlanFrag2.NextBtnListener nextBtnListener;

    public interface NextBtnListener{
        void onNewPlanBtnClicked2(Boolean result);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View NewPlanView2 = inflater.inflate(R.layout.newplan_frag2, container, false);

        gridView = NewPlanView2.findViewById(R.id.gridView);
        next = NewPlanView2.findViewById(R.id.newPlanNext2);

        return NewPlanView2;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gridView.setAdapter(new ImageAdapter(getContext()));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get image selected from gridview
                DocumentReference newPlan = FirebaseFirestore.getInstance().document("Gyms/EvolveFitness"+
                        "/Plans/"+PlanTitle);
                Map<String, Object> data2 = new HashMap<String, Object>();
                data2.put("PlanCoverId", R.drawable.gridwlp4); // later change to user input
                newPlan.set(data2, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nextBtnListener.onNewPlanBtnClicked2(true);
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
    public void getPlanTitle(String title){
        PlanTitle = title;
    }
}

