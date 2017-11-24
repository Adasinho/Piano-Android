package com.example.rmas_adasinho.piano_projekt;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

/**
 * Created by RMAS-Adasinho on 2017-11-03.
 */

public interface InfoWindow {

    public void update();
    public boolean collider(MotionEvent event);
    public void draw(Canvas canvas);
}
