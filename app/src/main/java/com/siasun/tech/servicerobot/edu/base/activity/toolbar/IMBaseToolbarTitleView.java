package com.siasun.tech.servicerobot.edu.base.activity.toolbar;

import android.support.annotation.MenuRes;
import android.support.v7.widget.Toolbar;

/**
 * Created by jian.shi on 2018/7/11.
 *
 * @Email shijian1@siasun.com
 */
public interface IMBaseToolbarTitleView {
    @MenuRes int getTitleMenuRes();
    void initTitleBack(Toolbar toolbar);
    void initTitleToolBar(Toolbar toolbar);
}
