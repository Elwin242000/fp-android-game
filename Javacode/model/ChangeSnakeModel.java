package com.sinhvien.snakepreygame.model;

import java.sql.Blob;

public class ChangeSnakeModel
{
    private int id;
    private String iv_snake;

    public ChangeSnakeModel(String iv_snake) { this.iv_snake = iv_snake; }
    public ChangeSnakeModel(int id, String iv_snake) { this.id = id; this.iv_snake = iv_snake; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIv_snake() { return iv_snake; }
    public void setIv_snake(String iv_snake) { this.iv_snake = iv_snake; }
}
