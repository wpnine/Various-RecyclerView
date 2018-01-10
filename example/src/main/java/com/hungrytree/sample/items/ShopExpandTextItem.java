package com.hungrytree.sample.items;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hungrytree.sample.R;
import com.hungrytree.varadapter.item.RecyclerItem;


/**
 * Created by wp.nine on 2018/1/3.
 */

public class ShopExpandTextItem extends RecyclerItem<String, ShopExpandTextItem.TextHolder> {
    private boolean isShowAllText = false;
    private String mBriefMsg = null;
    private boolean isShowMoreBtn = true;
    private int mFontColorRes = -1;
    private boolean showDivider;

    public ShopExpandTextItem(int fontColorRes, boolean showDivider) {
        this.mFontColorRes = fontColorRes;
        this.showDivider = showDivider;
    }

    @Override
    public TextHolder createChildViewHolder(ViewGroup parents, int viewType) {
        return new TextHolder(LayoutInflater.from(parents.getContext()).inflate(R.layout.item_coupon_expand_text, parents, false));
    }

    @Override
    public void onBindViewHolder(final TextHolder holder, int position, int viewType) {
        Context context = holder.mContainerContent.getContext();
        holder.mTvContent.setText(getData());
        holder.mTvContent.setTextColor(context.getResources().getColor(mFontColorRes));
        holder.mTvLookMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowAllText = true;
                holder.mTvContent.setText(getData());
                holder.mTvLookMore.setVisibility(View.GONE);
            }
        });

        if(showDivider){
            holder.mVDivder.setVisibility(View.VISIBLE);
        }else {
            holder.mVDivder.setVisibility(View.GONE);
        }

        if(!isShowAllText && isShowMoreBtn){

            if(mBriefMsg == null){
                makeTextViewResizable(holder.mTvContent,6,"………    ",holder.mTvLookMore);
            }else{
                holder.mTvContent.setText(mBriefMsg);
                holder.mTvLookMore.setVisibility(View.VISIBLE);
            }
        }else{
            holder.mTvContent.setText(getData());
            holder.mTvLookMore.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount(String data) {
        if (data != null) {
            return 1;
        } else {
            return 0;
        }
    }

    public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final View btn) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        tv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeOnPreDrawListener(this);

                if (tv.getLineCount() >= maxLine) {
                    Layout layout = tv.getLayout();
                    int lineEndIndex = layout.getLineEnd(maxLine - 1);

                    int maxLineWidth = 0;
                    for(int i = 0;i<maxLine;i++){
                        maxLineWidth = (int) Math.max(layout.getLineWidth(i),maxLineWidth);
                    }
                    int textViewWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight();
                    int rightPadding = Math.max(textViewWidth - maxLineWidth,0);
                    btn.setPadding(0,0,rightPadding,0);

                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    mBriefMsg = text;
                    isShowMoreBtn = true;
                    tv.setText(text);
                    btn.setVisibility(View.VISIBLE);
                }else{
                    isShowMoreBtn = false;
                    btn.setVisibility(View.GONE);
                }
                return false;
            }
        });

    }


    public static class TextHolder extends RecyclerView.ViewHolder {
        TextView mTvContent;
        TextView mTvLookMore;
        View mVDivder;
        RelativeLayout mContainerContent;

        public TextHolder(View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvLookMore = itemView.findViewById(R.id.tv_look_more);
            mVDivder = itemView.findViewById(R.id.v_divider);
            mContainerContent = itemView.findViewById(R.id.container_content);

        }
    }


}