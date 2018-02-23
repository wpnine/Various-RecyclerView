package com.hungrytree.varadapter.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hungrytree.varadapter.item.ItemManager;
import com.hungrytree.varadapter.item.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp.nine on 2018/2/11.
 */

public class VarDecoration extends RecyclerView.ItemDecoration {
    Paint mBackgroundPaint = null;
    private ItemManager mItemManager;

    public VarDecoration(ItemManager itemManager) {
        this.mItemManager = itemManager;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        long startTime = System.currentTimeMillis();

        onItemDecoration(c,parent,state);

        Log.i("haha","addItemDecoration duration" + (System.currentTimeMillis() - startTime));
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    private void onItemDecoration(Canvas c, RecyclerView parent, RecyclerView.State state){
        int childCount = parent.getChildCount();
        if(childCount == 0){
            return;
        }
        int itemIndex = -1;
        int itemStartPosition = 0;
        RecyclerItem curRecyclerItem = null;
        int viewStartPosition = parent.getChildAdapterPosition(parent.getChildAt(0));
        int curItemCount = 0;

        List<RecyclerItem> recyclerItemList = mItemManager.getAllItem();        //获取当前的所有item

        //找到起始的item 位置
        for(int i = 0;i<recyclerItemList.size();i++){
            RecyclerItem item = recyclerItemList.get(i);
            int count = item.getItemCount(item.getData());
            if(itemStartPosition + count > viewStartPosition){
                itemIndex = i;
                curItemCount = count;
                curRecyclerItem = item;
                break;
            }else{
                itemStartPosition += count;
            }
        }

        //说明列表没有满足的item,所选的item不在范围内
        if(itemIndex < 0){
            return;
        }

        List<Object[]> decoratorTargetList = new ArrayList<>();

        for(int i = 0;i< childCount;i++){
            int viewPosition = viewStartPosition + i;
            int itemChildPosition = viewPosition - itemStartPosition;
            ItemDecorator decorator = curRecyclerItem.getDecorator(itemChildPosition);
            if(decorator != null){

                View childView = parent.getChildAt(i);
                Rect rect = new Rect();
                rect.left = childView.getLeft();
                rect.right = childView.getRight();
                rect.top = childView.getTop();
                rect.bottom = childView.getBottom();

                if(decorator.isIncludeMargin()){
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
                    if(params != null){
                        rect.left -= params.leftMargin;
                        rect.right += params.rightMargin;
                        rect.top -= params.topMargin;
                        rect.bottom += params.bottomMargin;
                    }
                }

                drawBackground(rect,c,decorator);
                decoratorTargetList.add(new Object[]{rect,decorator});
            }

            //item 下一个item超出了，则往一个item去
            if(i + 1 != childCount && itemStartPosition + curItemCount <= viewPosition + 1) {
                boolean isFound = false;

                for (int k = itemIndex + 1; k < recyclerItemList.size(); k++) {
                    RecyclerItem item = recyclerItemList.get(k);
                    int tempCount = item.getItemCount(item.getData());
                    if(tempCount != 0){
                        isFound = true;
                        itemIndex = k;
                        curRecyclerItem = item;
                        itemStartPosition += curItemCount;
                        curItemCount = tempCount;
                        break;
                    }
                }

                if (!isFound) {
                    break;
                }
            }
        }

        for(Object[] target : decoratorTargetList){
            Rect rect = (Rect) target[0];
            ItemDecorator decorator = (ItemDecorator) target[1];
            drawLines(rect,c,decorator);
        }

    }

    private void drawLines(Rect rect,Canvas canvas,ItemDecorator decorator){
        List<ILine> lines = decorator.getLines();
        if(lines != null){
            for(ILine line : lines){
                line.onDraw(canvas,rect);
            }
        }
    }


    private void drawBackground(Rect rect,Canvas canvas,ItemDecorator decorator){
        int color = decorator.getBackgroundColor();
        if(color != 0){
            Paint paint = getBackgroundPaint();
            paint.setColor(color);
            canvas.drawRect(rect,paint);
        }
    }


    private Paint getBackgroundPaint(){
        if(mBackgroundPaint == null){
            mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        return mBackgroundPaint;
    }
}
