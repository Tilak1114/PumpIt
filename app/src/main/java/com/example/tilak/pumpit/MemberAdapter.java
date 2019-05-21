package com.example.tilak.pumpit;

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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberAdapter extends FirestoreRecyclerAdapter<Member, MemberAdapter.MemberHolder> {
    protected Context context;
    private Integer count;
    ArrayList<String> planNameList = new ArrayList<>();
    ArrayList<String> planCountList = new ArrayList<>();
    private ItemclickListener itemclickListener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MemberAdapter(@NonNull FirestoreRecyclerOptions options, Context context,
                         ItemclickListener itemclickListener) {
        super(options);

        count = options.getSnapshots().toArray().length;
        this.context = context;
        this.itemclickListener = itemclickListener;
    }
    public interface ItemclickListener{
        void onItemClick(String name, String plan, String payment,
                                String profileurl, String phone, String start_date, String end_date, String email);
        void callItem(String phone);
    }

    @Override
    protected void onBindViewHolder(@NonNull MemberHolder holder, int position, @NonNull final Member model) {

        /*Integer planCnt = 0;
        if(plnList.size()!=0){
            for(int i=0; i<plnList.size(); i++){
                if(model.getPlanName().equals(plnList.get(i))){
                    if(planCountList.get(i)==null){
                        planCountList.set(i, String.valueOf(1));
                    }
                    else if(planCountList.get(i)!=null){
                        planCnt = Integer.valueOf(planCountList.get(i));
                        planCnt = planCnt+1;
                        planCountList.set(i, String.valueOf(planCnt));
                    }
                }
            }
            for(int j =0; j<planCountList.size();j++){
                Log.d("countchk", planCountList.get(j));
            }

        }*/
        String tempTxt = model.getFirstName()+" "+model.getLastName();
        holder.membName.setText(tempTxt);
        holder.membPlan.setText(model.getMembPlan());
        if(model.getPayment().equals("Fees Paid")){
            holder.membPaymentStatus.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }
        else if(model.getPayment().equals("Payment Pending"))
        {
            holder.membPaymentStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }
        holder.parentlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListener.onItemClick( model.getFirstName()+" "+model.getLastName(),
                        model.getMembPlan(), model.getPayment(), model.getProfileUrl(),
                        model.getPhoneNo(), model.getStart_date(), model.getEnd_date(), model.getEmail());
            }
        });
        holder.membPaymentStatus.setText(model.getPayment());
        if(model.getProfileUrl()!=null){
            if(model.getProfileUrl().equals(String.valueOf(R.drawable.avatar))){
                Picasso.with(context).load(R.drawable.avatar).into(holder.membProfilePic);
            }
            else{
                Picasso.with(context).load(model.getProfileUrl()).into(holder.membProfilePic);
            }
        }
        else{
            Picasso.with(context).load(model.getProfileUrl())
                    .networkPolicy(NetworkPolicy.OFFLINE).into(holder.membProfilePic);
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
    public MemberHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_custom_layout,
                viewGroup,false);
        return new MemberHolder(view);
    }

    class MemberHolder extends RecyclerView.ViewHolder{
        TextView membName, membPlan, membPaymentStatus;
        RelativeLayout parentlay;
        CircleImageView membProfilePic;
        ImageView call;
        public MemberHolder(@NonNull View itemView) {
            super(itemView);
            membName = itemView.findViewById(R.id.MemberName);
            membPlan = itemView.findViewById(R.id.PlanDetails);
            parentlay = itemView.findViewById(R.id.parent_lay);
            membPaymentStatus = itemView.findViewById(R.id.PaymentStatus);
            membProfilePic = itemView.findViewById(R.id.membProfilepic);
            call = itemView.findViewById(R.id.callmemb);
            parentlay.setClickable(true);
        }
    }
    public int getMembCount(){
        return count;
    }
}
