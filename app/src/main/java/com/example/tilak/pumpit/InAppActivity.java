package com.example.tilak.pumpit;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InAppActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        defaultPlans();

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment segmentSelected = null;
                switch(menuItem.getItemId()){
                    case R.id.action_home:
                        segmentSelected = new HomeFragment();
                        break;
                    case R.id.action_manage:
                        segmentSelected = new ManageFragment();
                        break;
                    case R.id.action_store:
                        segmentSelected = new StoreFragment();
                        break;
                    case R.id.action_profile:
                        segmentSelected = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, segmentSelected).commit();
                return true;
            }
        });
    }
    private void defaultPlans() {
        DocumentReference planDoc1 = FirebaseFirestore.getInstance().document("Gyms/EvolveFitness"+
                "/Plans/Plan1");
        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("planName", "Plan1");
        data1.put("planPrice", "2500");
        data1.put("planDuration", "3 Months Plan");
        data1.put("planFeatures", "Cardio, Strength");
        data1.put("coverId", R.drawable.plan1);
        planDoc1.set(data1);

        DocumentReference planDoc2 = FirebaseFirestore.getInstance().document("Gyms/EvolveFitness"+
                "/Plans/Plan2");
        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("planName", "Plan2");
        data2.put("planPrice", "5000");
        data2.put("planDuration", "6 Months Plan");
        data2.put("planFeatures", "Cardio, Strength");
        data2.put("coverId", R.drawable.plan2);
        planDoc2.set(data2);

        DocumentReference planDoc3 = FirebaseFirestore.getInstance().document("Gyms/EvolveFitness"+
                "/Plans/Plan3");
        Map<String, Object> data3 = new HashMap<String, Object>();
        data3.put("planName", "Plan3");
        data3.put("planPrice", "12000");
        data3.put("planDuration", "12 Months Plan");
        data3.put("planFeatures", "Cardio, Strength");
        data3.put("coverId", R.drawable.plan3);
        planDoc3.set(data3);
    }

}
