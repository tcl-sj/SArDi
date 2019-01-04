package com.siasun.tech.servicerobot.edu.http.http.tools;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jian.shi on 2019/1/2.
 *
 * @Email shijian1@siasun.com
 */
public class JsonUtil {
    private final static Gson mGson = new Gson();

    private JsonUtil() {}

    public static Gson gson() {
        return mGson;
    }

    public static RequestBody packageRequestParams(Map<String, String> params) {
        return RequestBody.create(MediaType.parse("Content-Type: application/json;charset=UTF-8"),
                mGson.toJson(params));
    }
}
