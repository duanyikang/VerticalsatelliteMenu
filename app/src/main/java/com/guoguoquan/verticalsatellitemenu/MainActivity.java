package com.guoguoquan.verticalsatellitemenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.guoguoquan.verticalsatellitemenu.view.VerticalsatelliteMenu;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private ImageView iv_image;
    private VerticalsatelliteMenu verticalsatelliteMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
        verticalsatelliteMenu = (VerticalsatelliteMenu) findViewById(R.id.view);
        iv_image.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId()==R.id.iv_image) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    verticalsatelliteMenu.setVisibility(View.VISIBLE);
                    verticalsatelliteMenu.open();
                    verticalsatelliteMenu.onTouchEvent(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    verticalsatelliteMenu.onTouchEvent(event);
                    break;
                case MotionEvent.ACTION_UP:
                    verticalsatelliteMenu.onTouchEvent(event);
                    verticalsatelliteMenu.close();
                    verticalsatelliteMenu.setVisibility(View.INVISIBLE);
                    break;
            }
        }
        return true;
    }
}
