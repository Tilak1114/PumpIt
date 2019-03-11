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
        holder.planRate.setText("\u20B9"+model.getPlanPrice());
        holder.coverLay.setBackgroundResource(model.getCoverId());
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_plansel_recyclerlayout, viewGroup, false);
        return new PlanViewHolder(v);
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView planDuration, planRate;
        RelativeLayout coverLay;
        public PlanViewHolder(View itemView) {
            super(itemView);
            planDuration = itemView.findViewById(R.id.newPlanDur);
            planRate = itemView.findViewById(R.id.planratesel);
            coverLay = itemView.findViewById(R.id.newPlanlaysel);
        }
    }
}
