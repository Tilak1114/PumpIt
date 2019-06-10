package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewTrainerFrag1 extends Fragment {

    Uri uriProfileImage;
    String downloadUrl;
    FirebaseAuth mAuth;

    RelativeLayout next;
    ProgressDialog progressDialog;
    String profileImgUrl;
    NextBtnListener nextBtnListener;
    EditText firstName, lastName, phoneNo;
    CircleImageView avatar;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    public interface NextBtnListener{
        void onNewTrainerBtnClicked1(Boolean result, String trainerName);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View trainerFrag1V= inflater.inflate(R.layout.newtrainer_frag1, container, false);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext(), R.style.ProgressDialogStyle);
        firstName = trainerFrag1V.findViewById(R.id.newTrainerFN);
        avatar = trainerFrag1V.findViewById(R.id.newTrainerAvatar);
        lastName = trainerFrag1V.findViewById(R.id.newTrainerLN);
        phoneNo = trainerFrag1V.findViewById(R.id.newTrainerPNO);

        next = trainerFrag1V.findViewById(R.id.newTrainerNext);
        next.setClickable(false);

        GymName = user.getDisplayName();
        return trainerFrag1V;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NewTrainerFrag1.NextBtnListener){
            nextBtnListener = (NewTrainerFrag1.NextBtnListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        nextBtnListener = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 1: if(resultCode==getActivity().RESULT_OK && data!=null && data.getData()!=null){
                Bundle extras = data.getExtras();
                assert extras != null;
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                avatar.setImageBitmap(imageBitmap);
            }

                break;
            case 2: if(data!=null && data.getData()!=null){
                uriProfileImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(),
                            uriProfileImage);
                    avatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void uploadToFireBase() {
        final String trainerName = firstName.getText().toString()+lastName.getText().toString();
        final StorageReference profilepicRef = FirebaseStorage.getInstance()
                .getReference("TrainerUploads/"+GymName+trainerName+".jpg");
        if(uriProfileImage != null){
            progressDialog.setTitle("Uploading Profile Picture");
            progressDialog.setCancelable(false);
            progressDialog.show();
            profilepicRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImgUrl = taskSnapshot.toString();
                    progressDialog.dismiss();
                    final DocumentReference documentReference = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Trainers/"+firstName.getText().toString()+" "+lastName.getText().toString());
                    profilepicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                            Map<String, Object> data = new HashMap<String, Object>();
                            data.put("profileUrl", downloadUrl);
                            documentReference.set(data, SetOptions.merge());
                            nextBtnListener.onNewTrainerBtnClicked1(true, firstName.getText().toString()+" "+lastName.getText().toString());
                        }
                    });
                }
            });
        }
        else if(uriProfileImage==null){
            DocumentReference dr = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Trainers/"+firstName.getText().toString()+" "+lastName.getText().toString());
            Map<String, Object> data = new HashMap<String, Object>();
            int avatarid = R.drawable.avatar;
            data.put("profileUrl", Integer.toString(avatarid));
            dr.set(data, SetOptions.merge());
            nextBtnListener.onNewTrainerBtnClicked1(true, firstName.getText().toString()+" "+lastName.getText().toString());
        }
    }
    private void saveUserInfo() {

        progressDialog.setTitle("Uploading To Database");
        progressDialog.setMessage("Adding Trainer Data");
        progressDialog.setCancelable(false);
        String TrainerFN, TrainerLN, TrainerPhno;
        TrainerFN = firstName.getText().toString();
        TrainerLN = lastName.getText().toString();
        TrainerPhno = phoneNo.getText().toString();

        if(TrainerFN.isEmpty()){
            firstName.setError("First Name Required");
        }
        if(TrainerLN.isEmpty()){
            lastName.setError("Last Name Required");
        }
        if(TrainerPhno.isEmpty()){
            phoneNo.setError("Phone Number Required");
        }


        if(!TrainerFN.isEmpty()&&!TrainerLN.isEmpty()&&!TrainerPhno.isEmpty()){
            next.setClickable(true);
            progressDialog.show();

            DocumentReference documentReference = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Trainers/"+TrainerFN+" "+TrainerLN);

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("firstName", TrainerFN);
            data.put("fullname_lc", TrainerFN.toLowerCase()+" "+TrainerLN.toLowerCase());
            data.put("lastName", TrainerLN);
            data.put("phoneNo", TrainerPhno);
            data.put("email", "");
            data.put("trainerSpecs", "");
            data.put("trainees", "0 Trainees");
            data.put("salary", 25000);
            data.put("salaryStatus", "Paid");
            documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    uploadToFireBase();
                }
            });
        }
    }
    private void showImageChooser(){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()), R.style.AlertDialogStyle);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent imgSelect = new Intent();
                    imgSelect.setType("image/*");
                    imgSelect.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(imgSelect, "Select Profile Image"), 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
