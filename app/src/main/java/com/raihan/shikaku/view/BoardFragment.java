package com.raihan.shikaku.view;

import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.raihan.shikaku.MainActivity;
import com.raihan.shikaku.R;
import com.raihan.shikaku.databinding.FragmentBoardBinding;
import com.raihan.shikaku.model.Rectangle;
import com.raihan.shikaku.presenter.BoardContract;
import com.raihan.shikaku.presenter.BoardPresenter;

import java.util.ArrayList;
import java.util.List;

public class BoardFragment extends Fragment implements View.OnTouchListener,View.OnClickListener, BoardContract.View {
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

    private boolean firstOpen;
    private boolean isFinish;
    private boolean isPause;
    private boolean isPanning;

    private int tutorialLevel;
    private int tutorialCount;

    public static BoardFragment newInstance(String title){
        BoardFragment fragment = new BoardFragment();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.binding = FragmentBoardBinding.inflate(inflater,container,false);
        View view = this.binding.getRoot();
        tutorialCount=1;
        tutorialLevel=1;

        this.presenter= new BoardPresenter(this);
        this.binding.ivCanvas.setOnTouchListener(this);
        vibrator = (Vibrator) requireContext().getSystemService(getContext().VIBRATOR_SERVICE);
        zoomSeekBar = binding.zoomSeekBar;
        firstOpen = true;

        if(isTutorial()){
            binding.btnReset.setVisibility(View.GONE);
            binding.time.setVisibility(View.GONE);
            binding.level.setVisibility(View.GONE);
            binding.btnPause.setVisibility(View.GONE);
        }else{
            binding.chara.setVisibility(View.GONE);
            binding.tvTutor.setVisibility(View.GONE);
            binding.nextBtn.setVisibility(View.GONE);
        }

        zoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update skala berdasarkan nilai seekBar
                float newScale = 1.0f + (progress * 0.05f);
                if(newScale==1.0f){
                    binding.ivCanvas.setTranslationX(0);
                    binding.ivCanvas.setTranslationY(0);
                }
                updateZoom(newScale);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if(!isTutorial()){
            getParentFragmentManager().setFragmentResultListener("postKey", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                    // We use a String here, but any type that can be put in a Bundle is supported.
                    gridSize = bundle.getInt("GridSize");
                    level = bundle.getInt("level");
                    binding.ivCanvas.setGridSize(gridSize);
                    binding.level.setText("Level "+level);
                    Log.d("TAG", "onFragmentResult: "+gridSize);
                    presenter.sendGridSize(getContext(), gridSize, level);
                    if(gridSize==5){
                        binding.zoomSeekBar.setVisibility(View.GONE);
                        binding.pan.setVisibility(View.GONE);
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
                    isFinish = false;
                    isPause = false;
                }
            });
        }else{
            binding.box.setVisibility(View.INVISIBLE);
            binding.count.setVisibility(View.INVISIBLE);
        }
        this.binding.nextBtn.setOnClickListener(this);
        this.binding.btnPause.setOnClickListener(this);
        this.binding.btnReset.setOnClickListener(this);
        this.binding.pan.setOnClickListener(this);
        this.binding.pan1.setOnClickListener(this);

        return view;
    }

    private boolean isTutorial() {
        return ((MainActivity)getActivity()).preferences.getBoolean("isTutorial", true);
    }

    //inisiasi canvas pertama kali
    private void initCanvas() {
        if (binding.ivCanvas != null) {
            binding.ivCanvas.post(new Runnable() {
                @Override
                public void run() {
                    // Kode ini akan dijalankan setelah ImageView siap
                    presenter.sendWidth(binding.ivCanvas.getWidth());
                    presenter.sendHeight(binding.ivCanvas.getHeight());
                    if(isTutorial()){
                        if(tutorialCount==3){
                            binding.ivCanvas.setGridSize(5);
                            presenter.sendGridSize(getContext(), 5, 1);
                            presenter.newRectList();
                            rectList= null;
                            Log.d("TAG", "run: ");
                        }else{
                            binding.ivCanvas.setGridSize(2);
                            presenter.sendGridSize(getContext(), 2, tutorialLevel);
                            presenter.newRectList();
                            rectList= null;
                        }
                    }
                    binding.ivCanvas.background();
                    presenter.onProcessDrawingBoard(false);
                    if(rectList!=null){
                        drawSelectedcell();
                    }
                }
            });
        }
    }
    //reset canvas ketika melakukan interaksi dengan pengguna untuk memperbarui canvas
    public void resetCanvas(){
        binding.ivCanvas.background();
        presenter.onProcessDrawingBoard(true);
        if(rectList!=null){
            drawSelectedcell();
        }
    }

    //menangani sentuhan
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        int action = e.getAction();
        if(isPanning){
            switch (action) {
                case MotionEvent.ACTION_DOWN:
//                simpan titik awal ketika menyentuh layar
                    start = new PointF(e.getX(), e.getY());
                    return true;
                case MotionEvent.ACTION_MOVE:
                    float deltaX = e.getX() - start.x;
                    float deltaY = e.getY() - start.y;
                    binding.ivCanvas.setTranslationX(binding.ivCanvas.getTranslationX() + deltaX);
                    binding.ivCanvas.setTranslationY(binding.ivCanvas.getTranslationY() + deltaY);
                    Log.d("TAG", "getX: "+ binding.ivCanvas.getTranslationX()+" | getY: "+binding.ivCanvas.getTranslationY());
                    start.set(e.getX(), e.getY());
                    return true;

                case MotionEvent.ACTION_UP:
                    return true;
                default:
                    return false;
            }
        }else{
            switch (action) {
                case MotionEvent.ACTION_DOWN:
//                simpan titik awal ketika menyentuh layar
                    start = new PointF(e.getX(), e.getY());
                    return true;
                case MotionEvent.ACTION_UP:
                    presenter.onTouch(true, start, e.getX(), e.getY());
                    binding.count.setText("0");
                    if(this.isOverlap){
                        resetCanvas();
                        this.isOverlap= false;
                        Log.d("TAG", "onTouch: im in overlap");
                    }
                    presenter.checker();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    //hapus canvas
                    resetCanvas();
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
    }


    @Override
    public void overlapChecker(boolean isOverlap){
        this.isOverlap= isOverlap;
    }

    //untuk memberikan hint kepada pengguna ketika ada persegi yang menyalahi aturan
    @Override
    public void getWrongRect(ArrayList<Rectangle> wrongRect) {
        for(int i= 0; i< wrongRect.size(); i++){
            int left= wrongRect.get(i).getLeft();
            int top= wrongRect.get(i).getTop();
            int right= wrongRect.get(i).getRight();
            int bottom= wrongRect.get(i).getBottom();

            binding.ivCanvas.drawSelectedCell(true, left, top, right, bottom);
        }
    }

    //ketika selesai dan jawaban benar
    @Override
    public void checkerResult(boolean isValid) {
        if(isValid) {
            if(isTutorial()){
                if(tutorialCount==8){
                    this.binding.tvTutor.setText(getString(R.string.tutor8));
                    this.binding.nextBtn.setVisibility(View.VISIBLE);
                    tutorialCount++;
                }
                if(tutorialCount==10){
                    this.binding.tvTutor.setText(getString(R.string.tutor10));
                    this.binding.nextBtn.setVisibility(View.VISIBLE);
                    tutorialCount++;
                }
            }else{
                if(gridSize==5 && ((MainActivity)getActivity()).preferences.getInt("level_easy", 1)==level)
                    ((MainActivity)getActivity()).preferences.edit().putInt("level_easy", level+1).apply();

                if(gridSize==10)
                    ((MainActivity)getActivity()).preferences.edit().putInt("level_medium", level+1).apply();

                if(gridSize==15)
                    ((MainActivity)getActivity()).preferences.edit().putInt("level_hard", level+1).apply();


                presenter.stopStopwatch();
                this.isFinish= true;
                showDialog(false);
            }
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
    public void notPause() {
        this.isPause = false;
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
    public void setSelectedCell(ArrayList<Rectangle> rectList) {
        this.rectList= rectList;
        drawSelectedcell();
    }

    private void drawSelectedcell() {
        for(int i= 0; i< rectList.size(); i++){
            int left= rectList.get(i).getLeft();
            int top= rectList.get(i).getTop();
            int right= rectList.get(i).getRight();
            int bottom= rectList.get(i).getBottom();

            binding.ivCanvas.drawSelectedCell(false, left, top, right, bottom);
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
        presenter.pauseStopwatch();
    }
    @Override
    public void onResume(){
        super.onResume();
        if(!isTutorial()){
            if(!firstOpen && !isFinish && !isPause){
                showDialog(true);
            }else{
                this.firstOpen = false;
                zoomSeekBar.setProgress(0);
                updateZoom(1.0f);
            }
        }

    }
    public void vibrate() {
        if (vibrator != null) {
            // cek versi Android
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK));
            } else {
                // sebelum Android Oreo / 8.0
                vibrator.vibrate(50);
            }
        }
    }
    private void updateZoom(float newScale) {
        newScale = Math.max(1.0f, newScale);

        binding.ivCanvas.setScaleX(newScale);
        binding.ivCanvas.setScaleY(newScale);
    }
    public void showDialog(boolean isPause){
        if(isPause){
            this.isPause = isPause;
        }
        this.fdf= fdf.newInstance(this.presenter,this.gridSize, this.level, this.seconds, this.binding.time.getText().toString(), isPause);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        fdf.show(ft,"a");
        this.presenter.pauseStopwatch();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.btnPause){
            presenter.stopStopwatch();
            showDialog(true);
        }
        if(view == binding.pan){
            this.isPanning = true;
            binding.pan.setVisibility(View.GONE);
            binding.pan1.setVisibility(View.VISIBLE);
        }
        if(view == binding.pan1){
            this.isPanning = false;
            binding.pan.setVisibility(View.VISIBLE);
            binding.pan1.setVisibility(View.GONE);
        }
        if(view == binding.btnReset){
            rectList= null;
            presenter.newRectList();
            resetCanvas();
        }
        if(view == binding.nextBtn){
            Log.d("TAG", "onClick: "+tutorialCount);
            if(tutorialCount == 1){
                this.binding.tvTutor.setText(getString(R.string.tutor1));
            }
            if(tutorialCount == 2){
                this.binding.tvTutor.setText(getString(R.string.tutor2));
                this.binding.box.setVisibility(View.VISIBLE);
                this.binding.count.setVisibility(View.VISIBLE);
                initCanvas();
            }
            if(tutorialCount == 3){
                this.binding.tvTutor.setText(getString(R.string.tutor3));
                initCanvas();

            }
            if(tutorialCount == 4){
                this.binding.tvTutor.setText(getString(R.string.tutor4));
            }
            if(tutorialCount == 5){
                this.binding.tvTutor.setText(getString(R.string.tutor5));
            }
            if(tutorialCount == 6){
                this.binding.tvTutor.setText(getString(R.string.tutor6));
            }
            if(tutorialCount == 7){
                this.binding.tvTutor.setText(getString(R.string.tutor7));
                initCanvas();
                this.binding.nextBtn.setVisibility(View.GONE);
            }
            if(tutorialCount == 9){
                this.binding.tvTutor.setText(getString(R.string.tutor9));
                tutorialLevel++;
                initCanvas();
                this.binding.nextBtn.setVisibility(View.GONE);
            }
            if(tutorialCount == 11){
                ((MainActivity)getActivity()).changePage(0);
                ((MainActivity)getActivity()).preferences.edit().putBoolean("isTutorial", false).apply();
            }
            tutorialCount++;

        }
    }
}
