package com.raihan.shikaku.view.design;

import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raihan.shikaku.MainActivity;
import com.raihan.shikaku.databinding.FragmentLevelBinding;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Integer> mValues;
    private final OnItemClickListener mListener;
    private final SharedPreferences preferences;
    private int difficulty;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public MyItemRecyclerViewAdapter(List<Integer> items, OnItemClickListener listener, SharedPreferences preferences, int difficulty) {
        this.mValues = items;
        this.mListener = listener;
        this.preferences = preferences;
        this.difficulty = difficulty;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentLevelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mValue = mValues.get(position);
        holder.mIdView.setText(String.valueOf(holder.mValue));
        if(difficulty==5)
            if(preferences.getInt("level_easy", 1)>position)
                holder.mlock.setVisibility(View.GONE);
        if(difficulty==10)
            if(preferences.getInt("level_medium", 1)>position)
                holder.mlock.setVisibility(View.GONE);
        if(difficulty==15)
            if(preferences.getInt("level_hard", 1)>position)
                holder.mlock.setVisibility(View.GONE);

        Log.d("MyItemRecyclerViewAdapter", "onBindViewHolder: Position " + position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mIdView;
        public final View mlock;
        public int mValue;

        public ViewHolder(FragmentLevelBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mlock= binding.lock;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, mValue);
            }
        }
    }
}

