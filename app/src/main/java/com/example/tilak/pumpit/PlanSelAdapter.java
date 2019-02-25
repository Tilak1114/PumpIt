package com.example.tilak.pumpit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PlanSelAdapter extends FirestoreRecyclerAdapter<Plan, PlanSelAdapter.PlanViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;

    public PlanSelAdapter(@NonNull FirestoreRecyclerOptions<Plan> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlanViewHolder holder, int position, @NonNull final Plan model) {

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_planseladap", GymName);

        holder.planDuration.setText(model.getPlanDuration());
        holder.coverLay.setBackgroundResource(model.getCoverId());

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
        RelativeLayout delete;
        public PlanViewHolder(View itemView) {
            super(itemView);
            planDuration = itemView.findViewById(R.id.newPlanDur);
            coverLay = itemView.findViewById(R.id.newPlanlay);
            delete = itemView.findViewById(R.id.deleteIcon);
        }
    }
}
