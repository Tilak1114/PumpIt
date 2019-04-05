package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText em_inp, pwd_inp;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    RelativeLayout login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(LoginActivity.this);
        em_inp = findViewById(R.id.adminemail);
        login = findViewById(R.id.lgn_btn);
        pwd_inp = findViewById(R.id.adminpwd);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(em_inp.getText().toString().isEmpty()){
                    em_inp.setError("Email Required");
                }
                if(pwd_inp.getText().toString().isEmpty()){
                   pwd_inp.setError("Password Required");
                }
                String email = em_inp.getText().toString();
                String  pwd = pwd_inp.getText().toString();
                if(!pwd.isEmpty()&&!email.isEmpty()){
                    validate(email, pwd);
                }
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
                    finish();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), InAppActivity.class));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
