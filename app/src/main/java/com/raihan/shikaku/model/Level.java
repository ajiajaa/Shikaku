package com.raihan.shikaku.model;

public class Level {
    private int[][] cellNumbers;

    public Level(int gridSize) {
        this.cellNumbers= new int[gridSize][gridSize];
        fillCells();
    }

    private void fillCells() {
        cellNumbers[0][0] = 3;
        cellNumbers[0][1] = 0;
        cellNumbers[0][2] = 0;
        cellNumbers[0][3] = 0;
        cellNumbers[0][4] = 2;

        cellNumbers[1][0] = 0;
        cellNumbers[1][1] = 3;
        cellNumbers[1][2] = 0;
        cellNumbers[1][3] = 2;
        cellNumbers[1][4] = 0;

        cellNumbers[2][0] = 3;
        cellNumbers[2][1] = 0;
        cellNumbers[2][2] = 0;
        cellNumbers[2][3] = 0;
        cellNumbers[2][4] = 3;

        cellNumbers[3][0] = 2;
        cellNumbers[3][1] = 3;
        cellNumbers[3][2] = 0;
        cellNumbers[3][3] = 0;
        cellNumbers[3][4] = 0;

        cellNumbers[4][0] = 0;
        cellNumbers[4][1] = 4;
        cellNumbers[4][2] = 0;
        cellNumbers[4][3] = 0;
        cellNumbers[4][4] = 0;
    }

    public int[][] getCellNumbers() {
        return cellNumbers;
    }
}
