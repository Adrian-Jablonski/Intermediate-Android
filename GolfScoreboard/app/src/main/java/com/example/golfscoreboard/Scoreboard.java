package com.example.golfscoreboard;

public class Scoreboard {
    private int[] scoreboard = new int[18];

    public int getHoleScore(int hole) {
        return scoreboard[hole - 1];
    }

    public void decreaseScore(int hole) {
        this.scoreboard[hole -1] -= 1;
    }
    public void increaseScore(int hole) {
        this.scoreboard[hole -1] += 1;
    }

    public void resetScore() {
        scoreboard = new int[18];
    }
}
