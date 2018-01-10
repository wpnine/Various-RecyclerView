package com.hungrytree.varadapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.hungrytree.varadapter.item.IRecyclerSupport;
import com.hungrytree.varadapter.item.ItemManager;
import com.hungrytree.varadapter.refresh.IRefreshHandler;
import com.hungrytree.varadapter.refresh.PartRefreshHandler;
import com.hungrytree.varadapter.refresh.RefreshTask;

import java.util.*;

/**
 * 
 */
public class RVDelegation implements IDelegation {
    private ItemManager mRecyclerSupport;
    private IRefreshHandler mRefreshHandler;
    private RecyclerView.Adapter mRecyclerAdapter;

    /**
     * Default constructor
     */
    public RVDelegation() {
    }

    /**
     * @param view 
     * @return
     */
    public void initView(RecyclerView view){
        mRecyclerAdapter = view.getAdapter();
        if(mRecyclerAdapter == null){
            throw new IllegalFormatFlagsException("no found adapter");
        }

        if(mRecyclerSupport == null){
            ItemManager itemManager = new ItemManager();
            mRefreshHandler = new PartRefreshHandler(itemManager);
            mRecyclerSupport = itemManager;
        }
        mRefreshHandler.setAdapter(mRecyclerAdapter);
    }

    public int getItemCount() {
        assertInitRecyclerView();
        Log.i("haha","getItemCount");
        return mRecyclerSupport.getItemCount();
    }

    @Override
    public void putTask(RefreshTask task) {
        mRefreshHandler.putTask(task);
    }

    /**
     * @param parent 
     * @param viewType 
     * @return
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        assertInitRecyclerView();
        return mRecyclerSupport.onCreateViewHolder(parent,viewType);
    }

    @Override
    public int getItemViewType(int position) {
        assertInitRecyclerView();
        return mRecyclerSupport.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        assertInitRecyclerView();
        mRecyclerSupport.onBindViewHolder(viewHolder,position);
    }

    @Override
    public int getSpaceCount(int position, int maxCount) {
        assertInitRecyclerView();
        return mRecyclerSupport.getSpaceCount(position,maxCount);
    }


    @Override
    public RefreshTask createTask() {
        assertInitRecyclerView();
        return RefreshTask.create(mRefreshHandler);
    }

    @Override
    public void clearAllItems() {
        assertInitRecyclerView();
        mRefreshHandler.clearAllItems();
    }


    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        assertInitRecyclerView();
        mRecyclerAdapter = adapter;
        mRefreshHandler.setAdapter(adapter);
    }


    private void assertInitRecyclerView(){
        if(mRefreshHandler == null){
            throw new RuntimeException("must invoke initView() method");
        }
    }

    public ItemManager getItemManager(){
        return mRecyclerSupport;
    }
}