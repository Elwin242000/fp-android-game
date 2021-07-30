package com.sinhvien.snakepreygame.model;

public class MediumModeModel
{
    private int id;
    private int bestScoreMedium;

    public MediumModeModel(int bestScoreMedium) { this.bestScoreMedium = bestScoreMedium; }
    public MediumModeModel(int id, int bestScoreMedium) { this.id = id; this.bestScoreMedium = bestScoreMedium; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBestScoreMedium() { return bestScoreMedium; }
    public void setBestScoreMedium(int bestScoreMedium) { this.bestScoreMedium = bestScoreMedium; }
}
