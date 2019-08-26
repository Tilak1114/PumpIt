package com.pumpit.app.pumpit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScr extends AppCompatActivity {
    ImageView img;
    TextView tv1, tv2, info1, info2;
    ProgressBar splashProgress;
    Button retry;
    private Handler handler = new Handler();
    boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scr);
        splashProgress = findViewById(R.id.splashProgress);
        Animation animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_intwosec);

        img = findViewById(R.id.pumpitimglogo);
        info1 = findViewById(R.id.tvoops);
        info2 = findViewById(R.id.nointernettv);
        tv1 = findViewById(R.id.splashtv1);
        tv2 = findViewById(R.id.splashtv2);
        retry = findViewById(R.id.retry);
        splashProgress.setIndeterminate(true);

        img.startAnimation(animationFadeIn);
        img.setVisibility(View.VISIBLE);
        tv1.startAnimation(animationFadeIn);
        tv1.setVisibility(View.VISIBLE);
        tv2.startAnimation(animationFadeIn);
        tv2.setVisibility(View.VISIBLE);

        connectivityCheck();

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                info1.setVisibility(View.INVISIBLE);
                info2.setVisibility(View.INVISIBLE);
                splashProgress.setVisibility(View.VISIBLE);
                retry.setVisibility(View.INVISIBLE);
                img.startAnimation(animationFadeIn);
                img.setVisibility(View.VISIBLE);
                tv1.startAnimation(animationFadeIn);
                tv1.setVisibility(View.VISIBLE);
                tv2.startAnimation(animationFadeIn);
                tv2.setVisibility(View.VISIBLE);
                connectivityCheck();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void connectivityCheck(){
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
                    else{
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Animation animationFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                                info1.setVisibility(View.VISIBLE);
                                info2.setVisibility(View.VISIBLE);
                                retry.setVisibility(View.VISIBLE);
                                img.setAnimation(animationFadeOut);
                                tv1.setAnimation(animationFadeOut);
                                tv2.setAnimation(animationFadeOut);
                                img.setVisibility(View.INVISIBLE);
                                tv1.setVisibility(View.INVISIBLE);
                                tv2.setVisibility(View.INVISIBLE);
                                splashProgress.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }
            }
        };
        thread.start();
    }
}
