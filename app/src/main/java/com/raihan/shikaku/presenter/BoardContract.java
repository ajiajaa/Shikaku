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
        void getWrongRect(ArrayList<Rectangle> wrongRect);
        void checkerResult(boolean isValid);
        void sendStopwatch(String time);
        void getSecond(int seconds);
        void vibrating();
        void notPause();

    }

    interface Presenter {
        void onTouch(boolean isUp, PointF start, float e1, float e2);
        void sendGridSize(Context context, int gridSize, int level);
        void onProcessDrawingBoard(boolean isReset);
        void onProcessSelectedCell();
        void sendWrongRect(ArrayList<Rectangle> wrongRect);
        void sendWidth(int width);
        void sendHeight(int height);
        void newRectList();
        void startStopwatch();
        void stopStopwatch();
        void resumeStopwatch();
        void pauseStopwatch();
        void checker();
    }

    interface Model {
        void getGridSize(int gridSize);
        HashMap<String, Integer> getRectangleCoordinates(PointF start, float e1, float e2);
        int calculateBoard();
        void getWidth(int width);
        void getHeight(int height);
    }
}

