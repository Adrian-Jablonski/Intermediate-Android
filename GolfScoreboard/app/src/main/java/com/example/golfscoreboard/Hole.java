package com.example.golfscoreboard;

public class Hole {
    private String label;
    private int strokeCount;

    public Hole(String label, int strokeCount) {
        this.label = label;
        this.strokeCount = strokeCount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStrokeCount() {
        return strokeCount;
    }

    public void setStrokeCount(int strokeCount) {
        this.strokeCount = strokeCount;
    }
}
