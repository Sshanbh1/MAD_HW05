package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        myWebView = findViewById(R.id.wv_view1);

        Intent intent = getIntent();
        String action = intent.getAction();

        setTitle(intent.getStringExtra("title"));

        if ("com.example.hw05.intent.action.VIEW".equals(action)) {
//            myWebView.getSettings().setJavaScriptEnabled(true);
//            myWebView.getSettings().setLoadWithOverviewMode(true);
//            myWebView.getSettings().setUseWideViewPort(true);
//            myWebView.setWebChromeClient(new WebChromeClient());
            myWebView.loadUrl(intent.getStringExtra("url"));
        }
    }
}
