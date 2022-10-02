package com.pumpit.app.pumpitpro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class PlanAdapter extends FirestoreRecyclerAdapter<Plan, PlanAdapter.PlanViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String GymName;
    Context context;


    PlanAdapter(@NonNull FirestoreRecyclerOptions<Plan> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlanViewHolder holder, int position, @NonNull final Plan model) {
        GymName = user.getDisplayName();

        holder.planDuration.setText(model.getPlanDuration());
        Drawable drawable = context.getDrawable(model.getCoverId());
        holder.coverImg.setImageDrawable(drawable);
        FirebaseFirestore.getInstance().collection("/Gyms/"+GymName+"/Members")
                .whereEqualTo("planName", model.getPlanName()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                    holder.planMembCnt.setText(task.getResult().size()+" Members");
                else
                    holder.planMembCnt.setText("0 Members");
            }
        });
        holder.coverLay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.delete.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.delete.setClickable(true);
                holder.edit.setClickable(true);

                holder.coverLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.delete.setVisibility(View.INVISIBLE);
                        holder.delete.setClickable(false);
                        holder.edit.setVisibility(View.INVISIBLE);
                        holder.edit.setClickable(false);
                    }
                });

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Integer.parseInt(holder.planMembCnt.getText().toString().substring(0, 1))>0){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
                            builder1.setTitle("Deletion Not Allowed");
                            builder1.setMessage("Cannot delete this plan as it is in use");
                            builder1.setCancelable(true);
                            builder1.setNeutralButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    }).create().show();
                        }
                        else{
                            final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
                            builder.setTitle("Confirm Deletion").setMessage("Are you sure you want to delete this plan?");
                            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseFirestore.getInstance().
                                            collection("Gyms/"+GymName+"/Plans").document(model.planName).delete();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                        }
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
        TextView planDuration, planMembCnt;
        RelativeLayout coverLay;
        ImageView coverImg;
        RelativeLayout delete, edit;
        PlanViewHolder(View itemView) {
            super(itemView);
            planMembCnt = itemView.findViewById(R.id.newPlanmembCnt);
            planDuration = itemView.findViewById(R.id.newPlanDur);
            coverLay = itemView.findViewById(R.id.newPlanlay);
            delete = itemView.findViewById(R.id.deleteIconlay);
            edit = itemView.findViewById(R.id.editIconlay);
            coverImg = itemView.findViewById(R.id.coverImgId);

            coverLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
