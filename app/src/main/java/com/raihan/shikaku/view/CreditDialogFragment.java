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
import com.raihan.shikaku.databinding.FragmentDialogCreditBinding;
import com.raihan.shikaku.databinding.FragmentDialogFinishBinding;
import com.raihan.shikaku.presenter.BoardPresenter;

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
