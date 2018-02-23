package com.hungrytree.varadapter.decoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp.nine on 2018/2/9.
 */

public class ItemDecorator {
    private int backgroundColor = 0;
    /**
     * 给予绘制的位置会包含item的margin
     */
    private boolean isIncludeMargin = true;
    private List<ILine> lines;

    public boolean isIncludeMargin() {
        return isIncludeMargin;
    }

    public ItemDecorator setIncludeMargin(boolean includeMargin) {
        isIncludeMargin = includeMargin;
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public ItemDecorator setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public List<ILine> getLines() {
        return lines;
    }

    public ItemDecorator addLine(ILine line){
        if(lines == null){
            lines = new ArrayList<>();
        }
        lines.add(line);
        return this;
    }
}
