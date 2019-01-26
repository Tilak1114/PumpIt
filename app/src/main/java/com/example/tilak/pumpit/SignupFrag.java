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
import android.widget.ImageView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupFrag extends Fragment {
    private static final int CHOOSE_IMAGE = 101;
    Button signupadmin;
    Dialog snpDialog;
    ImageView cancelSnp;
    CircleImageView Avatar;
    Uri uriProfileImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signup = inflater.inflate(R.layout.signup_frag, container, false);
        signupadmin = signup.findViewById(R.id.newadmin);
        snpDialog = new Dialog(signup.getContext(), android.R.style.Theme_Light_NoTitleBar);
        snpDialog.setContentView(R.layout.signup_dialog);
        cancelSnp = snpDialog.findViewById(R.id.cancelSignUp);
        Avatar = snpDialog.findViewById(R.id.snpavatar);
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
}
