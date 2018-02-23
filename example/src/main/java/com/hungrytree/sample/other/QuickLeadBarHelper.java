package com.hungrytree.sample.other;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wp.nine on 2018/1/4.
 * //快速引导 切换栏,用于界面的计算
 */

public class QuickLeadBarHelper {
    private RecyclerView mRecyclcerView;
    private TabLayout mTabLayout;
    private OnPositionProvider mPositionProvider;
    private int mCurState = RecyclerView.SCROLL_STATE_IDLE;
    private int mCurSelectionIndex = -1;        //当前tab选中的item
    public void initView(TabLayout view, RecyclerView recyclerView,OnPositionProvider provider){
        mTabLayout = view;
        mRecyclcerView = recyclerView;
        mPositionProvider = provider;
        mCurSelectionIndex = mTabLayout.getSelectedTabPosition();
        initTabLayout();
        initScrollListener();
    }


    private void initTabLayout(){
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(mCurState == RecyclerView.SCROLL_STATE_IDLE){
                    onTabClick(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private void initScrollListener(){
        mRecyclcerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mCurState = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(mCurState == RecyclerView.SCROLL_STATE_IDLE){
                    return;
                }

                int showCount = recyclerView.getChildCount();
                if(showCount == 0){
                    return;
                }

                int showStartPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int showEndPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(showCount -1));

                int bestPosition = 0;
                int curShowContentHeight = 0;
                boolean isFullContent = false;



                for(int i = 0;i< mTabLayout.getTabCount();i++){
                    int[] scrop = mPositionProvider.getTabManagerScrop(i);
                    int startScrop = scrop[0];
                    int endStrop = scrop[1];


                    if(showEndPosition < startScrop){ //还没有到达下一层的内容范围,不需要再往下判断
                        break;
                    }

                    if(showStartPosition > endStrop){   //说明已经过了当前结点的选择范围，直接忽略当前的
                        continue;
                    }


                    //计算出内容占的高度
                    int measureStartPosition = Math.max(startScrop,showStartPosition);
                    int measureEndPosition = Math.min(endStrop,showEndPosition);
                    int measureStartIndex = measureStartPosition - showStartPosition;
                    int measureCount = measureEndPosition - measureStartPosition + 1;

                    int top = recyclerView.getChildAt(measureStartIndex).getTop();
                    int bottom = recyclerView.getChildAt( measureStartIndex + measureCount -1).getBottom();

                    int tempHeight = bottom - top;
                    int topInsert = Math.min(top,0);

                    int bottomInsert = bottom - mRecyclcerView.getHeight();
                    bottomInsert = Math.max(bottomInsert,0);

                    tempHeight = tempHeight + topInsert - bottomInsert;

                    if(measureCount == endStrop - startScrop + 1 && topInsert == 0 && bottomInsert == 0){
                        isFullContent = true;//说明整个内容已经展示在屏幕中
                        curShowContentHeight = tempHeight;
                        bestPosition = i;
                        continue;
                    }

                    if(isFullContent){      //说明上一个是完整内容显示，但当前的已经不是完整内容,直接跳过
                        break;
                    }


                    if(curShowContentHeight < tempHeight){  //内容比上次的内容占比相对多
                        curShowContentHeight = tempHeight;
                        bestPosition = i;
                    }else{  //内容没展示完整,内容占比没有多过，则说明不需要往下面判断了
                        break;
                    }
                }

                changeTabIndex(bestPosition);
            }
        });
    }


    private void changeTabIndex(int tabIndex){
        if(mCurSelectionIndex == tabIndex){
            return;
        }
        mCurSelectionIndex = tabIndex;
        mTabLayout.getTabAt(mCurSelectionIndex).select();
    }


    /**
     * tab被点击
     * @param tabIndex tab的下标
     */
    private void onTabClick(int tabIndex){
        mCurSelectionIndex = tabIndex;
        int[] scrop = mPositionProvider.getTabManagerScrop(tabIndex);
        int startLocation = scrop[0];
        ((LinearLayoutManager)mRecyclcerView.getLayoutManager()).scrollToPositionWithOffset(startLocation,mTabLayout.getHeight());
    }


    /**
     * 位置提供，划分每一个tab所包含的范围
     */
    public interface OnPositionProvider{
        /**
         * @param tabIndex 指定tab的下标
         * @return 获取tab所管的范围，例：返回[1,5]意思从第二个item到第5个item的范围
         */
        int[] getTabManagerScrop(int tabIndex);

    }

}
