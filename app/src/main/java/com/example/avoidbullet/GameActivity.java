package com.example.avoidbullet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    static int msec = 0;
    static String msec_format;
    static String sec_format;
    static String min_format;
    static TextView alive_time;
    static TextView alive_millietime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        alive_time = findViewById(R.id.alive_time);
        alive_millietime = findViewById(R.id.alive_millietime);  // 아래 첨자 밀리초

        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                msec++;
                msec_format = String.format("%02d", msec % 100);
                sec_format = String.format("%02d", (msec / 100) % 60);
                min_format = String.format("%02d", ((msec / 100) / 60));

                if (msec / 100 / 60 == 0) {  //
                    alive_time.setText(sec_format + " : " + msec_format);
                } else {
                    alive_millietime.setVisibility(View.VISIBLE);
                    alive_time.setText(min_format + " : " + sec_format);
                    alive_millietime.setText("." + msec_format);
                }
            }
        };

        Timer timer = new Timer();
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };
        timer.schedule(t, 0, 10);
    }
}