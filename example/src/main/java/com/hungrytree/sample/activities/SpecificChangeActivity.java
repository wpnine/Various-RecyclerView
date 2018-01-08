package com.hungrytree.sample.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hungrytree.sample.R;
import com.hungrytree.sample.items.LabelItem;
import com.hungrytree.varadapter.RVDelegation;
import com.hungrytree.varadapter.refresh.RefreshTask;
import com.hungrytree.varadapter.simple.SimpleAdapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by wp.nine on 2017/12/13.
 * 指定特定的item去改变数据
 */

public class SpecificChangeActivity extends Activity implements View.OnClickListener {
    private RecyclerView mRecyclerView = null;
    private RVDelegation mRVDelegation = new RVDelegation();
    static final int ITEM_COUNT = 5;
    static final int MAX_SPACE_COUNT = 2;

    int mAddItemIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_update);

        initView();
        initData();
    }

    //初始化控件
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rlv_test);
        View btnReset = findViewById(R.id.btnReset);
        View btnAdd = findViewById(R.id.btnAdd);
        View btnRemove = findViewById(R.id.btnRemove);

        //init Buttons
        btnReset.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);

        //init recyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(this, MAX_SPACE_COUNT);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mRVDelegation.getSpaceCount(position,MAX_SPACE_COUNT);
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new SimpleAdapter(mRVDelegation));
        mRVDelegation.initView(mRecyclerView);

    }

    private void initData() {
        mAddItemIndex = 0;
        mRVDelegation.clearAllItems();
        RefreshTask refreshTask = mRVDelegation.createTask();

        int itemCount = 0;

        int[] colors = new int[]{Color.WHITE,Color.BLUE,Color.RED,Color.GRAY,Color.YELLOW};
        Random random = new Random();
        for (int i = 0; i < ITEM_COUNT; i++) {
            int count = Math.abs(random.nextInt()) % 5 + 1;
            LinkedList<String> tempData = new LinkedList<>();
            for (int j = 0; j < count; j++) {
                itemCount++;
                tempData.add("Test Item " + itemCount);
            }
            refreshTask.attatchItem(new LabelItem(colors[i]), tempData);
        }
        refreshTask.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                addItem();
                break;
            case R.id.btnRemove:
                removeItem();
                break;
            case R.id.btnReset:
                initData();
                break;
        }
    }

    private void removeItem(){
        Random random = new Random();
        int ranItem = Math.abs(random.nextInt()) % ITEM_COUNT;

        LabelItem tempItem = (LabelItem)mRVDelegation.getItemManager().getAllItem().get(ranItem);
        List<String> data = tempItem.getData();
        if(data.isEmpty()){
            return;
        }
        data.remove(0);

        mRVDelegation.createTask()
                .changeData(tempItem,data, RefreshTask.RULE_ADJUST_TOP)
                .commit();
    }

    public void addItem(){
        Random random = new Random();
        int ranItem = Math.abs(random.nextInt()) % ITEM_COUNT;

        LabelItem tempItem = (LabelItem)mRVDelegation.getItemManager().getAllItem().get(ranItem);
        List<String> data = tempItem.getData();
        data.add("changeItem " + mAddItemIndex);
        mAddItemIndex++;


        mRVDelegation.createTask()
                .changeData(tempItem,data, RefreshTask.RULE_ADJUST_BOTTOM)
                .commit();

    }


}
