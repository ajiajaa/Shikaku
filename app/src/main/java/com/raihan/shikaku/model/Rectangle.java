package com.raihan.shikaku.model;

public class Rectangle {
    //+10 untuk tampilan
    private int left;//left || start x
    private int top;//top || start y
    private int right;//right || end x
    private int bottom;//bottom || end y

    private int totalCell; //total of cells in rectangle

    //index
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;

    public Rectangle(int left, int top, int right, int bottom, int totalCell) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.totalCell = totalCell;
    }

    public void setIndex(int startRow, int startCol, int endRow, int endCol){
        this.startRow= startRow;
        this.startCol= startCol;
        this.endRow= endRow;
        this.endCol= endCol;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getTotalCell() {
        return totalCell;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }
}
