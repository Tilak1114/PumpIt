package com.pumpit.app.pumpitpro;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FeedFragment extends Fragment {
    private String url = "https://www.acefitness.org/education-and-resources/professional/expert-articles";
    View feedFragView;
    private ArrayList<String> mBlogUploadDateAuthList = new ArrayList<>();
    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        feedFragView = inflater.inflate(R.layout.home_feed_fragment, container, false);
        new Description().execute();
        return feedFragView;
    }
    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document mBlogDocument = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements mElementDataSize = mBlogDocument.select("div[class=post-preview__meta]");
                // Locate the content attribute
                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < mElementSize; i++) {
                    Elements mElementAuthorName = mBlogDocument.select("div[class=post-preview__meta]").select("a").eq(i);
                    String mAuthorName = mElementAuthorName.text();

                    Elements mElementBlogUploadDate = mBlogDocument.select("div[class=post-preview__meta]").eq(i);
                    String mBlogUploadDate = mElementBlogUploadDate.text();

                    Elements mElementBlogTitle = mBlogDocument.select("h2[class=post-preview__title]").select("a").eq(i);
                    String mBlogTitle = mElementBlogTitle.text();

                    mBlogUploadDateAuthList.add(mBlogUploadDate);
                    mBlogTitleList.add(mBlogTitle);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            RecyclerView mRecyclerView = feedFragView.findViewById(R.id.feedrecycler);

            DataAdapter mDataAdapter = new DataAdapter(getContext(), mBlogTitleList, mBlogUploadDateAuthList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mDataAdapter);
        }
    }
}
