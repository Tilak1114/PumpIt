package com.example.tilak.pumpit;

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

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment {
    ImageView imageChange;
    int images[] = {R.drawable.boxingeq, R.drawable.water, R.drawable.proteins, R.drawable.dumbbell};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View storeV = inflater.inflate(R.layout.fragment_store, container, false);
        imageChange = storeV.findViewById(R.id.storeimgchange);
        return storeV;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //animateStoreImages();
    }

    public void animateStoreImages(){
        Animation aniFadeOut = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out);
        Animation aniFadeIn = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        while(true){
            for(int j = 0; j<4; j++)
            {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                    }
                }, 2000);
                imageChange.startAnimation(aniFadeOut);
                imageChange.setImageResource(images[j]);
                imageChange.startAnimation(aniFadeIn);
            }
        }
    }
}
