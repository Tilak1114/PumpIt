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
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpThird extends Fragment{
    //List<String> faciList;
    ExpandableListView expandableListView;
    HashMap<String, List<String>> selList;
    GymFaciAdapter adapter;
    List<String> faciParent;
    HashMap<String, List<String>> faciChildren;

    NextBtnListener nextBtnListener;
    RelativeLayout next;

    public interface NextBtnListener{
        void onBtnClickF(Boolean result, List<String> selItems);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View SnpSecV = inflater.inflate(R.layout.signup_final, container, false);
        expandableListView = SnpSecV.findViewById(R.id.expandGymFaci);

        faciChildren = GymFaciList.getInfo();
        faciParent = new ArrayList<String>(faciChildren.keySet());
        adapter = new GymFaciAdapter(getContext(), faciParent, faciChildren);
        expandableListView.setAdapter(adapter);
        next = SnpSecV.findViewById(R.id.Next3);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("ExpCBChk", )
                nextBtnListener.onBtnClickF(true, adapter.getSelItems());
            }
        });
        /*expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(),
                        faciParent.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(),
                        faciParent.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();
            }
        });*/
        return SnpSecV;
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
