package com.example.tilak.pumpit;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class OverViewFragment extends Fragment {
    Dialog myDialog;
    RelativeLayout addmemb;
    ImageView cancel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View ovfragview = inflater.inflate(R.layout.home_overview_fragment, container, false);
        myDialog = new Dialog(ovfragview.getContext(), android.R.style.Theme_Light_NoTitleBar);
        addmemb = ovfragview.findViewById(R.id.addmembfab);
        cancel = myDialog.findViewById(R.id.cancelpopup);
        return ovfragview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addmemb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
    }
    public void showPopup()
    {
        myDialog.setContentView(R.layout.newmemb_popup);
        myDialog.show();
    }
}
