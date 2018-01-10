package com.hungrytree.sample.items;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.hungrytree.sample.R;
import com.hungrytree.sample.model.ShopModel;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.List;

/**
 * Created by wp.nine on 16/11/15.
 */

public class ShopImageItem extends RecyclerItem<ShopModel,ShopImageItem.ImageViewHolder> {


    public ShopImageItem() {
    }





    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, int position, int viewType) {

    }

    @Override
    public int getItemCount(ShopModel data) {
        if(data == null){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public ImageViewHolder createChildViewHolder(ViewGroup parents,int viewType) {
        Activity activity = (Activity) parents.getContext();
        View view = LayoutInflater.from(activity).inflate(
                R.layout.item_shop_image, parents, false);
        ImageViewHolder mHolder = new ImageViewHolder(view);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int winWidth = dm.widthPixels;
        int advHeight = (int) (winWidth * (90f / 375));
        mHolder.mIvPicture.setLayoutParams(
                new RelativeLayout.LayoutParams(winWidth, advHeight));
        return mHolder;
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvPicture;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mIvPicture = itemView.findViewById(R.id.iv_picture);
        }

    }
}
