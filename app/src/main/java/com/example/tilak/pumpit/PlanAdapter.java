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
    Context context;

    private int lastSelectedPosition = -1;

    public PlanAdapter(@NonNull FirestoreRecyclerOptions<Plan> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlanViewHolder holder, int position, @NonNull final Plan model) {
        GymName = user.getDisplayName();

        Log.d("GymMetainfo_planadap", GymName);

        holder.planDuration.setText(model.getPlanDuration());
        holder.planDurTop.setText(model.getPlanDuration());
        holder.coverLay.setBackgroundResource(model.getCoverId());
        holder.planResMembCnt.setText(model.getPlanMembCount()+" Members");
        holder.coverLay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.delete.setVisibility(View.VISIBLE);
                holder.edit.setVisibility(View.VISIBLE);
                holder.delete.setClickable(true);
                holder.edit.setClickable(true);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                });
                return true;
            }
        });
        if(lastSelectedPosition != position){
           /* holder.delete.setVisibility(View.INVISIBLE);
            holder.delete.setClickable(false);
            holder.edit.setVisibility(View.INVISIBLE);
            holder.edit.setClickable(false);
            Animation aniFadeIn = AnimationUtils.loadAnimation(context,R.anim.fade_in);
            Animation aniFadeOut = AnimationUtils.loadAnimation(context,R.anim.fade_out);
            holder.blacktemp.startAnimation(aniFadeIn);
            holder.blacktemp.setVisibility(View.VISIBLE);
            holder.planResMembCnt.startAnimation(aniFadeIn);
            holder.planResMembCnt.setVisibility(View.VISIBLE);
            holder.planDuration.startAnimation(aniFadeIn);
            holder.planDuration.setVisibility(View.VISIBLE);
            holder.planDurTop.setAnimation(aniFadeOut);
            holder.planDurTop.setVisibility(View.INVISIBLE);*/
        }
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_plan_recyclerlayout, viewGroup, false);
        return new PlanViewHolder(v);
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView planDuration, planResMembCnt, planDurTop;
        RelativeLayout coverLay, blacktemp;
        RelativeLayout delete, edit;
        public PlanViewHolder(View itemView) {
            super(itemView);
            planResMembCnt = itemView.findViewById(R.id.newPlanmembCnt);
            planDuration = itemView.findViewById(R.id.newPlanDur);
            coverLay = itemView.findViewById(R.id.newPlanlay);
            delete = itemView.findViewById(R.id.deleteIconlay);
            edit = itemView.findViewById(R.id.editIconlay);
            planDurTop = itemView.findViewById(R.id.newPlanDurtop);
            blacktemp = itemView.findViewById(R.id.blacktemplate);

            coverLay.setOnClickListener(new View.OnClickListener() {
                Integer clickRegister = 1;
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    if(clickRegister.equals(1)){
                        edit.setVisibility(View.INVISIBLE);
                        edit.setClickable(false);
                        delete.setVisibility(View.INVISIBLE);
                        delete.setClickable(false);
                        Animation aniFadeOut = AnimationUtils.loadAnimation(context,R.anim.fade_out);
                        Animation aniFadeIn = AnimationUtils.loadAnimation(context,R.anim.fade_in);
                        blacktemp.startAnimation(aniFadeOut);
                        blacktemp.setVisibility(View.INVISIBLE);
                        planResMembCnt.startAnimation(aniFadeOut);
                        planResMembCnt.setVisibility(View.INVISIBLE);
                        planDuration.startAnimation(aniFadeOut);
                        planDuration.setVisibility(View.INVISIBLE);
                        planDurTop.setAnimation(aniFadeIn);
                        planDurTop.setVisibility(View.VISIBLE);
                        clickRegister = 2;
                    }
                    else if(clickRegister.equals(2))
                    {
                        delete.setVisibility(View.INVISIBLE);
                        delete.setClickable(false);
                        edit.setVisibility(View.INVISIBLE);
                        edit.setClickable(false);
                        Animation aniFadeIn = AnimationUtils.loadAnimation(context,R.anim.fade_in);
                        Animation aniFadeOut = AnimationUtils.loadAnimation(context,R.anim.fade_out);
                        blacktemp.startAnimation(aniFadeIn);
                        blacktemp.setVisibility(View.VISIBLE);
                        planResMembCnt.startAnimation(aniFadeIn);
                        planResMembCnt.setVisibility(View.VISIBLE);
                        planDuration.startAnimation(aniFadeIn);
                        planDuration.setVisibility(View.VISIBLE);
                        planDurTop.setAnimation(aniFadeOut);
                        planDurTop.setVisibility(View.INVISIBLE);
                        clickRegister = 1;
                    }
                }
            });
        }
    }
}
