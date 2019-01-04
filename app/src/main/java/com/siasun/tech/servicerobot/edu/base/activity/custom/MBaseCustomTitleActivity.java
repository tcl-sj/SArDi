package com.siasun.tech.servicerobot.edu.base.activity.custom;

import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;

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
public abstract class MBaseCustomTitleActivity extends SBaseActivity implements com.siasun.tech.servicerobot.edu.base.activity.custom.IMBaseCustomTitleView, BaseView {

    private View titleView;

    public final CompositeDisposable mDisposables = new CompositeDisposable();

    private LoadingDialog mLoadingDialog;

    @Override
    public View inflateTitleView(ViewStub viewStub, @LayoutRes int resId) {
        if (resId != 0) {
            viewStub.setLayoutResource(resId);
            titleView = viewStub.inflate();
            viewStub.setVisibility(View.VISIBLE);
        }
        return titleView;
    }

    @Override
    public void initImmersion() {
        if (isDisplayTitleBar() && titleView != null) {
            if ((getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) !=
                    WindowManager.LayoutParams.FLAG_FULLSCREEN) {
                initImmersionStatusBar(titleView);  //沉浸状态栏
            }
        }
    }

    @Override
    public boolean isDisplayTitleBar() {
        return true;
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
