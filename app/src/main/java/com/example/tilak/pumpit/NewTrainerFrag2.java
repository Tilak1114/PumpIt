package com.example.tilak.pumpit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewTrainerFrag2 extends Fragment {

    public interface TrainerFinishClick{
        public void onFinishClick();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View trainerFrag2 = inflater.inflate(R.layout.newtrainer_frag2, container, false);
        return trainerFrag2;
    }
}
