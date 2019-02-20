package com.example.tilak.pumpit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberEdit extends AppCompatActivity {
    ProgressDialog progressDialog;
    ImageView editdone, editcancel;
    RelativeLayout delmemb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_edit);
        Intent i = getIntent();
        final String name = i.getStringExtra("name");
        delmemb = findViewById(R.id.deletememb);
        editdone = findViewById(R.id.done);
        editcancel = findViewById(R.id.canceledit);

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
                        FirebaseFirestore.getInstance().collection("Gyms/EvolveFitness/Members").
                                document(name).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                final DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/EvolveFitness/MetaData/members");
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
}
