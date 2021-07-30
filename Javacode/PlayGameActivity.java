package com.sinhvien.snakepreygame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinhvien.snakepreygame.database.DatabaseHelper;
import com.sinhvien.snakepreygame.model.ChangeSnakeModel;
import com.sinhvien.snakepreygame.model.EasyModeModel;
import com.sinhvien.snakepreygame.model.HardModeModel;
import com.sinhvien.snakepreygame.model.MediumModeModel;

import java.io.ByteArrayOutputStream;

public class PlayGameActivity extends AppCompatActivity
{
    private GamePlayView gpv;
    ImageView ivPause;
    public static Dialog stopGame, failGame;
    RelativeLayout rlHome, rlPlay, rlReset, rlFailHome, rlFailReset;
    public static TextView tvScore, tvBestScore;
    public static int speed = 0, bestScore, type = 0;
    public static String colorSnake;
    public static DatabaseHelper db;
    private EasyModeModel emm;
    private MediumModeModel mmm;
    private HardModeModel hmm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constant.SCREEN_WIDTH = dm.widthPixels;
        Constant.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_play_game);

        db = new DatabaseHelper(this);
        if (db.tableEasy() && db.tableMedium() && db.tableHard())
        {
            db.addEasyScore(0);
            db.addMediumScore(0);
            db.addHardScore(0);
        }

        Intent t = getIntent();
        speed = t.getIntExtra("spd", -1);
        type = t.getIntExtra("sql", -1);
        if (type == 1)
        {
            emm = db.getEasyScore(1);
            bestScore = emm.getBestScoreEasy();
        }
        else if (type == 2)
        {
            mmm = db.getMediumScore(1);
            bestScore = mmm.getBestScoreMedium();
        }
        else
        {
            hmm = db.getHardScore(1);
            bestScore = hmm.getBestScoreHard();
        }

        stopGame = new Dialog(this);
        stopGame.setContentView(R.layout.stop_game);
        stopGame.setCanceledOnTouchOutside(false);

        failGame = new Dialog(this);
        failGame.setContentView(R.layout.fail_game);
        failGame.setCanceledOnTouchOutside(false);

        gpv = findViewById(R.id.gamePlayView);
        ivPause = findViewById(R.id.pause);
        rlHome = stopGame.findViewById(R.id.home);
        rlPlay = stopGame.findViewById(R.id.play);
        rlReset = stopGame.findViewById(R.id.reset);
        rlFailHome = failGame.findViewById(R.id.fail_home);
        rlFailReset = failGame.findViewById(R.id.fail_reset);
        tvScore = findViewById(R.id.txtScore);
        tvBestScore = findViewById(R.id.txtBestScore);

        ivPause.setOnClickListener(new MyEvent());
        rlHome.setOnClickListener(new MyEvent());
        rlPlay.setOnClickListener(new MyEvent());
        rlReset.setOnClickListener(new MyEvent());
        rlFailHome.setOnClickListener(new MyEvent());
        rlFailReset.setOnClickListener(new MyEvent());
        tvBestScore.setText(bestScore+"");
    }

    private class MyEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if (view == ivPause)
            {
                gpv.stopGame();
                stopGame.show();
            }
            if (view == rlHome || view == rlFailHome)
            {
                gpv.reset();
                finish();
            }
            if (view == rlPlay) { stopGame.dismiss(); }
            if (view == rlReset)
            {
                gpv.reset();
                tvScore.setText("0");
                stopGame.dismiss();
            }
            if (view == rlFailReset)
            {
                gpv.reset();
                tvScore.setText("0");
                failGame.dismiss();
            }
        }
    }

//    public byte[] iv_To_Byte(int img)
//    {
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), img);
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteA = stream.toByteArray();
//
//        return byteA;
//    }
}