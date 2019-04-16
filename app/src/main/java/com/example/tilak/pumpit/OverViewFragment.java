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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class OverViewFragment extends Fragment {
    RelativeLayout addmemb, messages, leads;
    FirebaseAuth firebaseAuth;
    ProgressBar pb1, pb2;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView allmembs, activememb, odmemb, activerect, odrect;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View ovfragview = inflater.inflate(R.layout.home_overview_fragment, container, false);

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_ov", GymName);

        allmembs = ovfragview.findViewById(R.id.seeallmemb);
        pb1= ovfragview.findViewById(R.id.pb1);
        pb2 = ovfragview.findViewById(R.id.pb2);
        leads = ovfragview.findViewById(R.id.leadstsk);
        messages = ovfragview.findViewById(R.id.messagetsk);
        activememb = ovfragview.findViewById(R.id.activecount);
        odmemb = ovfragview.findViewById(R.id.odcount);
        activerect = ovfragview.findViewById(R.id.activerectbtn);
        odrect = ovfragview.findViewById(R.id.odtv);

        swipeRefreshLayout = ovfragview.findViewById(R.id.pullToRefreshOv);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Objects.requireNonNull(getActivity()).finish();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        addmemb = ovfragview.findViewById(R.id.addmembfab);

        if(activememb.getText().toString().equals("")&&odmemb.getText().toString().equals("")){
            pb1.setVisibility(View.VISIBLE);
            pb2.setVisibility(View.VISIBLE);
            DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/MetaData/members");
            dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String actcnt = documentSnapshot.getString("activemembcount");
                    String odcnt = documentSnapshot.getString("overduemembcount");
                    activememb.setText(actcnt);
                    odmemb.setText(odcnt);
                    pb1.setVisibility(View.INVISIBLE);
                    pb2.setVisibility(View.INVISIBLE);
                }
            });
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activememb.setText("");
                odmemb.setText("");
                pb1.setVisibility(View.VISIBLE);
                pb2.setVisibility(View.VISIBLE);
                DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/MetaData/members");
                dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String actcnt = documentSnapshot.getString("activemembcount");
                        String odcnt = documentSnapshot.getString("overduemembcount");
                        activememb.setText(actcnt);
                        odmemb.setText(odcnt);
                        pb1.setVisibility(View.INVISIBLE);
                        pb2.setVisibility(View.INVISIBLE);
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        activerect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frag_container, new ActiveMembFrag())
                        .addToBackStack(null).commit();
            }
        });

        odrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frag_container, new OverDueMembFrag())
                        .addToBackStack(null).commit();
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

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });

        leads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LeadsActivity.class));
            }
        });

        allmembs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frag_container, new ManageMembersFragment()).addToBackStack(null).commit();
            }
        });
    }
}
