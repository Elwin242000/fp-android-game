    package com.sinhvien.snakepreygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.sinhvien.snakepreygame.database.DatabaseHelper;
import com.sinhvien.snakepreygame.model.ChangeSnakeModel;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GamePlayView extends View
{
    public static int sizeOfMap = 75 * Constant.SCREEN_WIDTH / 1080;
    private int w = 14, h = 23;
    private ArrayList<Grass> arrG = new ArrayList<>();
    private Bitmap bmGrass, bmSnake, bmPrey;
    private Snake snake;
    private Prey prey;
    private boolean move = false;
    private float mx, my;
    private Handler handler;
    private Runnable r;
    public static boolean isPlaying;
    public static int score = 0, bestScore = PlayGameActivity.bestScore, snakeColor = R.drawable.snake;
    public String colorS;
    private int soundEat, soundLose;
    private SoundPool soundPool;
    public boolean isSound;
    private ChangeSnakeModel csm;
    private DatabaseHelper db;

    public GamePlayView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        db = new DatabaseHelper(context);
        csm = db.getSnake(1);
        colorS = csm.getIv_snake();

        switch (colorS)
        {
            case "blue": snakeColor = R.drawable.snake; break;
            case "black": snakeColor = R.drawable.snake1; break;
            case "orange": snakeColor = R.drawable.snake2; break;
            case "gray": snakeColor = R.drawable.snake3; break;
            default: snakeColor = R.drawable.snake; break;
        }

        bmGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.img_grass);
        bmGrass = Bitmap.createScaledBitmap(bmGrass, sizeOfMap, sizeOfMap, true);

        bmSnake = BitmapFactory.decodeResource(this.getResources(), snakeColor);
        bmSnake = Bitmap.createScaledBitmap(bmSnake, 14 * sizeOfMap, sizeOfMap, true);

        bmPrey = BitmapFactory.decodeResource(this.getResources(), R.drawable.img_apple);
        bmPrey = Bitmap.createScaledBitmap(bmPrey, sizeOfMap, sizeOfMap, true);

        for (int i = 0; i < h; i++)
        {
            for (int j = 0; j < w; j++)
            {
                arrG.add(new Grass(bmGrass,
                        j * bmGrass.getWidth() + Constant.SCREEN_WIDTH / 2 - (w / 2) * bmGrass.getWidth(),
                        i * bmGrass.getHeight() + 50 * Constant.SCREEN_HEIGHT / 1920,
                        bmGrass.getWidth(),
                        bmGrass.getHeight()));
            }
        }

        snake = new Snake(bmSnake, arrG.get(146).getX(), arrG.get(146).getY(), 4);
        prey = new Prey(bmPrey, arrG.get(randomApple()[0]).getX(), arrG.get(randomApple()[1]).getY());

        handler = new Handler();
        r = new Runnable()
        {
            @Override
            public void run() { invalidate(); }
        };

        if(Build.VERSION.SDK_INT>=21)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            this.soundPool = builder.build();
        }
        else
        {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status)
            {
                isSound = true;
            }
        });

        soundEat = this.soundPool.load(context, R.raw.eat, 1);
        soundLose = this.soundPool.load(context, R.raw.lose, 1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int a = event.getActionMasked();
        switch (a)
        {
            case MotionEvent.ACTION_MOVE:
            {
                if (move == false)
                {
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                }
                else
                {
                    if (mx - event.getX() > 100 * Constant.SCREEN_WIDTH/1080 && !snake.isMove_right())
                    {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_left(true);
                        isPlaying = true;
                    }
                    else if (event.getX() - mx > 100 * Constant.SCREEN_WIDTH/1080 && !snake.isMove_left())
                    {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_right(true);
                        isPlaying = true;
                    }
                    else if (my - event.getY() > 100 * Constant.SCREEN_WIDTH/1080 && !snake.isMove_bottom())
                    {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_top(true);
                        isPlaying = true;
                    }
                    else if (event.getY() - my > 100 * Constant.SCREEN_WIDTH/1080 && !snake.isMove_top())
                    {
                        mx = event.getX();
                        my = event.getY();
                        snake.setMove_bottom(true);
                        isPlaying = true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                mx = 0;
                my = 0;
                move = false;
                break;
            }
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        canvas.drawColor(0xFF065700);
        for(int i = 0; i < arrG.size(); i++)
        {
            canvas.drawBitmap(arrG.get(i).getBm(), arrG.get(i).getX(), arrG.get(i).getY(), null);
        }

        if (isPlaying)
        {
            snake.update();
            if(snake.getArrPart().get(0).getX() < this.arrG.get(0).getX()
                    ||snake.getArrPart().get(0).getY() < this.arrG.get(0).getY()
                    ||snake.getArrPart().get(0).getY() + sizeOfMap > this.arrG.get(this.arrG.size()-1).getY() + sizeOfMap
                    ||snake.getArrPart().get(0).getX() + sizeOfMap > this.arrG.get(this.arrG.size()-1).getX() + sizeOfMap)
            {
                gameOver();
            }
            for (int i = 1; i < snake.getArrPart().size(); i++)
            {
                if (snake.getArrPart().get(0).getrBody().intersect(snake.getArrPart().get(i).getrBody()))
                {
                    gameOver();
                }
            }
        }

        snake.draw(canvas);
        prey.draw(canvas);

        if(snake.getArrPart().get(0).getrBody().intersect(prey.getR()))
        {
            if(isSound)
            {
                int streamId = this.soundPool.play(this.soundEat, (float)0.5, (float)0.5, 1, 0, 2f);
            }
            prey.reset(arrG.get(randomApple()[0]).getX(), arrG.get(randomApple()[1]).getY());
            snake.addLength();
            score++;
            PlayGameActivity.tvScore.setText(score+"");
            if (PlayGameActivity.bestScore < score)
            {
                if (PlayGameActivity.type == 1)
                {
                    PlayGameActivity.db.updateEasyScore(score);
                }
                else if (PlayGameActivity.type == 2)
                {
                    PlayGameActivity.db.updateMediumScore(score);
                }
                else
                {
                    PlayGameActivity.db.updateHardScore(score);
                }
                PlayGameActivity.bestScore = score;
                PlayGameActivity.tvBestScore.setText(PlayGameActivity.bestScore+"");
            }
        }
        handler.postDelayed(r, PlayGameActivity.speed);
    }

    public int[] randomApple()
    {
        int []xy = new int[2];
        Random r = new Random();
        xy[0] = r.nextInt(arrG.size() - 1);
        xy[1] = r.nextInt(arrG.size() - 1);
        Rect rect = new Rect(arrG.get(xy[0]).getX(),
                arrG.get(xy[1]).getY(),
                arrG.get(xy[0]).getX() + sizeOfMap,
                arrG.get(xy[1]).getY() + sizeOfMap);
        boolean check = true;
        while (check)
        {
            check = false;
            for (int i = 0; i < snake.getArrPart().size(); i++)
            {
                if (rect.intersect(snake.getArrPart().get(i).getrBody()))
                {
                    check = true;
                    xy[0] = r.nextInt(arrG.size() - 1);
                    xy[1] = r.nextInt(arrG.size() - 1);
                    rect = new Rect(arrG.get(xy[0]).getX(),
                            arrG.get(xy[1]).getY(),
                            arrG.get(xy[0]).getX() + sizeOfMap,
                            arrG.get(xy[1]).getY() + sizeOfMap);
                }
            }
        }
        return xy;
    }

    public void stopGame()
    {
        isPlaying = false;
    }

    public void gameOver()
    {
        isPlaying = false;
        PlayGameActivity.failGame.show();
        if(isSound)
        {
            int streamId = this.soundPool.play(this.soundLose, (float)0.5, (float)0.5, 1, 0, 1f);
        }
    }

    public void reset()
    {
        for(int i = 0; i < h; i++)
        {
            for (int j = 0; j < w; j++)
            {
                arrG.add(new Grass(bmGrass,
                        j * bmGrass.getWidth() + Constant.SCREEN_WIDTH / 2 - (w / 2) * bmGrass.getWidth(),
                        i * bmGrass.getHeight() + 50 * Constant.SCREEN_HEIGHT / 1920,
                        bmGrass.getWidth(),
                        bmGrass.getHeight()));
            }
        }
        snake = new Snake(bmSnake, arrG.get(146).getX(), arrG.get(146).getY(), 4);
        prey = new Prey(bmPrey, arrG.get(randomApple()[0]).getX(), arrG.get(randomApple()[1]).getY());
        bestScore = PlayGameActivity.bestScore;
        score = 0;
    }
}
