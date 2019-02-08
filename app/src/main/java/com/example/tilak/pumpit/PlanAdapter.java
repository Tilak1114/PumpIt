package com.example.tilak.pumpit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PlanAdapter extends FirestoreRecyclerAdapter<Plan, PlanAdapter.PlanViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PlanAdapter(@NonNull FirestoreRecyclerOptions<Plan> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PlanViewHolder holder, int position, @NonNull Plan model) {

        holder.planDuration.setText(model.getPlanDuration());
        holder.coverLay.setBackgroundResource(model.getCoverId());
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_plan_recyclerlayout, viewGroup, false);
        return new PlanViewHolder(v);
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView planDuration;
        RelativeLayout coverLay;
        public PlanViewHolder(View itemView) {
            super(itemView);
            planDuration = itemView.findViewById(R.id.newPlanDur);
            coverLay = itemView.findViewById(R.id.newPlanlay);
        }
    }
}
