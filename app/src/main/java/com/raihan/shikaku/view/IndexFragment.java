package com.raihan.shikaku.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(getContext())
                        .setTitle("Exit Shikaku")
                        .setMessage("Are you sure you want to exit Shikaku?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requireActivity().finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.binding = FragmentIndexBinding.inflate(inflater,container,false);
        if(((MainActivity)getActivity()).isMusicOn){
            this.binding.nomusic.setVisibility(View.GONE);
        }else{
            this.binding.music.setVisibility(View.GONE);
        }
        View view = this.binding.getRoot();
        this.binding.play.setOnClickListener(this);
        this.binding.music.setOnClickListener(this);
        this.binding.nomusic.setOnClickListener(this);
        this.binding.how.setOnClickListener(this);
        this.binding.bug.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(this.binding.play== v){
            ((MainActivity)getActivity()).changePage(2);
        }
        if(this.binding.music== v){
            ((MainActivity)getActivity()).musicSetting();
            binding.music.setVisibility(View.GONE);
            binding.nomusic.setVisibility(View.VISIBLE);
        }
        if(this.binding.nomusic== v){
            ((MainActivity)getActivity()).musicSetting();
            binding.nomusic.setVisibility(View.GONE);
            binding.music.setVisibility(View.VISIBLE);
        }
        if(this.binding.how== v){
            CreditDialogFragment cdf = new CreditDialogFragment();
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            cdf.show(ft,"a");
        }
        if(this.binding.bug== v){
            String url = "https://dpougqu6ock.typeform.com/to/XnjDEEnc";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

}
