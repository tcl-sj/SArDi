package com.siasun.tech.servicerobot.edu.http.http.responseparser;

import android.util.Log;

import org.json.JSONObject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by 健 on 2017/7/25.
 * 对response生成的实体bean进行预处理
 */

public class FuncResponseStringParser implements Function<String, String> {
    private final static String TAG = FuncResponseStringParser.class.getSimpleName();
    /**
     * {"Code": 100, "Msg": "参数不完整", "Resp": [ { "msg": "操作成功！"} ] }
     */
    private final static String RESPONSE_CODE = "code";
    private final static String RESPONSE_MSG = "Msg";
    private final static String RESPONSE_RESP = "Resp";
    private final static String RESPONSE_RESP_MSG = "msg";

    private final static int RESPONSE_CODE_SUCCESS = 200;

    @Override
    public String apply(@NonNull String dataResponse) throws Exception {
        Log.e(TAG, "apply : " + Thread.currentThread().getName() + " thread");
        Log.i(TAG, "response : " + dataResponse);
        JSONObject jsonObject = new JSONObject(dataResponse);
        Log.i(TAG, "Msg : " + jsonObject.getString(RESPONSE_MSG));
        Log.i(TAG, "data : " + jsonObject.getString(RESPONSE_RESP));
        if (jsonObject.getInt(RESPONSE_CODE) == RESPONSE_CODE_SUCCESS) {
            Log.i(TAG, "convert the data string of response");
            String resp = jsonObject.getString(RESPONSE_RESP);
            if (resp == null || resp.isEmpty()) {
                return "the Resp is empty";
            } else {
                return jsonObject.getString(RESPONSE_RESP);
            }
        } else {
            Log.w(TAG, jsonObject.optJSONArray(RESPONSE_RESP).optJSONObject(0).getString(RESPONSE_RESP_MSG));
            throw new IllegalArgumentException(jsonObject.toString());
        }
    }
}
