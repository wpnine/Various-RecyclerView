package com.hungrytree.sample.items;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hungrytree.sample.R;
import com.hungrytree.varadapter.item.RecyclerItem;


/**
 * Created by wp.nine on 2017/12/29.
 * 商店操作按钮，包括关注，视频，电话
 */

public class ShopConsolItem extends RecyclerItem<ShopConsolItem.ShopConsolSupport, ShopConsolItem.InfoViewHolder> {
    private Handler mHandler = null;
    public static final int WHAT_COLLECT_SHOP = 17777;
    public ShopConsolItem(Handler handler) {
        this.mHandler = handler;
    }


    @Override
    public ShopConsolItem.InfoViewHolder createChildViewHolder(ViewGroup parents, int viewType) {
        return new InfoViewHolder(LayoutInflater.from(parents.getContext()).inflate(R.layout.item_coupon_consol, parents, false));
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position, int viewType) {
        final ShopConsolSupport shopConsolSupport = getData();
        holder.mLlCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Call Phone", Toast.LENGTH_SHORT).show();
            }
        });

        //显示是否收藏
        holder.mTvCollectShop.setSelected(shopConsolSupport.isCollect());

        holder.mTvCollectShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(WHAT_COLLECT_SHOP);
            }
        });

        holder.mLlChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Chat with...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount(ShopConsolSupport data) {
        if (data == null) {
            return 0;
        }
        return 1;
    }

    public static class InfoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLlChat;
        TextView mTvCollectShop;
        LinearLayout mllCollectShop;
        LinearLayout mLlCallPhone;

        public InfoViewHolder(View itemView) {
            super(itemView);
            mLlChat = itemView.findViewById(R.id.ll_chat);
            mLlCallPhone = itemView.findViewById(R.id.ll_call_phone);
            mllCollectShop = itemView.findViewById(R.id.ll_collect_shop);
            mTvCollectShop = itemView.findViewById(R.id.tv_collect_shop);
        }

    }


    public interface ShopConsolSupport{
        String getShopPhone();
        boolean isCollect();
    }
}
