package com.example.rmas_adasinho.piano_projekt;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        GameSetting.height = size.y;
        GameSetting.width = size.x;
        GameSetting.tileHeight = size.y/4;
        GameSetting.tileWidth = size.x/4;
        GameSetting.spawnPoint = -GameSetting.tileHeight/2;
        GameSetting.setPosition();

        setContentView(new GamePanel(this));
    }
}
