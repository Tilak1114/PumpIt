package com.example.tilak.pumpit;

import android.content.Context;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpEmailPassword extends Fragment {
    RelativeLayout next;
    NextBtnListener nextBtnListener;
    EditText email, pwd, cpwd;
    FirebaseAuth mAuth;

    public interface NextBtnListener{
        void onBtnClicked(Boolean result);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View emailpwdV = inflater.inflate(R.layout.signup_empwd, container, false);
        mAuth = FirebaseAuth.getInstance();

        next = emailpwdV.findViewById(R.id.Next1);
        email = emailpwdV.findViewById(R.id.adminemail);
        pwd = (EditText) emailpwdV.findViewById(R.id.adminpwd);
        cpwd = (EditText) emailpwdV.findViewById(R.id.admincnfrmpwd);
        return emailpwdV;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
                    email.setError("Email required");
                }
                if(pwd.getText().toString().isEmpty()){
                    pwd.setError("Required");
                }
                if(cpwd.getText().toString().isEmpty()){
                    cpwd.setError("Required");
                }
                if(!pwd.getText().toString().equals(cpwd.getText().toString())){
                   cpwd.setError("Passwords Dont Match");
                }
                if(pwd.getText().toString().equals(cpwd.getText().toString()) &&
                        !email.getText().toString().isEmpty()){
                    String GymEmail = email.getText().toString();
                    String GymPwd = pwd.getText().toString();

                    mAuth.createUserWithEmailAndPassword(GymEmail, GymPwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Boolean res = true;
                            nextBtnListener.onBtnClicked(res);
                        }
                    });
                }
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
