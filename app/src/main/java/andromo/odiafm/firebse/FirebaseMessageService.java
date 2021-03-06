package andromo.odiafm.firebse;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import andromo.odiafm.MainActivity;
import andromo.odiafm.R;


public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String NOT_ID_EXTRA = "notId";
    private static final String IMG_URL_EXT = "imgLink";
    private static final String ADM_CHAN_ID ="admin_chnl";
    private NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent notificationIntent = new Intent(this, MainActivity.class);

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        //You should use an actual ID instead
        int notificationId = new Random().nextInt(60000);


        Bitmap bitmap = getBitmapfromUrl(remoteMessage.getData().get("pic"));

        Intent likeIntent = new Intent(this, ActionService.class);
        likeIntent.putExtra(NOT_ID_EXTRA,notificationId);
        likeIntent.putExtra(IMG_URL_EXT,remoteMessage.getData().get("pic"));
        PendingIntent likePendingIntent = PendingIntent.getService(this,
                notificationId+1,likeIntent,PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels();
        }

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, ADM_CHAN_ID)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.mipmap.icon)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .setSummaryText(remoteMessage.getData().get("message"))
                            .bigPicture(bitmap))
                    .setContentText(remoteMessage.getData().get("message"))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            notificationManager.notify(notificationId, notificationBuilder.build());

    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(){
        CharSequence adminChannelName = getString(R.string.not_admin_name);
        String adminChannelDescription = getString(R.string.not_admin_description);

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADM_CHAN_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}