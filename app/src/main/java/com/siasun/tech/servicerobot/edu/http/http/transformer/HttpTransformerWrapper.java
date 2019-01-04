package com.siasun.tech.servicerobot.edu.http.http.transformer;


import com.siasun.tech.servicerobot.edu.http.http.responseparser.FuncResponseEntityParser;
import com.siasun.tech.servicerobot.edu.http.http.responseparser.FuncResponseStringParser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 健 on 2017/7/23.s
 * 控制操作线程的辅助类
 */

public class HttpTransformerWrapper {

    /**
     * 返回String数据
     * */
    public static class ResponseToString{
        public static ObservableTransformer<String, String> requestThreadIoMain() {
            return new ObservableTransformer<String, String>() {
                @Override
                public ObservableSource<String> apply(@NonNull Observable<String> upstream) {
                    return upstream
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
//                            .observeOn(Schedulers.computation())
                            .map(new FuncResponseStringParser())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }
    }

    /**
     * 返回实体数据
     * */
    public static class ResponseToEntity {
        public static <T> ObservableTransformer<String, T> requestThreadIoMain(final Class<T> classOfT) {
            return new ObservableTransformer<String, T>() {
                @Override
                public ObservableSource<T> apply(@NonNull Observable<String> upstream) {
                    return upstream
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
//                            .observeOn(Schedulers.computation())
                            .map(new FuncResponseEntityParser<>(classOfT))
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }
    }
}
