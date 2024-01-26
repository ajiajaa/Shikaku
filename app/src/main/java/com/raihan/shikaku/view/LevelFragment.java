package com.raihan.shikaku.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raihan.shikaku.MainActivity;
import com.raihan.shikaku.R;
import com.raihan.shikaku.model.Level;
import com.raihan.shikaku.presenter.LevelContract;
import com.raihan.shikaku.presenter.LevelPresenter;
import com.raihan.shikaku.view.design.MyItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class LevelFragment extends Fragment implements LevelContract.View {
    private int mColumnCount = 5;
    private LevelPresenter presenter;
    private RecyclerView recyclerView;
    private MyItemRecyclerViewAdapter adapter;
    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_LEVEL_DATA = "levelData";
    private int difficulty;
    private List<Integer> levelData;

    private Level model;
    public LevelFragment() {
    }

    public static LevelFragment newInstance(String title) {
        LevelFragment fragment = new LevelFragment();
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_DIFFICULTY, difficulty);
        outState.putIntegerArrayList(KEY_LEVEL_DATA, (ArrayList<Integer>) levelData);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            difficulty = savedInstanceState.getInt(KEY_DIFFICULTY);
            levelData = savedInstanceState.getIntegerArrayList(KEY_LEVEL_DATA);
        }
        presenter = new LevelPresenter(this, this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level_list, container, false);

        recyclerView = view.findViewById(R.id.list);

        // Set the adapter
        Context context = view.getContext();
        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));

        if (levelData != null) {
            showData(levelData);
        }

        // Meminta presenter untuk menyediakan data
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                int result = bundle.getInt("difficulty");
                difficulty = result;
                presenter.sendDifficulty(result);
                presenter.requestData();
                Log.d("TAG", "getBundleLevelFragment: " + result);
            }
        });


        return view;
    }

    @Override
    public void showData(List<Integer> data) {
        levelData = data;
        // Menampilkan data ke RecyclerView
        adapter = new MyItemRecyclerViewAdapter(data, new MyItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int value) {
                presenter.onItemClick(value);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showItemClick(int value) {
        Bundle result = new Bundle();
        result.putInt("GridSize", difficulty);
        result.putInt("level", value);
        getParentFragmentManager().setFragmentResult("postKey", result);

        ((MainActivity)getActivity()).changePage(4);

        Log.d("Item Clicked", "Value: " + value +" & diff: "+difficulty);
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        // Membersihkan referensi presenter saat tampilan dihancurkan
//        presenter.detachView();
//    }
}


