package com.pumpit.app.pumpitpro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class OverViewFragment extends Fragment {
    RelativeLayout addmemb, messages, leads, money;
    LinearLayout taskslay, actodlay;
    FirebaseAuth firebaseAuth;
    ProgressBar pb1, pb2;
    int activecount, odcount;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView allmembs, activememb, odmemb, activerect, odrect;
    List<SliceValue> pieData;
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

        allmembs = ovfragview.findViewById(R.id.seeallmemb);
        taskslay = ovfragview.findViewById(R.id.qcklnkslayout);
        actodlay = ovfragview.findViewById(R.id.ov_actod_lay);
        pb1= ovfragview.findViewById(R.id.pb1);
        pb2 = ovfragview.findViewById(R.id.pb2);
        leads = ovfragview.findViewById(R.id.leadstsk);
        pieChartView = ovfragview.findViewById(R.id.piechart);
        messages = ovfragview.findViewById(R.id.messagetsk);
        activememb = ovfragview.findViewById(R.id.activecount);
        money = ovfragview.findViewById(R.id.finantask);
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
                        pieData = new ArrayList<>();
                        // #87BCBF for active
                        // #D97D54 for PP
                        // #6E8CA0 for inactive

                        FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members/").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    int inactcnt=0, actcnt=0, ppcnt=0, totalcnt=0;
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())){
                                                totalcnt++;
                                                if(Objects.equals(document.get("payment"), "Fees Paid")){
                                                    actcnt++;
                                                }
                                                else if(Objects.equals(document.get("payment"), "Payment Pending")){
                                                    ppcnt++;
                                                }
                                                else if(Objects.equals(document.get("payment"), "Inactive")){
                                                    inactcnt++;
                                                }
                                            }
                                        }
                                        Animation rtl = AnimationUtils.loadAnimation(getContext(), R.anim.rtl);
                                        Animation ltr = AnimationUtils.loadAnimation(getContext(), R.anim.ltr);

                                        pieData.add(new SliceValue(ppcnt, Color.parseColor("#D97D54")));
                                        pieData.add(new SliceValue(actcnt, Color.parseColor("#87BCBF")));
                                        pieData.add(new SliceValue(inactcnt, Color.parseColor("#6E8CA0")));

                                        PieChartData pieChartData = new PieChartData(pieData);

                                        Typeface typeface = ResourcesCompat.getFont(Objects.requireNonNull(getContext()), R.font.product_sans_reg);

                                        pieChartData.setHasCenterCircle(true).setCenterText1(totalcnt+" Members")
                                                .setCenterText1FontSize(12).setCenterText1Typeface(typeface)
                                                .setCenterText1Color(Color.parseColor("#000000"));

                                        pieChartView.setPieChartData(pieChartData);
                                        pieChartView.setVisibility(View.VISIBLE);
                                        pieChartView.startAnimation(rtl);
                                    }
                                });
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
                            FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members")
                                    .whereEqualTo("payment", "Payment Pending").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int count=0;
                                        count= Objects.requireNonNull(task.getResult()).size();
                                        odmemb.setText(String.valueOf(count));
                                        pb2.setVisibility(View.INVISIBLE);
                                    } else {
                                        activememb.setText("-");
                                        pb2.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members")
                                    .whereEqualTo("payment", "Fees Paid").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int count=0;
                                        count= Objects.requireNonNull(task.getResult()).size();
                                        activememb.setText(String.valueOf(count));
                                        pb1.setVisibility(View.INVISIBLE);
                                    } else {
                                        activememb.setText("-");
                                        pb1.setVisibility(View.INVISIBLE);
                                    }
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
//
                                FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members")
                                        .whereEqualTo("payment", "Payment Pending").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            int count=0;
                                            count= Objects.requireNonNull(task.getResult()).size();
                                            odmemb.setText(String.valueOf(count));
                                            pb2.setVisibility(View.INVISIBLE);
                                        } else {
                                            activememb.setText("-");
                                            pb2.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                                FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members")
                                        .whereEqualTo("payment", "Fees Paid").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            int count=0;
                                            count= Objects.requireNonNull(task.getResult()).size();
                                            activememb.setText(String.valueOf(count));
                                            pb1.setVisibility(View.INVISIBLE);
                                        } else {
                                            activememb.setText("-");
                                            pb1.setVisibility(View.INVISIBLE);
                                        }
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
                //temp
                //startAlarm();
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
                startActivity(new Intent(getActivity(), NewLeadActivity.class));
            }
        });

        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewExpense.class));
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
