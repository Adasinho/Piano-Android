package com.example.rmas_adasinho.piano_projekt;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by RMAS-Adasinho on 2017-11-03.
 */

public class RestartInfoWindow implements InfoWindow {

    private Point point;
    private int height;
    private int width;
    private Rect windowRect;
    private int padding;
    private int buttonWidth;
    private int buttonHeight;
    private Rect buttonRect;

    RestartInfoWindow() {
        this.width = GameSetting.width*3/4;
        this.height = GameSetting.height/4;
        this.point = new Point(GameSetting.width/2,GameSetting.height/2);
        this.windowRect = new Rect(point.x - width/2,point.y - height/2, point.x + width/2, point.y + height/2);
        this.buttonWidth = width/2;
        this.buttonHeight = height/2;

        this.buttonRect = new Rect(point.x - buttonWidth/2, point.y - buttonHeight/2 + 50, point.x + buttonWidth/2, point.y + buttonHeight/2 + 50);

        this.padding = 10;
    }

    @Override
    public boolean collider(MotionEvent event) {
        if((event.getX() > buttonRect.left) && (event.getX() < buttonRect.right) && (event.getY() > buttonRect.top) && (event.getY() < buttonRect.bottom)) return true;
        else return false;
    }

    @Override
    public void draw(Canvas canvas) {
        if(GameSetting.endGame) {
            Paint paint = new Paint();

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(padding);
            canvas.drawRect(windowRect,paint);

            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(0);
            canvas.drawRect(windowRect.left+padding,windowRect.top+padding,windowRect.right-padding,windowRect.bottom-padding,paint);

            paint.setStrokeWidth(0);
            paint.setColor(Color.BLACK);
            paint.setTextSize(GameSetting.width/13);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("GAME OVER",GameSetting.width/2,GameSetting.height/2-100,paint);

            canvas.drawRect(buttonRect, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("RESTART",GameSetting.width/2,GameSetting.height/2+50,paint);
        }
    }

    @Override
    public void update() {
    }
}
