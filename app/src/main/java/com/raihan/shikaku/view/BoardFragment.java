package com.raihan.shikaku.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.raihan.shikaku.R;
import com.raihan.shikaku.databinding.FragmentBoardBinding;
import com.raihan.shikaku.model.BoardCanvas;
import com.raihan.shikaku.model.Rectangle;
import com.raihan.shikaku.presenter.BoardContract;
import com.raihan.shikaku.presenter.BoardPresenter;

import java.util.List;

public class BoardFragment extends Fragment implements View.OnTouchListener, View.OnClickListener,BoardContract.View {
    protected FragmentBoardBinding binding;
    protected BoardPresenter presenter;

    private PointF start;

    public static BoardFragment newInstance(String title){
        BoardFragment fragment = new BoardFragment();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.binding = FragmentBoardBinding.inflate(inflater,container,false);
        View view = this.binding.getRoot();

        this.presenter= new BoardPresenter(this);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported.
                int result = bundle.getInt("GridSize");
                Log.d("TAG", "onFragmentResult: "+result);
                Log.d("TAG", "onFragmentResult: "+binding.ivCanvas.getWidth());

                presenter.sendGridSize(result);
                presenter.onProcessDrawingBoard();
                presenter.sendWidth(binding.ivCanvas.getWidth());
                presenter.sendHeight(binding.ivCanvas.getHeight());
                // Do something with the result.
            }
        });
        this.binding.ivCanvas.setOnTouchListener(this);
        this.binding.btnStart.setOnClickListener(this);

        return view;
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
                presenter.onTouch(true, start, e.getX(), e.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                presenter.onTouch(false, start, e.getX(), e.getY());
                return true;
            default:
                return false;
        }
    }


    @Override
    public void drawBoard(int left, int top, int right, int bottom) {
        binding.ivCanvas.drawBoard(left, top, right, bottom);

        Log.d("TAG", "drawBoard: "+left);
    }

    @Override
    public void drawNumbers(int angka, int left, int top, int right, int bottom) {
        binding.ivCanvas.drawNumbers(angka, left, top, right, bottom);
    }

    @Override
    public void drawSelectedCell(List<Rectangle> rectList) {
        for(int i= 0; i< rectList.size(); i++){
            int left= rectList.get(i).getStartRow();
            int top= rectList.get(i).getStartCol();
            int right= rectList.get(i).getEndRow();
            int bottom= rectList.get(i).getEndCol();

            binding.ivCanvas.drawSelectedCell(left, top, right, bottom);
        }
    }

    @Override
    public void drawOnMoveSelectedCell(int left, int top, int right, int bottom) {
        binding.ivCanvas.drawOnMoveSelectedCell(left, top, right, bottom);
    }

    @Override
    public void onClick(View v) {
        if(v==binding.btnStart){
        }
    }
}
