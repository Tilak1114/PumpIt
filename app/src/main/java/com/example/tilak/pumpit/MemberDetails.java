package com.example.tilak.pumpit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberDetails extends AppCompatActivity{
    TextView membname, plan, phone, payment, startdate, enddate, email, activeStatus;
    ImageView close, edit;
    String profileUrl;
    Switch activityswitch;
    ProgressDialog progressDialog;
    RelativeLayout editlay, editdef, savechanges, sendmsg;
    CircleImageView membprofile;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        membname = findViewById(R.id.MemberNameDetails);
        plan = findViewById(R.id.monthstv);
        phone = findViewById(R.id.phonevaldet);
        email = findViewById(R.id.emailvaldet);
        payment = findViewById(R.id.pddtv);
        activityswitch = findViewById(R.id.activitySwitch);
        sendmsg = findViewById(R.id.sendMessageIndividual);
        activeStatus = findViewById(R.id.activestatustv);

        editlay = findViewById(R.id.editlay);
        editdef = findViewById(R.id.topmembmenu);
        savechanges = findViewById(R.id.save_Changes);
        startdate = findViewById(R.id.startdateval);
        enddate = findViewById(R.id.enddateval);
        membprofile = findViewById(R.id.membProfilepicDetails);

        i= getIntent();
        Log.d("Intentchk", i.getStringExtra("name"));
        membname.setText(i.getStringExtra("name"));
        Log.d("Intentchk", i.getStringExtra("plan"));
        plan.setText(i.getStringExtra("plan"));
        Log.d("Intentchk", i.getStringExtra("phone"));
        phone.setText(i.getStringExtra("phone"));
        payment.setText(i.getStringExtra("payment"));
        email.setText(i.getStringExtra("email"));

        Picasso.with(getApplicationContext()).load(i.getStringExtra("profileurl"))
               .into(membprofile);

        profileUrl = i.getStringExtra("profileurl");

        startdate.setText(i.getStringExtra("start_date"));
        enddate.setText(i.getStringExtra("end_date"));

        close = findViewById(R.id.cancelmembprofile);
        membname = findViewById(R.id.MemberNameDetails);
        edit = findViewById(R.id.editmembprofile);

        progressDialog = new ProgressDialog(MemberDetails.this, R.style.ProgressDialogStyle);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("smsto:"+i.getStringExtra("phone")));
                smsIntent.putExtra("sms_body", "\n \n Regards, ");
                startActivity(smsIntent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editintent = new Intent(MemberDetails.this, MemberEdit.class);
                editintent.putExtra("name", membname.getText().toString());
                editintent.putExtra("phone", phone.getText().toString());
                editintent.putExtra("email", email.getText().toString());
                editintent.putExtra("profileurl", profileUrl);
                startActivityForResult(editintent, 69);
            }
        });
        if(payment.getText().toString().equals("Payment Pending")){
            activityswitch.setChecked(true);
            activityswitch.setVisibility(View.VISIBLE);
            activityswitch.setClickable(true);
        }
        activityswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    activeStatus.setText("Inactive");
                    activeStatus.setTypeface(Typeface.DEFAULT_BOLD);
                    payment.setTextColor(getResources().getColor(R.color.gray));
                    activeStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    savechanges.setVisibility(View.VISIBLE);
                    savechanges.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MemberDetails.this, R.style.AlertDialogStyle);
                            builder.setTitle("Update Changes");
                            builder.setMessage("You are about to make this member " +
                                    "Inactive. (Inactive members will be removed from overdue list but can later be renewed from the members section) CONTINUE?");
                            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // perform updation
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                        }
                    });
                }
                else if(isChecked){
                    savechanges.setVisibility(View.INVISIBLE);
                    activeStatus.setText("Active");
                    activeStatus.setTypeface(Typeface.DEFAULT_BOLD);
                    payment.setTextColor(getResources().getColor(R.color.typography_snow));
                    activeStatus.setTextColor(getResources().getColor(R.color.green));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 69&&data.getBooleanExtra("shouldFinish", true)){
            finish();
        }
    }
}
