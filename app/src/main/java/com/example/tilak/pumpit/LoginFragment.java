package com.example.tilak.pumpit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    Button lgn;
    Dialog myDialog;
    EditText em_inp, pwd_inp;
    RelativeLayout dialog_login;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View login = inflater.inflate(R.layout.login_frag, container, false);
        myDialog = new Dialog(login.getContext(), android.R.style.Theme_Light_NoTitleBar);
        myDialog.setContentView(R.layout.logindialog);
        em_inp = myDialog.findViewById(R.id.adminemail);
        pwd_inp = myDialog.findViewById(R.id.adminpwd);
        dialog_login = myDialog.findViewById(R.id.lgn_dialog_btn);
        return login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        lgn = view.findViewById(R.id.lgnadmin);
        progressDialog = new ProgressDialog(getContext());

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null){
            startActivity(new Intent(getActivity(), InAppActivity.class));
        }
        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                //startActivity(new Intent(getActivity(), InAppActivity.class));
            }
        });
    }
    public void showDialog(){
        myDialog.show();
        dialog_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = em_inp.getText().toString();
                String  pwd = pwd_inp.getText().toString();
                validate(email, pwd);
            }
        });
    }
    private void validate(String user_email, String user_pwd){
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Authenticating. Please Wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(user_email, user_pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), InAppActivity.class));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
