package andromo.odiafm;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import andromo.odiafm.RestCall.Client;
import andromo.odiafm.RestCall.Server;
import andromo.odiafm.adapter.FmAdp;
import andromo.odiafm.adapter.SongAdp;
import andromo.odiafm.adapter.Top10Adp;
import andromo.odiafm.model.Fm10Model;
import andromo.odiafm.model.Fm10View;
import andromo.odiafm.model.FmModel;
import andromo.odiafm.model.FmView;
import andromo.odiafm.model.SongModel;
import andromo.odiafm.model.SongView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd interstitialAd ;
    private List<SongModel> spllist;
    private List<FmModel> bhalist;
    private List<Fm10Model> fmlist ;
    ProgressDialog pogrd;
    private AdView adView;
    private AdView adView1;
    private AdView adView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadJSON1();
        loadJSON2();
        loadJSON3();
        AudienceNetworkAds.initialize(this);
        interstitialAd = new InterstitialAd(this, "296459714823771_296461661490243");
        adView = new AdView(this, "296459714823771_296460508157025", AdSize.BANNER_HEIGHT_50);
        adView1 = new AdView(this, "296459714823771_296460508157025", AdSize.BANNER_HEIGHT_50);
        adView2 = new AdView(this, "296459714823771_296460508157025", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container2);
        LinearLayout adContainer1 = (LinearLayout) findViewById(R.id.banner_container1);
        LinearLayout adContainer2 = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(adView);
        adContainer1.addView(adView1);
        adContainer2.addView(adView2);
        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(MainActivity.this, "Error: " + adError.getErrorMessage(),
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

        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed

                // Show the ad
                interstitialAd.show();
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
        adView2.loadAd();
        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown

        interstitialAd.loadAd();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "1";
        String channel2 = "2";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    "Channel 1", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("sm05.co.in");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationChannel notificationChannel2 = new NotificationChannel(channel2,
                    "Channel 2", NotificationManager.IMPORTANCE_MIN);

            notificationChannel.setDescription("sm05.co.in");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel2);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            String myUrl = "https://play.google.com/store/apps/details?id=andromo.odiafm";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl)));
        }
        if (id == R.id.prv) {
            Intent intent = new Intent(this, Prv.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadJSON1(){
        try{
            Client Clt = new Client();
            Server apiServer =
                    Clt.getClient().create(Server.class);
            Call<SongView> call = apiServer.getSplCal();
            call.enqueue(new Callback<SongView>() {
                @Override
                public void onResponse(Call<SongView> call, Response<SongView> response) {
                    List<SongModel> items = response.body().getSplCal();
                    bhalist = new ArrayList<>();
                    SongAdp firstAdapter = new SongAdp(getApplicationContext(), items);
                    MultiSnapRecyclerView firstRecyclerView = (MultiSnapRecyclerView)findViewById(R.id.recycler_view);
                    LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    firstRecyclerView.setLayoutManager(firstManager);
                    firstRecyclerView.setAdapter(firstAdapter);
                }

                @Override
                public void onFailure(Call<SongView> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void loadJSON3(){
        try{
            Client Clt = new Client();
            Server apiServer =
                    Clt.getClient().create(Server.class);
            Call<Fm10View> call = apiServer.getFm();
            call.enqueue(new Callback<Fm10View>() {
                @Override
                public void onResponse(Call<Fm10View> call, Response<Fm10View> response) {
                    List<Fm10Model> items = response.body().getFm();
                    fmlist = new ArrayList<>();
                    Top10Adp firstAdapter = new Top10Adp(getApplicationContext(), items);
                    MultiSnapRecyclerView firstRecyclerView = (MultiSnapRecyclerView)findViewById(R.id.recycler_view2);
                    LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    firstRecyclerView.setLayoutManager(firstManager);
                    firstRecyclerView.setAdapter(firstAdapter);
                }

                @Override
                public void onFailure(Call<Fm10View> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    private void loadJSON2(){
        try{
            Client Clt = new Client();
            Server apiServer =
                    Clt.getClient().create(Server.class);
            Call<FmView> call = apiServer.getNineBook();
            call.enqueue(new Callback<FmView>() {
                @Override
                public void onResponse(Call<FmView> call, Response<FmView> response) {
                    List<FmModel> items = response.body().getNineBook();
                    spllist = new ArrayList<>();
                    FmAdp firstAdapter = new FmAdp(getApplicationContext(), items);
                    RecyclerView firstRecyclerView = (RecyclerView)findViewById(R.id.recycler_view1);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    firstRecyclerView.setLayoutManager(mLayoutManager);
                    firstRecyclerView.setAdapter(firstAdapter);
                }

                @Override
                public void onFailure(Call<FmView> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
//                    pogrd.hide();

                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        if (interstitialAd != null && adView != null )  {
            interstitialAd.destroy();
            adView.destroy();
        }
        super.onDestroy();
    }
}
