package com.siasun.tech.servicerobot.edu;

import com.siasun.tech.servicerobot.edu.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by jian.shi on 2018/12/28.
 *
 * @Email shijian1@siasun.com
 */
public class MApplication extends DaggerApplication{
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
