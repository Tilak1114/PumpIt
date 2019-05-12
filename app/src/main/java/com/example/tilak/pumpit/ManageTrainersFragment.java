package com.example.tilak.pumpit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.jetbrains.annotations.NotNull;

public class ManageTrainersFragment extends Fragment {
    RelativeLayout fab;
    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View mngtrainers = inflater.inflate(R.layout.fragment_manage_trainers, container, false);
        fab = mngtrainers.findViewById(R.id.addTrainerfab);
        return mngtrainers;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TrainerActivity.class));
            }
        });
    }
}
