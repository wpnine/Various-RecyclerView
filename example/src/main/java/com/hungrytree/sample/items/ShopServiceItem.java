package com.hungrytree.sample.items;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.hungrytree.sample.R;
import com.hungrytree.sample.model.ShopModel;
import com.hungrytree.varadapter.decoration.ILine;
import com.hungrytree.varadapter.decoration.ItemDecorator;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.List;


/**
 * Created by wp.nine on 2018/1/4.
 */

public class ShopServiceItem extends RecyclerItem<List<ShopModel.ShopService>, ShopServiceItem.ServiceViewHolder> {


    @Override
    public int getSpaceCount(int maxCount) {
        return maxCount / 3;
    }

    @Override
    public ServiceViewHolder createChildViewHolder(ViewGroup parents, int viewType) {
        View view = LayoutInflater.from(parents.getContext()).inflate(R.layout.item_shop_service, parents, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position, int viewType) {
        if(position < getData().size()){
            ShopModel.ShopService shopService = getData().get(position);
            holder.mIvIcon.setImageResource(shopService.getLogoPic());
            holder.mTvLabel.setText(shopService.getServiceName());
        }else{
            holder.mIvIcon.setImageDrawable(null);
            holder.mTvLabel.setText("");
        }

    }

    @Override
    public int getItemCount(List<ShopModel.ShopService> data) {
        if (data == null || data.size() == 0) {
            return 0;
        }
        int count = data.size();

        if(count % 3 != 0){
            return count + (3 - (count % 3));
        }else{
            return count;
        }

    }

    @Override
    public ItemDecorator getDecorator(int position) {
        return new ItemDecorator()
                .setBackgroundColor(Color.WHITE);
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvIcon;
        TextView mTvLabel;


        public ServiceViewHolder(View view) {
            super(view);
            mIvIcon = view.findViewById(R.id.iv_icon);
            mTvLabel = view.findViewById(R.id.tv_label);
        }
    }

}
