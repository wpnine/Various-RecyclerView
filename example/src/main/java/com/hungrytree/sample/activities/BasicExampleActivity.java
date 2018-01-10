package com.hungrytree.sample.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hungrytree.sample.R;
import com.hungrytree.sample.items.ImageItem;
import com.hungrytree.sample.items.LabelItem;
import com.hungrytree.varadapter.RVDelegation;
import com.hungrytree.varadapter.simple.SimpleAdapter;

import java.util.Arrays;

/**
 * Created by wp.nine on 2018/1/7.
 */

public class BasicExampleActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView = null;
    private RVDelegation mRVDelegation = new RVDelegation();


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
                .attatchItem(new LabelItem(Color.WHITE), Arrays.asList("item 1>>1","item 1>>2"))
                .attatchItem(new LabelItem(Color.YELLOW), Arrays.asList("item 2>>1"))
                .attatchItem(new LabelItem(Color.RED), Arrays.asList("item 3>>1"))
                .attatchItem(new ImageItem(),new int[]{R.mipmap.ic_launcher_round})
                .commit();
    }






}
