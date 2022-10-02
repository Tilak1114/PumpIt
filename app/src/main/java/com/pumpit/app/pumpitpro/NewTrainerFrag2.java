package com.pumpit.app.pumpitpro;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewTrainerFrag2 extends Fragment {
    ListView trainerLv;
    List<String> fightList, grpList, cardioList, strengthList, othersList, listfinal;
    RelativeLayout finishBtn;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    TrainerSpecAdapter adapter;
    TrainerFinishClick trainerFinishClick;
    ArrayList<Spec> specList, temp;

    public interface TrainerFinishClick{
        public void onFinishClick(List selList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View trainerFrag2 = inflater.inflate(R.layout.newtrainer_frag2, container, false);
        trainerLv = trainerFrag2.findViewById(R.id.trainerLV);
        fightList = new ArrayList<String>();
        grpList = new ArrayList<String>();
        finishBtn = trainerFrag2.findViewById(R.id.newTrainerFinal);
        cardioList = new ArrayList<String>();
        strengthList = new ArrayList<String>();
        othersList = new ArrayList<String>();
        listfinal = new ArrayList<String>();
        specList = new ArrayList<>();

        finishBtn.setClickable(false);

        GymName = user.getDisplayName();
        displaySpecList();

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainerFinishClick.onFinishClick(adapter.getSelList());
            }
        });

        return trainerFrag2;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NewTrainerFrag1.NextBtnListener){
            trainerFinishClick = (NewTrainerFrag2.TrainerFinishClick) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        trainerFinishClick = null;
    }

    private void displaySpecList() {
        temp = new ArrayList<Spec>();
        DocumentReference faciRef = FirebaseFirestore.getInstance().document("/Gyms/"+GymName+"/GymFacilities/facilities");
        faciRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String fighting = document.getString("Fighting");
                        //Log.d("Fighting", fighting);
                        if(fighting!=null&&!fighting.isEmpty()){
                            fightList = Arrays.asList(fighting.split(", "));
                        }
                        String grp = document.getString("Group");
                        if(grp!=null&&!grp.isEmpty()){
                            grpList = Arrays.asList(grp.split(", "));
                        }
                        String cardio = document.getString("cardio");
                        if(cardio!=null&&!cardio.isEmpty()){
                            cardioList = Arrays.asList(cardio.split(", "));
                        }
                        String strength = document.getString("strength");
                        if(strength!=null&&!strength.isEmpty()){
                            strengthList = Arrays.asList(strength.split(", "));
                        }
                        String others = document.getString("Others");
                        if(others!=null&&!others.isEmpty()){
                            othersList = Arrays.asList(others.split(", "));
                        }
                        if(!fightList.isEmpty()){
                            listfinal.addAll(fightList);
                        }
                        if(!othersList.isEmpty()){
                            listfinal.addAll(othersList);
                        }
                        if(!strengthList.isEmpty()){
                            listfinal.addAll(strengthList);
                        }
                        if(!cardioList.isEmpty()){
                            listfinal.addAll(cardioList);
                        }
                        if(!grpList.isEmpty()){
                            listfinal.addAll(grpList);
                        }

                        for(int i=0; i<listfinal.size(); i++){
                            specList.add(new Spec(listfinal.get(i), false));
                        }
                        adapter = new TrainerSpecAdapter(specList, getContext());

                        trainerLv.setAdapter(adapter);

                        trainerLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView parent, View view, int position, long id) {
                                Spec spec = specList.get(position);
                                spec.selected = !spec.selected;
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

    }
}
