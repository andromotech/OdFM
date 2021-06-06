package andromo.odiafm;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;


public class Prv extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview111);
        WebView wb = (WebView) findViewById(R.id.webview);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setDisplayZoomControls(false);
        wb.getSettings().setUseWideViewPort(true);
        wb.setInitialScale(50);
        wb.loadUrl("http://www.sm05.co.in/privacy_policy.html");
        wb.setWebViewClient(new WebViewClient());
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("file:///android_res/drawable/.png");
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });
    }
}
