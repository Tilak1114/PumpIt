package com.example.tilak.pumpit;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.shuhart.stepview.StepView;

public class NewPlanActivity extends AppCompatActivity implements NewPlanFrag1.NextBtnListener, NewPlanFrag2.NextBtnListener, NewPlanFrag3.NextBtnListener {

    StepView stepView;
    FrameLayout newmembcont;
    NewPlanFrag1 newPlanFrag1 = new NewPlanFrag1();
    NewPlanFrag2 newPlanFrag2 = new NewPlanFrag2();
    NewPlanFrag3 newPlanFrag3 = new NewPlanFrag3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);
        stepView = findViewById(R.id.stepViewPlan);
        newmembcont = findViewById(R.id.newPlan_container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.newPlan_container, newPlanFrag1).commit();
    }

    @Override
    public void onNewPlanBtnClicked1(Boolean result, String ptitle) {
        if(result)
        {
            stepView.go(1, true);
            newPlanFrag2.getPlanTitle(ptitle);
            getSupportFragmentManager().beginTransaction().replace(R.id.newPlan_container,
                    newPlanFrag2).commit();
        }
    }

    @Override
    public void onNewPlanBtnClicked2(Boolean result) {
        if(result){
            stepView.go(2, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.newPlan_container,
                    newPlanFrag3).commit();

        }
    }

    @Override
    public void onNewPlanBtnClicked3(Boolean result) {
        stepView.done(true);
        Intent backToInApp = new Intent(NewPlanActivity.this, InAppActivity.class);
        startActivity(backToInApp);
    }
}
