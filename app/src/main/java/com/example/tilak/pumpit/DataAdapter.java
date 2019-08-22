package com.example.tilak.pumpit;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private ArrayList<String> mBlogUploadDateAuthList = new ArrayList<>();
    Context context;
    private int lastPosition = -1;

    public DataAdapter(Context context, ArrayList<String> mBlogTitleList, ArrayList<String> mBlogUploadDateAuthList) {
        this.context = context;
        this.mBlogTitleList = mBlogTitleList;
        this.mBlogUploadDateAuthList = mBlogUploadDateAuthList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_blog_title, tv_blog_upload_date;

        public MyViewHolder(View view) {
            super(view);
            tv_blog_title = (TextView) view.findViewById(R.id.feedtitletv);
            tv_blog_upload_date = (TextView) view.findViewById(R.id.feeddate);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_rv_lay, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_blog_title.setText(mBlogTitleList.get(position));
        holder.tv_blog_upload_date.setText(mBlogUploadDateAuthList.get(position));
    }

    @Override
    public int getItemCount() {
        return mBlogTitleList.size();
    }
}