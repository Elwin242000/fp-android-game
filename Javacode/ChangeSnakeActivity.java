package com.sinhvien.snakepreygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sinhvien.snakepreygame.database.DatabaseHelper;
import com.sinhvien.snakepreygame.model.ChangeSnakeModel;

public class ChangeSnakeActivity extends AppCompatActivity
{
    ImageView ivBack;
    Button btnBlue, btnBlack, btnOrange, btnGray;
    private DatabaseHelper db;
    private ChangeSnakeModel csm;
    private String snake;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_snake);

        db = new DatabaseHelper(this);
        csm = db.getSnake(1);
        snake = csm.getIv_snake();

        ivBack = findViewById(R.id.back);
        btnBlue = findViewById(R.id.btnBlue);
        btnBlack = findViewById(R.id.btnBlack);
        btnOrange = findViewById(R.id.btnOrange);
        btnGray = findViewById(R.id.btnGray);

        ivBack.setOnClickListener(new MyEvent());
        btnBlue.setOnClickListener(new MyEvent());
        btnBlack.setOnClickListener(new MyEvent());
        btnOrange.setOnClickListener(new MyEvent());
        btnGray.setOnClickListener(new MyEvent());

        if (snake.equals("blue"))
        {
            selectedSnake(btnBlue);
            notSelectSnake(btnBlack);
            notSelectSnake(btnOrange);
            notSelectSnake(btnGray);
        }
        else if (snake.equals("black"))
        {
            notSelectSnake(btnBlue);
            selectedSnake(btnBlack);
            notSelectSnake(btnOrange);
            notSelectSnake(btnGray);
        }
        else if (snake.equals("orange"))
        {
            notSelectSnake(btnBlue);
            notSelectSnake(btnBlack);
            selectedSnake(btnOrange);
            notSelectSnake(btnGray);
        }
        else if (snake.equals("gray"))
        {
            notSelectSnake(btnBlue);
            notSelectSnake(btnBlack);
            notSelectSnake(btnOrange);
            selectedSnake(btnGray);
        }
    }

    private class MyEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if (view == ivBack)
            {
                finish();
            }
            if (view == btnBlue)
            {
                GamePlayView.snakeColor = R.drawable.snake;
                selectedSnake(btnBlue);
                notSelectSnake(btnBlack);
                notSelectSnake(btnOrange);
                notSelectSnake(btnGray);
                db.updateSnake("blue");
            }
            if (view == btnBlack)
            {
                GamePlayView.snakeColor = R.drawable.snake1;
                notSelectSnake(btnBlue);
                selectedSnake(btnBlack);
                notSelectSnake(btnOrange);
                notSelectSnake(btnGray);
                db.updateSnake("black");
            }
            if (view == btnOrange)
            {
                GamePlayView.snakeColor = R.drawable.snake2;
                notSelectSnake(btnBlue);
                notSelectSnake(btnBlack);
                selectedSnake(btnOrange);
                notSelectSnake(btnGray);
                db.updateSnake("orange");
            }
            if (view == btnGray)
            {
                GamePlayView.snakeColor = R.drawable.snake3;
                notSelectSnake(btnBlue);
                notSelectSnake(btnBlack);
                notSelectSnake(btnOrange);
                selectedSnake(btnGray);
                db.updateSnake("gray");
            }
        }
    }

    public void notSelectSnake(Button btn)
    {
        btn.setText(R.string.btn_select);
        btn.setBackgroundResource(R.color.select);
    }
    public void selectedSnake(Button btn)
    {
        btn.setText(R.string.btn_selected);
        btn.setBackgroundResource(R.color.selected);
    }
}