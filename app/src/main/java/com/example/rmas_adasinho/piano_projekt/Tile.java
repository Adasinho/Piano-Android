package com.example.rmas_adasinho.piano_projekt;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by RMAS-Adasinho on 2017-10-21.
 */

public class Tile implements GameObject{

    private boolean blackTile;
    private Rect rectangle;
    private Point point;
    private int color;
    private boolean disable;

    public Tile() {
        this.point = new Point(0,0);
    }

    public Tile(Point point, boolean blackTile) {
        if(blackTile) this.color = Color.DKGRAY;
            else this.color = Color.WHITE;

        this.rectangle = new Rect(1, 1, (GameSetting.width / 4 - 2), (GameSetting.height / 4 - 2));
        this.point = point;
        this.blackTile = blackTile;
        this.disable = false;
    }

    public void trigger(Point point) {
        if((!disable) && (!GameSetting.endGame)) {
            if (onColliderEnter(point)) {
                disable = true;
                if(blackTile) {
                    this.color = Color.LTGRAY;
                    GameSetting.score++;
                } else {
                    this.color = Color.RED;
                    GameSetting.endGame = true;
                }
            }
        }
    }

    public boolean missing() {
        if((disable == false) && (blackTile == true)) return true;
            else return false;
    }

    private boolean onColliderEnter(Point point) {
        if((rectangle.left < point.x) && (rectangle.right > point.x) && (rectangle.top < point.y) && (rectangle.bottom > point.y)) return true;
        else return false;
    }

    void move() {
        point.set(point.x, point.y + (int)(10 * GameSetting.speed));
    }

    public static void spawn(ArrayList<Tile> tiles) {
        if(GameSetting.count != 20) {
            Random r = new Random();
            int randomNumber = r.nextInt(4);
            if (GameSetting.count == 0) { // pierwszy spawn

                for (int i = 0; i < 4; i++) {
                    if (i != randomNumber)
                        tiles.add(new Tile(new Point(GameSetting.position[i], GameSetting.spawnPoint), false));
                    else
                        tiles.add(new Tile(new Point(GameSetting.position[i], GameSetting.spawnPoint), true));
                    GameSetting.count++;
                }
                GameSetting.lastSpawnTile = tiles.get(tiles.size()-1);
                //GameSetting.spawnPoint = tiles[GameSetting.count - 1].getPosition().y - GameSetting.tileHeight;

            } else { // kolejne spawny
                if ((GameSetting.count < 20) && (GameSetting.lastSpawnTile.getPosition().y > -GameSetting.tileHeight)) {

                    GameSetting.spawnPoint = GameSetting.lastSpawnTile.getPosition().y - GameSetting.tileHeight;

                    for (int i = 0; i < 4; i++) {
                        if (i % 4 == randomNumber)
                            tiles.add(new Tile(new Point(GameSetting.position[i], GameSetting.spawnPoint), true));
                        else
                            tiles.add(new Tile(new Point(GameSetting.position[i], GameSetting.spawnPoint), false));
                        GameSetting.count++;

                    }

                    GameSetting.lastSpawnTile = tiles.get(tiles.size()-1);
                }

            }
        }
    }

    public Point getPosition() {
        return point;
    }

    @Override
    public void update() {
        // l,t,r,b
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }
}
