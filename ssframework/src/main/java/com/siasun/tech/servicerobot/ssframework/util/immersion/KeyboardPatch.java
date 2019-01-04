package com.siasun.tech.servicerobot.ssframework.util.immersion;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 健 on 2017/8/21.
 * 沉浸状态栏，解决底部输入框和软键盘的问题
 */
public class KeyboardPatch {
    private Activity mActivity;
    private View mDecorView;
    private View mContentView;
    private boolean flag = false;

    private KeyboardPatch(Activity activity) {
        this(activity, activity.getWindow());
    }

    private KeyboardPatch(Activity activity, Dialog dialog) {
        this(activity, dialog.getWindow());
    }

    private KeyboardPatch(Activity activity, Window window) {
        this.mActivity = activity;
        this.mDecorView = activity.getWindow().getDecorView();
        this.mContentView = window.getDecorView().findViewById(android.R.id.content);
    }

    private KeyboardPatch(Activity activity, View contentView) {
        this.mActivity = activity;
        this.mDecorView = activity.getWindow().getDecorView();
        this.mContentView = contentView;
        if (!mContentView.equals(activity.findViewById(android.R.id.content))
                || !mContentView.equals(mDecorView.findViewById(android.R.id.content)))
            this.flag = true;
    }

    public static KeyboardPatch patch(Activity activity, Dialog dialog) {
        return new KeyboardPatch(activity, dialog);
    }

    public static KeyboardPatch patch(Activity activity, Window window) {
        return new KeyboardPatch(activity, window);
    }

    public static KeyboardPatch patch(Activity activity) {
        return new KeyboardPatch(activity);
    }

    /**
     * Patch keyboard patch.
     *
     * @param activity    the activity
     * @param contentView 界面容器，指定布局的根节点
     * @return the keyboard patch
     */
    public static KeyboardPatch patch(Activity activity, View contentView) {
        return new KeyboardPatch(activity, contentView);
    }

    /**
     * 监听layout变化
     */
    public void enable() {
        enable(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void enable(int mode) {
        mActivity.getWindow().setSoftInputMode(mode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDecorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);//当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时，所要调用的回调函数的接口类
        }
    }

    /**
     * 取消监听
     */
    public void disable() {
        disable(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void disable(int mode) {
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDecorView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            mDecorView.getWindowVisibleDisplayFrame(r); //获取当前窗口可视区域大小的
            int height = mDecorView.getContext().getResources().getDisplayMetrics().heightPixels; //获取屏幕密度，不包含导航栏
            int diff = height - r.bottom;
            if (diff > 0) {
                if (mContentView.getPaddingBottom() != diff) {
                    if (flag || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && !ImmersionOsUtil.isEMUI3_1())) {
                        if (ImmersionConfig.IcSystem.getINSTANCE(mActivity).isHasVirtualNavigation()){
                            mContentView.setPadding(0, 0, 0, diff);    //导航栏不自动隐藏，只需加上高度差即可
                            //导航栏自动隐藏需要加上导航栏高度
//                            mContentView.setPadding(0, 0, 0, diff + ImmersionConfig.IcSystem.getINSTANCE(mActivity).getNavigationBarHeight());
                        }else {
                            mContentView.setPadding(0, 0, 0, diff);
                        }
                    } else {
                        mContentView.setPadding(0, 0, 0, diff + ImmersionConfig.IcSystem.getINSTANCE(mActivity).getNavigationBarHeight());
                    }
                }
            } else {
                if (mContentView.getPaddingBottom() != 0) {
                    if (flag || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && !ImmersionOsUtil.isEMUI3_1())) {
                        mContentView.setPadding(0, 0, 0, 0);
                    } else {
                        mContentView.setPadding(0, 0, 0, ImmersionConfig.IcSystem.getINSTANCE(mActivity).getNavigationBarHeight());
                    }
                }
            }
        }
    };
}
