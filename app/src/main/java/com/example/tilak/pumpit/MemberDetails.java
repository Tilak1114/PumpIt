package com.example.tilak.pumpit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberDetails extends AppCompatActivity {
    TextView membname, plan, phone, payment;
    ImageView close, edit;
    ProgressDialog progressDialog;
    RelativeLayout delmemb;
    CircleImageView membprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        membname = findViewById(R.id.MemberNameDetails);
        plan = findViewById(R.id.monthstv);
        phone = findViewById(R.id.phonevaldet);
        payment = findViewById(R.id.pddtv);
        membprofile = findViewById(R.id.membProfilepicDetails);
        Intent i= getIntent();
        Log.d("Intentchk", i.getStringExtra("name"));
        membname.setText(i.getStringExtra("name"));
        Log.d("Intentchk", i.getStringExtra("plan"));
        plan.setText(i.getStringExtra("plan"));
        Log.d("Intentchk", i.getStringExtra("phone"));
        phone.setText(i.getStringExtra("phone"));
        payment.setText(i.getStringExtra("payment"));
        Picasso.with(getApplicationContext()).load(i.getStringExtra("profileurl"))
               .into(membprofile);

        close = findViewById(R.id.cancelmembprofile);
        membname = findViewById(R.id.MemberNameDetails);
        edit = findViewById(R.id.editmembprofile);
        delmemb = findViewById(R.id.deletememb);
        progressDialog = new ProgressDialog(MemberDetails.this, R.style.ProgressDialogStyle);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delmemb.setVisibility(View.VISIBLE);
                delmemb.setClickable(true);
            }
        });

        delmemb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MemberDetails.this, R.style.AlertDialogStyle);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete this member?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progressDialog.setTitle("Deleting Member");
                        progressDialog.show();
                        FirebaseFirestore.getInstance().collection("Gyms/EvolveFitness/Members").
                                document(membname.getText().toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
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
