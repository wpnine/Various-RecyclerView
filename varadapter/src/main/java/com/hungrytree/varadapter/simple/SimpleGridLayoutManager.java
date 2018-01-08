package com.hungrytree.varadapter.simple;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

import com.hungrytree.varadapter.IDelegation;

/**
 * Created by wp.nine on 2017/12/8.
 */

public class SimpleGridLayoutManager extends GridLayoutManager {
    private IDelegation mDelegation;
    public SimpleGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes,IDelegation delegation) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(delegation);
    }

    public SimpleGridLayoutManager(Context context, int spanCount,IDelegation delegation) {
        super(context, spanCount);
        init(delegation);
    }

    public SimpleGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout,IDelegation delegation) {
        super(context, spanCount, orientation, reverseLayout);
        init(delegation);
    }


    private void init(IDelegation delegation){
        mDelegation = delegation;
        setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int count = mDelegation.getSpaceCount(position,getSpanCount());
                return count >= 0 ? count : getSpanCount();
            }
        });
    }

}
