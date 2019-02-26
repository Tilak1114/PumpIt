package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InAppActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //remove later
    String GymName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if(user==null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        GymName = user.getDisplayName();

        Log.d("GymMetainfo", GymName);

        defaultPlans();

        setupPlansWithCount();

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
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, segmentSelected).addToBackStack(null).commit();
                return true;
            }
        });
    }
    private void defaultPlans() {
        DocumentReference planDoc1 = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                "/Plans/Plan1");
        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("planName", "Plan1");
        data1.put("planPrice", "2500");
        data1.put("planDuration", "3 Months Plan");
        data1.put("planMembCount", "5");
        data1.put("planFeatures", "Cardio, Strength");
        data1.put("coverId", R.drawable.plan1);
        planDoc1.set(data1);

        DocumentReference planDoc2 = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                "/Plans/Plan2");
        Map<String, Object> data2 = new HashMap<String, Object>();
        data2.put("planName", "Plan2");
        data2.put("planPrice", "5000");
        data2.put("planMembCount", "3");
        data2.put("planDuration", "6 Months Plan");
        data2.put("planFeatures", "Cardio, Strength");
        data2.put("coverId", R.drawable.plan2);
        planDoc2.set(data2);

        DocumentReference planDoc3 = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                "/Plans/Plan3");
        Map<String, Object> data3 = new HashMap<String, Object>();
        data3.put("planName", "Plan3");
        data3.put("planPrice", "12000");
        data3.put("planMembCount", "2");
        data3.put("planDuration", "12 Months Plan");
        data3.put("planFeatures", "Cardio, Strength");
        data3.put("coverId", R.drawable.plan3);
        planDoc3.set(data3);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            Toast.makeText(getApplicationContext(), "Stack empty", Toast.LENGTH_LONG).show();
        }
    }

    public void setupPlansWithCount(){
        final ArrayList<String> planNameList = new ArrayList<>();
        final ArrayList<Integer> planCountList = new ArrayList<>();
        CollectionReference cr = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Plans");
        cr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //Log.d("planArri", document.getId());
                    if(!planNameList.contains(document.getId())){
                        planNameList.add(document.getId());
                    }
                }
                CollectionReference membRef = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members");
                membRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d("planNamelst", String.valueOf(planNameList.size()));
                            for(int i = 0; i<planNameList.size(); i++){
                                planCountList.add(0);
                            }
                            Log.d("plancntsize", String.valueOf(planCountList.size()));
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for(int i = 0; i<planNameList.size(); i++){
                                    if(planNameList.get(i).equals(document.getString("planName"))){
                                        Integer cnt = planCountList.get(i);
                                        cnt = cnt+1;
                                        planCountList.set(i, cnt);
                                    }
                                }
                            }
                            for(int j = 0; j<planCountList.size();j++){
                                Log.d("plancntval", String.valueOf(planCountList.get(j)));
                            }
                        }
                    }
                });
            }
        });
        for(int i =0; i<planNameList.size(); i++){
            DocumentReference writeRef = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                    "/Plans/"+planNameList.get(i));
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("planMembCount", planCountList.get(i));
            writeRef.set(data);
        }
    }
}
