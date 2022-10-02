package com.pumpit.app.pumpitpro;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewMembFrag3 extends Fragment {
    RelativeLayout next, upi, cc, dc, csh;
    ProgressDialog progressDialog;
    NextBtnListener nextBtnListener;
    TextView invoiceTotal;
    String selPlanName;

    public interface NextBtnListener{
        void onNewMembBtnClicked3(String planName);
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
        invoiceTotal = NewMembView3.findViewById(R.id.invoicetotal);
        next.setClickable(false);
        assert getArguments() != null;
        selPlanName = getArguments().getString("planName");
        String price = getArguments().getString("planPrice");

        invoiceTotal.setText(price);

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
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("onclicknext3", "clicked");
                        nextBtnListener.onNewMembBtnClicked3(selPlanName);
                    }
                });
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
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("onclicknext3", "clicked");
                        nextBtnListener.onNewMembBtnClicked3(selPlanName);
                    }
                });
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
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("onclicknext3", "clicked");
                        nextBtnListener.onNewMembBtnClicked3(selPlanName);
                    }
                });
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
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("onclicknext3", "clicked");
                        nextBtnListener.onNewMembBtnClicked3(selPlanName);
                    }
                });
            }
        });
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
