package com.hungrytree.varadapter.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.hungrytree.varadapter.item.ItemManager;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 局部刷新Handler
 */
public class PartRefreshHandler implements IRefreshHandler {
    private RecyclerView.Adapter mAdapter;
    private ItemManager mItemManager;
    private Queue<RefreshTask> mRefreshQueue = new LinkedList<>();


    /**
     * Default constructor
     */
    public PartRefreshHandler(ItemManager itemManager) {
        mItemManager = itemManager;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    /**
     * @return
     * @params RefreshTask
     */
    public void putTask(RefreshTask task) {
        mRefreshQueue.add(task);
        execute();
//        mAdapter.notifyDataSetChanged();    //通知哪一项发生变化
    }


    private void execute() {
        startRefresh();

    }


    private void startRefresh() {
        List<RecyclerItem> itemList = mItemManager.getAllItem();
        List<ItemCloneInfo> cloneItemList = new ArrayList<>();

        int startPosition = 0;
        for (RecyclerItem item : itemList) {
            ItemCloneInfo itemCloneInfo = new ItemCloneInfo();
            itemCloneInfo.count = mItemManager.getItemCountRecord().get(item);  //拿取旧的缓存记录
            itemCloneInfo.startPosition = startPosition;
            startPosition += itemCloneInfo.count;
            cloneItemList.add(itemCloneInfo);
        }

        RefreshTask refreshTask = mRefreshQueue.remove();

        List<RefreshTask.STask> tasks = refreshTask.mTaskList;
        for (RefreshTask.STask task : tasks) {
            switch (task.operate) {
                case RefreshTask.TASK_ADD:
                    executeAddTask(cloneItemList,task);
                    break;
                case RefreshTask.TASK_REMOVE:
                    executeRemoveTask(cloneItemList,task,itemList);
                    break;
                case RefreshTask.TASK_UPDATE:
                    executeUpdateTask(cloneItemList,task,itemList);
                    break;
            }
        }
    }


    private void executeAddTask(List<ItemCloneInfo> cloneItemList, RefreshTask.STask task){
        ItemCloneInfo cloneInfo = cloneItemInfo(task.item, task.object);
        if (task.index < 0) {
            int lastStartPosition = 0;
            if(cloneItemList.size() > 0){
                ItemCloneInfo itemCloneInfo = cloneItemList.get(cloneItemList.size() - 1);
                lastStartPosition = itemCloneInfo.startPosition + itemCloneInfo.count;
            }

            cloneInfo.startPosition = lastStartPosition;
        } else {
            int newItemStartPosition = 0;   //计算出新增加的item的起始位置
            if (task.index != 0) {
                ItemCloneInfo previousCloneInfo = cloneItemList.get(task.index - 1);
                newItemStartPosition = previousCloneInfo.startPosition + previousCloneInfo.count;
            }
            cloneInfo.startPosition = newItemStartPosition;
        }

        task.item.setData(task.object);
        if(task.index < 0){
            mItemManager.attatchItem(task.item);
            cloneItemList.add(cloneInfo);
        }else{
            mItemManager.attatchItem(task.item,task.index);

            //设置后面部分的位置
            resetStartPosition(cloneItemList,task.index,cloneInfo.count);
            cloneItemList.add(task.index,cloneInfo);
        }

        if(cloneInfo.count != 0){//更新控件
            mAdapter.notifyItemRangeInserted(cloneInfo.startPosition,cloneInfo.count);
        }
    }


    private void executeRemoveTask(List<ItemCloneInfo> cloneItemList, RefreshTask.STask task,List<RecyclerItem> itemList){
        int index = -1;
        for(int i = 0;i<itemList.size();i++){
            if(itemList.get(i) == task.item){
                index = i;
                break;
            }
        }
        if(index >= 0) {
            mItemManager.removeItem(index);
            ItemCloneInfo info = cloneItemList.remove(index);
            resetStartPosition(cloneItemList, index, info.count);
            if(info.count != 0){
                mAdapter.notifyItemRangeRemoved(info.startPosition, info.count);
            }
        }
    }


    private void executeUpdateTask(List<ItemCloneInfo> cloneItemList, RefreshTask.STask task,List<RecyclerItem> itemList){
        int updateIndex = -1;
        for(int i = 0;i<itemList.size();i++){
            if(itemList.get(i) == task.item){
                updateIndex = i;
                break;
            }
        }

        if(updateIndex >= 0){
            ItemCloneInfo itemCloneInfo = cloneItemList.get(updateIndex);
            RecyclerItem recyclerItem = task.item;
            int newCount = recyclerItem.getItemCount(task.object);

            task.item.setData(task.object);
            if(!(newCount == 0 && itemCloneInfo.count == 0)){//两个同时为空就不做考虑

                int customAppendCount = 0;  //自定义增加,删除的总数
                if(task.rule != RefreshTask.RULE_UPDATE_ALL && task.speicalOperation != null){
                    Set<Map.Entry<Integer,Integer>> entrySet = task.speicalOperation.entrySet();
                    for(Map.Entry<Integer,Integer> entry : entrySet){
                        int operate = entry.getValue();
                        int position = entry.getKey();
                        //自定义操作
                        switch (operate) {
                            case RefreshTask.OPERATE_INSERT:
                                mAdapter.notifyItemInserted(position);
                                customAppendCount++;
                                break;
                            case RefreshTask.OPERATE_REMOVE:
                                mAdapter.notifyItemRemoved(position);
                                customAppendCount--;
                                break;
                            case RefreshTask.OPERATE_UPDATE:
                                mAdapter.notifyItemChanged(position);
                                break;
                        }
                    }
                }


                int diff = newCount - itemCloneInfo.count - customAppendCount;

                if(diff == 0 && task.rule == RefreshTask.RULE_UPDATE_ALL){  //需要全部更新时，才能进行
                    mAdapter.notifyItemRangeChanged(itemCloneInfo.startPosition,itemCloneInfo.count);
                }else if(diff < 0){//当count对比之前减少时，则进行remove
                    int updateCount = itemCloneInfo.count + diff;
                    int removeStartPosition = itemCloneInfo.startPosition + updateCount;

                    if(updateCount != 0 && task.rule == RefreshTask.RULE_UPDATE_ALL){  //当需要的时候才进行更新
                        mAdapter.notifyItemRangeChanged(itemCloneInfo.startPosition,updateCount);
                    }

                    if(task.rule == RefreshTask.RULE_ADJUST_BOTTOM || task.rule == RefreshTask.RULE_UPDATE_ALL){
                        mAdapter.notifyItemRangeRemoved(removeStartPosition,-diff);
                    }else if(task.rule == RefreshTask.RULE_ADJUST_TOP){
                        mAdapter.notifyItemRangeRemoved(itemCloneInfo.startPosition,-diff);
                    }
                }else{
                    //增加多余的memeberItem
                    int addStartPosition = itemCloneInfo.startPosition + itemCloneInfo.count;
                    if(itemCloneInfo.count != 0 && task.rule == RefreshTask.RULE_UPDATE_ALL){
                        mAdapter.notifyItemRangeChanged(itemCloneInfo.startPosition,itemCloneInfo.count);
                    }
                    if(task.rule == RefreshTask.RULE_ADJUST_BOTTOM || task.rule == RefreshTask.RULE_UPDATE_ALL){
                        mAdapter.notifyItemRangeInserted(addStartPosition,diff);
                    }else if(task.rule == RefreshTask.RULE_ADJUST_TOP){
                        mAdapter.notifyItemRangeInserted(itemCloneInfo.startPosition,diff);
                    }
                }
                itemCloneInfo.count = newCount;

                resetStartPosition(cloneItemList,updateIndex + 1,diff + customAppendCount);
            }
        }
    }


    /**
     * 对插入item的后面部分全部增加次数
     */
    private void resetStartPosition(List<ItemCloneInfo> cloneInfos,int startIndex,int count){
        if (count != 0) {
            for (int i = startIndex; i < cloneInfos.size(); i++) {
                cloneInfos.get(i).startPosition += count;
            }
        }
    }


    private ItemCloneInfo cloneItemInfo(RecyclerItem item, Object data) {
        ItemCloneInfo itemCloneInfo = new ItemCloneInfo();
        itemCloneInfo.count = item.getItemCount(data);
        return itemCloneInfo;
    }

    /**
     * @return
     */
    public void clearAllItems() {
        mRefreshQueue.clear();
        int totalCount = mItemManager.getItemCount();
        mItemManager.clearAllItem();
        if(totalCount > 0){
            mAdapter.notifyItemRangeRemoved(0,totalCount);
        }
    }


    private static class ItemCloneInfo {
        private int count = 0;
        private int startPosition = 0;

    }
}