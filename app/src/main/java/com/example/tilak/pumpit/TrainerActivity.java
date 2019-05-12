package com.example.tilak.pumpit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shuhart.stepview.StepView;

public class TrainerActivity extends AppCompatActivity {
    StepView stepView;
    FrameLayout newmembcont;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);

    }
}
