package com.hungrytree.varadapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hungrytree.varadapter.item.IRecyclerSupport;
import com.hungrytree.varadapter.item.ItemManager;
import com.hungrytree.varadapter.refresh.IRefreshHandler;
import com.hungrytree.varadapter.refresh.RefreshTask;

import java.util.*;

/**
 * 
 */
public interface IDelegation extends IRecyclerSupport,IRefreshHandler{


    /**
     * @param view 
     * @return
     */
    void initView(RecyclerView view);


    /**
     * 创建刷新任务
     * @return
     */
    RefreshTask createTask();

}