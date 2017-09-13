package com.huxley.wiitools.retrofitUtils;


import com.huxley.wiitools.retrofitUtils.converterGson.GsonConverterFactory;
import com.huxley.wiitools.retrofitUtils.converterRxjava.RxJavaCallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by huxley on 2017/8/23.
 */

public class HttpClient<T>{

    Class<T> clazz;

    public HttpClient(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getApi(String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(clazz);
    }

    public T getApi(String baseUrl) {
        return getApi(baseUrl, getOkHttpClient());
    }


    public OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new OkHttpLoggingInterceptor())
                .build();
    }
}
