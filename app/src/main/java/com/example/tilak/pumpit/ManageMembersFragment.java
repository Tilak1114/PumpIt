package com.example.tilak.pumpit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageMembersFragment extends Fragment {
    FirebaseAuth mAuth;
    private static final int CHOOSE_IMAGE = 101;
    Uri uriProfileImage;
    EditText firstName, lastName, phoneNo;
    Dialog addMembDialog;
    RelativeLayout addNewMembFab;
    String profileImgUrl;
    RecyclerView recyclerView;
    CircleImageView avatar, cancelNewMember;
    Button addMembbtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View MemberManage = inflater.inflate(R.layout.fragment_manage_member, container, false);
        mAuth = FirebaseAuth.getInstance();
        addMembDialog = new Dialog(MemberManage.getContext(), android.R.style.Theme_Light_NoTitleBar);
        addMembDialog.setContentView(R.layout.newmemb_popup);
        firstName = addMembDialog.findViewById(R.id.newMembFN);
        avatar = addMembDialog.findViewById(R.id.newMembAvatar);
        lastName = addMembDialog.findViewById(R.id.newMembLN);
        phoneNo = addMembDialog.findViewById(R.id.newMembPNO);
        addMembbtn = addMembDialog.findViewById(R.id.addnewmemb);
        cancelNewMember = addMembDialog.findViewById(R.id.cancelpopup);
        recyclerView= MemberManage.findViewById(R.id.membRecyclerview);
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
                saveUserInfo();
                uploadToFireBase();
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
        cancelNewMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMembDialog.dismiss();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHOOSE_IMAGE && data!=null && data.getData()!=null){
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
                        uriProfileImage);
                avatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadToFireBase() {
        String memberName = firstName.getText().toString()+lastName.getText().toString();
        final StorageReference profilepicRef = FirebaseStorage.getInstance()
                .getReference(memberName+System.currentTimeMillis()+".jpg");
        if(uriProfileImage != null){
            profilepicRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImgUrl = taskSnapshot.toString();
                }
            });
        }
    }
    private void saveUserInfo() {
        String MemberFN, MemberLN, MemberPhno;
        MemberFN = firstName.getText().toString();
        MemberLN = lastName.getText().toString();
        MemberPhno = phoneNo.getText().toString();
        DocumentReference documentReference = FirebaseFirestore.getInstance().document("Gyms/MalakaFitness" +
                "/Members/"+MemberFN);
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
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null && profileImgUrl != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(profileImgUrl)).build();
            user.updateProfile(profileChangeRequest);
        }
    }
    private void showImageChooser(){
        Intent imgSelect = new Intent();
        imgSelect.setType("image/*");
        imgSelect.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imgSelect, "Select Profile Image"), CHOOSE_IMAGE);
    }
}
