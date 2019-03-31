package com.example.tilak.pumpit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class NewPlanFrag3 extends Fragment {

    NewPlanFrag3.NextBtnListener nextBtnListener;
    RelativeLayout next3;

    public interface NextBtnListener{
        void onNewPlanBtnClicked3(Boolean result);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View NewPlanView3 = inflater.inflate(R.layout.newplan_frag3, container, false);
        next3 = NewPlanView3.findViewById(R.id.newPlanNext3);
        return NewPlanView3;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtnListener.onNewPlanBtnClicked3(true);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NewPlanFrag3.NextBtnListener){
            nextBtnListener = (NewPlanFrag3.NextBtnListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        nextBtnListener = null;
    }
}
