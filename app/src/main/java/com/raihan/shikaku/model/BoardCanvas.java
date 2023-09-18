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

public class BoardCanvas extends androidx.appcompat.widget.AppCompatImageView {

//    untuk menggambar
    private Bitmap mBitmap;
    private Canvas mCanvas;

    private int width;
    private int height;


    private int strokeWidth = 7;// tebal stroke



    public BoardCanvas(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        this.width = this.getWidth();
        this.height = this.getHeight();
        Log.d("TAG", "onDraw: "+width+height);

        this.mBitmap = Bitmap.createBitmap(
                width, height, Bitmap.Config.ARGB_8888);
        setImageBitmap(mBitmap);
        this.mCanvas = new Canvas(mBitmap);
        //          gambar canvas dengan warna putih
        int mColorBackground = ResourcesCompat.getColor(getResources(), R.color.white, null);
        this.mCanvas.drawColor(mColorBackground);
    }

    public void drawBoard(int left, int top, int right, int bottom) {
        //          style gambar grid
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        //                  gambar border sel
        Log.d("TAG", "drawBoard: "+left+", "+top+", "+right+", "+bottom);
        mCanvas.drawRect(left, top, right, bottom, paint);

    }

    public void drawNumbers(int angka, int left, int top, int right, int bottom){
        //          style angka
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(64); // ukuran teks

        String angkaStr = Integer.toString(angka);

        //                      tentukan posisi tengah angka di dalam sel
        float textX = (left + right) / 2 - textPaint.measureText(angkaStr) / 2;
        float textY = (top + bottom) / 2 - ((textPaint.descent() + textPaint.ascent()) / 2);

        //                  gambar angka di dalam sel
        mCanvas.drawText(angkaStr, textX, textY, textPaint);
    }

    public void drawSelectedCell(int left, int top, int right, int bottom){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);

        mCanvas.drawRect(left, top, right, bottom, paint);
    }

    public void drawOnMoveSelectedCell(int left, int top, int right, int bottom){
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        mCanvas.drawRect(left, top, right, bottom, paint);
    }
}
