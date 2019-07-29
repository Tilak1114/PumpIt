package com.example.tilak.pumpit;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PlansActivity extends AppCompatActivity {
    Dialog addplanDialog;
    RelativeLayout addplanfab;
    ImageView cancelpopup;
    TextView plancount;
    RecyclerView planRv;

    Button addplanbtn;
    EditText planDur, planPrice, planDesc, planTitle;
    Context context;

    PlanAdapter adapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        addplanDialog = new Dialog(getApplicationContext(), android.R.style.Theme_Light_NoTitleBar);
        addplanDialog.setContentView(R.layout.addplan_popup);

        planDur = addplanDialog.findViewById(R.id.plnmonths);
        planDesc = addplanDialog.findViewById(R.id.planDesc);
        planPrice = addplanDialog.findViewById(R.id.planPrice);
        planTitle = addplanDialog.findViewById(R.id.plntitle);
        cancelpopup = addplanDialog.findViewById(R.id.cancelplanpopup);
        addplanbtn = addplanDialog.findViewById(R.id.addnewplan);
        addplanfab = findViewById(R.id.addplanfab);
        plancount = findViewById(R.id.mngplncnt);
        planRv = findViewById(R.id.newplanrv);

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_Plans", GymName);

        DocumentReference plncnt = FirebaseFirestore.getInstance()
                .document("Gyms/"+GymName+"/MetaData/plans/");
        plncnt.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String plncnt = documentSnapshot.getString("plancount");
                plancount.setText(plncnt+" Active Plans");
            }
        });
    }
}
