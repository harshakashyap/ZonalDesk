package com.example.dev.zonaldesk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebTrial extends AppCompatActivity {

    WebView webView;
    Toolbar toolbar;
    ProgressBar progressBar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        intent = getIntent();

        setContentView(R.layout.activity_web_trial);

        webView = findViewById(R.id.webact1);
        progressBar = findViewById(R.id.progressbar);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2;
                if (WebTrial.this.intent.getStringExtra("Activity").equals("Management"))
                    intent2 = new Intent(WebTrial.this, ManagementDashboard.class);
                else if (WebTrial.this.intent.getStringExtra("Activity").equals("Engineer"))
                    intent2 = new Intent(WebTrial.this, EngineersDashboard.class);
                else
                    intent2 = new Intent(WebTrial.this, CustomerDashboard.class);

                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });
        getSupportActionBar().setTitle("ZonalDesk");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                WebTrial.this.setProgress(1000 * newProgress);
                progressBar.setProgress((progressBar.getMax() * newProgress) / 100);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebTrial.this, "" + description, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(intent.getStringExtra("URL"));
    }
}
