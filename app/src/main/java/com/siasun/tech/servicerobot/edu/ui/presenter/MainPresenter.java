package com.siasun.tech.servicerobot.edu.ui.presenter;

import com.siasun.tech.servicerobot.edu.http.bean.RespData;
import com.siasun.tech.servicerobot.edu.http.bean.TokenBean;
import com.siasun.tech.servicerobot.edu.http.api.IApiService;
import com.siasun.tech.servicerobot.edu.http.http.tools.JsonUtil;
import com.siasun.tech.servicerobot.edu.tools.SLog;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jian.shi on 2019/1/3.
 *
 * @Email shijian1@siasun.com
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    IApiService mIApiService;
    TokenBean mTokenBean;

    private CompositeDisposable mDisposables;

    @Inject
    public MainPresenter(IApiService apiService, TokenBean tokenBean) {
        mIApiService = apiService;
        mTokenBean = tokenBean;
        mDisposables = new CompositeDisposable();
    }

    @Override
    public void doLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("mac", "123456");
        mIApiService.login(JsonUtil.packageRequestParams(map))
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<RespData<TokenBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                        mView.showLoadingDialog(false);
                    }

                    @Override
                    public void onSuccess(RespData<TokenBean> data) {
                        SLog.d("onSuccess() called with: s = [" + data + "]");
                        mView.dismissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        SLog.e("onError: ", e.getCause().toString());
                        mView.dismissLoadingDialog();
                    }
                });
    }

    @Override
    public void takeView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
