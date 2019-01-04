package com.siasun.tech.servicerobot.edu.http.http.responseparser;

import android.util.Log;

import com.siasun.tech.servicerobot.edu.http.http.tools.JsonUtil;

import org.json.JSONObject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by 健 on 2017/7/25.
 * 对response生成的实体bean进行预处理
 */

public class FuncResponseEntityParser<T> implements Function<String, T> {
    private final static String TAG = FuncResponseEntityParser.class.getSimpleName();
    /**
     * {"Code": 100, "Msg": "参数不完整", "Resp": [ { "msg": "操作成功！"} ] }
     */
    private final static String RESPONSE_CODE = "code";
    private final static String RESPONSE_MSG = "Msg";
    private final static String RESPONSE_RESP = "Resp";
    private final static String RESPONSE_RESP_MSG = "msg";

    private final static int RESPONSE_CODE_SUCCESS = 200;

    private Class<T> classOfT;

    public FuncResponseEntityParser(Class<T> classOfT) {
        this.classOfT = classOfT;
    }

    @Override
    public T apply(@NonNull String dataResponse) throws Exception {
        Log.e(TAG, "apply : " + Thread.currentThread().getName() + " thread");
        Log.i(TAG, "response : " + dataResponse);
        JSONObject jsonObject = new JSONObject(dataResponse);
        Log.i(TAG, "Msg : " + jsonObject.getString(RESPONSE_MSG));
        Log.i(TAG, "data : " + jsonObject.get(RESPONSE_RESP));
        if (jsonObject.getInt(RESPONSE_CODE) == RESPONSE_CODE_SUCCESS) {
            Log.i(TAG, "convert data response to real entity");
            return JsonUtil.gson().fromJson(jsonObject.optJSONArray(RESPONSE_RESP).getString(0), classOfT);
        } else {
            Log.w(TAG, jsonObject.optJSONArray(RESPONSE_RESP).optJSONObject(0).getString(RESPONSE_RESP_MSG));
            throw new IllegalArgumentException(jsonObject.toString());
        }
    }
}
