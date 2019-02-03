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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupFrag extends Fragment {
    private static final int CHOOSE_IMAGE = 101;
    private static final String TAG = "test ";
    Button signupadmin, subSignUp;
    String profileImgUrl;
    EditText gymName, gymEmail, gymPwd, gymPh, gymAddr;
    CircleImageView Avatar;
    Uri uriProfileImage;
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

        /*Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
        subSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("OnClickTest", "entering onclick");
                String GymEmail = gymEmail.getText().toString();
                String GymPwd = gymPwd.getText().toString();

                mAuth.createUserWithEmailAndPassword(GymEmail, GymPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("OnClickTest", "signup test");
                            Toast.makeText(getContext(), "Account created", Toast.LENGTH_LONG).show();
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
        return;
    }
    private void saveUserInfo() {
        final String GymName = gymName.getText().toString();
        String GymPhone = gymPh.getText().toString();
        String GymAddr = gymAddr.getText().toString();

        if(GymName.isEmpty()){
            gymName.setError("Name Required");
            gymName.requestFocus();
            return;
        }
        if(GymPhone.isEmpty()){
            gymName.setError("Phone Number Required");
            gymName.requestFocus();
            return;
        }
        if(GymAddr.isEmpty()){
            gymName.setError("Address Required");
            gymName.requestFocus();
            return;
        }
        documentReference = FirebaseFirestore.getInstance().document("Gyms/"+
                GymName+"/profile/myProfile");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("gym name", GymName);
        data.put("Phone Number", GymPhone);
        data.put("address", GymAddr);
        documentReference.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Profile data added", Toast.LENGTH_LONG).show();
                }
                else
                   Toast.makeText(getContext(), "Failed to add data", Toast.LENGTH_LONG).show();
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null && profileImgUrl != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(GymName).setPhotoUri(Uri.parse(profileImgUrl)).build();
            user.updateProfile(profileChangeRequest);
        }*/

    }
}
