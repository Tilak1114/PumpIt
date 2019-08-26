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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<String> membNames;
    private ArrayList<String> membProfilepics;
    private ArrayList<String> membPlans;
    private ArrayList<String> membPP;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> membNames,
                               ArrayList<String> membProfilepics, ArrayList<String> membPlans,
                               ArrayList<String> membPP) {
        this.mContext = mContext;
        this.membNames = membNames;
        this.membProfilepics = membProfilepics;
        this.membPlans = membPlans;
        this.membPP = membPP;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_custom_layout,
                viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Glide.with(mContext).asBitmap().load(membProfilepics.get(i)).into(holder.membImg);
        holder.membName.setText(membNames.get(i));
        holder.planDetails.setText(membPlans.get(i));
        holder.paymentStatus.setText(membPP.get(i));

    }

    @Override
    public int getItemCount() {
        return membNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView membImg;
        TextView membName, planDetails, paymentStatus;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            membImg = itemView.findViewById(R.id.membProfilepic);
            membName = itemView.findViewById(R.id.MemberName);
            planDetails = itemView.findViewById(R.id.PlanDetails);
            paymentStatus = itemView.findViewById(R.id.PaymentStatus);
            parentLayout = itemView.findViewById(R.id.parent_lay);
        }
    }
}
