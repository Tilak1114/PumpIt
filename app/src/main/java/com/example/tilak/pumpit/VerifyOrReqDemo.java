package com.example.tilak.pumpit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyOrReqDemo extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Button reqDemo;
    RelativeLayout validateBtn;
    EditText veriCode;
    ValidateBtnClick validateBtnClick;

    public interface ValidateBtnClick{
        void onValidateClicked(Boolean result, String validCode);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View veriorreqView = inflater.inflate(R.layout.veri_or_req, container, false);

        reqDemo = veriorreqView.findViewById(R.id.reqdemo);
        veriCode = veriorreqView.findViewById(R.id.validcode);
        validateBtn = veriorreqView.findViewById(R.id.validateBtn);

        mAuth.signInWithEmailAndPassword("pumpit@pumpit.com", "pumpitpumpit123").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                reqDemo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), RequestADemo.class));
                    }
                });
                validateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!veriCode.getText().toString().isEmpty()){
                           validateBtnClick.onValidateClicked(true, veriCode.getText().toString());
                        }
                    }
                });
            }
        });

        return veriorreqView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SignUpSecond.NextBtnListener){
            validateBtnClick = (VerifyOrReqDemo.ValidateBtnClick) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        validateBtnClick = null;
    }
}
