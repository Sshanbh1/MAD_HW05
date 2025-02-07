package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
            myWebView.setWebViewClient(new WebViewClient());
            myWebView.loadUrl(intent.getStringExtra("url"));
            myWebView.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()){
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
