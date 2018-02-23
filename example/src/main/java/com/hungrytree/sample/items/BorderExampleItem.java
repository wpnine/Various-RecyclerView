package com.hungrytree.sample.items;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hungrytree.sample.R;
import com.hungrytree.varadapter.decoration.BorderLine;
import com.hungrytree.varadapter.decoration.ItemDecorator;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.List;

/**
 * Created by wp.nine on 2017/12/8.
 */

public class BorderExampleItem extends RecyclerItem<List<String>,BorderExampleItem.LabelViewHolder> {
    private final ItemDecorator DIVIDER;


    public BorderExampleItem() {

        DIVIDER = new ItemDecorator()
                .setBackgroundColor(Color.WHITE)                        //Item的背景颜色
                .setIncludeMargin(true)                                 //绘制的基准范围是否有包含margin
                .addLine(new BorderLine()
                        .setLinePosition(BorderLine.POSITION_BOTTOM)    //指定分隔线的位置
                        .setLineWidth(10)                               //分隔线的宽度
                        .setLineLength(BorderLine.LENGTH_MATCH_ITEM)    //分隔线的长度
                        .setColor(Color.BLACK)                          //颜色
                        .setGravity(Gravity.CENTER)                     //分隔线基于边界面水平和垂直居中
                        .setOffsetBaseLine(0)                           //对分隔线的位置做一定程序的偏离
                        .setReduceLineLength(40)                        //在原有的长度上对分隔线做一定的缩小
                );
    }


    @Override
    public BorderExampleItem.LabelViewHolder createChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label,parent,false);
        return new BorderExampleItem.LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BorderExampleItem.LabelViewHolder viewHolder, int position, int viewType) {
        String content = getData().get(position);
        viewHolder.textView.setText(content);
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
    public ItemDecorator getDecorator(int position) {
        if(position == getData().size() - 1){
            return null;
        }
        return DIVIDER;
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
