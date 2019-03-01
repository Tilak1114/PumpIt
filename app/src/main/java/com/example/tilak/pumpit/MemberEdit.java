package com.example.tilak.pumpit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemberEdit extends AppCompatActivity {
    ProgressDialog progressDialog;
    ImageView editdone, editcancel;
    RelativeLayout delmemb;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_edit);
        Intent i = getIntent();
        final String name = i.getStringExtra("name");
        delmemb = findViewById(R.id.deletememb);
        editdone = findViewById(R.id.done);
        editcancel = findViewById(R.id.canceledit);

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_MemberEdit", GymName);

        progressDialog = new ProgressDialog(MemberEdit.this, R.style.ProgressDialogStyle);

        editcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delmemb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MemberEdit.this, R.style.AlertDialogStyle);
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
                                    }
                                });
                                progressDialog.dismiss();
                                setupPlansWithCount();
                                finish();
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
}