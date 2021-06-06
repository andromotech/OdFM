package andromo.odiafm;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

public class OSong extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl,
        MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener {
    ImageView imageView;
    Toolbar mActionBarToolbar;
    String url = "";
    MediaPlayer mRPlayer;
    ProgressDialog pd;
    private MediaController mRController;
    private AdView adView;
    private AdView adView1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webact);
        mRController = new MediaController(OSong.this);
        imageView = (ImageView) findViewById(R.id.thumbnail);
        String url = getIntent().getExtras().getString("url");
        String pic = getIntent().getExtras().getString("pic");
        Glide.with(this)
                .load(pic)
                .placeholder(R.drawable.as)
                .into(imageView);
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
                Toast.makeText(OSong.this, "Error: " + adError.getErrorMessage(),
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

        try {
            pd = new ProgressDialog(this);
            pd.setMessage("Buffering Please wait...");
            pd.show();
            mRPlayer = new MediaPlayer();
            mRPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mRPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mRPlayer.setOnPreparedListener(this);
            mRPlayer.setOnErrorListener(this);
            mRPlayer.setDataSource(url);
            mRPlayer.prepareAsync();
            mRPlayer.setLooping(true);
            mRPlayer.setOnCompletionListener(this);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Playing FM Radio!", Toast.LENGTH_LONG).show();
        }


    }
    @Override
    public void onPrepared(MediaPlayer mRPlayer) {
        pd.dismiss();
        mRPlayer.start();
        mRController.setMediaPlayer(this);
        mRController.setAnchorView(this.findViewById(R.id.thumbnail));
        mRController.setEnabled(true);
        mRController.show();
        mRController.isShowing();
        mRController.setPadding(0, 0, 0, 0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mRController.show();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mRPlayer) {
        pd.dismiss();
    }

    @Override
    public boolean onError(MediaPlayer mRPlayer, int what, int extra) {
        pd.dismiss();
        return false;
    }

    @Override
    public void start() {
        mRPlayer.start();
    }

    @Override
    public void pause() {
        if (mRPlayer != null && mRPlayer.isPlaying()) {
            Toast.makeText(getApplicationContext(), "Pausing FM Radio!", Toast.LENGTH_SHORT).show();
            mRPlayer.pause();
        }

    }

    @Override
    public int getDuration() {
        return mRPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mRPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mRPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mRPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mRPlayer.getAudioSessionId();
    }

    @Override
    public void onDestroy() {
        if (mRPlayer != null && adView != null)
            mRPlayer.release();
        adView.destroy();
        mRPlayer = null;
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
            //Things to Do
            if (mRPlayer != null) {
                mRPlayer.stop();
                mRPlayer = null;
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }

//onRestoreInstanceState

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

    }

}