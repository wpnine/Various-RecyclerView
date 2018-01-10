package com.hungrytree.sample.activities;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hungrytree.sample.R;
import com.hungrytree.sample.items.EmptySpaceItem;
import com.hungrytree.sample.items.ProductItem;
import com.hungrytree.sample.items.ShopBannerItem;
import com.hungrytree.sample.items.ShopConsolItem;
import com.hungrytree.sample.items.ShopExpandTextItem;
import com.hungrytree.sample.items.ShopImageItem;
import com.hungrytree.sample.items.ShopInfoItem;
import com.hungrytree.sample.items.ShopLoadMoreItem;
import com.hungrytree.sample.items.ShopOperationTime;
import com.hungrytree.sample.items.ShopServiceItem;
import com.hungrytree.sample.items.ShopTitleItem;
import com.hungrytree.sample.model.ProductDetail;
import com.hungrytree.sample.model.ShopModel;
import com.hungrytree.sample.model.TitleModel;
import com.hungrytree.sample.TestDataProvider;
import com.hungrytree.sample.other.QuickLeadBarHelper;
import com.hungrytree.sample.other.SafeHandler;
import com.hungrytree.sample.other.ShopItemDecoration;
import com.hungrytree.varadapter.RVDelegation;
import com.hungrytree.varadapter.item.ItemManager;
import com.hungrytree.varadapter.refresh.RefreshTask;
import com.hungrytree.varadapter.simple.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp.nine on 2018/1/2.
 * 该页面是从过去做过的项目的其中一部分，经过裁剪、修改，来做复杂一点的演示
 */

public class ShopDetailActivity extends AppCompatActivity {

    RecyclerView mRvDetail;
    TabLayout mTlQuickLeader;


    private SimpleAdapter mAdapter;
    private RVDelegation mDelegate = new RVDelegation();


    private final int MAX_SPACE_COUN = 6;

    private int mCurProductPageIndex = 0;   //当前的商品page下标
    private ShopModel mShopModel = null;
    private QuickLeadBarHelper mQuickLeadHelper = new QuickLeadBarHelper();

    private static class MyHandler extends SafeHandler<ShopDetailActivity> {

        public MyHandler(ShopDetailActivity objs) {
            super(objs);
        }

        @Override
        public void handlerMessageAction(Message msg) {
            switch (msg.what) {
                case ShopConsolItem.WHAT_COLLECT_SHOP:
                    getObj().changCollectStatus();
                    break;
            }
        }
    }

    private View.OnClickListener mOnLoadMoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadProductNextPage();
        }
    };

    private MyHandler mHandler = new MyHandler(this);
    //必须在handler之下创建
    private ItemProvider mItemProvider = new ItemProvider(this,mDelegate);

    private boolean isInitTabs = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        initView();
        loadShopDetail();
    }

    private void initView() {
        mRvDetail = (RecyclerView)findViewById(R.id.rv_detail);
        mTlQuickLeader = (TabLayout) findViewById(R.id.tl_quick_leader);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, MAX_SPACE_COUN);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDelegate.getSpaceCount(position, MAX_SPACE_COUN);
            }
        });
        mRvDetail.setLayoutManager(gridLayoutManager);
        mRvDetail.addItemDecoration(new ShopItemDecoration(mItemProvider));

        mAdapter = new SimpleAdapter(mDelegate);
        mRvDetail.setAdapter(mAdapter);
        mDelegate.initView(mRvDetail);

        mItemProvider.init();
    }


    private void initTabs(){
        if(isInitTabs){
            return;
        }
        isInitTabs = true;
        mTlQuickLeader.setVisibility(View.INVISIBLE);
        mTlQuickLeader.addTab(mTlQuickLeader.newTab().setText(R.string.shop_btn_scrop_desc));
        mTlQuickLeader.addTab(mTlQuickLeader.newTab().setText(R.string.shop_btn_scrop_product));
        mTlQuickLeader.addTab(mTlQuickLeader.newTab().setText(R.string.shop_btn_scrop_service));

        mQuickLeadHelper.initView(mTlQuickLeader,mRvDetail, new QuickLeadBarHelper.OnPositionProvider() {
            @Override
            public int[] getTabManagerScrop(int tabIndex) {
                ItemManager itemManager = mDelegate.getItemManager();
                switch (tabIndex){
                    case 0:
                        int titleIndex = itemManager.getContentStartIndex(mItemProvider.shopExpandTextItem);
                        return new int[]{
                                titleIndex,
                                titleIndex
                        };
                    case 1:
                        return new int[]{
                                itemManager.getContentStartIndex(mItemProvider.productTitle),
                                itemManager.getContentStartIndex(mItemProvider.serviceTitle) - 1
                        };
                    case 2:
                        int serviceIndex = itemManager.getContentStartIndex(mItemProvider.serviceTitle);
                        return new int[]{
                                serviceIndex,
                                serviceIndex + 1
                        };
                }
                return null;
            }
        });

        //初始化tab的滚动事件
        mRvDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int bindPosition = mDelegate.getItemManager().getContentStartIndex(mItemProvider.tabLayoutRectItem);

                int showStartPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int showEndPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(recyclerView.getChildCount() -1));

                if(bindPosition < showStartPosition){
                    mTlQuickLeader.setVisibility(View.VISIBLE);
                    setQuickLeaderY(0);
                }else if(bindPosition > showEndPosition){
                    mTlQuickLeader.setVisibility(View.INVISIBLE);
                }else{
                    mTlQuickLeader.setVisibility(View.VISIBLE);
                    int bindIndex = bindPosition - showStartPosition;
                    View view = recyclerView.getChildAt(bindIndex);
                    setQuickLeaderY(Math.max(view.getTop() + getResources().getDisplayMetrics().density * 10,0));
                }
            }
        });
    }

    private void setQuickLeaderY(float y){
        mTlQuickLeader.setY(y);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(y == 0){
                mTlQuickLeader.setElevation(getResources().getDisplayMetrics().density * 3);
            }else{
                mTlQuickLeader.setElevation(0);
            }
        }

    }

    private void loadProductNextPage() {
        mItemProvider.onProductInLoading();
        final int nextPage = mCurProductPageIndex + 1;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCurProductPageIndex = nextPage;
                mItemProvider.onProductLoadSuccess(TestDataProvider.getProductList());

            }
        },1500);
    }


    private void loadShopDetail() {
        mCurProductPageIndex = 1;   //商品详情会附带第一页的商品，同时第一页已经加载完成
        mShopModel = TestDataProvider.getShopExample();
        mItemProvider.onDataChanged(mShopModel);
        initTabs();

    }



    /**
     * 关注或取消商家
     */
    private void changCollectStatus() {
        final int collectStatus = mShopModel.getIsCollect() == 1 ? 0 : 1;
        mShopModel.setIsCollect(collectStatus);
        mItemProvider.notifyConsolItemChanged(mShopModel);
    }





    public static class ItemProvider {
        //商品相关属性
        public ShopBannerItem shopBannerItem = new ShopBannerItem(190.0f / 375);
        public ShopInfoItem shopInfoItem = new ShopInfoItem();
        public ShopConsolItem shopConsolItem;

        public ShopImageItem shopCouponEnter = new ShopImageItem();
        public ShopTitleItem shopLocationItem = new ShopTitleItem();  //显示位置及距离

        public EmptySpaceItem tabLayoutRectItem = new EmptySpaceItem();    //给tabLayout进行占位的item

        public ShopExpandTextItem shopExpandTextItem = new ShopExpandTextItem(R.color.font_negative,true);   //商家介绍

        public ShopTitleItem productTitle = new ShopTitleItem();       //商品列表
        public ProductItem productItem = new ProductItem();
        public ShopLoadMoreItem loadMoreItem = new ShopLoadMoreItem();

        public ShopTitleItem serviceTitle = new ShopTitleItem();       //服务
        public ShopOperationTime operationTime = new ShopOperationTime();  //营业时间
        public ShopServiceItem shopServiceItem = new ShopServiceItem();
        public EmptySpaceItem emptySpaceItem = new EmptySpaceItem();

        public RVDelegation delegate;

        public boolean isProductLoadFinish = false;
        public List<ProductDetail> allProductDetailList = new ArrayList<>();
        public ShopDetailActivity activity;
        public int curPageIndex = 0;
        private ItemProvider(ShopDetailActivity activity, RVDelegation delegate) {
            this.activity = activity;
            this.delegate = delegate;
        }

        public void init() {
            shopConsolItem = new ShopConsolItem(activity.mHandler);

            delegate.createTask()
                    //商家的基本信息
                    .attatchItem(shopBannerItem)
                    .attatchItem(shopInfoItem)
                    .attatchItem(shopConsolItem)
                    //某个列表的入口
                    .attatchItem(shopCouponEnter)
                    .attatchItem(shopLocationItem)
                    .attatchItem(tabLayoutRectItem)
                    //简介
                    .attatchItem(shopExpandTextItem)
                    //商品
                    .attatchItem(productTitle)
                    .attatchItem(productItem)
                    .attatchItem(loadMoreItem)
                    //服务
                    .attatchItem(serviceTitle)
                    .attatchItem(operationTime)
                    .attatchItem(shopServiceItem)
                    .attatchItem(emptySpaceItem)
                    .commit();

        }


        private void onDataChanged(final ShopModel shopModel) {
            float density = activity.getResources().getDisplayMetrics().density;

            RefreshTask refreshTask = delegate.createTask()
                    .changeData(shopBannerItem, shopModel.getShopPic())
                    .changeData(shopInfoItem, shopModel)
                    .changeData(shopConsolItem, shopModel)
                    .changeData(shopCouponEnter,shopModel)
                    .changeData(shopLocationItem, new TitleModel(shopModel.getAddr(), shopModel.getShopRange() + "m", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "Map", Toast.LENGTH_SHORT).show();
                        }
                    }, R.mipmap.ic_life_location))
                    .changeData(tabLayoutRectItem, (int) (53 * density))
                    .changeData(shopExpandTextItem, shopModel.getMerchantBrief())
                    .changeData(productTitle, new TitleModel(activity.getString(R.string.shop_btn_scrop_product)))
                    .changeData(serviceTitle, new TitleModel(activity.getString(R.string.shop_btn_scrop_service)))
                    .changeData(operationTime, shopModel.getBussinessTime())
                    .changeData(shopServiceItem, shopModel.getShopServiceList())
                    .changeData(emptySpaceItem, (int) (density * 50));

            isProductLoadFinish = false;    //重新更新状态
            allProductDetailList.clear();
            handleProduct(refreshTask, shopModel.getProductList());

            refreshTask.commit();
        }


        private void onProductLoadSuccess(List<ProductDetail> productList) {
            RefreshTask refreshTask = delegate.createTask();
            handleProduct(refreshTask,productList);
            refreshTask.commit();
        }

        private void handleProduct(RefreshTask refreshTask,List<ProductDetail> productList){
            if (productList != null && !productList.isEmpty()) {
                allProductDetailList.addAll(productList);
                refreshTask.changeData(productItem, allProductDetailList,RefreshTask.RULE_ADJUST_BOTTOM);
                curPageIndex++;
                if(curPageIndex >= 4){
                    changeLoadMoreStatus(refreshTask,ShopLoadMoreItem.STATE_COMPELETED);    //加载完成
                }else{
                    changeLoadMoreStatus(refreshTask,ShopLoadMoreItem.STATE_NORMAL);
                }
            } else {
                changeLoadMoreStatus(refreshTask,ShopLoadMoreItem.STATE_COMPELETED);    //加载完成
                isProductLoadFinish = true;
            }
        }


        private void onProductInLoading(){
            RefreshTask refreshTask = delegate.createTask();
            changeLoadMoreStatus(refreshTask,ShopLoadMoreItem.STATE_LOADING);
            refreshTask.commit();
        }

        private void changeLoadMoreStatus(RefreshTask task,int status){
            task.changeData(loadMoreItem,
                    new ShopLoadMoreItem.LoadMoreStatus(status,activity.mOnLoadMoreListener));

        }


        private void notifyConsolItemChanged(ShopModel shopModel){
            delegate.createTask()
                    .changeData(shopConsolItem,shopModel)
                    .commit();

        }


    }

}
