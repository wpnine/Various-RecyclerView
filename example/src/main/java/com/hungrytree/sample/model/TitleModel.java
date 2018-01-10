package com.hungrytree.sample.model;

import android.view.View;

/**
 * Created by wp.nine on 2017/12/28.
 * 用于展示标题
 */
public class TitleModel {
    private String title;
    private String subTitle;
    private View.OnClickListener listener;
    private int titleDrawable = -1;


    public TitleModel(String title) {
        this.title = title;
    }

    public TitleModel(String title, View.OnClickListener listener) {
        this.title = title;
        this.listener = listener;
    }

    public TitleModel(String title, String subTitle, View.OnClickListener listener) {
        this.title = title;
        this.subTitle = subTitle;
        this.listener = listener;
    }

    public TitleModel(String title, String subTitle, View.OnClickListener listener, int titleDrawable) {
        this.title = title;
        this.subTitle = subTitle;
        this.listener = listener;
        this.titleDrawable = titleDrawable;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public int getTitleDrawable() {
        return titleDrawable;
    }
}
