package com.example.tilak.pumpit;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoreFragment extends Fragment {
    ImageView imageChange;
    Handler handler = new Handler();

    Animation animationOut;
    Animation animationIn;

    int images[] = {R.drawable.boxingeq, R.drawable.water, R.drawable.proteins, R.drawable.dumbbell};
    int imgId;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View storeV = inflater.inflate(R.layout.fragment_store, container, false);
        imageChange = storeV.findViewById(R.id.storeimgchange);
        return storeV;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        animateStoreImages(view);
    }

    public void animateStoreImages(final View storeV){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    for(int i=0; i< images.length; i++){
                        imgId = images[i];
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                animationOut = AnimationUtils.loadAnimation(storeV.getContext(), R.anim.fade_outtwosec);
                                animationIn = AnimationUtils.loadAnimation(storeV.getContext(), R.anim.fade_intwosec);
                                imageChange.setAnimation(animationOut);
                                imageChange.setVisibility(View.INVISIBLE);
                                imageChange.setImageResource(imgId);
                                imageChange.setAnimation(animationIn);
                                imageChange.setVisibility(View.VISIBLE);
                            }
                        });
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        try {
            thread.start();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
