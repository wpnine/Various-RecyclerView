package com.hungrytree.sample.items;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hungrytree.sample.R;
import com.hungrytree.varadapter.decoration.BorderLine;
import com.hungrytree.varadapter.decoration.ItemDecorator;
import com.hungrytree.varadapter.item.RecyclerItem;

/**
 * Created by wp.nine on 2017/12/8.
 */

public class DecoratorExampleItem extends RecyclerItem<Object,DecoratorExampleItem.LabelViewHolder> {
    private int mShowColor = 0xff778899;
    private boolean isUpColor = false;

    private int mLineMove = 0;
    private boolean isInterMoveLine = true;


    public DecoratorExampleItem() {
    }


    @Override
    public DecoratorExampleItem.LabelViewHolder createChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_rect,parent,false);
        return new DecoratorExampleItem.LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DecoratorExampleItem.LabelViewHolder viewHolder, int position, int viewType) {
    }


    @Override
    public int getItemCount(Object data) {
        return 1;
    }


    @Override
    public ItemDecorator getDecorator(int position) {
        int nextValue = mShowColor & 0xff;
        if(nextValue == 255){
            isUpColor = false;
        }else if(nextValue == 0){
            isUpColor = true;
        }

        if(isUpColor){
            nextValue += 1;
        }else{
            nextValue -= 1;
        }
        mShowColor = ((mShowColor & 0xffffff00) | nextValue);



        if(mLineMove >= 240){
            isInterMoveLine = false;
        }else if(mLineMove <= 0){
            isInterMoveLine = true;
        }


        if(isInterMoveLine){
            mLineMove += 2;
        }else{
            mLineMove -= 2;
        }

        final int LINE_WIDTH = 10;
        final int DEFAULT_REDUCE = 40;

        return new ItemDecorator()
                .setBackgroundColor(mShowColor)                         //Item的背景颜色
                .setIncludeMargin(true)                                 //绘制的基准范围是否有包含margin
                .addLine(new BorderLine()
                        .setLinePosition(BorderLine.POSITION_BOTTOM)    //指定分隔线的位置
                        .setLineWidth(LINE_WIDTH)                               //分隔线的宽度
                        .setLineLength(BorderLine.LENGTH_MATCH_ITEM)    //分隔线的长度
                        .setColor(Color.BLACK)                          //颜色
                        .setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP)                     //分隔线基于边界面水平和垂直居中
                        .setOffsetBaseLine(-mLineMove)                           //对分隔线的位置做一定程序的偏离
                        .setReduceLineLength(mLineMove + DEFAULT_REDUCE)                        //在原有的长度上对分隔线做一定的缩小
                )
                .addLine(new BorderLine()
                        .setLinePosition(BorderLine.POSITION_TOP)
                        .setLineWidth(LINE_WIDTH)
                        .setLineLength(BorderLine.LENGTH_MATCH_ITEM)
                        .setColor(Color.BLACK)
                        .setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM)
                        .setOffsetBaseLine(mLineMove)
                        .setReduceLineLength(mLineMove + DEFAULT_REDUCE)
                )
                .addLine(new BorderLine()
                        .setLinePosition(BorderLine.POSITION_LEFT)
                        .setLineWidth(LINE_WIDTH)
                        .setLineLength(BorderLine.LENGTH_MATCH_ITEM)
                        .setColor(Color.BLACK)
                        .setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                        .setOffsetBaseLine(mLineMove)
                        .setReduceLineLength(mLineMove + DEFAULT_REDUCE)
                )
                .addLine(new BorderLine()
                        .setLinePosition(BorderLine.POSITION_RIGHT)
                        .setLineWidth(LINE_WIDTH)
                        .setLineLength(BorderLine.LENGTH_MATCH_ITEM)
                        .setColor(Color.BLACK)
                        .setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT)
                        .setOffsetBaseLine(-mLineMove)
                        .setReduceLineLength(mLineMove + DEFAULT_REDUCE)
                );
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
