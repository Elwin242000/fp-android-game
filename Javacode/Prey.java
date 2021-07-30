package com.sinhvien.snakepreygame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Prey
{
    private Bitmap bm;
    private int x, y;
    private Rect r;

    public Prey(Bitmap bm, int x, int y)
    {
        this.bm = bm;
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bm, x, y, null);
    }

    public void reset(int ax, int ay)
    {
        this.x = ax;
        this.y = ay;
    }

    public Bitmap getBm() { return bm; }
    public void setBm(Bitmap bm) { this.bm = bm; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public Rect getR()
    {
        return new Rect(this.x,
                this.y,
                this.x + GamePlayView.sizeOfMap,
                this.y + GamePlayView.sizeOfMap);
    }
    public void setR(Rect r) { this.r = r; }
}

