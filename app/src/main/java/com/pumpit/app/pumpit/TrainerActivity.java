package com.pumpit.app.pumpit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shuhart.stepview.StepView;

import java.util.List;

public class TrainerActivity extends AppCompatActivity implements NewTrainerFrag1.NextBtnListener, NewTrainerFrag2.TrainerFinishClick{
    StepView stepView;
    FrameLayout newTrainercont;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        GymName = user.getDisplayName();
        stepView = findViewById(R.id.stepViewTrainer);
        newTrainercont = findViewById(R.id.newTrainer_container);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.newTrainer_container, new NewTrainerFrag1()).commit();
    }

    @Override
    public void onNewTrainerBtnClicked1(Boolean result, String trainerName) {
        if(result){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.newTrainer_container, new NewTrainerFrag2()).commit();
            stepView.go(1, true);
        }
    }

    @Override
    public void onFinishClick(List selList) {
        stepView.done(true);
        finish();
    }
}
