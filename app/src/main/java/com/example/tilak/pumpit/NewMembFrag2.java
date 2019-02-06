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

public class NewMembFrag2 extends Fragment {
    RelativeLayout next;
    ProgressDialog progressDialog;
    NextBtnListener nextBtnListener;
    EditText email, pwd, cpwd;
    FirebaseAuth mAuth;

    public interface NextBtnListener{
        void onNewMembBtnClicked2(Boolean result);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View NewMembView1 = inflater.inflate(R.layout.newmemb_frag2, container, false);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());

        next = NewMembView1.findViewById(R.id.newMembNext);

        return NewMembView1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
