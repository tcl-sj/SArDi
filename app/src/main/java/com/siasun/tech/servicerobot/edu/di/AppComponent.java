package com.siasun.tech.servicerobot.edu.di;

import android.app.Application;

import com.siasun.tech.servicerobot.edu.MApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by jian.shi on 2018/7/11.
 *
 * @Email shijian1@siasun.com
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<MApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
