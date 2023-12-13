package com.raihan.shikaku.model;

import android.util.Log;
import android.widget.Toast;

import com.raihan.shikaku.presenter.BoardPresenter;

import java.util.ArrayList;

public class Checker {
    private  ArrayList<Rectangle> rectList;
    private Level lvl;
    private int gridSize;
    private BoardPresenter presenter;

    public Checker(BoardPresenter presenter, ArrayList<Rectangle> rectList, Level lvl, int gridSize){
        this.presenter= presenter;
        this.rectList= rectList;
        this.lvl= lvl;
        this.gridSize= gridSize;
    }
    public void validateBoard(){
        int totalFilledCells = 0;
        for (Rectangle rect : rectList) {
            // Hitung jumlah sel dalam setiap rectangle
            totalFilledCells += rect.getTotalCell();
        }
        boolean isWrong= false;
        if(totalFilledCells==this.gridSize*this.gridSize){
            Log.d("TAG", "validateBoard: full");
            isWrong = false; // Set false untuk setiap rectangle
            for (int r = 0; r < rectList.size(); r++) {
                for (int i = rectList.get(r).getStartRow(); i <= rectList.get(r).getEndRow(); i++) {
                    for (int j = rectList.get(r).getStartCol(); j <= rectList.get(r).getEndCol(); j++) {
                        if (lvl.getCellNumbers()[i][j] != 0) {
                            if (lvl.getCellNumbers()[i][j] != rectList.get(r).getTotalCell()) {
                                isWrong = true; // Tandai kesalahan
                            }
                            Log.d("TAG", "rectangle ["+r+"] : index= "+i+", "+j+" value= "+lvl.getCellNumbers()[i][j]+", "+rectList.get(r).getTotalCell());
                        }
                    }
                    if (isWrong) {
                        break;
                    }
                }
            }

// Tampilkan pesan jika tidak ada kesalahan
            if (!isWrong) {
                Log.d("TAG", "validateBoard: benar");
                presenter.sendResult(true);
            }else{
                Log.d("TAG", "validateBoard: salah");
                presenter.sendResult(false);
            }
        }else{
            Log.d("TAG", "validateBoard: "+totalFilledCells);
        }
    }
}
