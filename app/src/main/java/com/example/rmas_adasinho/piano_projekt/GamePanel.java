package com.example.rmas_adasinho.piano_projekt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by RMAS-Adasinho on 2017-10-28.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private ArrayList<Tile> tile = new ArrayList<>(20);
    private boolean done = false;

    private InfoWindowManager manager;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        manager = new InfoWindowManager();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for(Tile t : tile) {
            t.trigger(new Point((int)event.getX(),(int)event.getY()));
        }

        manager.collider(event);

        return super.onTouchEvent(event);
    }

    public void restart() {
        for(Tile t : tile)
            tile.remove(t);

        tile = new ArrayList<>(20);
        GameSetting.count = 0;
        GameSetting.endGame = false;
        GameSetting.restart = false;
        GameSetting.score = 0;
    }

    public void update() {
        if(!GameSetting.endGame) {
            Tile.spawn(tile);

            try {
                for (int i = 0; i < 4; i++) {

                    if (tile.get(0).getPosition().y > (GameSetting.height + GameSetting.tileHeight / 2)) {
                        if(tile.get(0).missing()) GameSetting.endGame = true;
                        tile.remove(tile.get(0));
                        GameSetting.count--;
                    }
                }

                for (Tile t : tile) {

                    t.move();
                    t.update();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(GameSetting.restart) restart();
        }

        manager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.BLACK);

        for(Tile t : tile)
            t.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(GameSetting.width/10);
        paint.setTextAlign(Paint.Align.CENTER);
        Shader shader = new Shader();
        paint.setShader(shader);

        if(GameSetting.score < 10) canvas.drawText(String.valueOf("0"+GameSetting.score),GameSetting.width/2,GameSetting.height/10,paint);
            else canvas.drawText(String.valueOf(GameSetting.score),GameSetting.width/2,GameSetting.height/10,paint);

        manager.draw(canvas);
    }
}
