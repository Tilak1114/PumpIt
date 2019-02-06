package com.example.tilak.pumpit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpThird extends Fragment {

    HashMap<String, List<String>> GymFacilities;
    List<String> faciList;
    ExpandableListView expandableListView;
    GymFaciAdapter adapter;

    NextBtnListener nextBtnListener;
    RelativeLayout next;
    public interface NextBtnListener{
        void onBtnClickF(Boolean result);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View SnpSecV = inflater.inflate(R.layout.signup_final, container, false);
        expandableListView = SnpSecV.findViewById(R.id.expandGymFaci);
        GymFacilities = GymFaciList.getInfo();
        faciList = new ArrayList<String>(GymFacilities.keySet());
        adapter = new GymFaciAdapter(getContext(), GymFacilities, faciList);
        expandableListView.setAdapter(adapter);
        next = SnpSecV.findViewById(R.id.Next3);
        return SnpSecV;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtnListener.onBtnClickF(true);
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SignUpThird.NextBtnListener){
            nextBtnListener = (SignUpThird.NextBtnListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        nextBtnListener = null;
    }

}
