package com.raihan.shikaku.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.raihan.shikaku.R;
import com.raihan.shikaku.databinding.FragmentBoardBinding;
import com.raihan.shikaku.model.BoardCanvas;

public class BoardFragment extends Fragment{
    protected FragmentBoardBinding binding;

    public static BoardFragment newInstance(String title){
        BoardFragment fragment = new BoardFragment();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.binding = FragmentBoardBinding.inflate(inflater,container,false);
        View view = this.binding.getRoot();
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported.
                int result = bundle.getInt("GridSize");
                Log.d("TAG", "onFragmentResult: "+result);
                binding.ivCanvas.setGridSize(result); // kirim hasil yang diterima dari fragment home ke model board canvas
                // Do something with the result.
            }
        });

        return view;
    }


}
