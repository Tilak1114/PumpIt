package com.example.tilak.pumpit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OverViewFragment extends Fragment {
    RelativeLayout addmemb;
    FirebaseAuth firebaseAuth;
    TextView allmembs, activememb, odmemb;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View ovfragview = inflater.inflate(R.layout.home_overview_fragment, container, false);

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_ov", GymName);

        allmembs = ovfragview.findViewById(R.id.seeallmemb);
        activememb = ovfragview.findViewById(R.id.activecount);
        odmemb = ovfragview.findViewById(R.id.odcount);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            getActivity().finish();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        addmemb = ovfragview.findViewById(R.id.addmembfab);

        DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/MetaData/members");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String actcnt = documentSnapshot.getString("activemembcount");
                String odcnt = documentSnapshot.getString("overduemembcount");
                activememb.setText(actcnt);
                odmemb.setText(odcnt);
            }
        });

        return ovfragview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addmemb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewMemberActivity.class));
            }
        });
        allmembs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frag_container, new ManageMembersFragment()).addToBackStack(null).commit();
            }
        });
    }
}
