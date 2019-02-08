package com.example.tilak.pumpit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MemberAdapter extends FirestoreRecyclerAdapter<Member, MemberAdapter.MemberHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MemberAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MemberHolder holder, int position, @NonNull Member model) {
        String Fullname = model.getfName()+" "+model.getlName();
        holder.membName.setText(Fullname);
        holder.membPlan.setText(model.getMembPlan());
        holder.membPaymentStatus.setText(model.getPayment());
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
        public MemberHolder(@NonNull View itemView) {
            super(itemView);
            membName = itemView.findViewById(R.id.MemberName);
            membPlan = itemView.findViewById(R.id.PlanDetails);
            membPaymentStatus = itemView.findViewById(R.id.PaymentStatus);
        }
    }
}
