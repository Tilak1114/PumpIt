package com.pumpit.app.pumpitpro;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GymFaciAdapter extends BaseExpandableListAdapter {

    Context context;
    List<String> selItems;
    HashMap<String, List<String>> GymFaciAll;
    List<String> GymFaci;
    boolean state =false;


    public GymFaciAdapter(Context context,
                          List<String> GymFaci, HashMap<String, List<String>> GymFaciAll){
        this.context = context;
        this.GymFaciAll = GymFaciAll;
        this.GymFaci = GymFaci;

        selItems = new ArrayList<>();
    }
    @Override
    public int getGroupCount() {
        return GymFaci.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return GymFaciAll.get(GymFaci.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return GymFaci.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return GymFaciAll.get(GymFaci.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group_title = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_parent, parent, false);
        }
        TextView parentTv = convertView.findViewById(R.id.parent_txt);
        parentTv.setTypeface(null, Typeface.BOLD);
        parentTv.setText(group_title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        final String child_title = (String) getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_child, parent, false);
        }
        CheckBox childCb = convertView.findViewById(R.id.checkbox_child);
        childCb.setChecked(false);
        childCb.setText(child_title);
        childCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    selItems.add(child_title);
                    state = true;
                }
                else
                    if(state){
                        selItems.remove(child_title);
                    }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public List<String> getSelItems() {
        return selItems;
    }
}
