package com.huxley.wiitools.companyUtils.acce;

import com.huxley.wiitools.companyUtils.acce.model.AcceUserModel;
import com.huxley.wiitools.retrofitUtils.HttpClient;
import com.huxley.wiitools.retrofitUtils.OkHttpLoggingInterceptor;
import com.huxley.wiitools.utils.Constant;
import com.huxley.wiitools.utils.DateUtils;
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

public class AcceHttpClient {

    public AcceApi getAcceApi(String baseUrl) {
        return new HttpClient<>(AcceApi.class).getApi(baseUrl, getOkHttpClient());
    }

    public OkHttpClient getOkHttpClient() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String nonce = AcceTools.getNonce();
                String timestamp = DateUtils.getCurrentTime(Constant.DATE_FORMAT_1);
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        .addQueryParameter("nonce", nonce)
                        .addQueryParameter("timestamp", timestamp)
                        .addQueryParameter("editTime", timestamp)
                        .addQueryParameter("sign", AcceTools.getSign(AcceUserModel.getInstance().getUser().token, timestamp, nonce))
                        .addQueryParameter("device", Tools.getDeviceName())
                        .addQueryParameter("ipaddr", Tools.getIpAddress())
                        .addQueryParameter("mac", Tools.getMacAddress())
                        .addQueryParameter("realName", AcceUserModel.getInstance().getRealName())
                        .addQueryParameter("companyId", AcceUserModel.getInstance().getCompanyId())
                        .addQueryParameter("owerName", AcceUserModel.getInstance().getOwerName())
                        .addQueryParameter("departmentId", AcceUserModel.getInstance().getDepartmentId())
                        .addQueryParameter("departmentName", AcceUserModel.getInstance().getDepartmentName())
                        .addQueryParameter("editUserId", AcceUserModel.getInstance().getAtUserId())
                        .addQueryParameter("sn", AcceTools.getSerialNumber())
                        .addQueryParameter("app", Tools.getAppName())
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return new OkHttpClient.Builder()
                .addInterceptor(addQueryParameterInterceptor)
                .addInterceptor(new OkHttpLoggingInterceptor())
                .build();
    }
}
