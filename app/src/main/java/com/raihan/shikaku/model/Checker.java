package com.raihan.shikaku.model;

import android.util.Log;

import com.raihan.shikaku.presenter.BoardPresenter;

import java.util.ArrayList;

public class Checker {
    private  ArrayList<Rectangle> rectList;
    private Level lvl;
    private BoardPresenter presenter;

    public Checker(BoardPresenter presenter, ArrayList<Rectangle> rectList, Level lvl){
        this.presenter = presenter;
        this.rectList= rectList;
        this.lvl= lvl;
    }
    public boolean validateBoard(){
        ArrayList<Angka> angkaList= lvl.getCellNumbers();
        ArrayList<Rectangle> correctRect= new ArrayList<>();
        ArrayList<Rectangle> wrongRect= new ArrayList<>(rectList);
        for (Rectangle r : rectList) {
            Angka tempAngka= null;
            int angkactr= 0;
            for (Angka a : lvl.getCellNumbers()) {
                if (a.getRow() >= r.getStartRow() && a.getRow() <= r.getEndRow()
                        && a.getColumn() >= r.getStartCol() && a.getColumn() <= r.getEndCol()) {
                    tempAngka = a;
                    angkactr++;
                }
                if(angkactr>1){
                    tempAngka= null;
                    break;
                }
            }
            if(tempAngka!= null && tempAngka.getValue() == r.getTotalCell()){
                correctRect.add(r);
                Log.d("TAG", "checkRect: "+tempAngka.getValue());
            }
        }
        if(correctRect.size()!=angkaList.size() || rectList.size()!=angkaList.size()) {
            Log.d("Output", "Pemetaan tidak bijection");
            wrongRect.removeAll(correctRect);
            if(presenter!=null){
                presenter.sendWrongRect(wrongRect);
            }
            return false;
        }
        Log.d("Output", "Pemetaan bijection");
        return true;
    }

}
