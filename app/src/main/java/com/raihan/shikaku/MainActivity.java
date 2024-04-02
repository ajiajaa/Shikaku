package com.raihan.shikaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private boolean isMusicPlaying;
    public boolean isMusicOn;
    public SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.ifg = ifg.newInstance("Fragment Index");
        this.df = df.newInstance("Fragment Difficulty");
        this.lf = lf.newInstance("Fragment Level");
        this.bf = bf.newInstance("Fragment Board");

        preferences = this.getSharedPreferences("Shikaku", Context.MODE_PRIVATE);
        isMusicOn  = preferences.getBoolean("isMusicOn",true);//second parameter default value.
        isMusicPlaying = true;
        if(isMusicOn){
            startMusicService();
        }


//      page thingy
        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(preferences.getBoolean("isTutorial",true)){
            ft.add(R.id.fragment_container, this.bf).commit();
        }else{
            ft.add(R.id.fragment_container, this.ifg).commit();
        }

        setContentView(binding.getRoot());
    }
    public void musicSetting(){
        if(isMusicOn){
            stopMusicService();
            preferences.edit().putBoolean("isMusicOn", false).apply();
            isMusicOn = false;
        }else{
            startMusicService();
            preferences.edit().putBoolean("isMusicOn", true).apply();
            isMusicOn = true;
        }

    }
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
//        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        if (page == 0) {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.fragment_container, this.ifg);
        } else if (page == 1) {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.fragment_container, this.ifg).addToBackStack(null);
        }else if (page == 2) {
            ft.replace(R.id.fragment_container, this.df).addToBackStack(null);
        }else if (page == 3) {
            this.lf.setArguments(null);
            ft.replace(R.id.fragment_container, this.lf).addToBackStack(null);
        }else if (page == 4) {
            ft.replace(R.id.fragment_container, this.bf).addToBackStack(null);
        }
        ft.commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Hentikan layanan musik saat aplikasi berpindah ke background (misalnya saat menekan tombol "Home")
        if (isMusicPlaying) {
            stopMusicService();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Mulai kembali layanan musik saat aplikasi dibuka kembali dari background
        if (!isMusicPlaying && isMusicOn) {
            startMusicService();
        }
    }
    private void startMusicService() {
        startService(new Intent(this, MusicService.class));
        isMusicPlaying = true;
    }

    private void stopMusicService() {
        stopService(new Intent(this, MusicService.class));
        isMusicPlaying = false;
    }

}