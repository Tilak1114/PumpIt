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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupFrag extends Fragment {
    private static final int CHOOSE_IMAGE = 101;
    Button signupadmin, subSignUp;
    Dialog snpDialog;
    String profileImgUrl;
    EditText gymName, gymEmail, gymPwd, gymPh, gymAddr;
    ImageView cancelSnp;
    CircleImageView Avatar;
    Uri uriProfileImage;

    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signup = inflater.inflate(R.layout.signup_frag, container, false);
        signupadmin = signup.findViewById(R.id.newadmin);
        snpDialog = new Dialog(signup.getContext(), android.R.style.Theme_Light_NoTitleBar);
        snpDialog.setContentView(R.layout.signup_dialog);
        subSignUp = snpDialog.findViewById(R.id.subsnp);
        cancelSnp = snpDialog.findViewById(R.id.cancelSignUp);
        gymName = snpDialog.findViewById(R.id.snpName);
        gymEmail = snpDialog.findViewById(R.id.adminemail);
        gymPwd = snpDialog.findViewById(R.id.adminpwd);

        Avatar = snpDialog.findViewById(R.id.snpavatar);

        mAuth = FirebaseAuth.getInstance();

        return signup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        signupadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snpDialog.show();
            }
        });
        cancelSnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snpDialog.dismiss();
                Avatar.setImageResource(R.drawable.avatar);
            }
        });
        Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
        subSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String GymEmail = gymEmail.getText().toString();
                String GymPwd = gymPwd.getText().toString();
                mAuth.createUserWithEmailAndPassword(GymEmail, GymPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            uploadToFireBase();
                            saveUserInfo();
                            startActivity(new Intent(getActivity(), InAppActivity.class));
                        }
                    }
                });
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
                Avatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageChooser(){
        Intent imgSelect = new Intent();
        imgSelect.setType("image/*");
        imgSelect.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imgSelect, "Select Profile Image"), CHOOSE_IMAGE);
    }
    private void uploadToFireBase() {
        final StorageReference profilepicRef = FirebaseStorage.getInstance()
                .getReference("profilepic"+System.currentTimeMillis()+".jpg");
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
        final String GymName = gymName.getText().toString();
        //final String GymPh = gymPh.getText().toString();
        //final String GymAddr = gymAddr.getText().toString();

        if(GymName.isEmpty()){
            gymName.setError("Name Required");
            gymName.requestFocus();
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null && profileImgUrl != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(GymName).setPhotoUri(Uri.parse(profileImgUrl)).build();
            user.updateProfile(profileChangeRequest);
        }
    }
}
