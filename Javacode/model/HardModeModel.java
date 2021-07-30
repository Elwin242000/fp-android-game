package com.sinhvien.snakepreygame.model;

public class HardModeModel
{
    private int id;
    private int bestScoreHard;

    public HardModeModel(int bestScoreMedium) { this.bestScoreHard = bestScoreMedium; }
    public HardModeModel(int id, int bestScoreMedium) { this.id = id; this.bestScoreHard = bestScoreMedium; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBestScoreHard() { return bestScoreHard; }
    public void setBestScoreHard(int bestScoreHard) { this.bestScoreHard = bestScoreHard; }
}
