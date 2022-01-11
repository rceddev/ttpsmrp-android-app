package com.tt.ttpsmrpapp.usecases.monitoring.utils;

public class DataString {
    private float x;
    private float y;

    public DataString(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public DataString(float x) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
