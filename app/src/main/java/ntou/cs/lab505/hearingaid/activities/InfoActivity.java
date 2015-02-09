package ntou.cs.lab505.hearingaid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ntou.cs.lab505.hearingaid.R;

public class InfoActivity extends Activity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String helpUrl = "file:///android_asset/help.html";
        WebView webView = (WebView) findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(helpUrl);

    }




}
