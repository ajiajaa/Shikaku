package com.raihan.shikaku.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

import com.raihan.shikaku.R;

import java.util.ArrayList;

public class BoardCanvas extends androidx.appcompat.widget.AppCompatImageView implements View.OnTouchListener {

//    untuk menggambar
    private Bitmap mBitmap;
    private Canvas mCanvas;

    private int gridSize;// untuk memilih size grid(difficulty) sesuai pilihan pengguna (easy= 5x5, medium= 10x10, hard= 15x15)

    private int strokeWidth = 7;// tebal stroke

//    untuk pengukuran
    private int cellSize;
    private int minSize;
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;

    private PointF start;// digunakan untuk simpan titik awal sentuhan

    private  final ArrayList<Rectangle> rectList = new ArrayList<>();// menyimpan rectangle yang dibuat user
    private int[][] cellNumbers;// mengolah angka dari resource

    public BoardCanvas(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        createBoard(canvas);// membuat grid sekaligus menggabar angka di tiap selnya
        drawSelectedCell(canvas);// menggambar rectangle yang dibuat user
    }

    private void createBoard(Canvas canvas) {
        this.width = this.getWidth();
        this.height = this.getHeight();

        this.mBitmap = Bitmap.createBitmap(
                width, height, Bitmap.Config.ARGB_8888);
        setImageBitmap(mBitmap);

        this.cellNumbers = new int[gridSize][gridSize];

        fillCells();
        this.mCanvas = new Canvas(mBitmap);

//          gambar canvas dengan warna putih
        int mColorBackground = ResourcesCompat.getColor(getResources(), R.color.white, null);
        this.mCanvas.drawColor(mColorBackground);

        this.minSize = Math.min(width, height); // ukuran minimum antara lebar dan tinggi dari permukaan image view di xml

        this.cellSize = minSize / gridSize; // ukuran sel
        this.offsetX = (width - minSize) / 2; // jarak horizontal dari tepi ImageView ke grid
        this.offsetY = (height - minSize) / 2; // jarak vertikal dari tepi ImageView ke grid

//          style gambar grid
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

//          style angka
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(64); // ukuran teks

//        untuk menggambar grid
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
//                  agar grid berada ditengah image view
                int left = offsetX + j * cellSize;
                int top = offsetY + i * cellSize;
                int right = offsetX + (j + 1) * cellSize;
                int bottom = offsetY + (i + 1) * cellSize;


//                  gambar border sel
                mCanvas.drawRect(left, top, right, bottom, paint);

                int angka = cellNumbers[i][j];

                if (angka != 0) {// tampilkan angka selain 0
                    String angkaStr = Integer.toString(angka);

//                      tentukan posisi tengah angka di dalam sel
                    float textX = (left + right) / 2 - textPaint.measureText(angkaStr) / 2;
                    float textY = (top + bottom) / 2 - ((textPaint.descent() + textPaint.ascent()) / 2);

//                      gambar angka di dalam sel
                    mCanvas.drawText(angkaStr, textX, textY, textPaint);
                }
            }
        }
    }

    private void drawSelectedCell(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);


        for(int i= 0; i<rectList.size(); i++){
            int left= rectCoordinateRow(true,10,
                    rectList.get(i).getStartRow(),
                    rectList.get(i).getEndRow());
            int top= rectCoordinateCol(true, 10,
                    rectList.get(i).getStartCol(),
                    rectList.get(i).getEndCol());
            int right= rectCoordinateRow(false, 10,
                    rectList.get(i).getStartRow(),
                    rectList.get(i).getEndRow());
            int bottom= rectCoordinateCol(false, 10,
                    rectList.get(i).getStartCol(),
                    rectList.get(i).getEndCol());

            mCanvas.drawRect(left, top, right, bottom, paint);
        }

    }

    private int rectCoordinateCol(boolean isStart, int add, int startCol, int endCol) {
        if(isStart){
            return offsetY + add + Math.min(startCol, endCol) * cellSize;
        }else{
            return offsetY - add + (Math.max(startCol, endCol) + 1) * cellSize;
        }
    }

    private int rectCoordinateRow(boolean isStart, int add, int startRow, int endRow) {
        if(isStart){
            return offsetX + add + Math.min(startRow, endRow) * cellSize;
        }else{
            return offsetX - add + (Math.max(startRow, endRow) + 1) * cellSize;
        }
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

//  temporary seeding
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

    @Override
    public boolean onTouch(View v, MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                simpan titik awal ketika menyentuh layar
                start = new PointF(e.getX(), e.getY());
                return true;
            case MotionEvent.ACTION_UP:
//                 tangani ketika melepaskan sentuhan
                onPlay(e, true);
                return true;
            case MotionEvent.ACTION_MOVE:
                onPlay(e, false);
                return true;
            default:
                return super.onTouchEvent(e);
        }
    }


    private void onPlay(MotionEvent e, boolean isUp) {
//        Log.d("TAG", "start: "+start.x+", "+start.y);
//        Log.d("TAG", "end "+e.getX()+" "+e.getY());

        if(start.x!=e.getX() && start.y!=e.getY()){
            int startRow = coordinateConvert(start.x);
            int startCol = coordinateConvert(start.y);
            int endRow = coordinateConvert(e.getX());
            int endCol = coordinateConvert(e.getY());

            startRow= checkMax(startRow);
            startCol= checkMax(startCol);
            endRow= checkMax(endRow);
            endCol= checkMax(endCol);

            startRow= checkMin(startRow);
            startCol= checkMin(startCol);
            endRow= checkMin(endRow);
            endCol= checkMin(endCol);

            if(isUp){
                Rectangle rect= new Rectangle(startRow, startCol, endRow, endCol);
                rectList.add(rect);
            }else{
                Paint paint = new Paint();
                paint.setColor(Color.GRAY);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(strokeWidth);
                paint.setStrokeJoin(Paint.Join.ROUND);
                paint.setStrokeCap(Paint.Cap.ROUND);

                int left = rectCoordinateRow(true, 10, startRow, endRow);
                int top = rectCoordinateCol(true, 10, startCol, endCol);
                int right = rectCoordinateRow(false, 10, startRow, endRow);
                int bottom = rectCoordinateCol(false, 10, startCol, endCol);

                mCanvas.drawRect(left, top, right, bottom, paint);

                int leftCheck = rectCoordinateRow(true, 0, startRow, endRow);
                int topCheck = rectCoordinateCol(true, 0, startCol, endCol);
                int rightCheck = rectCoordinateRow(false, 0, startRow, endRow);
                int bottomCheck = rectCoordinateCol(false, 0, startCol, endCol);

                int length= rightCheck-leftCheck;
                length/=cellSize;

                int width= bottomCheck-topCheck;
                width/=cellSize;

                length*= width;


                Log.d("TAG", "handleRelease: "+leftCheck+", "+topCheck+", "+rightCheck+", "+bottomCheck+": "+length);
            }

        }

    }

    private int coordinateConvert(float x) {
        return (int) (x / cellSize);
    }

    private int checkMin(int input) {
        if(input<0){
            return 0;
        }else{
            return input;
        }
    }

    private int checkMax(int input) {
        if((gridSize-1)-input<0){
            return gridSize-1;
        }else{
            return input;
        }
    }
}
