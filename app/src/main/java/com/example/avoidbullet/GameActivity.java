package com.example.avoidbullet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import com.zerokol.views.joystickView.JoystickView;

public class GameActivity extends AppCompatActivity {
    // 타이머
    static int msec;
    static int count;
    static String msec_format;
    static String sec_format;
    static String min_format;
    static TextView alive_time;
    static TextView alive_millietime;
    Timer timer;

    // 조이스틱
    private JoystickView joystick;

    // 화면 크기
    private Display display;
    private DisplayMetrics displayMetrics;
    private float dis_density;
    private float dis_w;
    private float dis_h;

    // 캐릭터 사이즈
    private ImageView img;
    private int cha_w;
    private int cha_h;

    // 캐릭터 사이즈
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        img = findViewById(R.id.character);
        cha_w = img.getWidth() / 2;
        cha_h = img.getHeight() / 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 하단 네비게이션 바 숨기기
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // 핸드폰 화면 사이즈
        display = getWindowManager().getDefaultDisplay();
        displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        dis_density = getResources().getDisplayMetrics().density;
        dis_w = displayMetrics.widthPixels - 1;
        dis_h = displayMetrics.heightPixels;

        // 조이스틱
        joystick = (JoystickView) findViewById(R.id.joystickView);
        //조이스틱 움직이기
        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {

            @Override
            public void onValueChanged(int angle, int power, int direction) {
                // TODO Auto-generated method stub
                if (count == 0) {
                    switch (direction) //조이스틱이 움직이는 방향에 따라 imageview 움직이기
                    {
                        case JoystickView.FRONT:  // 위
                            Wall(findViewById(R.id.character).getX(), findViewById(R.id.character).getY() - power / 5);
                            break;
                        case JoystickView.FRONT_FRONT_RIGHT:
                            Wall(findViewById(R.id.character).getX() - power / 10, findViewById(R.id.character).getY() - power / 4);
                            break;
                        case JoystickView.FRONT_RIGHT:
                            Wall(findViewById(R.id.character).getX() - power / 5, findViewById(R.id.character).getY() - power / 5);
                            break;
                        case JoystickView.FRONT_RIGHT_RIGHT:
                            Wall(findViewById(R.id.character).getX() - power / 4, findViewById(R.id.character).getY() - power / 10);
                            break;
                        case JoystickView.RIGHT:  // 오른쪽
                            Wall(findViewById(R.id.character).getX() - power / 5, findViewById(R.id.character).getY());
                            break;
                        case JoystickView.RIGHT_RIGHT_BOTTOM:
                            Wall(findViewById(R.id.character).getX() - power / 4, findViewById(R.id.character).getY() + power / 10);
                            break;
                        case JoystickView.RIGHT_BOTTOM:
                            Wall(findViewById(R.id.character).getX() - power / 5, findViewById(R.id.character).getY() + power / 5);
                            break;
                        case JoystickView.BOTTOM_BOTTOM_RIGHT:
                            Wall(findViewById(R.id.character).getX() - power / 10, findViewById(R.id.character).getY() + power / 4);
                            break;
                        case JoystickView.BOTTOM:  // 아래
                            Wall(findViewById(R.id.character).getX(), findViewById(R.id.character).getY() + power / 5);
                            break;
                        case JoystickView.BOTTOM_BOTTOM_LEFT:
                            Wall(findViewById(R.id.character).getX() + power / 10, findViewById(R.id.character).getY() + power / 4);
                            break;
                        case JoystickView.BOTTOM_LEFT:
                            Wall(findViewById(R.id.character).getX() + power / 5, findViewById(R.id.character).getY() + power / 5);
                            break;
                        case JoystickView.BOTTOM_LEFT_LEFT:
                            Wall(findViewById(R.id.character).getX() + power / 4, findViewById(R.id.character).getY() + power / 10);
                            break;
                        case JoystickView.LEFT:  // 왼쪽
                            Wall(findViewById(R.id.character).getX() + power / 5, findViewById(R.id.character).getY());
                            break;
                        case JoystickView.LEFT_LEFT_FRONT:
                            Wall(findViewById(R.id.character).getX() + power / 4, findViewById(R.id.character).getY() - power / 10);
                            break;
                        case JoystickView.LEFT_FRONT:
                            Wall(findViewById(R.id.character).getX() + power / 5, findViewById(R.id.character).getY() - power / 5);
                            break;
                        case JoystickView.LEFT_FRONT_FRONT:
                            Wall(findViewById(R.id.character).getX() + power / 10, findViewById(R.id.character).getY() - power / 4);
                            break;
                        default:
                            break;
                    }
                }
            }
        }, 20);  //loopInterval


        //타이머
        msec = 0;
        count = 400;
        alive_time = findViewById(R.id.alive_time);
        alive_millietime = findViewById(R.id.alive_millietime);  // 아래 첨자 밀리초

        final Handler handler = new Handler() {  // 없으면 UI 변경 못함
            public void handleMessage(Message msg) {
                if (count == 0) {
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
                } else {
                    count--;
                    TextView textView = findViewById(R.id.start_timer);
                    if (count == 0) {
                        textView.setVisibility(View.GONE);
                    } else {
                        textView.setText(String.valueOf((count / 100)));
                    }
                }
            }
        };

        timer = new Timer();
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };
        timer.schedule(t, 0, 10); // 10밀리초 마다 run
    }

    // 벽 셜정
    public void Wall(float x, float y) {
        if (x < 0 - cha_w) {  // 왼쪽 벽
            findViewById(R.id.character).setX(0 - cha_w);
        } else if (x > dis_w - cha_w) {  // 오른쪽 벽
            findViewById(R.id.character).setX(dis_w - cha_w);
        } else {
            findViewById(R.id.character).setX(x);
        }
        if (y < 0 - cha_h) {  // 위쪽 벽
            findViewById(R.id.character).setY(0 - cha_h);
        } else if (y > dis_h - cha_h - 25) {  // 아래쪽 벽
            findViewById(R.id.character).setY(dis_h - cha_h - 25);
        } else {
            findViewById(R.id.character).setY(y);
        }
    }

    // 뒤로가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}

