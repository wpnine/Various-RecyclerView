package com.hungrytree.varadapter.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;

/**
 * Created by wp.nine on 2018/2/9.
 */

public class BorderLine implements ILine {
    public final static int POSITION_LEFT = 1;
    public final static int POSITION_RIGHT = 2;
    public final static int POSITION_TOP = 4;
    public final static int POSITION_BOTTOM = 8;
    /**
     * 与item的长度适应，即item的高度或者宽度
     */
    public final static int LENGTH_MATCH_ITEM = -1;


    /**
     * 线的位置
     */
    private int linePosition = POSITION_BOTTOM;

    /**
     * 线宽
     */
    private int lineWidth = 1;  //线的宽度

    /**
     *线的长度
     */
    private int lineLength = LENGTH_MATCH_ITEM;
    /**
     * 相对边距线的位置
     */
    private int gravity = Gravity.BOTTOM;
    /**
     * 用于在原有长度的基础上减去的相应的长度，如实现:MAX_LENGTH - 10dp
     */
    private int reduceLineLength = 0;

    /**
     * 在绘制的基准线再做偏离
     */
    private int offsetBaseLine = 0;

    /**
     * 线的颜色,默认为黑
     */
    private int color = Color.BLACK;

    Paint mPaint = null;

    @Override
    public void onDraw(Canvas canvas, Rect rect) {
        RectF lineRect = new RectF();
        switch (linePosition){
            case POSITION_TOP:
                getTBPosition(lineRect,rect.top,rect.left,rect.right);
                break;
            case POSITION_BOTTOM:
                getTBPosition(lineRect,rect.bottom,rect.left,rect.right);
                break;
            case POSITION_RIGHT:
                getLRPosition(lineRect,rect.right,rect.top,rect.bottom);
                break;
            case POSITION_LEFT:
                getLRPosition(lineRect,rect.left,rect.top,rect.bottom);
                break;
        }
        if(mPaint == null){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        }
        mPaint.setColor(color);
        canvas.drawRect(lineRect.left,lineRect.top,lineRect.right,lineRect.bottom,mPaint);
    }


    private void getTBPosition(RectF lineRect, int baseY, int baseStartX, int baseEndX){
        //横向方向
        if((gravity & Gravity.TOP) == Gravity.TOP){          //居左
            lineRect.top = baseY - lineWidth;
            lineRect.bottom = baseY ;
        }else if((gravity & Gravity.BOTTOM) == Gravity.BOTTOM){   //居右
            lineRect.top = baseY;
            lineRect.bottom = lineRect.top + lineWidth;
        }else {  //居中
            float halfHeight = lineWidth / 2.0f;
            lineRect.top = baseY - halfHeight;
            lineRect.bottom = lineRect.top + lineWidth;
        }

        lineRect.top += offsetBaseLine;
        lineRect.bottom += offsetBaseLine;


        //横向方向
        float actLineLength = lineLength;
        if(lineLength == LENGTH_MATCH_ITEM){
            actLineLength = baseEndX - baseStartX;
        }
        actLineLength -= reduceLineLength;
        if((gravity & Gravity.LEFT) == Gravity.LEFT){       //居上
            lineRect.left = baseStartX;
            lineRect.right = baseStartX + actLineLength;
        }else if((gravity & Gravity.RIGHT) == Gravity.RIGHT){  //居下
            lineRect.left = baseEndX - actLineLength;
            lineRect.right = baseEndX;
        }else{                                  //居中
            float halfWidth = actLineLength/2.0f;
            float centerX = (baseEndX - baseStartX)/2 + baseStartX;
            lineRect.left = centerX - halfWidth;
            lineRect.right = centerX + halfWidth;
        }
    }



    //获得左右线的位置
    private void getLRPosition(RectF lineRect, int baseX, int baseStartY, int baseEndY){
        //纵向方向
        if((gravity & Gravity.LEFT) == Gravity.LEFT){          //居左
            lineRect.left = baseX - lineWidth;
            lineRect.right = baseX;
        }else if((gravity & Gravity.RIGHT) == Gravity.RIGHT){   //居右
            lineRect.left = baseX;
            lineRect.right = lineRect.left + lineWidth;
        }else{                                      //居中
            float halfWidth = lineWidth / 2.0f;
            lineRect.left = baseX - halfWidth;
            lineRect.right = lineRect.left + lineWidth;
        }
        lineRect.left += offsetBaseLine;
        lineRect.right += offsetBaseLine;


        //横向方向
        float actLineLength = lineLength;
        if(lineLength == LENGTH_MATCH_ITEM){
            actLineLength = baseEndY - baseStartY;
        }
        actLineLength -= reduceLineLength;
        if((gravity & Gravity.TOP) == Gravity.TOP){       //居上
            lineRect.top = baseStartY;
            lineRect.bottom = baseStartY + actLineLength;
        }else if((gravity & Gravity.BOTTOM) == Gravity.BOTTOM){  //居下
            lineRect.top = baseEndY - actLineLength;
            lineRect.bottom = baseEndY;
        }else{                                  //居中
            float halfHeight = actLineLength/2.0f;
            float centerY = (baseEndY - baseStartY)/2 + baseStartY;
            lineRect.top = centerY - halfHeight;
            lineRect.bottom = centerY + halfHeight;
        }

    }


    public BorderLine setLinePosition(int linePosition) {
        this.linePosition = linePosition;
        return this;
    }

    public BorderLine setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public BorderLine setLineLength(int lineLength) {
        this.lineLength = lineLength;
        return this;
    }

    public BorderLine setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public BorderLine setReduceLineLength(int reduceLineLength) {
        this.reduceLineLength = reduceLineLength;
        return this;
    }

    public BorderLine setOffsetBaseLine(int offsetBaseLine) {
        this.offsetBaseLine = offsetBaseLine;
        return this;
    }

    public BorderLine setColor(int color) {
        this.color = color;
        return this;
    }
}
