package com.raihan.shikaku.presenter;

import android.content.Context;

import com.raihan.shikaku.model.Level;
import com.raihan.shikaku.view.LevelFragment;

import java.util.List;

public class LevelPresenter implements LevelContract.Presenter{

    private final Level model;
    private LevelContract.View view;

    public LevelPresenter(LevelContract.View view, Context context) {
        this.view = view;
        this.model = new Level(context);
    }

    @Override
    public void sendDifficulty(int difficulty) {
        this.model.getDifficulty(difficulty);
    }

    // Metode untuk meminta data dari model dan memperbarui tampilan
    public void requestData() {
        List<Integer> data = model.getData();
        if (view != null) {
            view.showData(data);
        }
    }

    // Metode untuk menangani item yang diklik
    public void onItemClick(int value) {
        // Lakukan sesuatu dengan nilai yang diklik
        if (view != null) {
            view.showItemClick(value);
        }
    }



}

