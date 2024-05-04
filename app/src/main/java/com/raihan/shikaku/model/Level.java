package com.raihan.shikaku.model;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.raihan.shikaku.R;
import com.raihan.shikaku.presenter.LevelContract;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level implements LevelContract.Model {

    private  Context context;
    private int lvlIdle;
    private ArrayList<Angka> angkaList;
    Scanner sc;

    public Level(Context context, int gridSize) {
        this.angkaList = new ArrayList<>();
        this.context = context;
        getDifficulty(gridSize);
    }

    public Level(Context context) {
        this.angkaList = new ArrayList<>();
        this.context = context;
    }

    public List<Integer> getData() {
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= this.lvlIdle; i++) {
            data.add(i);
        }
        return data;
    }

    private void readNumberOfPuzzles() {
        if (this.sc.hasNextLine()) {
            // Membaca banyak puzzle (baris pertama)
            this.lvlIdle = Integer.parseInt(this.sc.nextLine());
        }
    }
    @Override
    public void readPuzzles(int chosenLevel) {
        // Membaca setiap puzzle
        for (int i = 0; i < this.lvlIdle; i++) {
            String puzzleData = this.sc.nextLine();
            if (i == chosenLevel) {
                String[] puzzleElements = puzzleData.split(";");

                // Memproses setiap elemen puzzle
                for (String element : puzzleElements) {
                    String[] parts = element.split(",");
                    int baris = Integer.parseInt(parts[0]);
                    int kolom = Integer.parseInt(parts[1]);
                    int nilai = Integer.parseInt(parts[2]);

                    Angka angka = new Angka(baris, kolom, nilai);
                    this.angkaList.add(angka);
                    // Lakukan sesuatu dengan nilai baris, kolom, dan nilai, misalnya, print ke layar
                    Log.d("TAG", "fillCells: Puzzle ke-" + (i + 1) + ": Baris=" + baris + ", Kolom=" + kolom + ", Nilai=" + nilai);
                }
            }
        }
    }

    public ArrayList<Angka> getCellNumbers() {
        return angkaList;
    }

    @Override
    public void getDifficulty(int difficulty) {
        try {
            Resources resources = this.context.getResources();
            InputStream inputStream = null;

            if (difficulty == 5) {
                inputStream = resources.openRawResource(R.raw.puzzles5);
            } else if (difficulty == 10) {
                inputStream = resources.openRawResource(R.raw.puzzles10);
            } else if (difficulty == 15) {
                inputStream = resources.openRawResource(R.raw.puzzles15);
            }else if (difficulty == 2) {
                inputStream = resources.openRawResource(R.raw.tutorial);
            }

            if (inputStream != null) {
                Log.d("TAG", "getDifficulty: " + difficulty);
                this.sc = new Scanner(inputStream);
                readNumberOfPuzzles();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}

