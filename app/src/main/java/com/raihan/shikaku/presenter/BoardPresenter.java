package com.raihan.shikaku.presenter;

import android.graphics.PointF;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.raihan.shikaku.model.BoardModel;
import com.raihan.shikaku.model.Level;
import com.raihan.shikaku.model.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardPresenter implements BoardContract.Presenter {

    private BoardContract.View view;
    private BoardContract.Model model;

    private HashMap<String, Integer> hm;
    private HashMap<String, Integer> hm1;

    private boolean isUp;

    private int gridSize;
    private Level lvl;


    private  final ArrayList<Rectangle> rectList = new ArrayList<>();// menyimpan rectangle yang dibuat user


    public BoardPresenter(BoardContract.View view) {
        this.view = view;
        this.model = new BoardModel();
    }

    @Override
    public void onTouch(boolean isUp, PointF start, float e1, float e2) {
        if(start.x!=e1 && start.y!=e2){
            this.hm1= this.model.getRectangleCoordinates(start, e1, e2);
            this.isUp= isUp;
            onProcessSelectedCell();
        }
    }

    @Override
    public void sendGridSize(int gridSize) {
        this.model.getGridSize(gridSize);
        this.gridSize= gridSize;
        lvl= new Level(gridSize);
    }

    @Override
    public void onProcessDrawingBoard() {
        this.hm= this.model.calculateBoard();
        view.getCellSize(hm.get("cellSize"));

        //        untuk menggambar grid
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
//                  agar grid berada ditengah image view
                int left = hm.get("offsetX") + j * hm.get("cellSize");
                int top = hm.get("offsetY") + i * hm.get("cellSize");
                int right = hm.get("offsetX") + (j + 1) * hm.get("cellSize");
                int bottom = hm.get("offsetY") + (i + 1) * hm.get("cellSize");


//                  gambar border sel
//                mCanvas.drawRect(left, top, right, bottom, paint);
//                Log.d("TAG", "onProcessDrawingBoard: "+left+", "+top+", "+right+", "+bottom);
                this.view.drawBoard(left, top, right, bottom);

                lvl.getCellNumbers();
                int angka = lvl.getCellNumbers()[i][j];


                if (angka != 0) {// tampilkan angka selain 0
                    String angkaStr = Integer.toString(angka);

                    this.view.drawNumbers(angka, left, top, right, bottom);
//                    mCanvas.drawText(angkaStr, textX, textY, textPaint);
                }
            }
        }
    }

    @Override
    public void onProcessSelectedCell() {
        int startRow= hm1.get("startRow");
        int startCol= hm1.get("startCol");
        int endRow = hm1.get("endRow");
        int endCol = hm1.get("endCol");

        int left = rectCoordinateRow(true, 10, startRow, endRow);
        int top = rectCoordinateCol(true, 10, startCol, endCol);
        int right = rectCoordinateRow(false, 10, startRow, endRow);
        int bottom = rectCoordinateCol(false, 10, startCol, endCol);

        int leftCheck = rectCoordinateRow(true, 0, startRow, endRow);
        int topCheck = rectCoordinateCol(true, 0, startCol, endCol);
        int rightCheck = rectCoordinateRow(false, 0, startRow, endRow);
        int bottomCheck = rectCoordinateCol(false, 0, startCol, endCol);

        int length= rightCheck-leftCheck;
        length/=hm.get("cellSize");

        int width= bottomCheck-topCheck;
        width/=hm.get("cellSize");

        length*= width;

//        menghitung dalam sebuah rectangle ada berapa sel, bila sel<1 jangan buat rectangle
        if(length>1){
            if(isUp){
                Rectangle rect= new Rectangle(left, top, right, bottom);
                rectList.add(rect);
                this.view.setSelectedCell(rectList);
            }else{
                this.view.drawOnMoveSelectedCell(left, top, right, bottom);
                this.view.cellCounter(length);
            }
        }else if(length==1){
            this.view.cellCounter(0);
        }
    }

    @Override
    public void sendWidth(int width) {
        this.model.getWidth(width);
    }

    @Override
    public void sendHeight(int height) {
        this.model.getHeight(height);
    }

    private int rectCoordinateCol(boolean isStart, int add, int startCol, int endCol) {
        if(isStart){
            return hm.get("offsetY") + add + Math.min(startCol, endCol) * hm.get("cellSize");
        }else{
            return hm.get("offsetY") - add + (Math.max(startCol, endCol) + 1) * hm.get("cellSize");
        }
    }

    private int rectCoordinateRow(boolean isStart, int add, int startRow, int endRow) {
        if(isStart){
            return hm.get("offsetX") + add + Math.min(startRow, endRow) * hm.get("cellSize");
        }else{
            return hm.get("offsetX") - add + (Math.max(startRow, endRow) + 1) * hm.get("cellSize");
        }
    }
}

