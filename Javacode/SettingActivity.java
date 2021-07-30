package com.sinhvien.snakepreygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SettingActivity extends AppCompatActivity
{
    RelativeLayout rlSoundOn, rlSoundOff;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        rlSoundOn = findViewById(R.id.btnOnSound);
        rlSoundOff = findViewById(R.id.btnOffSound);
        ivBack = findViewById(R.id.back);

        rlSoundOn.setOnClickListener(new MyEvent());
        rlSoundOff.setOnClickListener(new MyEvent());
        ivBack.setOnClickListener(new MyEvent());
    }

    private class MyEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if (view == rlSoundOn) { startService(new Intent(getApplicationContext(), MusicBackground.class)); }
            if (view == rlSoundOff) { stopService(new Intent(getApplicationContext(), MusicBackground.class)); }
            if (view == ivBack) { finish(); }
        }
    }
}