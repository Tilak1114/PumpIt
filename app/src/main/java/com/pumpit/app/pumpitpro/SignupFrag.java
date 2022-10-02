package com.pumpit.app.pumpitpro;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

public class SignupFrag extends Fragment {
    private static final String TAG = "test ";
    Button signupadmin, subSignUp;
    String profileImgUrl;
    DocumentReference documentReference;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signup = inflater.inflate(R.layout.signup_frag, container, false);
        signupadmin = signup.findViewById(R.id.newadmin);

        mAuth = FirebaseAuth.getInstance();

        return signup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        signupadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignupActivity.class));
            }
        });

    }
}
