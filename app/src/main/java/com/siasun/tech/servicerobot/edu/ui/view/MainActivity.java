package com.siasun.tech.servicerobot.edu.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.siasun.tech.servicerobot.edu.R;
import com.siasun.tech.servicerobot.edu.base.activity.toolbar.MBaseToolbarActivity;
import com.siasun.tech.servicerobot.edu.ui.presenter.MainContract;
import com.siasun.tech.servicerobot.edu.ui.presenter.MainPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


public class MainActivity extends MBaseToolbarActivity implements MainContract.View {
    private static final String TAG = "MainActivity";

    @Inject
    MainPresenter mPresenter;

    @Override
    public void onSubscribeView(Disposable d, String tag) {

    }

    @Override
    public int getTitleMenuRes() {
        return 0;
    }

    @Override
    public void initTitleToolBar(Toolbar toolbar) {

    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Intent data, Bundle savedInstanceState) {

    }

    @Override
    public void onInit() {
        mPresenter.takeView(this);
        mPresenter.doLogin();
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFail() {

    }
}
