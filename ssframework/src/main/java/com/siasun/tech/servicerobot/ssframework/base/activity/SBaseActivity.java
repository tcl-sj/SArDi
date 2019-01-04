package com.siasun.tech.servicerobot.ssframework.base.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.siasun.tech.servicerobot.ssframework.R;
import com.siasun.tech.servicerobot.ssframework.util.immersion.ImmersionConfig;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by 健 on 2017/8/15.
 */

public abstract class SBaseActivity extends DaggerAppCompatActivity implements ISBaseActivity {

    public final static int BASE_ROOT_VIEW_ID = R.id.base_root_view;
    public final static int BASE_CONTENT_VIEW_ID = R.id.base_content_view;
    public final static int BASE_TITLE_VIEW_STUB_ID = R.id.base_title_view_stub;
    public final static int BASE_TITLE_VIEW_ID = R.id.base_title_view;

    private View baseRootView;
    private ViewGroup baseContentView;
    private ViewStub baseTitleViewStub;
    private View baseTitleView;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(inflateContentView());
        ButterKnife.bind(this);
        initImmersion();
        initData(getIntent(), savedInstanceState);
        if (isDisplayTitleBar() && baseTitleView != null) {
            initTitle(baseTitleView);
        }
        onInit();
    }

    @Override
    public final View inflateContentView() {
        baseRootView = getLayoutInflater().inflate(R.layout.base_activity, null);
        baseTitleViewStub = baseRootView.findViewById(R.id.base_title_view_stub);
        if (isDisplayTitleBar()) {
            baseTitleView = inflateTitleView(baseTitleViewStub, getTitleLayoutRes());
        }
        baseContentView = baseRootView.findViewById(BASE_CONTENT_VIEW_ID);
        baseContentView.addView(getLayoutInflater().inflate(getContentLayoutRes(), null));
        return baseRootView;
    }

    public View getBaseRootView() {
        return baseRootView;
    }

    public ViewGroup getBaseContentView() {
        return baseContentView;
    }

    public View getBaseTitleView() {
        return baseTitleView;
    }

    /**
     * 虚拟导航栏自动隐藏
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setStickyNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && ImmersionConfig.IcSystem.getINSTANCE(this).isHasVirtualNavigation()) {
            int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
            getWindow().getDecorView().setSystemUiVisibility(
                    uiOptions | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
