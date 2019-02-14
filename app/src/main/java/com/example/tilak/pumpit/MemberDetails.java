package com.example.tilak.pumpit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberDetails extends AppCompatActivity {
    TextView membname, plan, phone, payment;
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
    }
}
