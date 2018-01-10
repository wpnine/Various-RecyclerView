package com.hungrytree.sample.items;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.hungrytree.sample.R;
import com.hungrytree.sample.adapter.ShopBannerAdapter;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.List;


/**
 * Created by ghg on 2016/03/02.
 * 图片banner
 */
public class ShopBannerItem extends RecyclerItem<List<? extends ShopBannerAdapter.ImageUrl>, ShopBannerItem.BannerViewHolder> {

    private float scaleValue = 0f;


    public ShopBannerItem(float scale) {
        scaleValue = scale;
    }


    @Override
    public void onBindViewHolder(BannerViewHolder holder, int position, int viewType) {
        holder.mBannerAdapter.addAll(getData());
        holder.mBannerAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount(List<? extends ShopBannerAdapter.ImageUrl> data) {
        if(data == null){
            return 0;
        }
        return 1;
    }

    @Override
    public BannerViewHolder createChildViewHolder(ViewGroup parents, int type) {
        Activity activity = (Activity) parents.getContext();
        View view = LayoutInflater.from(activity).inflate(
                R.layout.item_shop_banner, parents, false);
        BannerViewHolder holder = new BannerViewHolder(view);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int winWidth = dm.widthPixels;
        int advHeight = (int) (winWidth * scaleValue);
        holder.mVpBanner.setLayoutParams(
                new RelativeLayout.LayoutParams(winWidth, advHeight));
        return holder;
    }


    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        ViewPager mVpBanner;
        ShopBannerAdapter mBannerAdapter;

        public BannerViewHolder(View itemView) {
            super(itemView);
            mVpBanner = itemView.findViewById(R.id.vp_banner);
            initView(itemView.getContext());
        }

        private void initView(Context context) {
            mBannerAdapter = new ShopBannerAdapter(context);
            mVpBanner.setAdapter(mBannerAdapter);
        }


    }
}
