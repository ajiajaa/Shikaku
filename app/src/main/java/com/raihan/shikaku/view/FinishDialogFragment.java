package com.raihan.shikaku.view;

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

public class FinishDialogFragment extends DialogFragment implements View.OnClickListener{
    protected FragmentDialogFinishBinding binding;

    private static int gridSize;
    private static int level;
    private static int waktu;
    private static String waktuText;
    public static FinishDialogFragment newInstance(int gridSize, int level, int waktu, String waktuText){
        FinishDialogFragment fragment = new FinishDialogFragment();
        FinishDialogFragment.gridSize = gridSize;
        FinishDialogFragment.level= level;
        FinishDialogFragment.waktu= waktu;
        FinishDialogFragment.waktuText= waktuText;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = FragmentDialogFinishBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        this.binding.congrats.setText(this.binding.congrats.getText().toString()+waktuText);
        Log.d("TAG", "waktu: "+waktu);
        switch (gridSize){
            case 5: if(waktu>4 && waktu<10){
                binding.star3.setVisibility(View.INVISIBLE);
            }else if(waktu>10){
                binding.star1.setVisibility(View.INVISIBLE); binding.star3.setVisibility(View.INVISIBLE);
            }
            case 10: if(waktu>25 && waktu<35){
                binding.star3.setVisibility(View.INVISIBLE);
            }else if(waktu>35){
                binding.star1.setVisibility(View.INVISIBLE); binding.star3.setVisibility(View.INVISIBLE);
            }
            case 15: if(waktu>85 && waktu<115){
                binding.star3.setVisibility(View.INVISIBLE);
            }else if(waktu>115){
                binding.star1.setVisibility(View.INVISIBLE); binding.star3.setVisibility(View.INVISIBLE);
            }
        }

        binding.nextBtn.setOnClickListener(this);
        binding.menuBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view==binding.nextBtn){
            Bundle result = new Bundle();
            result.putInt("GridSize", gridSize);
            result.putInt("level", level+1);
            getParentFragmentManager().setFragmentResult("postKey", result);
            ((MainActivity)getActivity()).changePage(4);
            getParentFragmentManager().popBackStack();
            dismiss();
        }
        if(view==binding.menuBtn){
            ((MainActivity)getActivity()).changePage(1);
            getParentFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            dismiss();
        }
    }
}
