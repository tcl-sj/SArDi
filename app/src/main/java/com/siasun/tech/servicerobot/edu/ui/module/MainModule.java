package com.siasun.tech.servicerobot.edu.ui.module;

import com.siasun.tech.servicerobot.edu.di.ActivityScoped;
import com.siasun.tech.servicerobot.edu.di.ContextActivity;
import com.siasun.tech.servicerobot.edu.ui.presenter.MainContract;
import com.siasun.tech.servicerobot.edu.ui.view.MainActivity;

import dagger.Binds;
import dagger.Module;

/**
 * Created by jian.shi on 2019/1/3.
 *
 * @Email shijian1@siasun.com
 */
@Module
public abstract class MainModule {
    @ActivityScoped
    @Binds
    abstract @ContextActivity
    MainContract.View bindView(MainActivity view);
}
