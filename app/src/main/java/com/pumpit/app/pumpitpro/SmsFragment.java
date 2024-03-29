package com.pumpit.app.pumpitpro;

import android.content.Intent;
import android.net.Uri;
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
import java.util.Objects;

public class SmsFragment extends Fragment {
    RelativeLayout send;
    EditText msgcontent;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    String contacts;
    String msgContent;
    List<String> contactList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View smsView = inflater.inflate(R.layout.msg_compose_lay, container, false);
        send = smsView.findViewById(R.id.sendmsglay);
        msgcontent = smsView.findViewById(R.id.composemsget);

        contacts = "smsto:";
        GymName = user.getDisplayName();

        return smsView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        msgcontent.setText("Sample Message here \n"+"Regards, "+GymName);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msgcontent.getText().toString().isEmpty()){
                    msgcontent.setError("No Content");
                }
                else{
                    CollectionReference smsContact = FirebaseFirestore.getInstance()
                            .collection("Gyms/"+GymName+"/Members");
                    smsContact.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    contactList.add(Objects.requireNonNull(document.get("phoneNo")).toString());
                                }
                                for(int i=0; i<contactList.size(); i++){
                                    if(i==(contactList.size()-1)){
                                        contacts = contacts+contactList.get(i);
                                    }
                                    else
                                        contacts = contacts+contactList.get(i)+";";
                                }
                                Log.d("Contacts", contacts);
                                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(contacts));
                                smsIntent.putExtra("sms_body", msgcontent.getText().toString());
                                startActivity(smsIntent);
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
