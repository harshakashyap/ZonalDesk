package com.example.dev.zonaldesk;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by kashyap on 28/6/18.
 */

public class TheBrowser extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //return super.shouldOverrideUrlLoading(view, url);
        view.loadUrl(url);
        return true;
    }
}
