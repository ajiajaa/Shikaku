package com.raihan.shikaku.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.raihan.shikaku.MainActivity;
import com.raihan.shikaku.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment implements View.OnClickListener{
    protected FragmentHomeBinding binding;


    public static HomeFragment newInstance(String title){
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.binding = FragmentHomeBinding.inflate(inflater,container,false);
        View view = this.binding.getRoot();

        this.binding.btn5.setOnClickListener(this);
        this.binding.btn10.setOnClickListener(this);
        this.binding.btn15.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int value= 1;
        if(this.binding.btn5== v){
            value= 5;
        }
        if(this.binding.btn10== v){
            value= 10;
        }
        if(this.binding.btn15== v){
            value= 15;
        }
//        kirim gridsize ke fragment board
        Bundle result = new Bundle();
        result.putInt("GridSize", value);

        getParentFragmentManager().setFragmentResult("requestKey", result);

        ((MainActivity)getActivity()).changePage(2);
    }

}
