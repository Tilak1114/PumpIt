package com.example.tilak.pumpit;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity implements SignUpEmailPassword.NextBtnListener, SignUpSecond.NextBtnListener {
    StepView stepView;
    FrameLayout snpcont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        stepView = findViewById(R.id.stepView);
        snpcont = findViewById(R.id.signup_container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.signup_container, new SignUpEmailPassword()).commit();
    }

    @Override
    public void onBtnClicked(Boolean result) {
        if(result){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.signup_container, new SignUpSecond()).commit();
            stepView.go(1, true);
        }
    }

    @Override
    public void onBtnClick(Boolean result) {
        if(result){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.signup_container, new SignUpSecond()).commit();
            stepView.go(2, true);
        }
    }
}
