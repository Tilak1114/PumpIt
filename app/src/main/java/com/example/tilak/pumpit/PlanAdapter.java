package com.example.tilak.pumpit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


    public PlanAdapter(@NonNull FirestoreRecyclerOptions<Plan> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlanViewHolder holder, int position, @NonNull final Plan model) {
        GymName = user.getDisplayName();

        holder.planDuration.setText(model.getPlanDuration());
        holder.coverLay.setBackgroundResource(model.getCoverId());
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
                                    final DocumentReference plncntref = FirebaseFirestore.getInstance()
                                            .document("Gyms/"+GymName+"/MetaData/plans");
                                    plncntref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String plncnt = documentSnapshot.getString("plancount");
                                            int plncntint = Integer.parseInt(plncnt);
                                            plncntint = plncntint-1;
                                            Map<String, Object> data = new HashMap<String, Object>();
                                            data.put("plancount", String.valueOf(plncntint));
                                            plncntref.set(data, SetOptions.merge());
                                        }
                                    });
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
        TextView planDuration, planMembCnt, planDurTop;
        RelativeLayout coverLay;
        RelativeLayout delete, edit;
        public PlanViewHolder(View itemView) {
            super(itemView);
            planMembCnt = itemView.findViewById(R.id.newPlanmembCnt);
            planDuration = itemView.findViewById(R.id.newPlanDur);
            coverLay = itemView.findViewById(R.id.newPlanlay);
            delete = itemView.findViewById(R.id.deleteIconlay);
            edit = itemView.findViewById(R.id.editIconlay);


            coverLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
