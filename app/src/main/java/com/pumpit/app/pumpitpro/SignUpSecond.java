package com.pumpit.app.pumpitpro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpSecond extends Fragment {

    private static final int CHOOSE_IMAGE = 101;
    ProgressDialog progressDialog;
    String profileImgUrl;
    Uri uriProfileImage;
    NextBtnListener nextBtnListener;
    RelativeLayout next;
    CircleImageView Avatar;
    EditText gymName, gymPhone;

    GymBasicInfo info;

    public interface NextBtnListener{
        void onBtnClick(Boolean result, String Gymname);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){

        progressDialog = new ProgressDialog(getContext());

        View SnpSecV = inflater.inflate(R.layout.signup_second, container, false);
        next = SnpSecV.findViewById(R.id.Next2);
        Avatar = SnpSecV.findViewById(R.id.snpavatar);
        gymName = (EditText) SnpSecV.findViewById(R.id.snpName);
        gymPhone = (EditText) SnpSecV.findViewById(R.id.snpPhone);
        return SnpSecV;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gymPhone.getText().toString().isEmpty()&&!gymPhone.getText().toString().isEmpty()){
                    String GymName = gymName.getText().toString();
                    String GymPhone = gymPhone.getText().toString();
                    info = new GymBasicInfo(GymName, GymPhone);
                    uploadToFireBase();
                    nextBtnListener.onBtnClick(true, GymName);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SignUpSecond.NextBtnListener){
            nextBtnListener = (SignUpSecond.NextBtnListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        nextBtnListener = null;
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
                .getReference("profilepic" + System.currentTimeMillis() + ".jpg");
        if (uriProfileImage != null) {
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Uploading Profile Picture");
            progressDialog.show();
            profilepicRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    profileImgUrl = taskSnapshot.toString();
                    Log.d("Url", profileImgUrl);
                }
            });
        }
    }
}
