package com.siasun.tech.servicerobot.edu.base.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import com.siasun.tech.servicerobot.edu.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.util.ExceptionHelper;

/**
 * Created by 健 on 2017/7/26.
 */

public class LoadingDialog extends ProgressDialog implements DialogInterface.OnCancelListener, DialogInterface
        .OnDismissListener {

    private final static String TAG = LoadingDialog.class.getSimpleName();

    private HashSet<Disposable> disposables = new HashSet<>();

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        setContentView(R.layout.layout_loading_dialog);//loading的xml文件
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable());
        this.getWindow().setDimAmount(0);   //背景高亮，否则是灰色的
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.e(TAG, "onDismiss !");
        disposables.clear();
        Log.e(TAG, "disposables : " + disposables.size() + "");
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Log.e(TAG, "onCancel !");
        if (disposables != null && disposables.size() > 0) {
            Log.e(TAG, disposables.size() + "");
            dispose(disposables);
        }
    }

    /**
     * 显示loadingDialog
     *
     * @param cancelable Whether the dialog should be canceled by user.
     * @see {@link #setCancelable(boolean)}
     */
    public void show(boolean cancelable) {
        show(cancelable, null, null);
    }

    /**
     * 显示loadingDialog，用户可以手动取消
     * dialog消失时自动解除订阅
     *
     * @param disposable 用于解除订阅
     */
    public void show(@Nullable Disposable disposable) {
        show(true, disposable, this);
    }

    /**
     * 显示loadingDialog，dialog被用户主动取消
     *
     * @param onCancelListener loadingDialog 被取消监听
     */
    public void show(@Nullable OnCancelListener onCancelListener) {
        show(true, null, onCancelListener);
    }

    /**
     * 显示loadingDialog
     *
     * @param cancelable       dialog是否可以被用户主动取消.
     * @param disposable       用于解除订阅
     * @param onCancelListener loadingDialog 被取消监听
     */
    private synchronized void show(boolean cancelable, @Nullable Disposable disposable, @Nullable OnCancelListener
            onCancelListener) {
        setCancelable(cancelable);
        setCanceledOnTouchOutside(false);
        if (disposable != null) {
            disposables.add(disposable);
        }
        setOnCancelListener(onCancelListener);
        setOnDismissListener(this);
        super.show();
    }

    /**
     * 接触所有订阅
     *
     * @param set 所有订阅集合
     */
    void dispose(HashSet<Disposable> set) {
        if (set == null) {
            return;
        }
        List<Throwable> errors = null;
        Object[] array = set.toArray();
        for (Object o : array) {
            if (o instanceof Disposable) {
                try {
                    ((Disposable) o).dispose();
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    if (errors == null) {
                        errors = new ArrayList<Throwable>();
                    }
                    errors.add(ex);
                }
            }
        }
        if (errors != null) {
            if (errors.size() == 1) {
                throw ExceptionHelper.wrapOrThrow(errors.get(0));
            }
            throw new CompositeException(errors);
        }
    }
}
