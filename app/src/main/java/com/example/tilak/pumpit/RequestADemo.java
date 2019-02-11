package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class RequestADemo extends AppCompatActivity {
    RelativeLayout reqdemo;
    EditText name, gymname, phno, ead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ademo);
        reqdemo = findViewById(R.id.reqgo);
        name = findViewById(R.id.adminname);
        gymname = findViewById(R.id.gymname);
        phno = findViewById(R.id.admincontact);
        ead = findViewById(R.id.adminemailreq);

        reqdemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty()&&!gymname.getText().toString().isEmpty()
                &&!phno.getText().toString().isEmpty()&&!ead.getText().toString().isEmpty()){
                    sendReq();
                }
            }
        });
    }

    private void sendReq() {
        String[] recipients = {"tilaksharma1114@gmail.com"};
        String subject = "New Request";
        String message = "Hi. I would like to try Pumpit. I request you to give me a demo. Here are my details: \n";
        message = message+"Name:"+name.getText().toString()+"\n"+"Phone Number:"+phno.getText().toString()
                +"\n"+"Email:"+ead.getText().toString()+"\n"+"Gym Name:"+gymname.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
        finish();
    }
}
