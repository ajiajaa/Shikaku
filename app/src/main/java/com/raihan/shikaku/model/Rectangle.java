package com.raihan.shikaku.model;

import android.graphics.Rect;

public class Rectangle {
    private Rect rect;

    public Rectangle(Rect rect) {
        this.rect = rect;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
