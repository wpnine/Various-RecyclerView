package com.hungrytree.sample.items;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hungrytree.sample.R;
import com.hungrytree.sample.model.TitleModel;
import com.hungrytree.varadapter.item.RecyclerItem;

/**
 * Created by wp.nine on 2017/12/28.
 * 显示标题，多个页面复用
 */
public class ShopTitleItem extends RecyclerItem<TitleModel, ShopTitleItem.TitleViewHolder> {


    @Override
    public TitleViewHolder createChildViewHolder(ViewGroup parents, int viewType) {
        return new TitleViewHolder(LayoutInflater.from(parents.getContext()).inflate(R.layout.item_shop_title, parents, false));
    }


    @Override
    public void onBindViewHolder(TitleViewHolder holder, int position, int viewType) {
        TitleModel itemTitle = getData();
        Context context = holder.convertView.getContext();
        holder.convertView.setOnClickListener(itemTitle.getListener());
        holder.mTvTitle.setText(itemTitle.getTitle());
        holder.mTvSubInfo.setText(itemTitle.getSubTitle());
        if(itemTitle.getListener() == null){
            holder.mTvSubInfo.setVisibility(View.INVISIBLE);
        }else{
            holder.mTvSubInfo.setVisibility(View.VISIBLE);
        }

        if(itemTitle.getTitleDrawable() >= 0){
            Drawable drawable = context.getResources().getDrawable(itemTitle.getTitleDrawable());
            holder.mTvTitle.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        }else{
            holder.mTvTitle.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }
    }

    @Override
    public int getItemCount(TitleModel data) {
        if (data != null) {
            return 1;
        } else {
            return 0;
        }
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        TextView mTvSubInfo;
        View convertView;
        public TitleViewHolder(View itemView) {
            super(itemView);
            convertView = itemView;
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvSubInfo = itemView.findViewById(R.id.tv_sub_info);
        }
    }


}
