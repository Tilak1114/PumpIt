package com.example.tilak.pumpit;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shuhart.stepview.StepView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManageMembersFragment extends Fragment implements MemberAdapter.ItemclickListener {
    private static final String TAG = "FIREBASE";

    RelativeLayout addNewMembFab;
    RecyclerView recyclerView;
    SearchView searchView;
    TextView membcount;

    ArrayList<String> planNameList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MemberAdapter adapter;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    CollectionReference memberref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View MemberManage = inflater.inflate(R.layout.fragment_manage_member, container, false);

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_MMF", GymName);

       memberref = db.collection("Gyms/"+GymName+"/Members");

        //FragmentManager fm =
        //getFragmentManager().beginTransaction()
              // .replace(R.id.newMemb_container, new NewMembFrag1()).commit();

        addNewMembFab = MemberManage.findViewById(R.id.addMembfab);
        searchView = MemberManage.findViewById(R.id.searchmemb);
        membcount = MemberManage.findViewById(R.id.mngmmbcnt);
        recyclerView = MemberManage.findViewById(R.id.membRecyclerview);

        return MemberManage;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/MetaData/members");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String cnt = documentSnapshot.getString("allmembcount");
                membcount.setText(cnt+" Members");
            }
        });

        setUpRecyclerView();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                if (dy > 0 && addNewMembFab.getVisibility() == View.VISIBLE) {
                    addNewMembFab.setVisibility(View.INVISIBLE);
                } else if (dy < 0 && addNewMembFab.getVisibility() != View.VISIBLE) {
                    addNewMembFab.setVisibility(View.VISIBLE);
                }
            }
        });
        
        addNewMembFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewMemberActivity.class));
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void filter(String query) {
        Query searchQ = memberref.whereEqualTo("firstName", query);
        FirestoreRecyclerOptions<Member> options = new FirestoreRecyclerOptions.Builder<Member>().setQuery(searchQ, Member.class).build();
        adapter = new MemberAdapter(options, getContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setUpRecyclerView() {
        Query query = memberref;
        FirestoreRecyclerOptions<Member> options = new FirestoreRecyclerOptions.Builder<Member>().setQuery(query, Member.class).build();
        adapter = new MemberAdapter(options, getContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        DocumentReference dr = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/MetaData/members");
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String cnt = documentSnapshot.getString("allmembcount");
                membcount.setText(cnt+" Members");
            }
        });
    }

    @Override
    public void onItemClick(String name, String plan, String payment, String profileurl, String phone) {
        Intent i = new Intent(getActivity(), MemberDetails.class);
        i.putExtra("name", name);
        i.putExtra("plan", plan);
        i.putExtra("payment", payment);
        i.putExtra("profileurl", profileurl);
        i.putExtra("phone", phone);
        startActivity(i);
    }

    @Override
    public void callItem(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        Toast.makeText(getContext(), phone, Toast.LENGTH_LONG).show();
        callIntent.setData(Uri.parse("tel:"+phone));

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }
}
