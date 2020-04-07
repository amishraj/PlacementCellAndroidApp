package com.example.hp.homepage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Placement_Policy extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement__policy);

        webView= (WebView) findViewById(R.id.webplacement);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        String url = "http://www.mnit.ac.in/cms/uploads/2018/07/Institute_Placement_Policy-MNIT.pdf";
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
