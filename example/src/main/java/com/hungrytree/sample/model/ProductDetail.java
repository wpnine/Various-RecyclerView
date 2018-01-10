package com.hungrytree.sample.model;

import android.support.annotation.Keep;


import com.hungrytree.sample.adapter.ShopBannerAdapter;

import java.util.List;

/**
 * Created by wp.nine on 2017/12/27.
 * 优惠券商品详情
 */
@Keep
public class ProductDetail{
    private String name;// 商品名称",
    private long realPrice;//  商品真实售价,单位为分的字符串",
    private long fakePrice;//  商品划线价,单位为分的字符串",
    private int coverImage;

    public ProductDetail(String name, long realPrice, long fakePrice, int coverImage) {
        this.name = name;
        this.realPrice = realPrice;
        this.fakePrice = fakePrice;
        this.coverImage = coverImage;
    }

    public int getCoverImage() {
        return coverImage;
    }


    public String getName() {
        return name;
    }

    public long getRealPrice() {
        return realPrice;
    }

    public long getFakePrice() {
        return fakePrice;
    }


}
