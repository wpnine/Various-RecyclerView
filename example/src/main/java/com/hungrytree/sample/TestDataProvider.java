package com.hungrytree.sample;

import com.hungrytree.sample.R;
import com.hungrytree.sample.adapter.ShopBannerAdapter;
import com.hungrytree.sample.model.ProductDetail;
import com.hungrytree.sample.model.ShopModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wp.nine on 2018/1/9.
 */

public class TestDataProvider {

    public static ShopModel getShopExample() {
        return new ShopModel()
                .setShopName("测试商店10009号(深圳分店)")
                .setShopPhone("999999")
                .setShopPic(Arrays.asList(new Pic(R.mipmap.ic_launcher_round), new Pic(R.mipmap.ic_launcher_round)))
                .setIsFullToOff(1)
                .setFullPrice(10000)
                .setOffPrice(2000)
                .setMerchantBrief("《三国演义》反映了丰富的历史内容，人物名称、地理名称、主要事件与《三国志》基本相同。人物性格也是在《三国志》留下的固定形象基础上，才进行再发挥，进行夸张、美化、丑化等等，这也是历史演义小说的套路。《三国演义》一方面反映了较为真实的三国历史，照顾到读者希望了解真实历史的需要；另一方面，根据明朝社会的实际情况对三国人物进行了夸张、美化、丑化等等。\n" +
                        "故事远起汉灵帝年间刘、关、张桃园结义，描述了东汉末年和三国时期近百年发生的重大历史事件，和众多的叱咤风云的英雄人物。作者通过真实动人的故事，揭示了封建统治阶级内部的黑暗和腐朽，控诉了统治者的暴虐和丑恶。东汉末年，军阀混战，所谓“十八路”诸侯联军征讨董卓，打的是“扶持王室，拯救黎民”的旗号，干的是勾心斗角、尔虞我诈的勾当，都企图称王称霸。《三国演义》以没落的汉室宗亲刘备和以宗族起兵的曹操作为两条主线的展开了中前期的故事，而中后期以大汉丞相诸葛亮率领汉军北伐，与魏国重臣司马懿的斗智斗勇为主线，以三国归晋而告终。")
                .setAddr("广东省深圳市南山区ABC栋102")
                .setShopRange(100)
                .setShopServiceList(getShopService())
                .setBussinessTime("周一至周五08:00~22:00\n周六至日 09:00~23:00")
                .setProductList(getProductList())
                .setIsCollect(1);

    }

    public static List<ShopModel.ShopService> getShopService() {
        List<ShopModel.ShopService> shopServiceList = new ArrayList<>();
        shopServiceList.add(new ShopModel.ShopService("免费WiFi", R.mipmap.ic_launcher));
        shopServiceList.add(new ShopModel.ShopService("移动支付", R.mipmap.ic_launcher));
        shopServiceList.add(new ShopModel.ShopService("支持停车", R.mipmap.ic_launcher));
        shopServiceList.add(new ShopModel.ShopService("婴儿专座", R.mipmap.ic_launcher));
        shopServiceList.add(new ShopModel.ShopService("露天餐位", R.mipmap.ic_launcher));
        return shopServiceList;
    }

    public static List<ProductDetail> getProductList() {
        List<ProductDetail> productList = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            productList.add(new ProductDetail("商品" + (i + 1), 70000, 10000, R.mipmap.ic_launcher));
        }
        return productList;
    }


    public static class Pic implements ShopBannerAdapter.ImageUrl {
        private int imageRes = -1;

        public Pic(int imageRes) {
            this.imageRes = imageRes;
        }

        @Override
        public int getImageRes() {
            return this.imageRes;
        }
    }
}
