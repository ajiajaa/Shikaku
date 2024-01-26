package com.raihan.shikaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;


import com.raihan.shikaku.databinding.ActivityMainBinding;
import com.raihan.shikaku.view.BoardFragment;
import com.raihan.shikaku.view.IndexFragment;
import com.raihan.shikaku.view.DifficultyFragment;
import com.raihan.shikaku.view.LevelFragment;

public class MainActivity extends AppCompatActivity{

    protected ActivityMainBinding binding;

    private FragmentManager fragmentManager;
    private IndexFragment ifg;

    private DifficultyFragment df;
    private BoardFragment bf;
    private LevelFragment lf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.ifg = ifg.newInstance("Fragment Index");
        this.df = df.newInstance("Fragment Difficulty");
        this.lf = lf.newInstance("Fragment Level");
        this.bf = bf.newInstance("Fragment Board");


//      page thingy
        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, this.ifg).commit();

        setContentView(binding.getRoot());
    }

    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        if (page == 1) {
            ft.replace(R.id.fragment_container, this.ifg).addToBackStack(null);
        } else if (page == 2) {
            ft.replace(R.id.fragment_container, this.df).addToBackStack(null);
        }else if (page == 3) {
            this.lf.setArguments(null);
            ft.replace(R.id.fragment_container, this.lf).addToBackStack(null);
        }else if (page == 4) {
            ft.replace(R.id.fragment_container, this.bf).addToBackStack(null);
        }
        ft.commit();
    }
}