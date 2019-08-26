package com.pumpit.app.pumpit;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewLeadActivity extends AppCompatActivity {

    Spinner gspinner;
    TextView dispdate;
    EditText fullname, emailet, phoneet, enquiry;
    RelativeLayout datelay;
    RelativeLayout create;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName, newLeadName, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_lead_activity);
        gspinner = findViewById(R.id.genderspinner);
        dispdate = findViewById(R.id.selectdatetv);
        datelay = findViewById(R.id.newleaddate);
        create = findViewById(R.id.newLeadCreate);
        fullname = findViewById(R.id.newleadfullname);
        emailet = findViewById(R.id.newleademailet);
        phoneet = findViewById(R.id.newleadphnoet);
        enquiry = findViewById(R.id.newleadenqet);

        GymName = user.getDisplayName();

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Male");
        spinnerArray.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gspinner.setAdapter(adapter);

        datelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(NewLeadActivity.this,
                        android.R.style.Theme_Material_Dialog, mDateSetListener, year, month, date);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" + month + "/" + year;
                dispdate.setText(date);
            }
        };
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullname.getText().toString().isEmpty()){
                    fullname.setError("Field Required");
                }
                if(phoneet.getText().toString().isEmpty()){
                    phoneet.setError("Field Required");
                }
                if(emailet.getText().toString().isEmpty()){
                    emailet.setError("Field Required");
                }

                if(!fullname.getText().toString().isEmpty()&&!phoneet.getText().toString().isEmpty()
                        &&!emailet.getText().toString().isEmpty()){

                    Log.d("lead", GymName+ gspinner.getSelectedItem().toString()+
                            emailet.getText().toString()+phoneet.getText().toString()+dispdate.getText().toString()
                            +enquiry.getText().toString()
                    );

                    newLeadName = fullname.getText().toString();

                    DocumentReference leadsRef = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/Leads/"+newLeadName);

                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("name", fullname.getText().toString());
                    data.put("gender", gspinner.getSelectedItem().toString());
                    data.put("email", emailet.getText().toString());
                    data.put("phoneno", phoneet.getText().toString());
                    data.put("date", dispdate.getText().toString());
                    data.put("enquiry", enquiry.getText().toString());

                    leadsRef.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finish();
                        }
                    });
                }
            }
        });

    }
}
