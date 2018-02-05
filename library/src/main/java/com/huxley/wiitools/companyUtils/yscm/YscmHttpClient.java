package com.huxley.wiitools.companyUtils.yscm;

import com.huxley.wiitools.companyUtils.yscm.model.YscmUserModel;
import com.huxley.wiitools.companyUtils.yscm.model.bean.YscmCommonParamsBean;
import com.huxley.wiitools.retrofitUtils.HttpClient;
import com.huxley.wiitools.retrofitUtils.HttpLoggingInterceptor;
import com.huxley.wiitools.retrofitUtils.StethoInterceptor;
import com.huxley.wiitools.utils.GsonUtils;
import com.huxley.wiitools.utils.Tools;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by huxley on 2017/3/25.
 */

public class YscmHttpClient {

    public YscmApi getApi(String baseUrl) {
        return new HttpClient<>(YscmApi.class).getApi(baseUrl, getOkHttpClient());
    }

    public YscmApi getUploadApi(String baseUrl){
        return new HttpClient<>(YscmApi.class).getApi(baseUrl, getUploadOkHttpClient());
    }

    public OkHttpClient getUploadOkHttpClient() {
        return new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor())
            .addNetworkInterceptor(new StethoInterceptor())
            .build();
    }

    public OkHttpClient getOkHttpClient() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                YscmCommonParamsBean yscmCommonParamsBean = new YscmCommonParamsBean();
                yscmCommonParamsBean.loginId = YscmUserModel.getInstance().getLoginId();
                yscmCommonParamsBean.loginKey = YscmUserModel.getInstance().getLoginKey();
                yscmCommonParamsBean.deviceId = Tools.getSzImei();
                yscmCommonParamsBean.version = Tools.getVersionName();
                yscmCommonParamsBean.deviceType = "1";
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("commonParams", GsonUtils.toJson(yscmCommonParamsBean))
                    .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return new OkHttpClient.Builder()
            .addInterceptor(addQueryParameterInterceptor)
            .addInterceptor(new HttpLoggingInterceptor())
            .addNetworkInterceptor(new StethoInterceptor())
            .build();
    }
}
