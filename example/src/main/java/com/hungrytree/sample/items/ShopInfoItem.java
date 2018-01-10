package com.hungrytree.sample.items;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hungrytree.sample.R;
import com.hungrytree.sample.model.ShopModel;
import com.hungrytree.sample.other.FormatUtil;
import com.hungrytree.varadapter.item.RecyclerItem;


/**
 * Created by wp.nine on 2017/12/28.
 */

public class ShopInfoItem extends RecyclerItem<ShopModel, ShopInfoItem.InfoViewHolder> {

    @Override
    public InfoViewHolder createChildViewHolder(ViewGroup parents, int viewType) {
        return new InfoViewHolder(LayoutInflater.from(parents.getContext()).inflate(R.layout.item_coupon_shop_info, parents, false));
    }


    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position, int viewType) {
        Context context = holder.mConvertView.getContext();
        ShopModel shopModel = getData();
        holder.mTvShopName.setText(shopModel.getShopName());

        if(shopModel.getIsFullToOff() == 1){
            holder.mTvDiscountFlag.setVisibility(View.VISIBLE);
            holder.mTvDiscountFlag.setText(context.getString(
                    R.string.shop_coupon_discount_hint,
                    FormatUtil.formatPrice(shopModel.getFullPrice()),
                    FormatUtil.formatPrice(shopModel.getOffPrice())));
        }else{
            holder.mTvDiscountFlag.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount(ShopModel data) {
        if (data == null) {
            return 0;
        }
        return 1;
    }


    public static class InfoViewHolder extends RecyclerView.ViewHolder {
        TextView mTvShopName;
        TextView mTvDiscountFlag;
        View mConvertView = itemView;
        public InfoViewHolder(View itemView) {
            super(itemView);
            mTvShopName = itemView.findViewById(R.id.tv_shop_name);
            mTvDiscountFlag = itemView.findViewById(R.id.tv_discount_flag);
        }

    }


}
