package com.hungrytree.sample.items;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hungrytree.sample.R;
import com.hungrytree.varadapter.item.RecyclerItem;


/**
 * Created by wp.nine on 2017/12/28.
 * 用于占位的透明控件
 */

public class EmptySpaceItem extends RecyclerItem<Integer,EmptySpaceItem.EmptyViewHolder> {


    public EmptySpaceItem() {
    }


    @Override
    public EmptyViewHolder createChildViewHolder(ViewGroup parents, int viewType) {
        View view = LayoutInflater.from(parents.getContext()).inflate(R.layout.item_bottom_space,parents,false);
        return new EmptyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmptyViewHolder viewHolder, int position, int viewType) {
        viewHolder.convertView.getLayoutParams().height = getData();
    }

    @Override
    public int getItemCount(Integer data) {
        if(data == null){
            return 0;
        }
        return 1;
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder{
        View convertView;
        public EmptyViewHolder(View itemView) {
            super(itemView);
            convertView = itemView;
        }
    }


}
