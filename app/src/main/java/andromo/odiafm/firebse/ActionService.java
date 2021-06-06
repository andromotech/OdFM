package andromo.odiafm.firebse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;


public class ActionService extends Service {

    private static final String NOT_ID_EXTRA = "notId";
    private static final String IMG_URL_EXTRA = "imgUrl";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        //Saving action implementation

        return null;
    }
}
