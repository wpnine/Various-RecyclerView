package com.hungrytree.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hungrytree.sample.activities.BorderExampleActivity;
import com.hungrytree.sample.activities.DecoratorExampleActivity;
import com.hungrytree.sample.activities.ShopDetailActivity;
import com.hungrytree.sample.activities.InternalControlActivity;
import com.hungrytree.sample.activities.BasicExampleActivity;
import com.hungrytree.sample.activities.SpecificChangeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //初始化控件
    private void initView(){
        findViewById(R.id.btnChange).setOnClickListener(this);
        findViewById(R.id.btnBasic).setOnClickListener(this);
        findViewById(R.id.btnAttatch).setOnClickListener(this);
        findViewById(R.id.btnShopDetail).setOnClickListener(this);
        findViewById(R.id.btnBorderComplex).setOnClickListener(this);
        findViewById(R.id.btnBorderExample).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btnBasic:
                intent = new Intent(this, BasicExampleActivity.class);
                break;
            case R.id.btnChange:
                intent = new Intent(this, SpecificChangeActivity.class);
                break;
            case R.id.btnAttatch:
                intent = new Intent(this, InternalControlActivity.class);
                break;
            case R.id.btnShopDetail:
                intent = new Intent(this, ShopDetailActivity.class);
                break;
            case R.id.btnBorderExample:
                intent = new Intent(this, BorderExampleActivity.class);
                break;
            case R.id.btnBorderComplex:
                intent = new Intent(this, DecoratorExampleActivity.class);
                break;
        }
        startActivity(intent);
    }
}
