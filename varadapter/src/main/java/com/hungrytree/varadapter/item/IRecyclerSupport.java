package com.hungrytree.varadapter.item;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by wp.nine on 2017/12/7.
 */

public interface IRecyclerSupport {
    /**
     * @return
     */
    int getItemCount();

    /**
     * 获得位置上的 viewType
     * @param position
     * @return
     */
    int getItemViewType(int position);
    /**
     * @param parent
     * @param viewType
     * @return
     */
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * @return
     */
    void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int position);

    /**
     * 获得当前指定 position
     * @return
     */
    int getSpaceCount(int position,int maxCount);

}
