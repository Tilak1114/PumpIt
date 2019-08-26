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

public class LeadsAdapter extends FirestoreRecyclerAdapter<Lead, LeadsAdapter.LeadHolder> {
    protected Context context;
    Integer count;

    ItemclickListener itemclickListener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LeadsAdapter(@NonNull FirestoreRecyclerOptions options, Context context,
                        ItemclickListener itemclickListener) {
        super(options);

        count = options.getSnapshots().toArray().length;
        this.context = context;
        this.itemclickListener = itemclickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull LeadHolder holder, int position, @NonNull final Lead model) {
        holder.leadName.setText(model.getName());
        holder.expected_date.setText(model.getDate());
        holder.parentlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListener.onItemClick( model.getName(),
                        model.getGender(), model.getEmail(), model.getPhoneno(),
                        model.getDate(), model.getEnquiry());
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListener.callItem(model.getPhoneno());
            }
        });
    }

    public interface ItemclickListener{
        public void onItemClick(String name, String gender, String email,
                                String phone, String date, String enquiry);
        public void callItem(String phone);
    }

    @NonNull
    @Override
    public LeadHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_leads_custom,
                viewGroup,false);
        return new LeadHolder(view);
    }


    class LeadHolder extends RecyclerView.ViewHolder{
        TextView leadName, expected_date;
        RelativeLayout parentlay;
        ImageView call;
        public LeadHolder(@NonNull View itemView) {
            super(itemView);
            leadName = itemView.findViewById(R.id.LeadName);
            expected_date = itemView.findViewById(R.id.expected_date_lead);
            parentlay = itemView.findViewById(R.id.parent_lay_lead);
            call = itemView.findViewById(R.id.calllead);
            parentlay.setClickable(true);
        }
    }
}
