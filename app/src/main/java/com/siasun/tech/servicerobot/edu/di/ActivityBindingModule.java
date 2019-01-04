package com.siasun.tech.servicerobot.edu.di;

import com.siasun.tech.servicerobot.edu.ui.module.MainModule;
import com.siasun.tech.servicerobot.edu.ui.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by jian.shi on 2018/7/11.
 *
 * @Email shijian1@siasun.com
 */
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
