package com.raihan.shikaku.presenter;

import android.content.Context;
import android.graphics.PointF;
import android.os.CountDownTimer;
import android.util.Log;

import com.raihan.shikaku.model.Angka;
import com.raihan.shikaku.model.BoardModel;
import com.raihan.shikaku.model.Checker;
import com.raihan.shikaku.model.Level;
import com.raihan.shikaku.model.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

public class
BoardPresenter implements BoardContract.Presenter {

    private BoardContract.View view;
    private BoardContract.Model model;

    private HashMap<String, Integer> hm;
    private HashMap<String, Integer> hm1;

    private boolean isUp;

    private int gridSize;
    private Level lvl;

    private CountDownTimer countDownTimer;
    private boolean isStopwatchRunning;
    private long elapsedTime; // Menyimpan waktu terakhir saat stopwatch di-pause


    private String formattedTime;



    private ArrayList<Rectangle> rectList;// menyimpan rectangle yang dibuat user


    public BoardPresenter(BoardContract.View view) {
        this.view = view;
        this.model = new BoardModel();
        this.isStopwatchRunning= false;
        this.elapsedTime = 0;
    }

    //menangani sentuhan dari view, dikalkulasi di model dan memproses hasil kalkulasi
    @Override
    public void onTouch(boolean isUp, PointF start, float e1, float e2) {
        if(start.x!=e1 && start.y!=e2){
            this.hm1= this.model.getRectangleCoordinates(start, e1, e2);
            this.isUp= isUp;
            onProcessSelectedCell();
        }
    }

    @Override
    public void sendGridSize(Context context, int gridSize, int level) {
        this.model.getGridSize(gridSize);
        this.gridSize= gridSize;
        lvl= new Level(context, gridSize);
        lvl.readPuzzles(level-1);
    }

    // untuk menggambar grid dan angka
    @Override
    public void onProcessDrawingBoard(boolean isReset) {
        if(!isReset){
            this.hm = this.model.calculateBoard();
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
//                  agar grid berada ditengah image view
                int left = toCoordinateCol(true, 0, j, Integer.MAX_VALUE);
                int top = toCoordinateRow(true, 0, i, Integer.MAX_VALUE);
                int right = toCoordinateCol(false, 0, j, Integer.MIN_VALUE);
                int bottom = toCoordinateRow(false, 0, i, Integer.MIN_VALUE);

                this.view.drawBoard(left, top, right, bottom);
            }
        }
        ArrayList<Angka> angkaList= lvl.getCellNumbers();
        for (int i = 0; i < angkaList.size(); i++){
            int left = toCoordinateCol(true, 0, angkaList.get(i).getColumn(), Integer.MAX_VALUE);
            int top = toCoordinateRow(true, 0, angkaList.get(i).getRow(), Integer.MAX_VALUE);
            int right = toCoordinateCol(false, 0, angkaList.get(i).getColumn(), Integer.MIN_VALUE);
            int bottom = toCoordinateRow(false, 0, angkaList.get(i).getRow(), Integer.MIN_VALUE);

            this.view.drawNumbers(angkaList.get(i).getValue(), left, top, right, bottom);
        }
    }
    //memproses pembuatan persegi maupun saat disentuh atau ketika sudah tidak disentuh
    @Override
    public void onProcessSelectedCell() {
        int startRow= Math.min(hm1.get("startRow"), hm1.get("endRow"));
        int startCol= Math.min(hm1.get("startCol"), hm1.get("endCol"));
        int endRow =  Math.max(hm1.get("startRow"), hm1.get("endRow"));
        int endCol =  Math.max(hm1.get("startCol"), hm1.get("endCol"));
        Log.d("TAG", "onProcessSelectedCell: "+startRow+", "+startCol);
        int left = toCoordinateCol(true, 10, startCol, endCol);
        int top = toCoordinateRow(true, 10, startRow, endRow);
        int right = toCoordinateCol(false, 10, startCol, endCol);
        int bottom = toCoordinateRow(false, 10, startRow, endRow);

        int leftCheck = toCoordinateCol(true, 0, startRow, endRow);
        int topCheck = toCoordinateRow(true, 0, startCol, endCol);
        int rightCheck = toCoordinateCol(false, 0, startRow, endRow);
        int bottomCheck = toCoordinateRow(false, 0, startCol, endCol);

//        menghitung dalam sebuah rectangle ada berapa sel, bila sel<1 jangan buat rectangle
        int length= rightCheck-leftCheck;
        int width= bottomCheck-topCheck;
        length/=hm.get("cellSize");
        width/=hm.get("cellSize");

        length*= width;

        if(length>1){
            if(isUp){
                ArrayList<Rectangle> rectanglesToRemove = new ArrayList<>();
                for (Rectangle existingRect : rectList) {
                    if (isRectangleOverlapping(left, top, right, bottom, existingRect)) {
                        rectanglesToRemove.add(existingRect);
                        view.overlapChecker(true);
                        Log.d("TAG", "overlapped");
                        view.vibrating();
                    }
                }
                rectList.removeAll(rectanglesToRemove);

                Rectangle rect= new Rectangle(left, top, right, bottom, length);
                rect.setIndex(startRow, startCol, endRow, endCol);
                Log.d("TAG", "startRow: "+startRow+" endRow "+endRow);
                Log.d("TAG", "startCol: "+startCol+" endCol "+endCol);

                rectList.add(rect);
                this.view.setSelectedCell(rectList);
                view.vibrating();
            }else{
                this.view.drawOnMoveSelectedCell(left, top, right, bottom);
                this.view.cellCounter(length);
            }
        }else if(length==1){
            this.view.cellCounter(0);
        }
    }
    //mengirimkan persegi mana saja yang menyalahi aturan setelah dicek
    @Override
    public void sendWrongRect(ArrayList<Rectangle> wrongRect) {
        view.getWrongRect(wrongRect);
    }

    //mengecek apakah persegi yang akan dibuat bertumpang tindih dengan persegi yang sudah ada
    private boolean isRectangleOverlapping(int startRow1, int startCol1, int endRow1, int endCol1,
                                           Rectangle rect2) {
        int left1 = Math.min(startRow1, endRow1);
        int top1 = Math.min(startCol1, endCol1);
        int right1 = Math.max(startRow1, endRow1);
        int bottom1 = Math.max(startCol1, endCol1);

        int left2 = rect2.getLeft();
        int top2 = rect2.getTop();
        int right2 = rect2.getRight();
        int bottom2 = rect2.getBottom();

        // Pengecekan tumpang tindih
        return left1 <= right2 && right1 >= left2 && top1 <= bottom2 && bottom1 >= top2;
    }


    @Override
    public void sendWidth(int width) {
        this.model.getWidth(width);
    }

    @Override
    public void sendHeight(int height) {
        this.model.getHeight(height);
    }

    //me-reset isi kumpulan persegi untuk level selanjutnya
    @Override
    public void newRectList() {
        this.rectList= new ArrayList<>();
    }

    //menkonversikan kembali kebentuk koordinat untuk memudahkan penggambaran
    private int toCoordinateCol(boolean isStart, int add, int startCol, int endCol) {
        if(isStart){
            return hm.get("offsetX") + add + Math.min(startCol, endCol) * hm.get("cellSize");
        }else{
            return hm.get("offsetX") - add + (Math.max(startCol, endCol) + 1) * hm.get("cellSize");
        }
    }

    private int toCoordinateRow(boolean isStart, int add, int startRow, int endRow) {
        if(isStart){
            return hm.get("offsetY") + add + Math.min(startRow, endRow) * hm.get("cellSize");
        }else{
            return hm.get("offsetY") - add + (Math.max(startRow, endRow) + 1) * hm.get("cellSize");
        }
    }

    @Override
    public void checker() {
        //Check apakah sudah menyelesaikan papan, jika sudah check jawaban
        Checker checker= new Checker(this, this.rectList, this.lvl);
        int totalRecCell= 0;
        for (int i= 0; i<rectList.size(); i++){
            totalRecCell+= rectList.get(i).getTotalCell();
        }
        if(totalRecCell==Math.pow(gridSize,2)){
            view.onToastResult(checker.validateBoard());
        }
    }

    //timer thingy
    @Override
    public void startStopwatch() {
        this.elapsedTime= 0;
        this.formattedTime= "";
        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) { // Setiap 1000 milidetik (1 detik)

            @Override
            public void onTick(long millisUntilFinished) {
                elapsedTime += 1000;
                updateStopwatch(elapsedTime);
            }

            @Override
            public void onFinish() {
                // Metode ini dipanggil saat timer selesai (meskipun tidak diaktifkan di sini)
            }
        }.start();
        isStopwatchRunning = true;
    }

    @Override
    public void stopStopwatch() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isStopwatchRunning = false;
            calculateScore();
        }
    }
    @Override
    public void pauseStopwatch() {
        if (countDownTimer != null && isStopwatchRunning) {
            countDownTimer.cancel();
            isStopwatchRunning = false;
        }
    }

    // Implementasi metode resumeStopwatch()
    @Override
    public void resumeStopwatch() {
        if (!isStopwatchRunning) {
            countDownTimer = new CountDownTimer(Long.MAX_VALUE - elapsedTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    elapsedTime = Long.MAX_VALUE - millisUntilFinished;
                    updateStopwatch(elapsedTime);
                }

                @Override
                public void onFinish() {
                    // Metode ini dipanggil saat timer selesai (meskipun tidak diaktifkan di sini)
                }
            }.start();
            isStopwatchRunning = true;
            view.notPause();
        }
    }

    private void updateStopwatch(long elapsedTime) {
        // Konversi waktu dalam milidetik menjadi format menit:detik
        long minutes = (elapsedTime / 1000) / 60;
        long seconds = (elapsedTime / 1000) % 60;

        // Format waktu dan setel ke TextView
        this.formattedTime = String.format("%d:%02d", minutes, seconds);
        view.sendStopwatch(this.formattedTime);
    }
    private void calculateScore() {
        String timeText = this.formattedTime;

        // Split waktu menjadi menit dan detik
        String[] timeComponents = timeText.split(":");
        if (timeComponents.length == 2) {
            try {
                int minutes = Integer.parseInt(timeComponents[0]);
                int seconds = Integer.parseInt(timeComponents[1]);

                // Konversi waktu menjadi total detik
                int totalSeconds = (minutes * 60) + seconds;

                view.getSecond(totalSeconds);


            } catch (NumberFormatException e) {
                // Handle jika parsing gagal
                e.printStackTrace();
            }
        }
    }
}

