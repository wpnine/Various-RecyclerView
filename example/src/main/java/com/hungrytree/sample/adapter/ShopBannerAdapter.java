package com.hungrytree.sample.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp.nine on 2017/12/02.
 */
public class ShopBannerAdapter extends PagerAdapter {

    private final String mLock = new String();
    private Context context;
    private List<ImageUrl> adverts = new ArrayList<>();
    public ShopBannerAdapter(Context context) {
        this.context = context;
    }

    public ShopBannerAdapter(Context context, List<? extends ImageUrl> data) {
        this.context = context;
        addAll(data);
    }



    @Override
    public int getCount() {
        return  adverts.size();
    }


    @Override
    public final boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //避免数据变化没有更新
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    public void addAll(List<? extends ImageUrl> newData) {
        synchronized (mLock) {
            if (adverts != null) {
                adverts.clear();
                adverts.addAll(newData);
            }
        }
        notifyDataSetChanged();
    }


    public ShopBannerAdapter.ImageUrl getItem(final int position) {
        return adverts.get(position);
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        if (convertView == null) {
            imageView = new ImageView(parent.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            imageView = (ImageView) convertView;
        }


        final ImageUrl imageUrl = getItem(position);
        imageView.setImageResource(imageUrl.getImageRes());
        return imageView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(position, null, container);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    public interface ImageUrl {
        public int getImageRes();
    }



}

