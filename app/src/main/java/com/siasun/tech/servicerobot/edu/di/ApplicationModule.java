package com.siasun.tech.servicerobot.edu.di;

import android.app.Application;
import android.content.Context;

import com.siasun.tech.servicerobot.edu.http.bean.TokenBean;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by jian.shi on 2018/7/11.
 *
 * @Email shijian1@siasun.com
 */
@Module
public abstract class ApplicationModule {
    @Binds
    abstract @ContextApplication
    Context bindContext(Application application);

    @Singleton
    @Provides
    static TokenBean provideTokenBean() {
        return new TokenBean();
    }
}
