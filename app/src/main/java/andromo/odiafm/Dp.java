package andromo.odiafm;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Dp extends Activity {

    private static int DP_TIMEOUT=3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Dp.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, DP_TIMEOUT);

    }


}