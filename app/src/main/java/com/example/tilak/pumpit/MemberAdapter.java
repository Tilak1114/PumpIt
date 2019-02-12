package com.example.tilak.pumpit;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberAdapter extends FirestoreRecyclerAdapter<Member, MemberAdapter.MemberHolder> {

    protected Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MemberAdapter(@NonNull FirestoreRecyclerOptions options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MemberHolder holder, int position, @NonNull Member model) {
        String Fullname = model.getfName()+" "+model.getlName();
        holder.membName.setText(Fullname);
        holder.membPlan.setText(model.getMembPlan());
        if(model.getPayment().equals("Fees Paid")){
            holder.membPaymentStatus.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }
        else if(model.getPayment().equals("Payment Pending"))
        {
            holder.membPaymentStatus.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }
        holder.membPaymentStatus.setText(model.getPayment());
        if(model.getProfileUrl()!=null){
            Picasso.with(context).load(model.getProfileUrl()).into(holder.membProfilePic);
        }
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
        CircleImageView membProfilePic;
        public MemberHolder(@NonNull View itemView) {
            super(itemView);
            membName = itemView.findViewById(R.id.MemberName);
            membPlan = itemView.findViewById(R.id.PlanDetails);
            membPaymentStatus = itemView.findViewById(R.id.PaymentStatus);
            membProfilePic = itemView.findViewById(R.id.membProfilepic);
        }
    }
}
