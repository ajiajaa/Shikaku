package com.raihan.shikaku.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.raihan.shikaku.databinding.FragmentDialogCreditBinding;

public class CreditDialogFragment extends DialogFragment{
    protected FragmentDialogCreditBinding binding;
    public static CreditDialogFragment newInstance(){
        CreditDialogFragment fragment = new CreditDialogFragment();

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        this.binding = FragmentDialogCreditBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }
}
