package com.siasun.tech.servicerobot.edu.base;

import android.content.DialogInterface;
import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;

/**
 * Created by jian.shi on 2018/7/11.
 *
 * @Email shijian1@siasun.com
 */
public interface BaseView {
    /**
     * 显示loadingDialog
     *
     * @param cancelable 用户是否可以主动取消
     *                   true 可以主动取消
     *                   false 不可以主动取消
     */
    void showLoadingDialog(boolean cancelable);

    /**
     * 显示loadingDialog，用户可以主动取消
     * dialog消失时自动解除订阅
     *
     * @param disposable disposable集合
     * @see {@link android.app.Dialog#setCancelable(boolean)}
     */
    void showLoadingDialog(@NonNull Disposable disposable);

    /**
     * 显示loadingDialog，可被用户主动取消
     *
     * @param onCancelListener loadingDialog 被用户取消监听
     */
    void showLoadingDialog(@NonNull DialogInterface.OnCancelListener onCancelListener);

    /**
     * 关闭loadingDialog
     */
    void dismissLoadingDialog();

    /**
     * loadingDialog 是否正在显示
     */
    boolean isLoadingShowing();

//    /**
//     * 订阅当前view
//     *
//     * @param tag 用于区分调用
//     * @param d   用于{@link android.app.Activity#onDestroy()}解除订阅
//     */
//    void onSubscribeView(Disposable d, String tag);
}
