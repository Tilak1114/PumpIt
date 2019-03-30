package com.example.tilak.pumpit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PlanSelAdapter extends FirestoreRecyclerAdapter<Plan, PlanSelAdapter.PlanViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private Context mContext;

    private int lastSelectedPosition = -1;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName, planSel, planName;

    public PlanSelAdapter(@NonNull FirestoreRecyclerOptions<Plan> options, Context context) {
        super(options);
        this.mContext = context;
    }

    public interface RvSelectedListener{
        void onItemSelected(int position);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlanViewHolder holder, int position, @NonNull final Plan model) {

        GymName = user.getDisplayName();

        Log.d("GymMetainfo_planseladap", GymName);

        holder.planDuration.setText(model.getPlanDuration());
        holder.planRate.setText("\u20B9"+model.getPlanPrice());
        holder.coverLay.setBackgroundResource(model.getCoverId());

        holder.selSwicth.setChecked(lastSelectedPosition == position);
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
        Switch selSwicth;

        public PlanViewHolder(View itemView) {
            super(itemView);
            planDuration = itemView.findViewById(R.id.newPlanDur);
            planRate = itemView.findViewById(R.id.planratesel);
            coverLay = itemView.findViewById(R.id.newPlanlaysel);
            selSwicth = itemView.findViewById(R.id.planselswitch);

            coverLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    planSel = planDuration.getText().toString();
                    Toast.makeText(mContext, "Selected " + planDuration.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            selSwicth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    planSel = planDuration.getText().toString();
                    Toast.makeText(mContext, "Selected " + planDuration.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String getPlanSel(){
        return planSel;
    }

}
