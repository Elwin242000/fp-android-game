package com.sinhvien.snakepreygame.model;

public class EasyModeModel
{
    private int id;
    private int bestScoreEasy;

    public EasyModeModel(int bestScoreEasy) { this.bestScoreEasy = bestScoreEasy; }
    public EasyModeModel(int id, int bestScoreEasy) { this.id = id; this.bestScoreEasy = bestScoreEasy; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBestScoreEasy() { return bestScoreEasy; }
    public void setBestScoreEasy(int bestScoreEasy) { this.bestScoreEasy = bestScoreEasy; }
}
