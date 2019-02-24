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
    Dialog myDialog;
    RelativeLayout addmemb;
    FirebaseAuth firebaseAuth;
    ImageView cancel;
    TextView allmembs, activememb, odmemb;
    Button testlogout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View ovfragview = inflater.inflate(R.layout.home_overview_fragment, container, false);
        allmembs = ovfragview.findViewById(R.id.seeallmemb);
        activememb = ovfragview.findViewById(R.id.activecount);
        odmemb = ovfragview.findViewById(R.id.odcount);
        myDialog = new Dialog(ovfragview.getContext(), android.R.style.Theme_Light_NoTitleBar);
        myDialog.setContentView(R.layout.newmemb_popup);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            getActivity().finish();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        addmemb = ovfragview.findViewById(R.id.addmembfab);
        cancel = myDialog.findViewById(R.id.cancelpopup);
        testlogout = myDialog.findViewById(R.id.testlogout);

        DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/EvolveFitness/MetaData/members");
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
    public void showPopup()
    {
        myDialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        testlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
                builder.setTitle("Confirm Sign out").setMessage("Are you sure you want to sign out?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        startActivity(new Intent(getActivity(), Activity2.class));
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
}
