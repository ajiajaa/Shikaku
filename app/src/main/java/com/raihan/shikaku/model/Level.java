package com.raihan.shikaku.model;

import java.util.ArrayList;

public class Level {
    private ArrayList<Angka> angkaList;

    public Level(int gridSize) {
        this.angkaList= new ArrayList<>();
        fillCells();
    }

    private void fillCells() {
        Angka angka= new Angka(0,1,2);
        Angka angka1= new Angka(1,3,4);
        Angka angka2= new Angka(1,4,2);
        Angka angka3= new Angka(2,0,4);
        Angka angka4= new Angka(2,4,3);
        Angka angka5= new Angka(3,3,4);
        Angka angka6= new Angka(3,4,2);
        Angka angka7= new Angka(4,1,2);
        Angka angka8= new Angka(4,2,2);

        angkaList.add(angka);
        angkaList.add(angka1);
        angkaList.add(angka2);
        angkaList.add(angka3);
        angkaList.add(angka4);
        angkaList.add(angka5);
        angkaList.add(angka6);
        angkaList.add(angka7);
        angkaList.add(angka8);
    }

    public ArrayList<Angka> getCellNumbers() {
        return angkaList;
    }
}
