package com.raihan.shikaku.model;

import android.graphics.PointF;
import android.util.Log;

import com.raihan.shikaku.presenter.BoardContract;

import java.util.HashMap;

public class BoardModel implements BoardContract.Model {
    private int gridSize;// untuk memilih size grid(difficulty) sesuai pilihan pengguna (easy= 5x5, medium= 10x10, hard= 15x15)

    //    untuk pengukuran
    private int cellSize;
    private int minSize;
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;

    private PointF start;// digunakan untuk simpan titik awal sentuhan



    @Override
    public void getGridSize(int gridSize) {
        this.gridSize= gridSize;
    }

    @Override
    public HashMap<String, Integer> getRectangleCoordinates(PointF start, float e1, float e2) {

        HashMap<String, Integer> hm= new HashMap<>();

        int startRow = coordinateConvert(start.y);
        int startCol = coordinateConvert(start.x);
        int endRow = coordinateConvert(e2);
        int endCol = coordinateConvert(e1);

        startRow= checkMax(startRow);
        startCol= checkMax(startCol);
        endRow= checkMax(endRow);
        endCol= checkMax(endCol);

        startRow= checkMin(startRow);
        startCol= checkMin(startCol);
        endRow= checkMin(endRow);
        endCol= checkMin(endCol);

        hm.put("startRow", startRow);
        hm.put("startCol", startCol);
        hm.put("endRow", endRow);
        hm.put("endCol", endCol);

        return hm;
    }

    @Override
    public HashMap<String, Integer> calculateBoard() {
        HashMap<String, Integer> hm= new HashMap<>();

        this.minSize = Math.min(width, height); // ukuran minimum antara lebar dan tinggi dari permukaan image view di xml
        this.cellSize = minSize / gridSize; // ukuran sel
        this.offsetX = (width - minSize) / 2; // jarak horizontal dari tepi ImageView ke grid
        this.offsetY = (height - minSize) / 2; // jarak vertikal dari tepi ImageView ke grid

        hm.put("offsetX", offsetX);
        hm.put("offsetY", offsetY);
        hm.put("cellSize", cellSize);

        Log.d("TAG", "calculateBoard: offx "+offsetX);
        Log.d("TAG", "calculateBoard: offy "+offsetY);

        return hm;


    }

    @Override
    public void getWidth(int width) {
        this.width= width;
    }

    @Override
    public void getHeight(int height) {
        this.height= height;

    }


    private int coordinateConvert(float x) {
        return (int) (x / cellSize);
    }

    private int checkMin(int input) {
        if(input<0){
            return 0;
        }else{
            return input;
        }
    }

    private int checkMax(int input) {
        if((gridSize-1)-input<0){
            return gridSize-1;
        }else{
            return input;
        }
    }



}

