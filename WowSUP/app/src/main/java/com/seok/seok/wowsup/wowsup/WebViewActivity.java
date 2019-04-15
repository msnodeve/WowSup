package com.seok.seok.wowsup.wowsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.seok.seok.wowsup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {
    @BindView(R.id.web_view)
    WebView webView;
    private WebSettings mWebSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        webView.setWebViewClient(new WebViewClient());
        mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://www.heywowsup.com/wordpress/");
    }
}
