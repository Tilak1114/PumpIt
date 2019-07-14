package com.example.tilak.pumpit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class FeedFragment extends Fragment {
    WebView webView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View feedfragview = inflater.inflate(R.layout.home_feed_fragment, container, false);
        webView = feedfragview.findViewById(R.id.webViewFeed);
        webView.loadUrl("https://www.fitnessmarketingmastery.com/fitness-marketing-blog/");
        return feedfragview;
    }
}
