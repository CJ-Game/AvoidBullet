package com.example.avoidbullet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



    }
    class GameView extends View {
        int width, height;                                  // 화면의 폭과 높이
        float x, y;                                            // 캐릭터의 현재 좌표
        float dx, dy;                                        // 캐릭터가 이동할 방향과 거리
        int cw, ch;                                          // 캐릭터의 폭과 높이
        int counter;                                         // 루프 카운터
        Bitmap character[] = new Bitmap[2];   // 캐릭터의 비트맵 이미지
        float x1, y1, x2, y2;                             // Down, Up
        boolean canRun = true;

        //-----------------------------------
        //   Constructor - 게임 초기화
        //-----------------------------------
        public GameView(Context context) {
            super(context);

            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay();
            width = display.getWidth();              // 화면의 가로폭
            height = display.getHeight();            // 화면의 세로폭
            x = 100;                                         // 캐릭터의 현재 x위치
            y = 100;                                         // 캐릭터의 현재 y위치
            dx = 4;                                          // 캐릭터가 x축으로 이동할 거리
            dy = 6;                                          // 캐릭터가 y축으로 이동할 거리

            // 캐릭터의 비트맵 읽기
            character[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            cw = character[0].getWidth() / 2;               // 캐릭터의 폭/2
            ch = character[0].getHeight() / 2;              // 캐릭터의 높이/2

            mHandler.sendEmptyMessageDelayed(0, 10);
        }
        public void onDraw(Canvas canvas) {
            x += dx;                              // 가로 방향으로 이동
            y += dy;                              // 세로 방향으로 이동
            if (x < cw) {                        // 왼쪽 벽
                x = cw;
                dx = -dx;
            } else if (x > width - cw) {   // 오른쪽 벽
                x = width - cw;
                dx = -dx;
            } else if (y < ch) {               // 천정
                y = ch;
                dy = -dy;
            } else if (y > height - ch) {    // 바닥
                y = height - ch;
                dy = -dy;
            }
            counter++;
            int n = counter % 20 / 10;
            canvas.drawBitmap(character[n], x - cw, y - ch, null);
        }
        Handler mHandler = new Handler() {               // 타이머로 사용할 Handler
            public void handleMessage(Message msg) {
                if (canRun == true) {
                    invalidate();                                              // onDraw() 다시 실행
                    Log.v("변수 값", "x=" + x + "  y=" + y);
                    mHandler.sendEmptyMessageDelayed(0, 10); // 10/1000초마다 실행
                } else
                    finish();
            }
        }; // Handler

        //------------------------------------
        //      onTouchEvent
        //------------------------------------
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x1 = event.getX();                   // 버튼을 누른 위치
                y1 = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                x2 = event.getX();                   // 버튼을 이동한 후 손을 뗀 위치
                y2 = event.getY();
                dx = (x2 - x1) / 10;                 // 버튼의 거리
                dy = (y2 - y1) / 10;
                x = x1;                                   // 캐릭터의 현재 위치를 버튼을 누른 곳으로 설정
                y = y1;
            }
            return true;
        } // onTouchEvent

        //------------------------------------
        //      onKeyDown
        //------------------------------------
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                canRun = false;
            }
            return true;
        } // onKeyDown

    }
}