package com.sinhvien.snakepreygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TutorialActivity extends AppCompatActivity
{
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ivBack = findViewById(R.id.back);

        ivBack.setOnClickListener(new MyEvent());
    }

    private class MyEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if (view == ivBack) { finish(); }
        }
    }
}