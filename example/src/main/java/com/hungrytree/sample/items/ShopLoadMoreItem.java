package com.hungrytree.sample.items;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hungrytree.sample.R;
import com.hungrytree.varadapter.decoration.ItemDecorator;
import com.hungrytree.varadapter.item.RecyclerItem;


/**
 * Created by wp.nine on 2018/1/4.
 */

public class ShopLoadMoreItem extends RecyclerItem<ShopLoadMoreItem.LoadMoreStatus, ShopLoadMoreItem.LoadMoreHolder> {
    public final static int STATE_NORMAL = 1;
    public final static int STATE_LOADING = 2;
    public final static int STATE_FAILED = 3;
    public final static int STATE_COMPELETED = 4;

    @Override
    public LoadMoreHolder createChildViewHolder(ViewGroup parents, int viewType) {
        return new LoadMoreHolder(LayoutInflater.from(parents.getContext()).inflate(R.layout.item_shop_loadmore, parents, false));
    }

    @Override
    public void onBindViewHolder(LoadMoreHolder holder, int position, int viewType) {
        LoadMoreStatus status = getData();
        Activity activity = (Activity) holder.mPbLoading.getContext();
        switch (status.state) {
            case STATE_NORMAL:
                holder.mTvHintMsg.setVisibility(View.VISIBLE);
                holder.mPbLoading.setVisibility(View.GONE);
                holder.mTvHintMsg.setText("更多商品");
                Drawable drawable = activity.getResources().getDrawable(R.mipmap.ic_shop_more_product);
                holder.mTvHintMsg.setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
                holder.convertView.setOnClickListener(status.gotoLoadCallback);
                break;
            case STATE_COMPELETED:
                holder.mTvHintMsg.setVisibility(View.VISIBLE);
                holder.mPbLoading.setVisibility(View.GONE);
                holder.mTvHintMsg.setText("暂无更多商品");
                holder.mTvHintMsg.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                holder.convertView.setOnClickListener(null);
                break;
            case STATE_FAILED:
                holder.mTvHintMsg.setVisibility(View.VISIBLE);
                holder.mPbLoading.setVisibility(View.GONE);
                holder.mTvHintMsg.setText("加载失败，请点击重试");
                holder.mTvHintMsg.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                holder.convertView.setOnClickListener(status.gotoLoadCallback);
                break;
            case STATE_LOADING:
                holder.mTvHintMsg.setVisibility(View.GONE);
                holder.mPbLoading.setVisibility(View.VISIBLE);
                holder.convertView.setOnClickListener(null);
                break;
        }
    }

    @Override
    public int getItemCount(LoadMoreStatus data) {
        if (data != null) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public ItemDecorator getDecorator(int position) {
        return new ItemDecorator()
                .setBackgroundColor(Color.WHITE);
    }

    public static class LoadMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar mPbLoading;
        TextView mTvHintMsg;
        View convertView;
        public LoadMoreHolder(View itemView) {
            super(itemView);
            mPbLoading = itemView.findViewById(R.id.pb_loading);
            mTvHintMsg = itemView.findViewById(R.id.tv_hint_msg);
            convertView = itemView;
        }
    }


    public static class LoadMoreStatus {
        private int state;
        private View.OnClickListener gotoLoadCallback;

        public LoadMoreStatus(int state, View.OnClickListener listener) {
            this.state = state;
            this.gotoLoadCallback = listener;
        }
    }
}
