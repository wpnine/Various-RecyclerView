package com.hungrytree.varadapter.item;


import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 负责管理所有item,包含对recyclerView adapter对基本方法的实现
 */
public class ItemManager implements IRecyclerSupport{
    private List<RecyclerItem> mItems = new ArrayList<>();
    public static final int MAX_VIEW_TYPE = 100;
    private SparseArray<RecyclerItem> mTypeMap = new SparseArray<>();
    private int mViewTypeCount = 0;
    private HashMap<RecyclerItem,Integer> mItemCountRecord = new HashMap<>();
    /**
     * Default constructor
     */
    public ItemManager() {
    }

    /**
     * invoked by adapter
     * @return 获得列表的总长度
     */
    @Override
    public int getItemCount() {
        int count = 0;
        mItemCountRecord.clear();
        for(RecyclerItem item : mItems){
            int tempCount = item.getItemCount(item.getData());  //单项里面可能有多个子项
            mItemCountRecord.put(item,tempCount);       //记录每次的item数量
            count += tempCount;
        }
        return count;
    }


    public HashMap<RecyclerItem,Integer> getItemCountRecord(){
        return mItemCountRecord;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int childType = viewType % MAX_VIEW_TYPE;
        int groupType = (viewType - childType)/MAX_VIEW_TYPE;
        RecyclerItem item = mTypeMap.get(groupType);
        return item.createChildViewHolder(parent,childType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int position) {
        int[] index = getPositionFromItemList(position);
        if(index!=null){
            RecyclerItem item = mItems.get(index[0]);
            item.onBindViewHolder(viewHolder,index[1],item.getChildItemType(index[1]));
        }
    }

    @Override
    public int getSpaceCount(int position,int maxCount) {
        int[] releative = getPositionFromItemList(position);
        if(releative == null){
            return -1;
        }
        return mItems.get(releative[0]).getSpaceCount(maxCount);
    }


    @Override
    public int getItemViewType(int position) {
        int index[] = getPositionFromItemList(position);
        int groupPostion = index[0];
        int childPostion = index[1];
        RecyclerItem recyclerItem = mItems.get(groupPostion);
        int childItemType = recyclerItem.getChildItemType(childPostion);

        if(childItemType >= MAX_VIEW_TYPE || childItemType < 0){
            throw new IllegalArgumentException("the type of view can't smaller than 0 or bigger than 100 >>" + recyclerItem.getClass().getName());
        }

        for(int i = 0;i<mTypeMap.size();i++){
            RecyclerItem item = mTypeMap.valueAt(i);
            if(item.getClass() == recyclerItem.getClass()){
                return mTypeMap.keyAt(i) * MAX_VIEW_TYPE + childItemType;
            }
        }

        mViewTypeCount++;
        mTypeMap.put(mViewTypeCount, recyclerItem);
        return mViewTypeCount * MAX_VIEW_TYPE + childItemType;
    }

    public void attatchItem(RecyclerItem item) {
        mItems.add(item);
    }

    public void attatchItem(RecyclerItem item,int index) {
        mItems.add(index,item);
    }




    public boolean containeItem(RecyclerItem item){
        return mItems.contains(item);
    }

    public void removeItem(RecyclerItem item) {
        mItems.remove(item);
    }

    public void clearAllItem(){
        mItems.clear();
    }

    public void removeItem(int index) {
        mItems.remove(index);
    }


    /**
     * @param position  计算出position在子项中的位置
     * @return 返回子项中的坐置{第几组中，第几个项中}
     */
    private int[] getPositionFromItemList(int position) {
        int groupIndex = 0;
        for(RecyclerItem item : mItems){
            if(position < item.getItemCount(item.getData())){
                return new int[]{groupIndex,position};
            }else{
                groupIndex++;
                position -= item.getItemCount(item.getData());
            }
        }
        return null;    //一般情况下不可能走到这里来
    }

    /**
     * 根据group position 得到 item
     * @param position 
     * @return
     */
    public RecyclerItem getItemByGroupPosition(int position) {
        return mItems.get(position);
    }



    /*获取单个Item项在当前占用的数量
     * */
    public int getContentStartIndex(RecyclerItem searchItem){
        int position = 0;
        for(RecyclerItem item : mItems){
            if(item != searchItem){
                position+= item.getItemCount(item.getData());
            }else{
                return position;
            }
        }
        return position;
    }



    public List<RecyclerItem> getAllItem(){
        return mItems;
    }
}