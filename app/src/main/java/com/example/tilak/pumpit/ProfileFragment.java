package com.example.tilak.pumpit;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    Button logout;
    CardView cashflow, leads;
    TextView alltrans;
    TextView profilename;
    CircleImageView profilepic;
    ImageView settings;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View profileV = inflater.inflate(R.layout.fragment_profile, container, false);
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse("https://images.pexels.com/photos/1554824/pexels-photo-1554824.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"))
                .build();

        user.updateProfile(profileUpdates);
        cashflow = profileV.findViewById(R.id.cashflowprofilelay);
        alltrans = profileV.findViewById(R.id.alltransactionsclick);
        leads = profileV.findViewById(R.id.leadsprofilelay);
        profilename = profileV.findViewById(R.id.profilenametv);
        settings = profileV.findViewById(R.id.settingsprofile);
        profilepic = profileV.findViewById(R.id.adminprofilepic);
        return profileV;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        profilename.setText(user.getDisplayName());
        Picasso.with(getContext()).load("https://images.pexels.com/photos/1554824/pexels-photo-1554824.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260").into(profilepic);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        cashflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Finances_Activity.class));

            }
        });

        alltrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllTransactionsActivity.class));
            }
        });
//        cashflow.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(cashflow,
//                                "scaleX", 0.96f);
//                        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(cashflow,
//                                "scaleY", 0.96f);
//                        scaleDownX.setDuration(100);
//                        scaleDownY.setDuration(100);
//
//                        AnimatorSet scaleDown = new AnimatorSet();
//                        scaleDown.play(scaleDownX).with(scaleDownY);
//
//                        scaleDown.start();
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(
//                                cashflow, "scaleX", 1f);
//                        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(
//                                cashflow, "scaleY", 1f);
//                        scaleDownX2.setDuration(100);
//                        scaleDownY2.setDuration(100);
//
//                        AnimatorSet scaleDown2 = new AnimatorSet();
//                        scaleDown2.play(scaleDownX2).with(scaleDownY2);
//
//                        scaleDown2.start();
//                        break;
////                }
//            }
//            return true;
//            }
//        });


        leads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LeadsActivity.class));
            }
        });
    }
}
