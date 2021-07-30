package com.sinhvien.snakepreygame.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sinhvien.snakepreygame.ChangeSnakeActivity;
import com.sinhvien.snakepreygame.model.ChangeSnakeModel;
import com.sinhvien.snakepreygame.model.EasyModeModel;
import com.sinhvien.snakepreygame.model.HardModeModel;
import com.sinhvien.snakepreygame.model.MediumModeModel;

import java.sql.Blob;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static String DATABASE_NAME = "best_score_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EASY = "easymode";
    private static final String ID_EASY = "ideasymode";
    private static final String SCORE_EASY = "scoreeasymode";

    private static final String TABLE_MEDIUM = "mediummode";
    private static final String ID_MEDIUM = "idmediummode";
    private static final String SCORE_MEDIUM = "scoremediummode";

    private static final String TABLE_HARD = "hardmode";
    private static final String ID_HARD = "idhardmode";
    private static final String SCORE_HARD = "scorehardmode";

    private static final String TABLE_SNAKE = "changesnake";
    private static final String ID_SNAKE = "idsnake";
    private static final String COLOR_SNAKE = "typesnake";

    private static final String CREATE_TABLE_EASY_MODE = "CREATE TABLE " + TABLE_EASY + "("
            + ID_EASY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SCORE_EASY + " INTEGER)";

    private static final String CREATE_TABLE_MEDIUM_MODE = "CREATE TABLE " + TABLE_MEDIUM + "("
            + ID_MEDIUM + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SCORE_MEDIUM + " INTEGER)";

    private static final String CREATE_TABLE_HARD_MODE = "CREATE TABLE " + TABLE_HARD + "("
            + ID_HARD + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SCORE_HARD + " INTEGER)";

    private static final String CREATE_TABLE_CHANGE_SNAKE = "CREATE TABLE " + TABLE_SNAKE + "("
            + ID_SNAKE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLOR_SNAKE + " TEXT)";

    public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_EASY_MODE);
        db.execSQL(CREATE_TABLE_MEDIUM_MODE);
        db.execSQL(CREATE_TABLE_HARD_MODE);
        db.execSQL(CREATE_TABLE_CHANGE_SNAKE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_EASY + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_MEDIUM + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_HARD + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SNAKE + "'");

        onCreate(db);
    }

//Check empty
    public boolean tableEasy()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int NoOfRows = (int) DatabaseUtils.queryNumEntries(db,TABLE_EASY);

        if (NoOfRows == 0){ return true; }
        else { return false; }
    }
    public boolean tableMedium()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int NoOfRows = (int) DatabaseUtils.queryNumEntries(db,TABLE_MEDIUM);

        if (NoOfRows == 0){ return true; }
        else { return false; }
    }
    public boolean tableHard()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int NoOfRows = (int) DatabaseUtils.queryNumEntries(db,TABLE_HARD);

        if (NoOfRows == 0){ return true; }
        else { return false; }
    }
    public boolean tableSnake()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int NoOfRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_SNAKE);

        if (NoOfRows == 0) { return true; }
        else { return false;}
    }

//add data
    public void addEasyScore(int bestScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(SCORE_EASY, bestScore);

        db.insert(TABLE_EASY, null, cv);
    }
    public void addMediumScore(int bestScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(SCORE_MEDIUM, bestScore);

        db.insert(TABLE_MEDIUM, null, cv);
    }
    public void addHardScore(int bestScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(SCORE_HARD, bestScore);

        db.insert(TABLE_HARD, null, cv);
    }

    public void addSnake(String iv_snake)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLOR_SNAKE, iv_snake);

        db.insert(TABLE_SNAKE, null, cv);
    }

//get data
    public EasyModeModel getEasyScore(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EASY, null, ID_EASY + " = ?", new String[] { String.valueOf(id) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        EasyModeModel easyScore = new EasyModeModel(cursor.getInt(0), cursor.getInt(1));
        return easyScore;
    }
    public MediumModeModel getMediumScore(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEDIUM, null, ID_MEDIUM + " = ?", new String[] { String.valueOf(id) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        MediumModeModel mediumScore = new MediumModeModel(cursor.getInt(0), cursor.getInt(1));
        return mediumScore;
    }
    public HardModeModel getHardScore(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HARD, null, ID_HARD + " = ?", new String[] { String.valueOf(id) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        HardModeModel hardScore = new HardModeModel(cursor.getInt(0), cursor.getInt(1));
        return hardScore;
    }

    public ChangeSnakeModel getSnake(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SNAKE, null, ID_SNAKE + " = ?", new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        ChangeSnakeModel snakeColor = new ChangeSnakeModel(cursor.getInt(0), cursor.getString(1));
        return snakeColor;
    }

//update data
    public void updateEasyScore(int bestScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCORE_EASY, bestScore);

        db.update(TABLE_EASY, values, ID_EASY + " = 1", null);
    }
    public void updateMediumScore(int bestScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCORE_MEDIUM, bestScore);

        db.update(TABLE_MEDIUM, values, ID_MEDIUM + " = 1", null);
    }
    public void updateHardScore(int bestScore)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SCORE_HARD, bestScore);

        db.update(TABLE_HARD, values, ID_HARD + " = 1", null);
    }
    public void updateSnake(String iv_snake)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLOR_SNAKE, iv_snake);

        db.update(TABLE_SNAKE, values, ID_SNAKE + " = 1", null);
    }
}
