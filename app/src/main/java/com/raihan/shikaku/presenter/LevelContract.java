package com.raihan.shikaku.presenter;

import com.raihan.shikaku.model.Level;

import java.util.List;

public interface LevelContract {

    interface View{
        void showData(List<Integer> data);
        void showItemClick(int value);
    }

    interface Presenter{
        void sendDifficulty(int difficulty);
        void requestData();
        void onItemClick(int value);
        void drawBoard(int chosenLevel);
        void detachView();
    }

    interface Model{
        void getDifficulty(int difficulty);
        void readPuzzles(int chosenLevel);
    }


}
