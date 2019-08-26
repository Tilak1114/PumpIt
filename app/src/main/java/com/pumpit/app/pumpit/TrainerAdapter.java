package com.pumpit.app.pumpit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrainerAdapter extends FirestoreRecyclerAdapter<Trainer, TrainerAdapter.TrainerHolder> {
    protected Context context;
    private Integer count;
    private TrainerAdapter.ItemclickListener itemclickListener;

    public TrainerAdapter(@NonNull FirestoreRecyclerOptions options, Context context,
                         TrainerAdapter.ItemclickListener itemclickListener) {
        super(options);

        count = options.getSnapshots().toArray().length;
        this.context = context;
        this.itemclickListener = itemclickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull TrainerHolder holder, int position, @NonNull final Trainer model) {
        String tempTxt = model.getFirstName()+" "+model.getLastName();
        holder.TrainerName.setText(tempTxt);
        holder.numbTrainees.setText(model.getTrainees());
        holder.parentlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListener.onItemClick( model.getFirstName()+" "+model.getLastName(), model.getProfileUrl(), model.getPhoneNo(), model.getEmail(),
                        model.getTrainees(), model.getTrainerSpecs(), model.getSalaryStatus(), model.getSalary());
            }
        });
        if(model.getProfileUrl()!=null){
            if(model.getProfileUrl().equals(String.valueOf(R.drawable.avatar))){
                Picasso.with(context).load(R.drawable.avatar).into(holder.trainerProfilePic);
            }
            else{
                Picasso.with(context).load(model.getProfileUrl()).into(holder.trainerProfilePic);
            }
        }
        else{
            Picasso.with(context).load(model.getProfileUrl())
                    .networkPolicy(NetworkPolicy.OFFLINE).into(holder.trainerProfilePic);
        }
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListener.callItem(model.getPhoneNo());
            }
        });
    }

    @NonNull
    @Override
    public TrainerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trainer_rv_lay,
                viewGroup,false);
        return new TrainerAdapter.TrainerHolder(view);
    }

    public interface ItemclickListener{
        void onItemClick(String name, String profileurl, String phone, String email, String trainees,
                         String trainerSpecs, String salaryStatus, Integer salary);
        void callItem(String phone);
    }

    class TrainerHolder extends RecyclerView.ViewHolder{
        TextView TrainerName, numbTrainees;
        RelativeLayout parentlay;
        CircleImageView trainerProfilePic;
        ImageView call;
        public TrainerHolder(@NonNull View itemView) {
            super(itemView);
            TrainerName = itemView.findViewById(R.id.TrainerName);
            numbTrainees = itemView.findViewById(R.id.NumberOftrainees);
            parentlay = itemView.findViewById(R.id.parent_lay_trainer);
            trainerProfilePic = itemView.findViewById(R.id.trainerProfilepic);
            call = itemView.findViewById(R.id.callTrainer);
            parentlay.setClickable(true);
        }
    }
}

