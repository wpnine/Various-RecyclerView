package com.hungrytree.sample.items;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hungrytree.sample.R;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp.nine on 2017/12/8.
 */

public class ExpandLabelItem extends RecyclerItem<ExpandLabelItem.Config,RecyclerView.ViewHolder> {
    Handler mHandler;

    public static final int TYPE_EXPAND = 1;
    public static final int TYPE_ITEMS = 2;
    public ExpandLabelItem(Handler handler) {
        this.mHandler = handler;
    }


    @Override
    public int getChildItemType(int position) {
        if(position == 0){
            return TYPE_EXPAND;
        }else{
            return TYPE_ITEMS;
        }
    }


    @Override
    public RecyclerView.ViewHolder createChildViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_EXPAND:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable,parent,false);
                return new ExpandLabelItem.ExpandableViewHolder(view1);
            case TYPE_ITEMS:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label,parent,false);
                return new ExpandLabelItem.LabelViewHolder(view2);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
        Config config = getData();
        switch (viewType){
            case TYPE_EXPAND:
                ExpandableViewHolder  expandableViewHolder = (ExpandableViewHolder)viewHolder;
                expandableViewHolder.textView.setText(config.itemName + ">>>>>"  + (config.isExpand ? "Close" : "Open" ) + "(Click This)");
                expandableViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHandler.obtainMessage(1,ExpandLabelItem.this).sendToTarget();
                    }
                });
                break;
            case TYPE_ITEMS:
                LabelViewHolder labelViewHolder = (LabelViewHolder)viewHolder;
                String content = config.showList.get(position - 1);
                labelViewHolder.textView.setText(content);
                Log.i("haha","onBindViewHolder=>" +content);
                break;
        }
    }




    @Override
    public int getItemCount(Config data) {
        if(data == null){
            return 0;
        }else{
            if(data.isExpand){
                return data.showList.size() + 1;     //在这个item的数据尾部增加1
            }else{
                return 1;
            }
        }
    }




    public static class LabelViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private LabelViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_label);
        }
    }

    public static class ExpandableViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ExpandableViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_label);
        }
    }



    public static class Config{
        private boolean isExpand = false;
        private String itemName = null;
        private List<String> showList = new ArrayList<>();

        public Config(String itemName,boolean isExpand, List<String> showList) {
            this.itemName = itemName;
            this.isExpand = isExpand;
            this.showList = showList;
        }

        public Config setExpand(boolean expand) {
            isExpand = expand;
            return this;
        }

        public boolean isExpand() {
            return isExpand;
        }
    }
}
