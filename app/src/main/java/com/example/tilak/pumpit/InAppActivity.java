package com.example.tilak.pumpit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InAppActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    Fragment homeFrag = new HomeFragment();
    Fragment mngFrag = new ManageFragment();
    Fragment storeFrag = new StoreFragment();
    Fragment profileFrag = new ProfileFragment();
    Fragment active = homeFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if(user==null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        GymName = user.getDisplayName();

        Log.d("GymMetainfo", GymName);

        if(GymName.isEmpty()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frag_container, mngFrag).hide(mngFrag).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frag_container, storeFrag).hide(storeFrag).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frag_container, profileFrag).hide(profileFrag).commit();


        setupPlansWithCount();

        getSupportFragmentManager().beginTransaction().add(R.id.frag_container, homeFrag).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment segmentSelected = null;
                switch(menuItem.getItemId()){
                    case R.id.action_home:
                        getSupportFragmentManager().beginTransaction().hide(active).show(homeFrag).addToBackStack(null).commit();
                        active = homeFrag;
                        break;
                    case R.id.action_manage:
                        getSupportFragmentManager().beginTransaction().hide(active).show(mngFrag).addToBackStack(null).commit();
                        active = mngFrag;
                        break;
                    case R.id.action_store:
                        getSupportFragmentManager().beginTransaction().hide(active).show(storeFrag).addToBackStack(null).commit();
                        active = storeFrag;
                        break;
                    case R.id.action_profile:
                        getSupportFragmentManager().beginTransaction().hide(active).show(profileFrag).addToBackStack(null).commit();
                        active = profileFrag;
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(InAppActivity.this, R.style.AlertDialogStyle);
            builder.setTitle("Exit");
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                    moveTaskToBack(true);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }
        else
            super.onBackPressed();
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
            DocumentReference writeRef = FirebaseFirestore.getInstance().document("Gyms/"+GymName+
                    "/Plans/"+planNameList.get(i));
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("planMembCount", planCountList.get(i));
            writeRef.set(data);
        }
    }
}
