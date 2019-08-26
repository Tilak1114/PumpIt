package com.pumpit.app.pumpit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignUpThird extends Fragment{
    List<String> selList, tempList;
    ExpandableListView expandableListView;
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
        View SnpThrV = inflater.inflate(R.layout.signup_final, container, false);
        expandableListView = SnpThrV.findViewById(R.id.expandGymFaci);

        faciChildren = GymFaciList.getInfo();
        faciParent = new ArrayList<String>(faciChildren.keySet());
        adapter = new GymFaciAdapter(getContext(), faciParent, faciChildren);
        expandableListView.setAdapter(adapter);
        next = SnpThrV.findViewById(R.id.Next3);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("ExpCBChk", )
                nextBtnListener.onBtnClickF(false, selList);//skipped for now
            }
        });
        return SnpThrV;
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
