package com.siasun.tech.servicerobot.edu.http.api;


import com.siasun.tech.servicerobot.edu.http.bean.AnswerBean;
import com.siasun.tech.servicerobot.edu.http.bean.RespData;
import com.siasun.tech.servicerobot.edu.http.bean.TokenBean;
import com.siasun.tech.servicerobot.edu.http.bean.UserInfoBean;
import com.siasun.tech.servicerobot.edu.http.http.annotation.BaseUrl;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jian.shi on 2019/1/2.
 *
 * @Email shijian1@siasun.com
 */
@BaseUrl(baseUrl = "http://192.168.1.188:8080")
public interface IApiService {
    /**
     * 登录接口
     */
    @POST("voicedemo/api/robot/login")
    Single<RespData<TokenBean>> login(@Body RequestBody body);

    /**
     * 获取用户信息接口
     */
    @GET("voicedemo/api/robot/getUserInfo")
    Single<RespData<UserInfoBean>> getUserInfo(@Query("robotId") String robotId);

    /**
     * 获取问题答案接口
     */
    @GET("voicedemo/api/voice/getAnswer")
    Single<RespData<AnswerBean>> getAnswer(@Query("question") String question);
}
