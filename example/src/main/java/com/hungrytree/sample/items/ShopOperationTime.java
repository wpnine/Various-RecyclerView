package com.hungrytree.sample.items;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hungrytree.sample.R;
import com.hungrytree.varadapter.item.RecyclerItem;


/**
 * Created by wp.nine on 2018/1/4.
 * 营业时间
 */

public class ShopOperationTime extends RecyclerItem<String, ShopOperationTime.OpenTimeViewHolder> {

    @Override
    public OpenTimeViewHolder createChildViewHolder(ViewGroup parents, int type) {
        View view = LayoutInflater.from(parents.getContext()).inflate(R.layout.item_shop_open_time, parents, false);
        return new OpenTimeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(OpenTimeViewHolder holder, int position, int viewType) {
        holder.mTvOpenTime.setText(getData());
    }

    @Override
    public int getItemCount(String data) {
        if (data == null) {
            return 0;
        }
        return 1;
    }

    static class OpenTimeViewHolder extends RecyclerView.ViewHolder {
        TextView mTvOpenTime;

        OpenTimeViewHolder(View view) {
            super(view);
            mTvOpenTime = view.findViewById(R.id.tv_open_time);
        }
    }

}
