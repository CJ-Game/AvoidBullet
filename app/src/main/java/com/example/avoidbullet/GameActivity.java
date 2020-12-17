package com.example.avoidbullet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import com.zerokol.views.joystickView.JoystickView;

public class GameActivity extends AppCompatActivity {
    // 타이머
    static int msec = 0;
    static String msec_format;
    static String sec_format;
    static String min_format;
    static TextView alive_time;
    static TextView alive_millietime;

    // 조이스틱
    private JoystickView joystick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

    // 조이스틱
        joystick = (JoystickView) findViewById(R.id.joystickView);
        //조이스틱 움직이기
        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {

            @Override
            public void onValueChanged(int angle, int power, int direction) {
                // TODO Auto-generated method stub
                switch (direction) //조이스틱이 움직이는 방향에 따라 imageview 움직이기
                {
                    case JoystickView.FRONT:  // 위
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() - power / 5);
                        break;
                    case JoystickView.FRONT_RIGHT:
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() - power / 5);
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() - power / 5);
                        break;
                    case JoystickView.RIGHT:  // 오른쪽
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() - power / 5);
                        break;
                    case JoystickView.RIGHT_BOTTOM:
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() - power / 5);
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() + power / 5);
                        break;
                    case JoystickView.BOTTOM:  // 아래
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() + power / 5);
                        break;
                    case JoystickView.BOTTOM_LEFT:
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() + power / 5);
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() + power / 5);
                        break;
                    case JoystickView.LEFT:  // 왼쪽
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() + power / 5);
                        break;
                    case JoystickView.LEFT_FRONT:
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() + power / 5);
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() - power / 5);
                        break;
                    default:
                        break;
                }

            }
        }, 15);  //loopInterval

     //타이머
        alive_time = findViewById(R.id.alive_time);
        alive_millietime = findViewById(R.id.alive_millietime);  // 아래 첨자 밀리초

        final Handler handler = new Handler() {  // 없으면 UI 변경 못함
            public void handleMessage(Message msg) {
                msec++;
                msec_format = String.format("%02d", msec % 100);
                sec_format = String.format("%02d", (msec / 100) % 60);
                min_format = String.format("%02d", ((msec / 100) / 60));

                if (msec / 100 / 60 == 0) {  // 초:밀리초
                    alive_time.setText(sec_format + " : " + msec_format);
                } else {  // 분:초
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
        timer.schedule(t, 0, 10); // 10밀리초 마다 run
    }
}

