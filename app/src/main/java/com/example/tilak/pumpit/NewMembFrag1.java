package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewMembFrag1 extends Fragment {

    private static final int CHOOSE_IMAGE = 101;

    FirebaseAuth mAuth;

    Uri uriProfileImage;
    String downloadUrl;

    RelativeLayout next;
    ProgressDialog progressDialog;
    String profileImgUrl;
    NextBtnListener nextBtnListener;
    EditText firstName, lastName, phoneNo;
    CircleImageView avatar;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    public interface NextBtnListener{
        void onNewMembBtnClicked1(Boolean result, String membName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View NewMembView1 = inflater.inflate(R.layout.newmemb_frag1, container, false);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext(), R.style.ProgressDialogStyle);
        firstName = NewMembView1.findViewById(R.id.newMembFN);
        avatar = NewMembView1.findViewById(R.id.newMembAvatar);
        lastName = NewMembView1.findViewById(R.id.newMembLN);
        phoneNo = NewMembView1.findViewById(R.id.newMembPNO);

        next = NewMembView1.findViewById(R.id.newMembNext);
        next.setClickable(false);

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_nmf1", GymName);

        return NewMembView1;
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
        if(context instanceof NextBtnListener){
            nextBtnListener = (NextBtnListener) context;
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
                avatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void uploadToFireBase() {

        final String memberName = firstName.getText().toString()+lastName.getText().toString();
        final StorageReference profilepicRef = FirebaseStorage.getInstance()
                .getReference("MemberUploads/"+GymName+memberName+".jpg");
        if(uriProfileImage != null){
            progressDialog.setTitle("Uploading Profile Picture");
            progressDialog.show();
            profilepicRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImgUrl = taskSnapshot.toString();
                    progressDialog.dismiss();
                    final DocumentReference documentReference = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Members/"+firstName.getText().toString()+" "+lastName.getText().toString());
                    profilepicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                            Map<String, Object> data = new HashMap<String, Object>();
                            data.put("profileUrl", downloadUrl);
                            documentReference.set(data, SetOptions.merge());
                            nextBtnListener.onNewMembBtnClicked1(true, firstName.getText().toString()+" "+lastName.getText().toString());
                        }
                    });
                }
            });
        }
        else if(uriProfileImage==null){
            DocumentReference dr = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Members/"+firstName.getText().toString()+" "+lastName.getText().toString());
            Map<String, Object> data = new HashMap<String, Object>();
            Integer avatarid = R.drawable.avatar;
            data.put("profileUrl", avatarid.toString());
            dr.set(data, SetOptions.merge());
            nextBtnListener.onNewMembBtnClicked1(true, firstName.getText().toString()+" "+lastName.getText().toString());
        }
    }
    private void saveUserInfo() {

        progressDialog.setTitle("Uploading To Database");
        progressDialog.setMessage("Adding Member Data");
        String MemberFN, MemberLN, MemberPhno;
        MemberFN = firstName.getText().toString();
        MemberLN = lastName.getText().toString();
        MemberPhno = phoneNo.getText().toString();

        if(MemberFN.isEmpty()){
            firstName.setError("First Name Required");
        }
        if(MemberLN.isEmpty()){
            lastName.setError("Last Name Required");
        }
        if(MemberPhno.isEmpty()){
            phoneNo.setError("Phone Number Required");
        }


        if(!MemberFN.isEmpty()&&!MemberLN.isEmpty()&&!MemberPhno.isEmpty()){
            next.setClickable(true);
            progressDialog.show();

            DocumentReference documentReference = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Members/"+MemberFN+" "+MemberLN);

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("firstName", MemberFN);
            data.put("lastName", MemberLN);
            data.put("membPlan", "");
            data.put("payment", "");
            data.put("planName", "");
            data.put("phoneNo", MemberPhno);
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
        Intent imgSelect = new Intent();
        imgSelect.setType("image/*");
        imgSelect.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imgSelect, "Select Profile Image"), CHOOSE_IMAGE);
    }
}
