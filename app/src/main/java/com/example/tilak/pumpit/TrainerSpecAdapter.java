package com.example.tilak.pumpit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TrainerSpecAdapter extends ArrayAdapter<Spec> {
    private ArrayList specSet;
    Context mContext;
    private List selList;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        CheckBox checkBox;
    }

    public TrainerSpecAdapter(ArrayList data, Context context) {
        super(context, R.layout.trainerspeclv, data);
        this.specSet = data;
        this.mContext = context;
        selList = new ArrayList();
    }
    @Override
    public int getCount() {
        return specSet.size();
    }

    @Override
    public Spec getItem(int position) {
        return (Spec) specSet.get(position);
    }


    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainerspeclv, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.specTv);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.specCb);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        final Spec item = getItem(position);

        assert item != null;
        viewHolder.txtName.setText(item.Name);
        viewHolder.checkBox.setChecked(item.selected);

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selList.add(item.Name);
                }
                else
                    selList.remove(item.Name);
            }
        });

        return result;
    }
    public List getSelList() {
        return selList;
    }
}

