package com.siasun.tech.servicerobot.edu.base.activity.toolbar;

import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;

import com.siasun.tech.servicerobot.edu.R;
import com.siasun.tech.servicerobot.edu.base.BaseView;
import com.siasun.tech.servicerobot.edu.base.ImmersionConfigGlobal;
import com.siasun.tech.servicerobot.edu.base.dialog.LoadingDialog;
import com.siasun.tech.servicerobot.ssframework.base.activity.SBaseActivity;
import com.siasun.tech.servicerobot.ssframework.util.immersion.ImmersionBar;
import com.siasun.tech.servicerobot.ssframework.util.immersion.ImmersionConfig;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jian.shi on 2018/7/11.
 *
 * @Email shijian1@siasun.com
 */
public abstract class MBaseToolbarActivity extends SBaseActivity implements com.siasun.tech.servicerobot.edu.base.activity.toolbar.IMBaseToolbarTitleView, BaseView {
    public final static int TITLE_TOOLBAR_ID = R.id.title_toolbar;

    public final CompositeDisposable mDisposables = new CompositeDisposable();

    private LoadingDialog mLoadingDialog;

    @Override
    public int getTitleLayoutRes() {
        return R.layout.layout_base_title_toolbar;
    }

    @Override
    public View inflateTitleView(ViewStub viewStub, @LayoutRes int resId) {
        if (resId != 0) {
            viewStub.setLayoutResource(resId);
        } else {
            viewStub.setLayoutResource(R.layout.layout_base_title_toolbar);
        }
        View baseTitleView = viewStub.inflate();
        viewStub.setVisibility(View.VISIBLE);
        if (resId == R.layout.layout_base_title_toolbar) {
            setSupportActionBar((Toolbar) baseTitleView.findViewById(TITLE_TOOLBAR_ID));
            getSupportActionBar().setTitle("");
        }
        return baseTitleView;
    }

    @Override
    public void initTitle(View titleView) {
        initTitleBack((Toolbar) titleView.findViewById(TITLE_TOOLBAR_ID));
        initTitleToolBar((Toolbar) titleView.findViewById(TITLE_TOOLBAR_ID));
    }

    @Override
    public void initTitleBack(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initImmersion() {
        if (isDisplayTitleBar() && findViewById(TITLE_TOOLBAR_ID) != null){
            initImmersionStatusBar(findViewById(TITLE_TOOLBAR_ID));
        }
    }

    @Override
    public boolean isDisplayTitleBar() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getTitleMenuRes() != 0) {
            getMenuInflater().inflate(getTitleMenuRes(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showLoadingDialog(boolean cancelable) {
        synchronized (this) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this);
            }
            mLoadingDialog.show(cancelable);
        }
    }

    @Override
    public void showLoadingDialog(@NonNull Disposable disposable) {
        synchronized (this) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this);
            }
            mLoadingDialog.show(disposable);
        }
    }

    @Override
    public void showLoadingDialog(@NonNull DialogInterface.OnCancelListener onCancelListener) {
        synchronized (this) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this);
            }
            mLoadingDialog.show(onCancelListener);
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            synchronized (mLoadingDialog) {
                if (mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
            }
        }
    }

    @Override
    public boolean isLoadingShowing() {
        return mLoadingDialog != null && mLoadingDialog.isShowing();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        mDisposables.dispose();
    }

    /**
     * @param fitView 需要适配状态栏的view，防止与状态栏重叠
     */
    private void initImmersionStatusBar(View fitView) {
        if (fitView != null) {
            ImmersionBar.newInstance(
                    ImmersionConfigGlobal.global(this).icFit,
                    ImmersionConfigGlobal.global(this).icStatusBar())
                    .initImmersion(this, fitView);
        } else {
            //标题栏没有被初始化
            ImmersionBar.newInstance(
                    new ImmersionConfig.IcFit(false, false),
                    new ImmersionConfig.IcStatusBar(getResources().getColor(R.color.colorPrimary), 0))
                    .initImmersion(this);
        }
    }
}
