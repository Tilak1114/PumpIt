package com.example.tilak.pumpit;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

public class InAppActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment segmentSelected = null;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app);

        //startAlarm(true, true);
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

        checkNoti();

        //startAlarm(true, true);

        setupInitData();

        setupPlansWithCount();

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new HomeFragment()).addToBackStack(null).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.action_home:
                        segmentSelected = new HomeFragment();
                        break;
                    case R.id.action_manage:
                        segmentSelected = new ManageFragment();
                        break;
                    case R.id.action_store:
                        segmentSelected = new StoreFragment();
                        break;
                    case R.id.action_profile:
                        segmentSelected = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, segmentSelected, "simp_frag_tag")
                        .addToBackStack(null).commit();
                return true;
            }
        });
    }

    private void setupInitData() {
        final ArrayList<Integer> membMetaInfo = new ArrayList<>();
        membMetaInfo.add(0);
        membMetaInfo.add(0);
        CollectionReference fetchMemb = FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members");
        fetchMemb.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //Log.d("planArri", document.getId());
                    if(document.get("payment").equals("Payment Pending")){
                        Integer count = membMetaInfo.get(0);
                        count++;
                        membMetaInfo.set(0, count);
                    }
                    else if(document.get("payment").equals("Fees Paid")){
                        Integer count1 = membMetaInfo.get(1);
                        count1++;
                        membMetaInfo.set(1, count1);
                    }
                }
                DocumentReference updateInitData = FirebaseFirestore.getInstance().document("Gyms/"+GymName+"/MetaData/members");
                updateInitData.update("activemembcount", String.valueOf(membMetaInfo.get(1)));
                updateInitData.update("overduemembcount", String.valueOf(membMetaInfo.get(0)));
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

    public void checkNoti(){
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);

        Intent myIntent = new Intent(getApplicationContext(), InAppActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                myIntent,
                FLAG_ONE_SHOT );

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Manage Gym")
                .setSmallIcon(R.drawable.notificationicon)
                .setColor(getResources().getColor(R.color.gtstrtbck))
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.iconsplash))
                .setContentIntent(pendingIntent)
                .setContentText("Send intimation message to members approaching due date and overdue members")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Action");

        notificationManager.notify(notificationId, builder.build());
    }
}
