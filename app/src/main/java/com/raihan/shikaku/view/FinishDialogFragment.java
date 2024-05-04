package com.raihan.shikaku.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.raihan.shikaku.MainActivity;
import com.raihan.shikaku.databinding.FragmentDialogFinishBinding;
import com.raihan.shikaku.presenter.BoardPresenter;

public class FinishDialogFragment extends DialogFragment implements View.OnClickListener{
    protected FragmentDialogFinishBinding binding;

    private static int gridSize;
    private static int level;
    private static int waktu;
    private static String waktuText;
    private static boolean isPause;
    private static BoardPresenter presenter;
    public static FinishDialogFragment newInstance(BoardPresenter presenter, int gridSize, int level, int waktu, String waktuText, boolean isPause){
        FinishDialogFragment fragment = new FinishDialogFragment();
        FinishDialogFragment.gridSize = gridSize;
        FinishDialogFragment.level= level;
        FinishDialogFragment.waktu= waktu;
        FinishDialogFragment.waktuText= waktuText;
        FinishDialogFragment.isPause= isPause;
        FinishDialogFragment.presenter= presenter;
        return fragment;
    }
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("waktuText", waktuText);
//        outState.putInt("level2", level);
//        outState.putInt("gridSize2", gridSize);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = FragmentDialogFinishBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        setCancelable(false);
//        if (savedInstanceState != null) {
//            waktuText = savedInstanceState.getString("waktuText");
//            level = savedInstanceState.getInt("level2");
//            gridSize = savedInstanceState.getInt("gridSize2");
//        }
        if (isPause){
            binding.star1.setVisibility(View.GONE);
            binding.star2.setVisibility(View.GONE);
            binding.star3.setVisibility(View.GONE);
            binding.congrats.setVisibility(View.GONE);
            binding.nextBtn.setVisibility(View.GONE);
        }else{
            this.binding.congrats.setText(this.binding.congrats.getText().toString()+waktuText);
            this.binding.btnPlay.setVisibility(View.GONE);
            this.binding.pause.setVisibility(View.GONE);
        }
        if(level==50){
            this.binding.nextBtn.setVisibility(View.GONE);
        }
        Log.d("TAG", "waktu: "+waktu);
        switch (gridSize){
            case 5: if(waktu>4 && waktu<=10){
                binding.star3.setVisibility(View.INVISIBLE);
            }else if(waktu>10){
                binding.star1.setVisibility(View.INVISIBLE); binding.star3.setVisibility(View.INVISIBLE);
            }
            case 10: if(waktu>25 && waktu<=35){
                binding.star3.setVisibility(View.INVISIBLE);
            }else if(waktu>35){
                binding.star1.setVisibility(View.INVISIBLE); binding.star3.setVisibility(View.INVISIBLE);
            }
            case 15: if(waktu>85 && waktu<=115){
                binding.star3.setVisibility(View.INVISIBLE);
            }else if(waktu>115){
                binding.star1.setVisibility(View.INVISIBLE); binding.star3.setVisibility(View.INVISIBLE);
            }
        }

        binding.nextBtn.setOnClickListener(this);
        binding.menuBtn.setOnClickListener(this);
        binding.btnPlay.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view==binding.nextBtn){
            Bundle result = new Bundle();
            result.putInt("GridSize", gridSize);
            result.putInt("level", level+1);
            getParentFragmentManager().setFragmentResult("postKey", result);
            dismiss();
        }
        if(view==binding.menuBtn){
            ((MainActivity)getActivity()).changePage(1);
            getParentFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            dismiss();
        }
        if(view==binding.btnPlay) {
            presenter.resumeStopwatch();
            dismiss();
        }
    }
}
