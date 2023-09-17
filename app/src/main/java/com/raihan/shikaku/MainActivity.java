package com.raihan.shikaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;


import com.raihan.shikaku.databinding.ActivityMainBinding;
import com.raihan.shikaku.view.BoardFragment;
import com.raihan.shikaku.view.HomeFragment;

public class MainActivity extends AppCompatActivity{

    protected ActivityMainBinding binding;

    private FragmentManager fragmentManager;

    private HomeFragment hf;
    private BoardFragment bf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.hf = hf.newInstance("Fragment Home");
        this.bf = bf.newInstance("Fragment Board");


//      page thingy
        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, this.hf).commit();

        setContentView(binding.getRoot());
    }

    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (page == 1) {
            ft.replace(R.id.fragment_container, this.hf).addToBackStack(null);
        } else if (page == 2) {
            ft.replace(R.id.fragment_container, this.bf).addToBackStack(null);
        }
        ft.commit();
    }
}