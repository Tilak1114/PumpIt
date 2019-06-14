package com.example.tilak.pumpit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class OverViewFragment extends Fragment {
    RelativeLayout addmemb, messages, leads;
    LinearLayout taskslay, actodlay;
    FirebaseAuth firebaseAuth;
    ProgressBar pb1, pb2;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView allmembs, activememb, odmemb, activerect, odrect;
    PieChartView pieChartView;

    Handler handler;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View ovfragview = inflater.inflate(R.layout.home_overview_fragment, container, false);

        handler = new Handler();
        GymName = user.getDisplayName();

        Log.d("GymMetainfo_ov", GymName);

        allmembs = ovfragview.findViewById(R.id.seeallmemb);
        taskslay = ovfragview.findViewById(R.id.qcklnkslayout);
        actodlay = ovfragview.findViewById(R.id.ov_actod_lay);
        pb1= ovfragview.findViewById(R.id.pb1);
        pb2 = ovfragview.findViewById(R.id.pb2);
        leads = ovfragview.findViewById(R.id.leadstsk);
        pieChartView = ovfragview.findViewById(R.id.piechart);
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

       // actodlay.startAnimation(rtl);
        //taskslay.startAnimation(ltr);

        // Initialize charts
        Thread chartThread = new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        List<SliceValue> pieData = new ArrayList<>();

                        Animation rtl = AnimationUtils.loadAnimation(getContext(),R.anim.rtl);
                        Animation ltr = AnimationUtils.loadAnimation(getContext(), R.anim.ltr);

                        pieData.add(new SliceValue(160, Color.parseColor("#87BCBF")));
                        pieData.add(new SliceValue(120, Color.parseColor("#6E8CA0")));
                        pieData.add(new SliceValue(64, Color.parseColor("#D97D54")));

                        PieChartData pieChartData = new PieChartData(pieData);

                        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.product_sans_reg);

                        pieChartData.setHasCenterCircle(true).setCenterText1("330 Members")
                                .setCenterText1FontSize(12).setCenterText1Typeface(typeface)
                                .setCenterText1Color(Color.parseColor("#000000"));

                        pieChartView.setPieChartData(pieChartData);

                        pieChartView.startAnimation(rtl);
                    }
                });
            }
        });
        chartThread.start();


        if(activememb.getText().toString().equals("")&&odmemb.getText().toString().equals("")){
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
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
                    });
                }
            });
            t1.start();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                activememb.setText("");
                                odmemb.setText("");
                                pb1.setVisibility(View.VISIBLE);
                                pb2.setVisibility(View.VISIBLE);
                                DocumentReference dr = FirebaseFirestore.getInstance()
                                        .document("/Gyms/"+GymName+"/MetaData/members");
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
                        });
                    }
                });
                t2.start();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        activerect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActiveMembActivity.class));
            }
        });

        odrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OverdueMembActivity.class));
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
                startActivity(new Intent(getActivity(), AllMembActivity.class));
            }
        });
    }
}
