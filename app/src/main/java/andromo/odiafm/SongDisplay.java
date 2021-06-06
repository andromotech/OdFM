package andromo.odiafm;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.squareup.picasso.Picasso;


public class SongDisplay extends AppCompatActivity {
    Handler handler;
    Runnable r;
    private AdView adView;
    private AdView adView1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview111);
        WebView wb = (WebView) findViewById(R.id.webview);
        handler = new Handler();
        r = new Runnable() {

            @Override
            public void run() {
            }
        };
        startHandler();
        ImageView img = (ImageView) findViewById(R.id.img);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
        String url = getIntent().getExtras().getString("url");
        String pic = getIntent().getExtras().getString("pic");
        Picasso.get()
                .load(pic)
                .resize(120, 120)
                .centerCrop()
                .noFade()
                .into(img);

        wb.loadUrl(url);
        wb.setWebViewClient(new WebViewClient());
        wb.getSettings().setJavaScriptEnabled(true);
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "296459714823771_296460508157025", AdSize.BANNER_HEIGHT_50);
        adView1 = new AdView(this, "296459714823771_296460508157025", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container2);
        LinearLayout adContainer1 = (LinearLayout) findViewById(R.id.banner_container1);
        adContainer.addView(adView);
        adContainer1.addView(adView1);
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(SongDisplay.this, "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });
        adView.loadAd();
        adView1.loadAd();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        }
    }
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();//stop first and then start
        startHandler();
    }
    public void stopHandler() {
        handler.removeCallbacks(r);
    }
    public void startHandler() {
        handler.postDelayed(r, 5*60*1000); //for 5 minutes
    }
    @Override
    protected void onDestroy() {
        if (adView != null )  {
            adView.destroy();
        }
        super.onDestroy();
    }
}