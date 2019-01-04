package com.siasun.tech.servicerobot.ssframework.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewStub;

/**
 * Created by 健 on 2017/9/4.
 */

public interface ISBaseActivity {
    /**
     * 获取界面布局资源
     */
    @LayoutRes
    int getContentLayoutRes();

    /**
     * 获取标题栏布局资源
     */
    @LayoutRes
    int getTitleLayoutRes();

    View inflateContentView();
    /**
     * 初始化标题栏view
     *
     * @param viewStub 包裹标题栏的viewStub
     * @param resId    标题栏资源ID，resId > 0 初始化自定义title
     *                 resId < 0 初始化默认title
     * @return 返回标题栏view
     */
    View inflateTitleView(ViewStub viewStub, @LayoutRes int resId);


    void initData(Intent data, Bundle savedInstanceState);

    /**
     * 是否显示标题栏
     */
    boolean isDisplayTitleBar();

    /**
     * 初始化标题栏
     */
    void initTitle(View titleView);

    /**
     * 初始化
     *
     * @see android.app.Activity#onCreate(Bundle)
     */
    void onInit();

    /**
     * 初始化沉浸
     */
    void initImmersion();
}
