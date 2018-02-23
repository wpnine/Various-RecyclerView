package com.hungrytree.sample.other;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hungrytree.sample.R;
import com.hungrytree.sample.activities.ShopDetailActivity;
import com.hungrytree.varadapter.RVDelegation;
import com.hungrytree.varadapter.item.ItemManager;

/**
 * Created by wp.nine on 2018/1/10.
 * 用于商品列表的分隔线绘画，现由RVDelegation.enableDelegation 来实现
 */
@Deprecated
public class ShopItemDecoration extends RecyclerView.ItemDecoration {
    private ShopDetailActivity.ItemProvider mItemProvider;
    private Paint mBackgroundPaint = null;
    private ColorDrawable mDivider;

    public ShopItemDecoration(ShopDetailActivity.ItemProvider itemProvider) {
        this.mItemProvider = itemProvider;
        mDivider = new ColorDrawable(getResources().getColor(R.color.divider_gray_dark));
    }

    public RVDelegation getDelegation(){
        return mItemProvider.delegate;
    }

    public Resources getResources(){
        return mItemProvider.activity.getResources();
    }

    public ItemManager getItemManager(){
        return getDelegation().getItemManager();
    }

    public Paint getBackgroundPaint(){
        if(mBackgroundPaint == null){
            mBackgroundPaint = new Paint();
            mBackgroundPaint.setColor(Color.WHITE);
        }
        return mBackgroundPaint;
    }



    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        //绘画商品列表的分隔线及背景色
        drawProductDivider(c, parent, state, mDivider);

        //绘画服务列表的背景色
        drawServiceBackground(c, parent, state);

    }




    private void drawServiceBackground(Canvas c, RecyclerView parent, RecyclerView.State state) {
        ItemManager itemManager = getItemManager();

        final int childCount = parent.getChildCount();
        int serviceStartPosition = itemManager.getContentStartIndex(mItemProvider.shopServiceItem);
        int serviceEndPosition = serviceStartPosition + itemManager.getItemCount(mItemProvider.shopServiceItem) - 1;

        //当前展示的起始位置
        int startPosition = parent.getChildAdapterPosition(parent.getChildAt(0));
        int endPosition = parent.getChildAdapterPosition(parent.getChildAt(childCount - 1));

        if (startPosition > serviceEndPosition || endPosition < serviceStartPosition) {
            return;
        }

        int firstShowPosition = Math.max(childCount - (endPosition - serviceStartPosition) - 1, 0);
        int endShowPosition = Math.min(childCount, serviceEndPosition - startPosition) - 1;

        RectF rectF = new RectF(0,
                parent.getChildAt(firstShowPosition).getTop(),
                parent.getWidth(),
                parent.getChildAt(endShowPosition).getBottom() + 30);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        //画白色背景
        c.drawRect(rectF, paint);
    }


    private void drawProductDivider(Canvas c, RecyclerView parent, RecyclerView.State state, Drawable divider) {
        final int childCount = parent.getChildCount();

        int productStartPosition = getItemManager().getContentStartIndex(mItemProvider.productItem);
        int productEndPosition = getItemManager().getContentStartIndex(mItemProvider.serviceTitle) - 1;

        //当前展示的起始位置
        int startPosition = parent.getChildAdapterPosition(parent.getChildAt(0));
        int endPosition = parent.getChildAdapterPosition(parent.getChildAt(childCount - 1));

        if (endPosition < productStartPosition || startPosition > productEndPosition) {
            return;
        }


        int listLeft = parent.getLeft();
        int listRight = parent.getRight();

        int startChildIndex = Math.max(childCount - (endPosition - productStartPosition) - 1, 0);
        int productOperateCount = Math.min(productEndPosition - startPosition + 1, childCount);

        View lastView =  parent.getChildAt(productOperateCount - 1);

        RectF rectF = new RectF(0,
                parent.getChildAt(startChildIndex).getTop(),
                parent.getWidth(),
                lastView.getBottom());
        c.drawRect(rectF, getBackgroundPaint());


        int loadmoreCount = getItemManager().getItemCount(mItemProvider.loadMoreItem);
        int loadmoreStartPosition = getItemManager().getContentStartIndex(mItemProvider.loadMoreItem);
        int lastViewPosition = parent.getChildAdapterPosition(lastView);
        if(loadmoreCount > 0 && loadmoreStartPosition == lastViewPosition){//最后一个item为控件，加载更多的按钮不需要画线
            productOperateCount -= loadmoreCount;
        }

        int startFlag = startChildIndex % 2;
        //画横线
        for (int i = startChildIndex; i < productOperateCount; i++) {
            if (i % 2 != startFlag) {  //只根据最左边一个进行绘制，
                continue;
            }
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + 1;
            divider.setBounds(listLeft, top, listRight, bottom);
            divider.draw(c);
        }

        //画竖线
        for (int i = startChildIndex; i < productOperateCount; i++) {
            if (i % 2 != startFlag) {
                continue;
            }
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() + params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + 1;
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }
}
