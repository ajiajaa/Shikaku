package com.raihan.shikaku.model;

import android.graphics.PointF;
import android.util.Log;

import com.raihan.shikaku.presenter.BoardContract;

import java.util.HashMap;

public class BoardModel implements BoardContract.Model {
    private int gridSize;// untuk memilih size grid(difficulty) sesuai pilihan pengguna (easy= 5x5, medium= 10x10, hard= 15x15)

    //    untuk pengukuran
    private int cellSize;
    private int width;
    private int height;

    //mendapatkan gridSize dari presenter
    @Override
    public void getGridSize(int gridSize) {
        this.gridSize= gridSize;
    }

    //mendapatkan koordinat rectangle untuk dikonversikan kebentuk papan 2D
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
    //untuk kalkulasi papan
    @Override
    public int calculateBoard() {
        int minSize = Math.min(width, height); // ukuran minimum antara lebar dan tinggi dari permukaan image view di xml
        this.cellSize = minSize / gridSize; // ukuran sel

        return this.cellSize;
    }

    @Override
    public void getWidth(int width) {
        this.width= width;
    }

    @Override
    public void getHeight(int height) {
        this.height= height;

    }

    //konversi kooordinat ke bidang papan.
    private int coordinateConvert(float x) {
        return (int) (x / cellSize);
    }

    //untuk check apakah coordinate yang diberikan melebihi ukuran papan atau tidak, jika ya ganti dengan batas ukuran papan

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

