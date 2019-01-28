package com.example.tilak.pumpit;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageMembersFragment extends Fragment {
    EditText firstName, lastName, phoneNo;
    Dialog addMembDialog;
    RelativeLayout addNewMembFab;
    private DocumentReference documentReference = FirebaseFirestore.getInstance().document("Gyms/MalakaFitness" +
            "/Members/Member1");
    Button addMembbtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View MemberManage = inflater.inflate(R.layout.fragment_manage_member, container, false);
        addMembDialog = new Dialog(MemberManage.getContext(), android.R.style.Theme_Light_NoTitleBar);
        addMembDialog.setContentView(R.layout.newmemb_popup);
        firstName = addMembDialog.findViewById(R.id.newMembFN);
        lastName = addMembDialog.findViewById(R.id.newMembLN);
        phoneNo = addMembDialog.findViewById(R.id.newMembPNO);
        addMembbtn = addMembDialog.findViewById(R.id.addnewmemb);
        addNewMembFab = MemberManage.findViewById(R.id.addMembfab);
        return MemberManage;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addNewMembFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMembDialog.show();
            }
        });
        addMembbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MemberFN, MemberLN, MemberPhno;
                MemberFN = firstName.getText().toString();
                MemberLN = lastName.getText().toString();
                MemberPhno = phoneNo.getText().toString();
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("firstName", MemberFN);
                data.put("lastName", MemberLN);
                data.put("phoneNo", MemberPhno);
                documentReference.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Member Added", Toast.LENGTH_LONG).show();
                            addMembDialog.dismiss();
                        }
                        else
                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
