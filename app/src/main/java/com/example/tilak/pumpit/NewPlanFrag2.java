package com.example.tilak.pumpit;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class NewPlanFrag2 extends Fragment {
    RelativeLayout next;
    GridView gridView;
    String PlanTitle;
    Integer coverId = R.drawable.gridwlp6;
    CustomAdapter adapter;
    NewPlanFrag2.NextBtnListener nextBtnListener;
    private Integer[] mThumbIds = {
            R.drawable.gridwlp1, R.drawable.gridwlp2,
            R.drawable.gridwlp3, R.drawable.gridwlp4,
            R.drawable.gridwlp5, R.drawable.gridwlp6
    };

    public interface NextBtnListener{
        void onNewPlanBtnClicked2(Boolean result);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){
        View NewPlanView2 = inflater.inflate(R.layout.newplan_frag2, container, false);

        gridView = NewPlanView2.findViewById(R.id.gridView);
        next = NewPlanView2.findViewById(R.id.newPlanNext2);

        return NewPlanView2;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new CustomAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coverId = Integer.parseInt(adapter.getItem(position).toString());
                Toast.makeText(getContext(), coverId.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference newPlan = FirebaseFirestore.getInstance().document("Gyms/EvolveFitness"+
                        "/Plans/"+PlanTitle);
                Map<String, Object> data2 = new HashMap<String, Object>();
                data2.put("coverId", coverId); // later change to user input
                newPlan.set(data2, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nextBtnListener.onNewPlanBtnClicked2(true);
                    }
                });
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NextBtnListener){
            nextBtnListener = (NextBtnListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        nextBtnListener = null;
    }
    public void getPlanTitle(String title){
        PlanTitle = title;
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int i) {
            return mThumbIds[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.grid_cust_lay,null);
            //getting view in row_data
            ImageView image = view1.findViewById(R.id.gridImg);
            Picasso.with(getContext()).load(mThumbIds[i]).resize(220, 180).into(image);
            return view1;
        }
    }
}


