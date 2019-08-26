package com.pumpit.app.pumpit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

public class StoreFragment extends Fragment {
//    ImageView imageChange;
    ImageView imageView;
//    Handler handler = new Handler();
//    Thread thread;
//
//    Animation animationOut;
//    Animation animationIn;
//
//    int images[] = {R.drawable.boxingeq, R.drawable.water, R.drawable.proteins, R.drawable.dumbbell};
//    int imgId;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View storeV = inflater.inflate(R.layout.fragment_store, container, false);
        imageView = storeV.findViewById(R.id.storebckimg);
//        imageChange = storeV.findViewById(R.id.storeimgchange);
//        animateStoreImages(storeV);
        return storeV;
    }

//    public void animateStoreImages(final View storeV){
////        thread = new Thread(new Runnable() {
////            @Override
////            public void run() {
////                while (true){
////                    for(int i=0; i< images.length; i++){
////                        imgId = images[i];
////                        handler.post(new Runnable() {
////                            @Override
////                            public void run() {
////                                animationOut = AnimationUtils.loadAnimation(storeV.getContext(), R.anim.fade_outtwosec);
////                                animationIn = AnimationUtils.loadAnimation(storeV.getContext(), R.anim.fade_intwosec);
////                                imageChange.setAnimation(animationOut);
////                                imageChange.setVisibility(View.INVISIBLE);
////                                imageChange.setImageResource(imgId);
////                                imageChange.setAnimation(animationIn);
////                                imageChange.setVisibility(View.VISIBLE);
////                            }
////                        });
////                        try {
////                            Thread.sleep(3000);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                }
////            }
////        });
////        thread.start();
////    }
}
