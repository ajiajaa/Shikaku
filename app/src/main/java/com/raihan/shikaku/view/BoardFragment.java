package com.raihan.shikaku.view;

import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.raihan.shikaku.databinding.FragmentBoardBinding;
import com.raihan.shikaku.model.Rectangle;
import com.raihan.shikaku.presenter.BoardContract;
import com.raihan.shikaku.presenter.BoardPresenter;

import java.util.List;

public class BoardFragment extends Fragment implements View.OnTouchListener, BoardContract.View {
    protected FragmentBoardBinding binding;
    protected BoardPresenter presenter;

    private PointF start;
    private List<Rectangle> rectList;

    private boolean isOverlap= false;

    private int gridSize;
    private int level;

    private int seconds;
    private FinishDialogFragment fdf;

    private Vibrator vibrator;
    private SeekBar zoomSeekBar;

    private boolean isPanning;





    public static BoardFragment newInstance(String title){
        BoardFragment fragment = new BoardFragment();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.binding = FragmentBoardBinding.inflate(inflater,container,false);
        View view = this.binding.getRoot();

        this.presenter= new BoardPresenter(this);
        this.binding.ivCanvas.setOnTouchListener(this);
        vibrator = (Vibrator) requireContext().getSystemService(getContext().VIBRATOR_SERVICE);
        zoomSeekBar = binding.zoomSeekBar;
        zoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update skala berdasarkan nilai seekBar
                float newScale = 1.0f + (progress * 0.05f);
                updateZoom(newScale);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        getParentFragmentManager().setFragmentResultListener("postKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported.
                gridSize = bundle.getInt("GridSize");
                level = bundle.getInt("level");
                binding.ivCanvas.setGridSize(gridSize);
                Log.d("TAG", "onFragmentResult: "+gridSize);
                presenter.sendGridSize(getContext(), gridSize, level);
                if(gridSize==5){
                    binding.zoomSeekBar.setVisibility(View.INVISIBLE);
                }

                // untuk case saat menekan next level di dialog finish.
                updateZoom(1.0f);
                zoomSeekBar.setProgress(0);
                rectList= null;
                presenter.newRectList();
                initCanvas();

                seconds= 0;
                binding.time.setText("0:00");
                presenter.startStopwatch();
            }
        });

        return view;
    }


    private void initCanvas() {
        if (binding.ivCanvas != null) {
            binding.ivCanvas.post(new Runnable() {
                @Override
                public void run() {
                    // Kode ini akan dijalankan setelah ImageView siap
                    presenter.sendWidth(binding.ivCanvas.getWidth());
                    presenter.sendHeight(binding.ivCanvas.getHeight());
                    binding.ivCanvas.background();
                    presenter.onProcessDrawingBoard();
                    if(rectList!=null){
                        drawSelectedcell();
                    }
                }
            });
        }
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
                presenter.onTouch(true, start, e.getX(), e.getY());
                binding.count.setText("0");
                if(this.isOverlap){
                    binding.ivCanvas.background();
                    presenter.onProcessDrawingBoard();
                    drawSelectedcell();
                    this.isOverlap= false;
                    Log.d("TAG", "onTouch: im in overlap");
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                //hapus canvas
                binding.ivCanvas.background();
                presenter.onProcessDrawingBoard();
                Log.d("TAG", "onTouch/MOVE/start: "+start.x+ ", "+start.y);
                Log.d("TAG", "onTouch/MOVE/end: "+e.getX()+ ", "+e.getY());

                if(rectList!=null){
                    drawSelectedcell();
                }
                presenter.onTouch(false, start, e.getX(), e.getY());
                return true;
            default:
                return false;
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        // untuk case saat aplikasi diminimize
//        initCanvas();
//    }

    @Override
    public void overlapChecker(boolean isOverlap){
        this.isOverlap= isOverlap;
    }

    @Override
    public void onToastResult(boolean isValid) {
        if(isValid) {
            presenter.stopStopwatch();
            this.fdf= fdf.newInstance(this.gridSize, this.level, this.seconds, this.binding.time.getText().toString());
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            fdf.show(ft,"a");
            Toast.makeText(getContext(), "Selamat jawaban Anda benar!🎉", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendStopwatch(String time) {
        this.binding.time.setText(time);
    }

    @Override
    public void getSecond(int seconds) {
        this.seconds= seconds;
    }

    @Override
    public void vibrating() {
        vibrate();
    }

    @Override
    public void drawBoard(int left, int top, int right, int bottom) {
        binding.ivCanvas.drawBoard(left, top, right, bottom);
    }

    @Override
    public void drawNumbers(int angka, int left, int top, int right, int bottom) {
        binding.ivCanvas.drawNumbers(angka, left, top, right, bottom);
    }

    @Override
    public void setSelectedCell(List<Rectangle> rectList) {
        this.rectList= rectList;

        drawSelectedcell();

    }

    private void drawSelectedcell() {
        for(int i= 0; i< rectList.size(); i++){
            int left= rectList.get(i).getLeft();
            int top= rectList.get(i).getTop();
            int right= rectList.get(i).getRight();
            int bottom= rectList.get(i).getBottom();

            binding.ivCanvas.drawSelectedCell(left, top, right, bottom);
        }
    }

    @Override
    public void drawOnMoveSelectedCell(int left, int top, int right, int bottom) {
        binding.ivCanvas.drawOnMoveSelectedCell(left, top, right, bottom);
    }

    @Override
    public void cellCounter(int ctr) {
        binding.count.setText(Integer.toString(ctr));
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopStopwatch();
        zoomSeekBar.setProgress(0);
        updateZoom(1.0f);
    }
    public void vibrate() {
        if (vibrator != null) {
            // Mengecek versi Android untuk menggunakan metode yang sesuai
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK));
            } else {
                // Sebelum Android Oreo
                vibrator.vibrate(50);
            }
        }
    }
    private void updateZoom(float newScale) {
        newScale = Math.max(1.0f, newScale);

        binding.ivCanvas.setScaleX(newScale);
        binding.ivCanvas.setScaleY(newScale);
    }


}
