package com.pumpit.app.pumpit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.List;

public class PlanCoverAdapter extends RecyclerView.Adapter<PlanCoverAdapter.MyViewHolder> {

    private List<PlanCover> data;
    private Context context;

    private Long coverId;

    private int lastSelectedPosition = -1;

    public PlanCoverAdapter(Context context, List<PlanCover> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rv_plan_cover, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.imageView.setImageResource(data.get(i).getThumbNail());
        myViewHolder.imageView.setTag(data.get(i).getThumbNail());
        myViewHolder.radioButton.setChecked(lastSelectedPosition == i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        RadioButton radioButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gridImg);
            radioButton = itemView.findViewById(R.id.gridImgRadio);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    coverId = Long.parseLong(String.valueOf(imageView.getTag()));
                }
            });
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    coverId = Long.parseLong(String.valueOf(imageView.getTag()));
                }
            });
        }
    }
    public Long getCoverId() {
        return coverId;
    }
}
