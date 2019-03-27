package com.example.tilak.pumpit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements SignUpEmailPassword.NextBtnListener,
        SignUpSecond.NextBtnListener, SignUpThird.NextBtnListener, VerifyOrReqDemo.ValidateBtnClick {

    StepView stepView;
    FrameLayout snpcont;
    String GymName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        stepView = findViewById(R.id.stepView);
        snpcont = findViewById(R.id.signup_container);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.signup_container, new VerifyOrReqDemo()).commit();
    }

    @Override
    public void onBtnClicked(Boolean result) {
        if(result){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.signup_container, new SignUpSecond()).commit();
            stepView.go(2, true);
        }
    }

    @Override
    public void onBtnClick(Boolean result, String Gymname) {
        if(result){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null){
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(Gymname).build();

                user.updateProfile(profileUpdates);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.signup_container, new SignUpThird()).commit();
            stepView.go(3, true);
        }
    }

    @Override
    public void onBtnClickF(Boolean result) {
        if(result){
            stepView.go(4, true);
            stepView.done(true);
            initSetup();
            startActivity(new Intent(getApplicationContext(), InAppActivity.class));
        }
    }

    public void initSetup(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        GymName = user.getDisplayName();

        DocumentReference membmeta = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/MetaData/members");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("activemembcount", "0");
        data.put("allmembcount", "0");
        data.put("overduemembcount", "0");
        membmeta.set(data, SetOptions.merge());

        defaultPlanSetup();

        DocumentReference planmeta = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/MetaData/plans");

        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("plancount", "3");
        planmeta.set(data1, SetOptions.merge());

        DocumentReference trainermeta = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/MetaData/trainers");

        Map<String, Object> data2 = new HashMap<String, Object>();
        data1.put("trainercnt", "0");
        trainermeta.set(data2, SetOptions.merge());
    }

    private void defaultPlanSetup() {

        /*DocumentReference Metadatamemb = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                "/MetaData/members");

        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("activemembcount", "0");
        metadata.put("allmembcount", "0");
        metadata.put("overduemembcount", "0");

        Metadatamemb.set(metadata);

        DocumentReference MetadataPlan = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                "/MetaData/plans");

        Map<String, Object> metadata1 = new HashMap<String, Object>();
        metadata1.put("plancount", "3");

        Metadatamemb.set(metadata);*/

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
    public void onValidateClicked(Boolean result, final String validCode) {
        final List<String> codes = new ArrayList<>();
        CollectionReference validcodes = FirebaseFirestore.getInstance().collection("VerificationCodes");
        validcodes.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               codes.add(document.get("code").toString());
                               if(codes.contains(validCode)){
                                   FirebaseAuth.getInstance().signOut();
                                   stepView.go(1, true);
                                   getSupportFragmentManager().beginTransaction()
                                           .replace(R.id.signup_container, new SignUpEmailPassword()).commit();
                               }
                               else {
                                   Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_LONG).show();
                               }
                            }
                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
