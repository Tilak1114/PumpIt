package com.pumpit.app.pumpit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

public class LastMonthFragment extends Fragment {
    TextView revenue, profit;
    CollectionReference transRef;
    String GymName;
    int revenVal, profitVal, expenseVal;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View lastMonthsummv = inflater.inflate(R.layout.last_month_frag, container, false);
        revenue = lastMonthsummv.findViewById(R.id.revvaltv);
        profit = lastMonthsummv.findViewById(R.id.profitvaltv);
        GymName = user.getDisplayName();
        transRef = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Cashflow");
        return lastMonthsummv;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Date now = new Date();
        int month = now.getMonth();
        int year = now.getYear();
        Date lastmonthstart = new Date(year, month-1, 1);
        Date thisMonthstart = new Date(year, month, 1);

        // revenue
        transRef.whereGreaterThanOrEqualTo("timestamp", new Timestamp(lastmonthstart))
                .whereLessThan("timestamp", new Timestamp(thisMonthstart)).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int netrevenue = 0;
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if(Objects.equals(document.get("inorout"), "in")){
                                    int amt = Integer.valueOf(Objects.requireNonNull(document.getString("amount")));
                                    netrevenue = netrevenue+amt;
                                }
                            }
                            revenVal = netrevenue;
                            revenue.setText("\u20B9 "+revenVal);
                        }
                        else
                            revenue.setText("No Data");
                    }
                });

        //expense
        transRef.whereGreaterThanOrEqualTo("timestamp", new Timestamp(lastmonthstart))
                .whereLessThan("timestamp", new Timestamp(thisMonthstart)).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int netexp = 0;
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if(Objects.equals(document.get("inorout"), "out")){
                                    int amt = Integer.valueOf(Objects.requireNonNull(document.getString("amount")));
                                    netexp = netexp+amt;
                                }
                            }
                            expenseVal = netexp;
                        }
                        profitVal = revenVal-expenseVal;
                        profit.setText("\u20B9 "+profitVal);
                    }
                });
    }
}
