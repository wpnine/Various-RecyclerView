package com.hungrytree.sample.items;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.hungrytree.sample.R;
import com.hungrytree.sample.model.ProductDetail;
import com.hungrytree.sample.other.FormatUtil;
import com.hungrytree.varadapter.decoration.BorderLine;
import com.hungrytree.varadapter.decoration.ItemDecorator;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.List;

/**
 * 本地精选页面的标题item
 * Created by wp.nine on 2016/9/14.
 */
public class ProductItem extends RecyclerItem<List<ProductDetail>, ProductItem.GoodsViewHolder> {
    private ItemDecorator LEFT_DECORATOR = new ItemDecorator()
            .setBackgroundColor(Color.WHITE)
            .addLine(new BorderLine()
                    .setLinePosition(BorderLine.POSITION_BOTTOM)
                    .setColor(0xffe6e6e6)
                    .setLineLength(BorderLine.LENGTH_MATCH_ITEM)
                    .setLineWidth(1)
                    .setGravity(Gravity.CENTER)
            )
            .addLine(new BorderLine()
                    .setLinePosition(BorderLine.POSITION_RIGHT)
                    .setColor(0xffe6e6e6)
                    .setLineLength(BorderLine.LENGTH_MATCH_ITEM)
                    .setLineWidth(1)
                    .setGravity(Gravity.CENTER)
            );

    private ItemDecorator RIGHT_DECORATOR = new ItemDecorator()
            .setBackgroundColor(Color.WHITE)
            .addLine(new BorderLine()
                    .setLinePosition(BorderLine.POSITION_BOTTOM)
                    .setColor(0xffe6e6e6)
                    .setLineLength(BorderLine.LENGTH_MATCH_ITEM)
                    .setLineWidth(1)
                    .setGravity(Gravity.CENTER)
            );


    private int mWinWidth = 0;
    private int mCurCount = 0;
    public ProductItem() {
    }

    @Override
    public GoodsViewHolder createChildViewHolder(ViewGroup parents, int type) {
        Context context = parents.getContext();
        if(mWinWidth == 0){
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            mWinWidth = dm.widthPixels;
        }

        View view = LayoutInflater.from(parents.getContext()).inflate(R.layout.item_product_shortcut, parents, false);
        GoodsViewHolder viewHolder = new GoodsViewHolder(view);
        int width = mWinWidth * 140 / 375;  //计算图片框的大小
        ViewGroup.LayoutParams params = viewHolder.mIvGoodsPic.getLayoutParams();
        params.width = width;
        params.height = width;      //高宽一致
        return viewHolder;
    }

    @Override
    public int getSpaceCount(int maxCount) {
        return maxCount/2;
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder viewHolder, int position, int viewType) {
        final ProductDetail entity = getData().get(position);

        viewHolder.mTvActualPrice.setText("¥" + FormatUtil.formatPrice(entity.getFakePrice()));
        viewHolder.mTvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        viewHolder.mTvOriginPrice.setText("¥" + FormatUtil.formatPrice(entity.getRealPrice()));
        viewHolder.mIvGoodsPic.setImageResource( entity.getCoverImage());
        viewHolder.mTvGoodsTitle.setText(entity.getName());
    }

    @Override
    public int getItemCount(List<ProductDetail> data) {
        if (data == null) {
            mCurCount = 0;
        }else{
            mCurCount = data.size();
        }
        return mCurCount;

    }

    @Override
    public ItemDecorator getDecorator(int position) {
        if(position % 2 == 0){
            return LEFT_DECORATOR;
        }else{
            return RIGHT_DECORATOR;
        }
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvGoodsPic;
        TextView mTvGoodsTitle;
        TextView mTvActualPrice;
        TextView mTvOriginPrice;

        GoodsViewHolder(View view) {
            super(view);
            mIvGoodsPic = view.findViewById(R.id.iv_goods_pic);
            mTvGoodsTitle = view.findViewById(R.id.tv_goods_title);
            mTvActualPrice = view.findViewById(R.id.tv_actual_price);
            mTvOriginPrice = view.findViewById(R.id.tv_origin_price);
        }
    }
}
