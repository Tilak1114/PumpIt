package com.example.tilak.pumpit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewPlanAdapter extends RecyclerView.Adapter<RecyclerViewPlanAdapter.ViewHolder>{

    ArrayList<String> planDurList = new ArrayList<>();
    ArrayList<Integer> planCover = new ArrayList<>();
    Context context;

    public RecyclerViewPlanAdapter(Context context, ArrayList<String> planDurList, ArrayList<Integer> planCover) {
        this.planDurList = planDurList;
        this.planCover = planCover;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.custom_plan_recyclerlayout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.planDur.setText(planDurList.get(i));
        viewHolder.parentLay.setBackgroundResource(planCover.get(i));
        viewHolder.parentLay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return planDurList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView planDur;
        RelativeLayout parentLay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            planDur = itemView.findViewById(R.id.newPlanDur);
            parentLay = itemView.findViewById(R.id.newPlanlay);
        }
    }
}
