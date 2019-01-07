package com.siasun.tech.servicerobot.edu.ui.presenter;

import com.siasun.tech.servicerobot.edu.base.BasePresenter;
import com.siasun.tech.servicerobot.edu.base.BaseView;

/**
 * Created by jian.shi on 2018/8/8.
 *
 * @Email shijian1@siasun.com
 */
public interface MainContract {
    interface View extends BaseView {
        void onLoginSuccess();

        void onLoginFail();

        void onGetUserInfoSuccess();

        void onGetUserInfoFail();

        void onGetAnswerSuccess();

        void onGetAnswerFail();
    }

    interface Presenter extends BasePresenter<View> {
        void doLogin(String macAddress);

        void getUserInfo(String robotId);

        void getAnswer(String question);
    }
}
