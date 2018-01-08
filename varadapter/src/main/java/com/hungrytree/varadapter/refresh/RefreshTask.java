package com.hungrytree.varadapter.refresh;

import android.support.v7.widget.RecyclerView;

import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.*;

/**
 * 
 */
public class RefreshTask {
    /**
     * 新增加RecyclerItem
     */
    public static final int TASK_ADD = 1;
    /**
     * 更新RecyclerItem的数据
     */
    public static final int TASK_UPDATE = 2;

    /**
     * 移除RecyclerItem
     */
    public static final int TASK_REMOVE = 3;


    /**
     * 对当前item的数据整体进行更新
     * 假如以前 size 为 3，更新后size 为 5, 则 notifyUpdate 0-2,notifyInsert 3-4
     */
    public static final int RULE_UPDATE_ALL = 0;

    /**
     * 数据的调整只在底部进行增加或删除，不做内容的更新
     * 假如以前 size 为 3，更新后size 为 5, 则 notifyInsert 3-4
     */
    public static final int RULE_ADJUST_BOTTOM = 1;

    /**
     * 数据更新只在顶部进行增加或删除，不做内容的更新
     * 假如以前 size 为 3，更新后size 为 5, 则 notifyInsert 0-1
     */
    public static final int RULE_ADJUST_TOP = 2;



    /**
     *指定对item的特定操作,插入
     */
    public static final int OPERATE_INSERT = 0;

    /**
     * 删除
     */
    public static final int OPERATE_REMOVE = 1;

    /**
     * 更新
     */
    public static final int OPERATE_UPDATE = 2;



    /**
     * 最终提交的任务列表
     */
    public List<STask> mTaskList = new ArrayList<>();

    private IRefreshHandler refreshHandler;

    /**
     * @return
     */
    public static RefreshTask create(IRefreshHandler refreshHandler) {
        return new RefreshTask(refreshHandler);
    }

    private RefreshTask(IRefreshHandler refreshHandler) {
        this.refreshHandler = refreshHandler;
    }

    /**
     * @param item 
     * @param data
     * @return
     */
    public <T,K extends RecyclerView.ViewHolder> RefreshTask changeData(RecyclerItem<T,K> item, T data) {
        changeData(item,data,RULE_UPDATE_ALL);
        return this;
    }

    /**
     * @param item
     * @param data
     * @return
     */
    public <T,K extends RecyclerView.ViewHolder> RefreshTask changeData(RecyclerItem<T,K> item, T data,int rule) {
        changeData(item,data,rule,null);
        return this;
    }

    /**
     * @param item
     * @param data
     * @param rule :没有指定的更新操作，按该操作进行
     * @param specialOperation : 对特定的item进行更新，例如果指定对position为2的位置进行增加： specialOperation.put(2,OPERATE_INSERT)
     * @return
     */
    public <T,K extends RecyclerView.ViewHolder> RefreshTask changeData(RecyclerItem<T,K> item, T data,int rule,HashMap<Integer,Integer> specialOperation ) {
        STask task = addTask(TASK_UPDATE,item,data);
        task.rule = rule;
        task.speicalOperation = specialOperation;
        return this;
    }



    /**
     * @param item 
     * @param data
     * @return
     */
    public <T,K extends RecyclerView.ViewHolder> RefreshTask  attatchItem(RecyclerItem<T,K> item, T data) {
        addTask(TASK_ADD,item,data);
        return this;
    }

    /**
     * 插入item指定到对应的位置
     * @param item
     * @param data
     * @return
     */
    public <T,K extends RecyclerView.ViewHolder> RefreshTask  attatchItem(RecyclerItem<T,K> item,int index, T data) {
        addTask(TASK_ADD,item,data,index);
        return this;
    }




    /**
     * @param item 
     * @return
     */
    public RefreshTask attatchItem(RecyclerItem item) {
        addTask(TASK_ADD,item,null);
        return this;
    }

    /**
     * @param item 
     * @return
     */
    public RefreshTask removeItem(RecyclerItem item) {
        addTask(TASK_REMOVE,item,null);
        return this;
    }


    /**
     * @param items 
     * @return
     */
    public RefreshTask attatchItemList(List<RecyclerItem> items) {
        for(RecyclerItem item : items){
            attatchItem(item);
        }
        return this;
    }

    /**
     * @param items 
     * @return
     */
    public RefreshTask removeItemList(List<RecyclerItem> items) {
        for(RecyclerItem item : items){
            removeItem(item);
        }
        return this;
    }

    private STask addTask(int taskType,RecyclerItem item,Object object){
        return addTask(taskType, item,object,-1);
    }

    public void commit(){
        refreshHandler.putTask(this);
    }


    private STask addTask(int taskType,RecyclerItem item,Object object,int index){
        STask task = new STask();
        task.operate = taskType;
        task.item = item;
        task.object = object;
        task.index = index;
        mTaskList.add(task);
        return task;
    }


    public static class STask{
        public RecyclerItem item;
        public int operate;
        public Object object;
        public int index = -1;//为-1时默认插到尾部
        public int rule = -1;
        public HashMap<Integer,Integer> speicalOperation;
    }

}