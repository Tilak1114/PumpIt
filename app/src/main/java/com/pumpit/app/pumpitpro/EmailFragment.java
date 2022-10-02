package com.pumpit.app.pumpitpro;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmailFragment extends Fragment {
    RelativeLayout send;
    EditText mailcontent, subject;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    List<String> contactList = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View emailView = inflater.inflate(R.layout.mail_composelay, container, false);
        send = emailView.findViewById(R.id.sendmaillay);
        mailcontent = emailView.findViewById(R.id.composemailet);
        subject = emailView.findViewById(R.id.emailsubject);

        GymName = user.getDisplayName();
        return emailView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mailcontent.setText("Sample mail here \n"+"Regards, "+GymName);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mailcontent.getText().toString().isEmpty()){
                    mailcontent.setError("No Content");
                }
                else{
                    CollectionReference smsContact = FirebaseFirestore.getInstance()
                            .collection("Gyms/"+GymName+"/Members");
                    smsContact.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    contactList.add(document.get("email").toString());
                                }
                                Log.d("emailcheckcontacts", contactList.toString());
                                String[] recipients = new String[contactList.size()];
                                for(int i=0; i<contactList.size(); i++){
                                    recipients[i] = String.valueOf(contactList.get(i));
                                }
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                                intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                                intent.putExtra(Intent.EXTRA_TEXT, mailcontent.getText().toString());
                                intent.setType("message/rfc822");
                                startActivity(Intent.createChooser(intent, "Choose an email client"));
                            }
                            else
                                Log.d("Contacts", "fail");
                        }
                    });
                    //Log.d("contactsize", String.valueOf(contactList.size()));
                }
            }
        });
    }
}
