package com.hungrytree.varadapter.refresh;

import android.support.v7.widget.RecyclerView;

import com.hungrytree.varadapter.item.ItemManager;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.*;

/**
 * 
 */
public class DefaultRefreshHandler implements IRefreshHandler {
    private RecyclerView.Adapter mAdapter;
    private ItemManager mItemManager;
    private Queue<RefreshTask> mRefreshQueue = new LinkedList<>();


    /**
     * Default constructor
     */
    public DefaultRefreshHandler( ItemManager itemManager) {
        mItemManager = itemManager;
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mAdapter = adapter;
    }

    /**
     * @params RefreshTask
     * @return
     */
    public void putTask(RefreshTask task) {
        mRefreshQueue.add(task);
        execute();
        mAdapter.notifyDataSetChanged();    //通知哪一项发生变化
    }


    private void execute(){
        RefreshTask refreshTask = mRefreshQueue.poll();
        List<RefreshTask.STask> taskList = refreshTask.mTaskList;
        for(RefreshTask.STask task : taskList){
            RecyclerItem item = task.item;
            switch (task.operate) {
                case RefreshTask.TASK_ADD:
                    item.setData(task.object);
                    mItemManager.attatchItem(item);
                    break;
                case RefreshTask.TASK_UPDATE:
                    if(mItemManager.containeItem(item)){
                        item.setData(task.object);
                    }
                    break;
                case RefreshTask.TASK_REMOVE:
                    mItemManager.removeItem(item);
                    break;
            }
        }
    }

    /**
     * @return
     */
    public void clearAllItems() {
        while (!mRefreshQueue.isEmpty()){
            execute();
        }
        mAdapter.notifyDataSetChanged();
    }

}