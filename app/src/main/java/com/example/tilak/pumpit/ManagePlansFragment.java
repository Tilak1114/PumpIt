package com.example.tilak.pumpit;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ManagePlansFragment extends Fragment {
    ScrollView scrollView;
    Dialog addplanDialog;
    RelativeLayout addplanfab;
    GridView gridview;
    ImageView cancelpopup;
    Button cancel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View mngplans = inflater.inflate(R.layout.fragment_manage_plans, container, false);
        scrollView = mngplans.findViewById(R.id.plansscroll);
        cancel = mngplans.findViewById(R.id.planclose);
        addplanDialog = new Dialog(mngplans.getContext(), android.R.style.Theme_Light_NoTitleBar);
        addplanDialog.setContentView(R.layout.addplan_popup);
        cancelpopup = addplanDialog.findViewById(R.id.cancelplanpopup);
        addplanfab = mngplans.findViewById(R.id.addplanfab);

        gridview = addplanDialog.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getContext()));
        return mngplans;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager myfragmentManager = getFragmentManager ();
                myfragmentManager.beginTransaction().replace(R.id.frag_container, new ManageMembersFragment()).commit();
            }
        });
        addplanfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data from dialog and then
                addplanDialog.show();
            }
        });
        cancelpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addplanDialog.dismiss();
            }
        });
    }
}
