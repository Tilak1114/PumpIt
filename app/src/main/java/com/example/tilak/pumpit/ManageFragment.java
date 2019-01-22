package com.example.tilak.pumpit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ManageFragment extends Fragment {
    RelativeLayout memblay, planlay;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View manageFragmentView = inflater.inflate(R.layout.fragment_manage, container, false);
        memblay = manageFragmentView.findViewById(R.id.memblayout);
        planlay = manageFragmentView.findViewById(R.id.planlay);
        return manageFragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        memblay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager myfragmentManager = getFragmentManager ();
                myfragmentManager.beginTransaction().replace(R.id.frag_container, new ManageMembersFragment()).commit();
            }
        });
        planlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager myfragmentManager = getFragmentManager ();
                myfragmentManager.beginTransaction().replace(R.id.frag_container, new ManagePlansFragment()).commit();
            }
        });
    }
}
