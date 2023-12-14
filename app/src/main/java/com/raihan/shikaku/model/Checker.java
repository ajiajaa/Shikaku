package com.raihan.shikaku.model;

import android.util.Log;

import java.util.ArrayList;

public class Checker {
    private  ArrayList<Rectangle> rectList;
    private Level lvl;
    private int gridSize;

    public Checker(ArrayList<Rectangle> rectList, Level lvl, int gridSize){
        this.rectList= rectList;
        this.lvl= lvl;
        this.gridSize= gridSize;
    }
    public boolean validateBoard(){
        int totalFilledCells = 0;
        for (Rectangle rect : rectList) {
            // Hitung jumlah sel dalam setiap rectangle
            totalFilledCells += rect.getTotalCell();
        }
        boolean isWrong= false;
        if(totalFilledCells==this.gridSize*this.gridSize){
            Log.d("TAG", "validateBoard: full");
            for (int r = 0; r < rectList.size(); r++) {
                int numberCount= 0;
                int iLength= Math.max(rectList.get(r).getStartRow(), rectList.get(r).getEndRow());
                for (int i= Math.min(rectList.get(r).getStartRow(), rectList.get(r).getEndRow()); i <= iLength; i++) {
                    int jLength= Math.max(rectList.get(r).getStartCol(), rectList.get(r).getEndCol());
                    for (int j = Math.min(rectList.get(r).getStartCol(), rectList.get(r).getEndCol()); j <= jLength; j++) {
                        if (lvl.getCellNumbers()[i][j] != 0) {
                            if (lvl.getCellNumbers()[i][j] != rectList.get(r).getTotalCell()) {
                                isWrong = true; // Tandai kesalahan
                            }
                            numberCount++;
                            Log.d("TAG", "rectangle ["+r+"] : index= "+i+", "+j+" value= "+lvl.getCellNumbers()[i][j]+", "+rectList.get(r).getTotalCell());
                        }
                    }
                    if (isWrong) {
                        break;
                    }
                }
                Log.d("TAG", "numberCount: ["+r+"] "+numberCount);
                if(numberCount!=1){
                    isWrong= true;
                }
                if (isWrong) {
                    break;
                }
            }

// Tampilkan pesan jika tidak ada kesalahan
            if (!isWrong) {
                Log.d("TAG", "validateBoard: benar");
            }else{
                Log.d("TAG", "validateBoard: salah");
            }
        }else{
            Log.d("TAG", "papan tidak terisi penuh");
            isWrong= true;
        }
        return isWrong;
    }
}
