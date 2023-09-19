package com.raihan.shikaku.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.res.ResourcesCompat;

import com.raihan.shikaku.R;
import com.raihan.shikaku.presenter.BoardContract;
import com.raihan.shikaku.presenter.BoardPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
//        Log.d("TAG", "start: "+start.x+", "+start.y);
//        Log.d("TAG", "end "+e.getX()+" "+e.getY());

        HashMap<String, Integer> hm= new HashMap<>();

        int startRow = coordinateConvert(start.x);
        int startCol = coordinateConvert(start.y);
        int endRow = coordinateConvert(e1);
        int endCol = coordinateConvert(e2);

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

        return hm;


    }

    @Override
    public void getWidth(int width) {
        this.width= width;
        Log.d("TAG", "getWidth: "+width);
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

