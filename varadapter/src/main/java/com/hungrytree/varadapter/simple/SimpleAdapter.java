package com.hungrytree.varadapter.simple;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hungrytree.varadapter.IDelegation;
import com.hungrytree.varadapter.RVDelegation;

/**
 * Created by wp.nine on 2017/12/8.
 */

public class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private IDelegation mDelegation;
    public SimpleAdapter(IDelegation delegation) {
        mDelegation = delegation;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mDelegation.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mDelegation.onBindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return mDelegation.getItemCount();
    }


    @Override
    public int getItemViewType(int position) {
        return mDelegation.getItemViewType(position);
    }
}
