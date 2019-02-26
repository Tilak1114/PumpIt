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
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity implements SignUpEmailPassword.NextBtnListener,
        SignUpSecond.NextBtnListener, SignUpThird.NextBtnListener, VerifyOrReqDemo.ValidateBtnClick {

    StepView stepView;
    FrameLayout snpcont;

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
            startActivity(new Intent(getApplicationContext(), InAppActivity.class));
        }
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
