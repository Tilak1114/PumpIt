package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

public class NewMembFrag3 extends Fragment {
    RelativeLayout next, upi, cc, dc, csh;
    ProgressDialog progressDialog;
    NextBtnListener nextBtnListener;

    public interface NextBtnListener{
        void onNewMembBtnClicked3(Boolean result);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View NewMembView3 = inflater.inflate(R.layout.newmemb_frag3, container, false);
        progressDialog = new ProgressDialog(getContext());
        csh = NewMembView3.findViewById(R.id.cashlay);
        dc = NewMembView3.findViewById(R.id.debitlay);
        cc = NewMembView3.findViewById(R.id.creditlay);
        upi = NewMembView3.findViewById(R.id.upilay);
        next = NewMembView3.findViewById(R.id.newMembNext3);
        return NewMembView3;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        upi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upi.setBackgroundResource(R.drawable.roundedbtnpaymentsel);
                cc.setBackgroundResource(R.drawable.roundedbtnpayment);
                dc.setBackgroundResource(R.drawable.roundedbtnpayment);
                csh.setBackgroundResource(R.drawable.roundedbtnpayment);
                next.setClickable(true);
            }
        });
        csh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upi.setBackgroundResource(R.drawable.roundedbtnpayment);
                cc.setBackgroundResource(R.drawable.roundedbtnpayment);
                dc.setBackgroundResource(R.drawable.roundedbtnpayment);
                csh.setBackgroundResource(R.drawable.roundedbtnpaymentsel);
                next.setClickable(true);
            }
        });
        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upi.setBackgroundResource(R.drawable.roundedbtnpayment);
                cc.setBackgroundResource(R.drawable.roundedbtnpaymentsel);
                dc.setBackgroundResource(R.drawable.roundedbtnpayment);
                csh.setBackgroundResource(R.drawable.roundedbtnpayment);
                next.setClickable(true);
            }
        });
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upi.setBackgroundResource(R.drawable.roundedbtnpayment);
                cc.setBackgroundResource(R.drawable.roundedbtnpayment);
                dc.setBackgroundResource(R.drawable.roundedbtnpaymentsel);
                csh.setBackgroundResource(R.drawable.roundedbtnpayment);
                next.setClickable(true);
            }
        });

        if(next.isClickable()){
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextBtnListener.onNewMembBtnClicked3(true);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NextBtnListener){
            nextBtnListener = (NextBtnListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        nextBtnListener = null;
    }
}
