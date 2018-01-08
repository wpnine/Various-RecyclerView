package com.hungrytree.sample.items;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hungrytree.sample.R;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.List;

/**
 * Created by wp.nine on 2017/12/8.
 */

public class LabelItem extends RecyclerItem<List<String>,LabelItem.LabelViewHolder> {
    private int backgroundColor = Color.WHITE;
    public LabelItem() {
    }


    public LabelItem(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public LabelItem.LabelViewHolder createChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label,parent,false);
        return new LabelItem.LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LabelItem.LabelViewHolder viewHolder, int position, int viewType) {
        String content = getData().get(position);
        viewHolder.textView.setText(content);
        viewHolder.textView.setBackgroundColor(backgroundColor);
        Log.i("haha","onBindViewHolder=>" +content);
    }


    @Override
    public int getItemCount(List<String> data) {
        if(data == null){
            return 0;
        }else{
            return data.size();
        }
    }

    @Override
    public int getSpaceCount(int maxCount) {
        return 1;
    }

    public static class LabelViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private LabelViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_label);
        }
    }
}
