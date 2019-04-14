package com.example.golfscoreboard;

public class Scoreboard {
    private int[] scoreboard = new int[18];

    public String getHoleScore(int hole) {
        return scoreboard[hole] + "";
    }

    public void setHoleScore(int hole, String score) {
        this.scoreboard[hole] = Integer.parseInt(score);
    }

    public void decreaseScore(int hole) {
        this.scoreboard[hole] -= 1;
    }
    public void increaseScore(int hole) {
        this.scoreboard[hole] += 1;
    }

    public void resetScore() {
        scoreboard = new int[18];
    }
}
