package andromo.odiafm.firebse;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


public class FireBaseInstanceIDService {

    private static final String TAG = "MyFirebaseIIDService";
    private static final String TP_CAL = "cal activity";

    public void onTokenRefresh() {
        // Get updated InstanceID token.

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        FirebaseMessaging.getInstance().subscribeToTopic(TP_CAL);
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {

    }
}