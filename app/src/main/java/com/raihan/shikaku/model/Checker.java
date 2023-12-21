package com.raihan.shikaku.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        Map<Angka, Rectangle> pemetaan = new HashMap<>();
        ArrayList<Angka> angkaList= lvl.getCellNumbers();
        int count = 1;
        for (Rectangle r : rectList) {
            for (Angka a : lvl.getCellNumbers()) {
                if (a.getRow() >= r.getStartRow() && a.getRow() <= r.getEndRow()
                        && a.getColumn() >= r.getStartCol() && a.getColumn() <= r.getEndCol()
                        && a.getValue() == r.getTotalCell()) {
                    pemetaan.put(a, r);
                    System.out.println("rect ke-"+count+": "+r.getTotalCell()+", "+a.getValue());
                    count++;
                    break;
                }
            }
        }

        if(pemetaan.size()!=angkaList.size()) {
            System.out.println("Pemetaan tidak satu ke satu");
            return false;
        }
        System.out.println("Pemetaan satu ke satu");
        return true;
    }
}
