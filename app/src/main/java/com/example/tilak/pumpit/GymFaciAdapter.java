package com.example.tilak.pumpit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class GymFaciAdapter extends BaseExpandableListAdapter {

    Context context;
    HashMap<String, List<String>> GymFaciAll;
    List<String> GymFaci;

    public GymFaciAdapter(Context context, HashMap<String, List<String>> GymFaciAll,
                          List<String> GymFaci){
        this.context = context;
        this.GymFaciAll = GymFaciAll;
        this.GymFaci = GymFaci;

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
        String child_title = (String) getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exp_child, parent, false);
        }
        CheckBox childCb = convertView.findViewById(R.id.checkbox_child);
        childCb.setText(child_title);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
