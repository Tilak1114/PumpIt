package com.pumpit.app.pumpitpro;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewPlanFrag2 extends Fragment {
    RelativeLayout next;
    RecyclerView planCoverRv;
    String PlanTitle;
    List<PlanCover> planCoverList;

    PlanCoverAdapter adapter;
    NewPlanFrag2.NextBtnListener nextBtnListener;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    private Integer[] mThumbIds = {
            R.drawable.gridwlp1, R.drawable.gridwlp2,
            R.drawable.gridwlp3, R.drawable.gridwlp4,
            R.drawable.gridwlp5, R.drawable.gridwlp6
    };

    public interface NextBtnListener{
        void onNewPlanBtnClicked2(Boolean result);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View NewPlanView2 = inflater.inflate(R.layout.newplan_frag2, container, false);

        planCoverRv = NewPlanView2.findViewById(R.id.planCoverRv);
        next = NewPlanView2.findViewById(R.id.newPlanNext2);
        planCoverList = new ArrayList<>();

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_npf2", GymName);

        return NewPlanView2;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        for (Integer mThumbId : mThumbIds) {
            planCoverList.add(new PlanCover(mThumbId));
        }

        adapter = new PlanCoverAdapter(getContext(), planCoverList);
        planCoverRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        planCoverRv.setAdapter(adapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference newPlan = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                        "/Plans/"+PlanTitle);
                Map<String, Object> data2 = new HashMap<>();
                data2.put("coverId", adapter.getCoverId());
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


