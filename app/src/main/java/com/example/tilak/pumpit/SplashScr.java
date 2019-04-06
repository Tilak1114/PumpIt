package com.example.tilak.pumpit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScr extends AppCompatActivity {
    ImageView img;
    TextView tv1, tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scr);
        Animation animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_intwosec);
        img = findViewById(R.id.pumpitimglogo);
        tv1 = findViewById(R.id.splashtv1);
        tv2 = findViewById(R.id.splashtv2);

        img.startAnimation(animationFadeIn);
        tv1.startAnimation(animationFadeIn);
        tv2.startAnimation(animationFadeIn);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(SplashScr.this, MainActivity.class));
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
