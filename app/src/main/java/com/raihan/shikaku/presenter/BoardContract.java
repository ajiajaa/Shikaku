package com.raihan.shikaku.presenter;

import android.content.Context;
import android.graphics.PointF;

import com.raihan.shikaku.model.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface BoardContract {

    interface View {
        void drawBoard(int left, int top, int right, int bottom);
        void drawNumbers(int angka, int left, int top, int right, int bottom);
        void setSelectedCell(List<Rectangle> rectList);
        void drawOnMoveSelectedCell(int left, int top, int right, int bottom);
        void cellCounter(int ctr);
        void overlapChecker(boolean isOverlap);
        void onToastResult(boolean isValid);
    }

    interface Presenter {
        void onTouch(boolean isUp, PointF start, float e1, float e2);
        void sendGridSize(Context context, int gridSize, int level);
        void onProcessDrawingBoard();
        void onProcessSelectedCell();
        void sendWidth(int width);
        void sendHeight(int height);
        void sendRectangles();
    }

    interface Model {
        void getGridSize(int gridSize);
        HashMap<String, Integer> getRectangleCoordinates(PointF start, float e1, float e2);
        HashMap<String, Integer> calculateBoard();
        void getWidth(int width);
        void getHeight(int height);
    }
}

