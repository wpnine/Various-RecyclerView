package com.hungrytree.sample.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hungrytree.sample.R;
import com.hungrytree.sample.items.ExpandLabelItem;
import com.hungrytree.sample.other.SafeHandler;
import com.hungrytree.varadapter.RVDelegation;
import com.hungrytree.varadapter.simple.SimpleAdapter;

import java.util.Arrays;

/**
 * Created by wp.nine on 2018/1/7.
 * 实现上下拉功能
 */

public class InternalControlActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView = null;
    private RVDelegation mRVDelegation = new RVDelegation();


    private static class MyHandler extends SafeHandler<InternalControlActivity>{
        public MyHandler(InternalControlActivity objs) {
            super(objs);
        }

        @Override
        public void handlerMessageAction(Message msg) {
            getObj().changeItem((ExpandLabelItem)msg.obj);
        }
    }
    MyHandler mHandler = new MyHandler(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        initView();
        initData();
    }

    //初始化控件
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rlv_test);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SimpleAdapter(mRVDelegation));
        mRVDelegation.initView(mRecyclerView);
    }

    private void initData() {
        mRVDelegation.createTask()
                .attatchItem(new ExpandLabelItem(mHandler),
                        new ExpandLabelItem.Config("Color",true, Arrays.asList("Yellow","Red")))
                .attatchItem(new ExpandLabelItem(mHandler),
                        new ExpandLabelItem.Config("Fruit",false, Arrays.asList("Apple","Pear")))
                .attatchItem(new ExpandLabelItem(mHandler),
                        new ExpandLabelItem.Config("Animal",false, Arrays.asList("Dog","Cat","Mouse")))
                .commit();
    }



    public void changeItem(ExpandLabelItem item){
        ExpandLabelItem.Config config = item.getData();
        config.setExpand(!config.isExpand());
        mRVDelegation.createTask()
                .changeData(item,config)
                .commit();

    }



}
