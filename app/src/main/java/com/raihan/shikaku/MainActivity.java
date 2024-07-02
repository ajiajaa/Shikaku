package com.raihan.shikaku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.LocaleManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;


import com.raihan.shikaku.databinding.ActivityMainBinding;
import com.raihan.shikaku.view.BoardFragment;
import com.raihan.shikaku.view.CreditDialogFragment;
import com.raihan.shikaku.view.IndexFragment;
import com.raihan.shikaku.view.DifficultyFragment;
import com.raihan.shikaku.view.LevelFragment;

import java.util.ArrayList;
import java.util.Locale;

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
    boolean displayedPage;
    private static final String KEY_DISPLAYED_PAGE = "displayedpage";



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_DISPLAYED_PAGE, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = this.getSharedPreferences("Shikaku", Context.MODE_PRIVATE);
//        loadLocale();
        if (savedInstanceState != null) {
            displayedPage = savedInstanceState.getBoolean(KEY_DISPLAYED_PAGE, false);
        }else
            displayedPage = false;

        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.ifg = ifg.newInstance("Fragment Index");
        this.df = df.newInstance("Fragment Difficulty");
        this.lf = lf.newInstance("Fragment Level");
        this.bf = bf.newInstance("Fragment Board");

        isMusicOn  = preferences.getBoolean("isMusicOn",true);//second parameter default value.
        isMusicPlaying = true;
        if(isMusicOn){
            startMusicService();
        }


//      page thingy
        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(!displayedPage){
            if(preferences.getBoolean("isTutorial",true)){
                ft.add(R.id.fragment_container, this.bf);
                CreditDialogFragment cdf = new CreditDialogFragment();
                cdf.show(ft,"a");
            }else{
                ft.add(R.id.fragment_container, this.ifg).commit();
            }
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
    public void setLocale(String lang) {
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(lang);
// Call this on the main thread as it may require Activity.restart()
        AppCompatDelegate.setApplicationLocales(appLocale);
//        preferences.edit().putString("language", lang).apply(); // Store the selected language in SharedPreferences
    }

//    public void loadLocale() {
//        Log.d("TAG", "loadLocale: "+ preferences.getString("language", "en"));
//        Log.d("TAG", "loadLocale: "+ AppCompatDelegate.getApplicationLocales().toString());
//        setLocale(preferences.getString("language", "en"));
//    }

}