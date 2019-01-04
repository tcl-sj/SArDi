package com.siasun.tech.servicerobot.edu.http.http.interceptor;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;

import edu.servicerobot.tech.siasun.com.projectedu.http.base.tools.NetworkUtil;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 健 on 2017/8/24.
 * 缓存控制拦截器
 */

public class CacheControlInterceptor implements Interceptor {
    private Context mContext;

    public CacheControlInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtil.isConnected(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);
        if (NetworkUtil.isConnected(mContext)) {
            int maxAge = 60;//在有网络连接的情况下，一分钟内不再请求网络
            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=" + maxAge;
            }
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();

        } else {
            int maxStale = 60 * 60 * 24 * 30;   //离现时缓存保存一个月，单位：秒
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
