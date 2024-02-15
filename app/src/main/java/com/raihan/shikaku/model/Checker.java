package com.raihan.shikaku.model;

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
            ArrayList<Angka> tempAngka= new ArrayList<>();
            for (Angka a : lvl.getCellNumbers()) {
                if (a.getRow() >= r.getStartRow() && a.getRow() <= r.getEndRow()
                        && a.getColumn() >= r.getStartCol() && a.getColumn() <= r.getEndCol()) {
                    tempAngka.add(a);
                }
            }
            if(tempAngka.size()==1 && tempAngka.get(0).getValue() == r.getTotalCell()){
                correctRect.add(r);
            }
        }
        if(correctRect.size()!=angkaList.size()) {
            System.out.println("Pemetaan tidak bijection");
            wrongRect.removeAll(correctRect);
            presenter.sendWrongRect(wrongRect);
            return false;
        }
        System.out.println("Pemetaan bijection");
        return true;
    }

}
