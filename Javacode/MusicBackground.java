package com.sinhvien.snakepreygame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicBackground extends Service
{
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    public void onCreate()
    {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_background);
        mediaPlayer.setLooping(true);
    }

    public void onStart(Intent intent, int startId) { mediaPlayer.start(); }
    public void onDestroy() { mediaPlayer.stop(); }
}
