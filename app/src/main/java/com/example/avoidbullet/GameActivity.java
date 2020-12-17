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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zerokol.views.joystickView.JoystickView;

public class GameActivity extends AppCompatActivity {
    private JoystickView joystick;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        joystick = (JoystickView) findViewById(R.id.joystickView);
        //조이스틱 움직이기
        joystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {

            @Override
            public void onValueChanged(int angle, int power, int direction) {
                // TODO Auto-generated method stub
                switch (direction) //조이스틱이 움직이는 방향에 따라 imageview 움직이기
                {
                    case JoystickView.FRONT:
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() - power / 5);
                        break;
                    case JoystickView.FRONT_RIGHT:
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() - power / 5);
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() - power / 5);
                        break;
                    case JoystickView.RIGHT:
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() - power / 5);
                        break;
                    case JoystickView.RIGHT_BOTTOM:
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() - power / 5);
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() + power / 5);
                        break;
                    case JoystickView.BOTTOM:
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() + power / 5);
                        break;
                    case JoystickView.BOTTOM_LEFT:
                        findViewById(R.id.character).setX(findViewById(R.id.character).getX() + power / 5);
                        findViewById(R.id.character).setY(findViewById(R.id.character).getY() + power / 5);
                        break;
                    case JoystickView.LEFT:
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
    }
}

