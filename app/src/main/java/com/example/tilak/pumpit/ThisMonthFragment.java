package com.example.tilak.pumpit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.text.format.DateUtils;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ThisMonthFragment extends Fragment {
    TextView totalEarning, thisweekaddn;
    String GymName;
    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
    CollectionReference cashRef;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View thisMonthsummv = inflater.inflate(R.layout.this_month_frag, container, false);
        totalEarning = thisMonthsummv.findViewById(R.id.totalearningval);
        thisweekaddn = thisMonthsummv.findViewById(R.id.additionvalcsh);

        GymName = user.getDisplayName();

        cashRef = FirebaseFirestore.getInstance().collection("Gyms/"+GymName+"/Cashflow");

        return thisMonthsummv;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Date now = new Date();
        int month = now.getMonth();
        int year = now.getYear();
        int day = now.getDay();
        int datenumber = now.getDate();
        Log.d("thismonthday", String.valueOf(datenumber));
        Date date = new Date(year, month, 1);
        Date weekdatestart = new Date(year, month, datenumber-day);
        cashRef.whereGreaterThanOrEqualTo("timestamp", new Timestamp(date)).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int earningCount = 0;
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if(Objects.equals(document.get("inorout"), "in")){
                                    int amt = Integer.valueOf(Objects.requireNonNull(document.getString("amount")));
                                    earningCount = earningCount+amt;
                                }
                            }
                            totalEarning.setText("\u20B9 "+earningCount);
                        }
                        else
                            totalEarning.setText(" ");
                    }
                });
        cashRef.whereGreaterThanOrEqualTo("timestamp", weekdatestart).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int weekcount = 0;
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        if(Objects.equals(document.get("inorout"), "in")){
                            int amt = Integer.valueOf(Objects.requireNonNull(document.getString("amount")));
                            weekcount = weekcount+amt;
                        }
                    }
                    thisweekaddn.setText("+"+weekcount);
                }
                else
                    thisweekaddn.setText("+0");
            }
        });
    }
}
