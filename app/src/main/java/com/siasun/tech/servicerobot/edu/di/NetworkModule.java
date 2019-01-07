package com.siasun.tech.servicerobot.edu.di;

import com.siasun.tech.servicerobot.edu.http.bean.TokenBean;
import com.siasun.tech.servicerobot.edu.http.api.IApiService;
import com.siasun.tech.servicerobot.edu.http.http.annotation.BaseUrl;
import com.siasun.tech.servicerobot.edu.http.http.tools.JsonUtil;
import com.siasun.tech.servicerobot.edu.tools.SLog;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jian.shi on 2019/1/2.
 *
 * @Email shijian1@siasun.com
 */
@Module
public abstract class NetworkModule {

    @Provides
    @Singleton
    static IApiService provideApiService(OkHttpClient.Builder okHttpClientBuilder, final TokenBean tokenBean) {
        String baseUrl = IApiService.class.getAnnotation(BaseUrl.class).baseUrl();
        if (baseUrl == null) {
            throw new RuntimeException("Base url is null, please use annotation "
                    + "BaseUrl.class" + " to set baseUrl !");
        }
        Interceptor mTokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                SLog.d("intercept() called with: tokenBean = [" + tokenBean + "]");
                Request.Builder request = chain.request().newBuilder();
                request.addHeader("Content-Type", "application/json;charset=UTF-8");
                request.addHeader("token", tokenBean == null ? "" : tokenBean.getToken());
                request.addHeader("mac", "123456");
                return chain.proceed(request.build());
            }
        };
        //打印请求、响应日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClientBuilder.addInterceptor(loggingInterceptor)
                        .addNetworkInterceptor(loggingInterceptor)
                        .addNetworkInterceptor(mTokenInterceptor)
                        .build())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(JsonUtil.gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(IApiService.class);
    }

    @Provides
    @Singleton
    static OkHttpClient.Builder provideHttpClientBuilder() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(15, TimeUnit.SECONDS);
        httpClientBuilder.retryOnConnectionFailure(true);
        return httpClientBuilder;
    }
}
