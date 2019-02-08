package com.example.tilak.pumpit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.shuhart.stepview.StepView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewMemberActivity extends AppCompatActivity implements NewMembFrag1.NextBtnListener, NewMembFrag2.NextBtnListener{

    StepView stepView;
    FrameLayout newmembcont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member);
        stepView = findViewById(R.id.stepViewMemb);
        newmembcont = findViewById(R.id.newMemb_container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.newMemb_container, new NewMembFrag1()).commit();
    }

    @Override
    public void onNewMembBtnClicked1(Boolean result) {
        if(result){
            getSupportFragmentManager().beginTransaction().replace(R.id.newMemb_container,
                    new NewMembFrag2()).commit();
        }
    }

    @Override
    public void onNewMembBtnClicked2(Boolean result) {
        startActivity(new Intent(NewMemberActivity.this, InAppActivity.class));
    }
}
