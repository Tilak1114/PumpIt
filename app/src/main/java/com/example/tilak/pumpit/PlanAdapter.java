package com.example.tilak.pumpit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    public PlanAdapter(@NonNull FirestoreRecyclerOptions<Plan> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlanViewHolder holder, int position, @NonNull final Plan model) {

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_planadap", GymName);

        holder.planDuration.setText(model.getPlanDuration());
        holder.coverLay.setBackgroundResource(model.getCoverId());
        holder.planResMembCnt.setText(model.getPlanMembCount()+" Members");
        holder.coverLay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.delete.setVisibility(View.VISIBLE);
                holder.delete.setClickable(true);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseFirestore.getInstance().
                                collection("Gyms/"+GymName+"/Plans").document(model.planName).delete();
                    }
                });
                holder.coverLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.delete.setVisibility(View.INVISIBLE);
                        holder.delete.setClickable(false);
                    }
                });
                return true;
            }
        });
        holder.coverLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_plan_recyclerlayout, viewGroup, false);
        return new PlanViewHolder(v);
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView planDuration, planResMembCnt;
        RelativeLayout coverLay;
        RelativeLayout delete;
        public PlanViewHolder(View itemView) {
            super(itemView);
            planResMembCnt = itemView.findViewById(R.id.newPlanmembCnt);
            planDuration = itemView.findViewById(R.id.newPlanDur);
            coverLay = itemView.findViewById(R.id.newPlanlay);
            delete = itemView.findViewById(R.id.deleteIcon);
        }
    }
}
