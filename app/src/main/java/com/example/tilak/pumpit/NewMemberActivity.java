package com.example.tilak.pumpit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.shuhart.stepview.StepView;

public class NewMemberActivity extends AppCompatActivity implements NewMembFrag1.NextBtnListener {

    StepView stepView;
    FrameLayout newmembcont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member);
        stepView = findViewById(R.id.stepViewMemb);
        newmembcont = findViewById(R.id.newMemb_container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.newMemb_container, new NewMembFrag1()).commit();
    }

    @Override
    public void onNewMembBtnClicked1(Boolean result) {
        if(result){
            getSupportFragmentManager().beginTransaction().replace(R.id.newMemb_container,
                    new NewMembFrag2()).commit();
        }
    }
}
