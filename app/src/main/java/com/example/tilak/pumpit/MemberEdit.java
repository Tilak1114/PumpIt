package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class MemberEdit extends AppCompatActivity {
    ProgressDialog progressDialog;
    ImageView editdone, editcancel;

    Uri uriProfileImage;
    Boolean changed;
    String downloadUrl, profileImgUrl;

    CircleImageView avatar;
    EditText nameedit, phoneedit, emailedit, whatsappEdit;
    RelativeLayout delmemb;
    String membName, membNameWithoutSpace;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    Boolean phoneUpdated, emailUpdated;
    DocumentReference membUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_edit);
        Intent i = getIntent();
        changed = false;
        phoneUpdated = false;
        emailUpdated = false;
        final String name = i.getStringExtra("name");
        delmemb = findViewById(R.id.deletememb);
        avatar = findViewById(R.id.cirImgAvatarEdit);
        editdone = findViewById(R.id.done);
        editcancel = findViewById(R.id.canceledit);
        nameedit = findViewById(R.id.nameedit);
        phoneedit = findViewById(R.id.phoneedit);
        emailedit = findViewById(R.id.emailedit);
        whatsappEdit = findViewById(R.id.whatsappedit);

        nameedit.setText(i.getStringExtra("name"));
        phoneedit.setText(i.getStringExtra("phone"));
        emailedit.setText(i.getStringExtra("email"));
        Picasso.with(getApplicationContext()).load(i.getStringExtra("profileurl"))
                .into(avatar);


        membName = i.getStringExtra("name");
        membNameWithoutSpace = membName.replaceAll("\\s","");

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_MemberEdit", GymName);

        progressDialog = new ProgressDialog(MemberEdit.this, R.style.ProgressDialogStyle);

        membUpdate = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Members/"+membName);

        phoneedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changed = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        editdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MemberEdit.this);
                builder.setTitle("Update");
                builder.setMessage("Do you want to apply changes?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //deleteExisting(membNameWithoutSpace, membName);
                        uploadToFireBase(membName, membNameWithoutSpace);
                        membUpdate.update("phoneNo", phoneedit.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                phoneUpdated = true;
                            }
                        });
                        membUpdate.update("email", emailedit.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                emailUpdated = true;
                            }
                        });
                        if(!whatsappEdit.getText().toString().isEmpty()){
                            Map<String, Object> data = new HashMap<>();
                            data.put("whatsapp", whatsappEdit.getText().toString());
                            membUpdate.set(data);
                        }
                        if(phoneUpdated||emailUpdated){
                            Toasty.success(getApplicationContext(), "Updated Successfully", Toasty.LENGTH_LONG).show();
                        }
                        changed = false;
                        Intent intent=new Intent();
                        intent.putExtra("shouldFinish", true);
                        setResult(69,intent);
                        finish();//
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        editcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changed){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MemberEdit.this);
                    builder.setTitle("Exit");
                    builder.setMessage("Exit before applying changes?");
                    builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }
                else{
                    finish();
                }
            }
        });



        delmemb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MemberEdit.this);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete this member?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progressDialog.setTitle("Deleting Member");
                        progressDialog.show();
                        FirebaseFirestore.getInstance().collection("Gyms/"+GymName+"/Members").
                                document(name).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                final DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/MetaData/members");
                                dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String cnt = documentSnapshot.getString("allmembcount");
                                        Integer cnti = Integer.valueOf(cnt);
                                        cnti = cnti -1;
                                        dr.update("allmembcount", cnti.toString());

                                        setupInitData();
                                    }
                                });
                                progressDialog.dismiss();
                                setupPlansWithCount();
                                Intent intent=new Intent();
                                intent.putExtra("shouldFinish", true);
                                setResult(69,intent);
                                finish();//
                            }
                        });
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
    }
    public void setupPlansWithCount(){
        final ArrayList<String> planNameList = new ArrayList<>();
        final ArrayList<Integer> planCountList = new ArrayList<>();
        CollectionReference cr = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Plans");
        cr.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //Log.d("planArri", document.getId());
                    if(!planNameList.contains(document.getId())){
                        planNameList.add(document.getId());
                    }
                }
                CollectionReference membRef = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members");
                membRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d("planNamelst", String.valueOf(planNameList.size()));
                            for(int i = 0; i<planNameList.size(); i++){
                                planCountList.add(0);
                            }
                            Log.d("plancntsize", String.valueOf(planCountList.size()));
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for(int i = 0; i<planNameList.size(); i++){
                                    if(planNameList.get(i).equals(document.getString("planName"))){
                                        Integer cnt = planCountList.get(i);
                                        cnt = cnt+1;
                                        planCountList.set(i, cnt);
                                    }
                                }
                            }
                            for(int j = 0; j<planCountList.size();j++){
                                Log.d("plancntval", String.valueOf(planCountList.get(j)));
                            }
                        }
                    }
                });
            }
        });
        for(int i =0; i<planNameList.size(); i++){
            DocumentReference writeRef = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Plans/"+planNameList.get(i));
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("planMembCount", planCountList.get(i));
            writeRef.set(data);
        }
    }
    private void setupInitData() {
        final ArrayList<Integer> membMetaInfo = new ArrayList<>();
        membMetaInfo.add(0);
        membMetaInfo.add(0);
        CollectionReference fetchMemb = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members");
        fetchMemb.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //Log.d("planArri", document.getId());
                    if(document.get("payment").equals("Payment Pending")){
                        Integer count = membMetaInfo.get(0);
                        count++;
                        membMetaInfo.set(0, count);
                    }
                    else if(document.get("payment").equals("Fees Paid")){
                        Integer count1 = membMetaInfo.get(1);
                        count1++;
                        membMetaInfo.set(1, count1);
                    }
                }
                DocumentReference updateInitData = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/MetaData/members");
                updateInitData.update("activemembcount", String.valueOf(membMetaInfo.get(1)));
                updateInitData.update("overduemembcount", String.valueOf(membMetaInfo.get(0)));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 1: if(resultCode==RESULT_OK && data!=null && data.getData()!=null){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                avatar.setImageBitmap(imageBitmap);
                changed = true;
            }

                break;
            case 2: if(data!=null && data.getData()!=null){
                uriProfileImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                            uriProfileImage);
                    avatar.setImageBitmap(bitmap);
                    changed = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showImageChooser(){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MemberEdit.this, R.style.AlertDialogStyle);
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

    public void uploadToFireBase(final String membName, String membNameWithoutSpace){
        final StorageReference profilepicRef = FirebaseStorage.getInstance()
                .getReference("MemberUploads/"+GymName+membNameWithoutSpace+".jpg");
        if(uriProfileImage != null){
            progressDialog.setTitle("Uploading Profile Picture");
            progressDialog.setCancelable(false);
            progressDialog.show();
            profilepicRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImgUrl = taskSnapshot.toString();
                    progressDialog.dismiss();
                    final DocumentReference membRef = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Members/"+membName);
                    profilepicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                            Map<String, Object> data = new HashMap<String, Object>();
                            data.put("profileUrl", downloadUrl);
                            membRef.set(data, SetOptions.merge());
                        }
                    });
                }
            });
        }
    }

    public void deleteExisting(final String membNameWithoutSpace, final String membName){
        final StorageReference profilepicRef = FirebaseStorage.getInstance()
                .getReference("MemberUploads/"+GymName+membNameWithoutSpace+".jpg");
        profilepicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                profilepicRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        uploadToFireBase(membName, membNameWithoutSpace);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FNF", e.toString());
                uploadToFireBase(membName, membNameWithoutSpace);
            }
        });
    }
}
