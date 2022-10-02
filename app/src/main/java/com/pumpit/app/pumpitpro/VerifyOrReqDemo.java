package com.pumpit.app.pumpitpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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
        reqDemo.setClickable(false);
        veriCode = veriorreqView.findViewById(R.id.validcode);
        validateBtn = veriorreqView.findViewById(R.id.validateBtn);

        mAuth.signInWithEmailAndPassword("pumpit@pumpit.com", "pumpitpumpit123").addOnCompleteListener(authResult -> {
            if(authResult.isSuccessful()){
                reqDemo.setClickable(true);
                reqDemo.setBackgroundResource(R.drawable.button_states1);
                reqDemo.setOnClickListener(v -> startActivity(new Intent(getActivity(), RequestADemo.class)));
                validateBtn.setOnClickListener(v -> {

                    if(!veriCode.getText().toString().isEmpty()){
                        validateBtnClick.onValidateClicked(true, veriCode.getText().toString());
                    }
                });
            }
           else {
                Log.d("Fail", "Fail");
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
