package com.hungrytree.sample.model;

import android.support.annotation.Keep;


import com.hungrytree.sample.adapter.ShopBannerAdapter;
import com.hungrytree.sample.items.ShopConsolItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp.nine on 2017/12/29.
 */
@Keep
public class ShopModel implements ShopConsolItem.ShopConsolSupport,Serializable {

    private String shopName;
    private int shopRange;
    private String addr;
    private int isCollect;//  是否收藏该店铺  1 收藏    0 未收藏",
    private String shopPhone;// 店铺电话",
    private String merchantBrief;//   店铺简介",
    private List<? extends ShopBannerAdapter.ImageUrl> shopPic = new ArrayList<>();//   店铺图片以,分隔   ",
    private int IsFullToOff;//   是否开启满立减优惠   1 开启  0不开启",
    private long fullPrice;//满  多少的价格",
    private long offPrice;// 减多少的价格",
    private String bussinessTime;// 店铺营业时间
    private List<ProductDetail> productList;
    private List<ShopService> shopServiceList;



    public ShopModel setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public ShopModel setShopRange(int shopRange) {
        this.shopRange = shopRange;
        return this;
    }

    public ShopModel setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    public ShopModel setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
        return this;
    }

    public ShopModel setMerchantBrief(String merchantBrief) {
        this.merchantBrief = merchantBrief;
        return this;
    }

    public ShopModel setShopPic(List<? extends ShopBannerAdapter.ImageUrl> shopPic) {
        this.shopPic = shopPic;
        return this;
    }

    public ShopModel setIsFullToOff(int isFullToOff) {
        IsFullToOff = isFullToOff;
        return this;
    }

    public ShopModel setFullPrice(long fullPrice) {
        this.fullPrice = fullPrice;
        return this;
    }

    public ShopModel setOffPrice(long offPrice) {
        this.offPrice = offPrice;
        return this;
    }

    public ShopModel setBussinessTime(String bussinessTime) {
        this.bussinessTime = bussinessTime;
        return this;
    }

    public ShopModel setProductList(List<ProductDetail> productList) {
        this.productList = productList;
        return this;
    }

    public String getShopName() {
        return shopName;
    }


    @Override
    public boolean isCollect() {
        return isCollect == 1;
    }

    public int getShopRange() {
        return shopRange;
    }

    public String getAddr() {
        return addr;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public ShopModel setIsCollect(int isCollect) {
        this.isCollect = isCollect;
        return this;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public String getMerchantBrief() {
        return merchantBrief;
    }

    public List<? extends ShopBannerAdapter.ImageUrl> getShopPic() {
        return shopPic;
    }

    public int getIsFullToOff() {
        return IsFullToOff;
    }

    public long getFullPrice() {
        return fullPrice;
    }

    public long getOffPrice() {
        return offPrice;
    }

    public String getBussinessTime() {
        return bussinessTime;
    }

    public List<ProductDetail> getProductList() {
        return productList;
    }

    public List<ShopService> getShopServiceList() {
        return shopServiceList;
    }

    public ShopModel setShopServiceList(List<ShopService> shopServiceList) {
        this.shopServiceList = shopServiceList;
        return this;
    }

    public static class ShopService{
        private String serviceName;//店铺服务名称
        private int logoPic;//店铺服务图标url


        public ShopService(String serviceName, int logoPic) {
            this.serviceName = serviceName;
            this.logoPic = logoPic;
        }

        public String getServiceName() {
            return serviceName;
        }


        public int getLogoPic() {
            return logoPic;
        }



    }
}
