package com.example.rmas_adasinho.piano_projekt;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by RMAS-Adasinho on 2017-11-03.
 */

public class InfoWindowManager {
    ArrayList<InfoWindow> infoWindows = new ArrayList<>();
    public static int ACTIVE_WINDOW;

    InfoWindowManager() {
        ACTIVE_WINDOW = 0;
        infoWindows.add(new RestartInfoWindow());
    }

    public void collider(MotionEvent event) {
        infoWindows.get(ACTIVE_WINDOW).collider(event);
        if(infoWindows.get(ACTIVE_WINDOW).collider(event)) GameSetting.restart = true;
    }

    public void update() {
        infoWindows.get(ACTIVE_WINDOW).update();
    }

    public void draw(Canvas canvas) {
        infoWindows.get(ACTIVE_WINDOW).draw(canvas);
    }
}
