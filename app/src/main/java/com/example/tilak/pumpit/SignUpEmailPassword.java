package com.example.tilak.pumpit;

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

public class SignUpEmailPassword extends Fragment {
    RelativeLayout next;
    EditText email, pwd, cpwd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View emailpwdV = inflater.inflate(R.layout.signup_empwd, container, false);
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
                if(!pwd.equals(cpwd) && !email.getText().toString().isEmpty()){
                   cpwd.setError("Passwords Dont Match");
                }
            }
        });
    }
}
