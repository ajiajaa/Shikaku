package com.raihan.shikaku.model;

import java.util.ArrayList;

public class Level {
    private ArrayList<Angka> angkaList;

    public Level(int gridSize) {
        this.angkaList= new ArrayList<>();
        fillCells();
    }

    private void fillCells() {
        Angka angka= new Angka(0,2,2);
        Angka angka1= new Angka(1,3,2);
        Angka angka2= new Angka(1,9,3);
        Angka angka3= new Angka(2,3,2);
        Angka angka4= new Angka(2,6,15);
        Angka angka5= new Angka(3,4,3);
        Angka angka6= new Angka(3,5,2);
        Angka angka7= new Angka(4,2,2);
        Angka angka8= new Angka(4,4,3);
        Angka angka9= new Angka(5,6,2);
        Angka angka10= new Angka(6,2,3);
        Angka angka11= new Angka(6,6,2);
        Angka angka12= new Angka(6,8,12);
        Angka angka13= new Angka(7,3,12);
        Angka angka14= new Angka(7,8,2);
        Angka angka15= new Angka(8,1,10);
        Angka angka16= new Angka(8,6,2);
        Angka angka17= new Angka(8,9,3);
        Angka angka18= new Angka(9,0,10);
        Angka angka19= new Angka(9,5,6);
        Angka angka20= new Angka(9,8,2);

        angkaList.add(angka);
        angkaList.add(angka1);
        angkaList.add(angka2);
        angkaList.add(angka3);
        angkaList.add(angka4);
        angkaList.add(angka5);
        angkaList.add(angka6);
        angkaList.add(angka7);
        angkaList.add(angka8);
        angkaList.add(angka9);
        angkaList.add(angka10);
        angkaList.add(angka11);
        angkaList.add(angka12);
        angkaList.add(angka13);
        angkaList.add(angka14);
        angkaList.add(angka15);
        angkaList.add(angka16);
        angkaList.add(angka17);
        angkaList.add(angka18);
        angkaList.add(angka19);
        angkaList.add(angka20);
    }

    public ArrayList<Angka> getCellNumbers() {
        return angkaList;
    }
}
