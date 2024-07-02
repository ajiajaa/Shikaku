package com.raihan.shikaku.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.raihan.shikaku.MainActivity;
import com.raihan.shikaku.databinding.FragmentDialogCreditBinding;

public class CreditDialogFragment extends DialogFragment implements View.OnClickListener {
    protected FragmentDialogCreditBinding binding;
    public static CreditDialogFragment newInstance(){
        CreditDialogFragment fragment = new CreditDialogFragment();

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = FragmentDialogCreditBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        if(((MainActivity)getActivity()).preferences.getBoolean("isTutorial",true)){
            binding.box.setVisibility(View.GONE);
            binding.box1.setVisibility(View.GONE);
            setCancelable(false);
        }else{
            binding.box2.setVisibility(View.GONE);
        }

        binding.btnEnglish.setOnClickListener(this);
        binding.btnIndonesia.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        if(binding.btnEnglish== view){
            ((MainActivity)getActivity()).setLocale("en");
        }else if(binding.btnIndonesia== view){
            ((MainActivity)getActivity()).setLocale("in");
        }
        dismiss();
    }
}
