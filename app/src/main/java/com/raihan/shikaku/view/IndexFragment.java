package com.raihan.shikaku.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.raihan.shikaku.MainActivity;
import com.raihan.shikaku.databinding.FragmentIndexBinding;


public class IndexFragment extends Fragment implements View.OnClickListener{
    protected FragmentIndexBinding binding;


    public static IndexFragment newInstance(String title){
        IndexFragment fragment = new IndexFragment();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.binding = FragmentIndexBinding.inflate(inflater,container,false);
        View view = this.binding.getRoot();

        this.binding.play.setOnClickListener(this);
        this.binding.music.setOnClickListener(this);
        this.binding.how.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(this.binding.play== v){
            ((MainActivity)getActivity()).changePage(2);
        }
        if(this.binding.music== v){
            FinishDialogFragment fdf= new FinishDialogFragment();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            fdf.show(ft,"a");
        }
        if(this.binding.how== v){
        }
    }

}
