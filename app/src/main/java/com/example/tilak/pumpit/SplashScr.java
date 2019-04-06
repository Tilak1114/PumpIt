package com.example.tilak.pumpit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScr extends AppCompatActivity {
    ImageView img;
    TextView tv1, tv2;
    ProgressBar splashProgress;
    boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scr);
        splashProgress = findViewById(R.id.splashProgress);
        Animation animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_intwosec);
        img = findViewById(R.id.pumpitimglogo);
        tv1 = findViewById(R.id.splashtv1);
        tv2 = findViewById(R.id.splashtv2);
        splashProgress.setIndeterminate(true);

        img.startAnimation(animationFadeIn);
        tv1.startAnimation(animationFadeIn);
        tv2.startAnimation(animationFadeIn);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(2500);
                    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        connected = true;
                    }
                    else
                        connected = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    if(connected){
                        startActivity(new Intent(SplashScr.this, MainActivity.class));
                    }
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
