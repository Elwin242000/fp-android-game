package com.sinhvien.snakepreygame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.snakepreygame.database.DatabaseHelper;

public class HomeActivity extends AppCompatActivity
{
    Intent service;
    Button btnPlayGame,btnTutorial, btnSetting, btnChangeSnake, btnEasy, btnMedium, btnHard;
    ImageView ivBack;
    public static Dialog selectLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        service = new Intent(getApplicationContext(), MusicBackground.class);
        startService(service);

        selectLevel = new Dialog(this);
        selectLevel.setContentView(R.layout.level_select);
        selectLevel.setCanceledOnTouchOutside(false);

        btnPlayGame = findViewById(R.id.btnPlay);
        btnTutorial = findViewById(R.id.btnHowToPlay);
        btnSetting = findViewById(R.id.btnSetting);
        btnChangeSnake = findViewById(R.id.btnChange);
        ivBack = selectLevel.findViewById(R.id.backHome);
        btnEasy = selectLevel.findViewById(R.id.easy);
        btnMedium = selectLevel.findViewById(R.id.medium);
        btnHard = selectLevel.findViewById(R.id.hard);

        btnPlayGame.setOnClickListener(new MyEvent());
        btnTutorial.setOnClickListener(new MyEvent());
        btnSetting.setOnClickListener(new MyEvent());
        btnChangeSnake.setOnClickListener(new MyEvent());
        ivBack.setOnClickListener(new MyEvent());
        btnEasy.setOnClickListener(new MyEvent());
        btnMedium.setOnClickListener(new MyEvent());
        btnHard.setOnClickListener(new MyEvent());
    }

    private class MyEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if (view == btnPlayGame)  { selectLevel.show(); }
            if (view == btnTutorial)
            {
                Intent intent = new Intent(HomeActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
            if (view == btnSetting)
            {
                Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(intent);
            }
            if (view == btnChangeSnake)
            {
                Intent intent = new Intent(HomeActivity.this, ChangeSnakeActivity.class);
                startActivity(intent);
            }
            if (view == ivBack)
            {
                selectLevel.dismiss();
            }
            if (view == btnEasy)
            {
                Intent intent = new Intent(HomeActivity.this, PlayGameActivity.class);
                intent.putExtra("spd", 200);
                intent.putExtra("sql", 1);
                startActivity(intent);
            }
            if (view == btnMedium)
            {
                Intent intent = new Intent(HomeActivity.this, PlayGameActivity.class);
                intent.putExtra("spd", 100);
                intent.putExtra("sql", 2);
                startActivity(intent);
            }
            if (view == btnHard)
            {
                Intent intent = new Intent(HomeActivity.this, PlayGameActivity.class);
                intent.putExtra("spd", 200);
                intent.putExtra("sql", 3);
                startActivity(intent);
            }
        }
    }
}