package com.raihan.shikaku.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Rectangle implements Parcelable {
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
    //only for test
    public Rectangle(int totalCell) {
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

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(left);
        dest.writeInt(top);
        dest.writeInt(right);
        dest.writeInt(bottom);
        dest.writeInt(totalCell);
        dest.writeInt(startRow);
        dest.writeInt(startCol);
        dest.writeInt(endRow);
        dest.writeInt(endCol);
    }

    public static final Parcelable.Creator<Rectangle> CREATOR = new Parcelable.Creator<Rectangle>() {
        @Override
        public Rectangle createFromParcel(Parcel in) {
            return new Rectangle(in);
        }

        @Override
        public Rectangle[] newArray(int size) {
            return new Rectangle[size];
        }
    };

    private Rectangle(Parcel in) {
        left = in.readInt();
        top = in.readInt();
        right = in.readInt();
        bottom = in.readInt();
        totalCell = in.readInt();
        startRow = in.readInt();
        startCol = in.readInt();
        endRow = in.readInt();
        endCol = in.readInt();
    }
}