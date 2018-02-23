package com.hungrytree.varadapter.item;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hungrytree.varadapter.decoration.ItemDecorator;
import com.hungrytree.varadapter.refresh.IRefreshHandler;


/**
 * 
 */
public abstract class RecyclerItem<T,K extends RecyclerView.ViewHolder> {
    private T mCurData;
    /**
     * Default constructor
     */
    public RecyclerItem() {
    }


    /**
     * @param parent 
     * @param viewType 
     * @return
     */
    public abstract K createChildViewHolder(ViewGroup parent, int viewType);

    /**
     * @param viewHolder
     * @param viewType 
     * @return
     */
    public abstract void onBindViewHolder(K viewHolder, int position, int viewType);


    /**
     * 获取item的修饰，用于设置背景或分隔线等
     * @return 为空则说明不需要
     */
    public ItemDecorator getDecorator(int position){
        return null;
    }

    /**
     * 根据数据获得item的数量
     * @return
     */
    public abstract int getItemCount(T data);


    /**
     * @return
     */
    public T getData() {
        return mCurData;
    }


    /**
     * 设置当前的data
     * @param data 
     * @return
     */
    public void setData(T data) {
        this.mCurData = data;
    }

    /**
     * 获得item横向的数量
     * @return
     */
    public int getSpaceCount(int maxCount) {
        return maxCount;
    }

    /**
     * 获得子项item中的类型
     * @return
     */
    public int getChildItemType(int position) {
        return 0;
    }


}