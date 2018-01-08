package com.hungrytree.varadapter.refresh;


import android.support.v7.widget.RecyclerView;

/**
 * 
 */
public interface IRefreshHandler {


    void setAdapter(RecyclerView.Adapter adapter);



    /**
     * 新增刷新任务
     * @param task
     */
    void putTask(RefreshTask task);



    /**
     * 清除所有的item
     * @return
     */
    void clearAllItems();




}