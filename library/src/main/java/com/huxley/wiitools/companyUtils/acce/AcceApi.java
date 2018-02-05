package com.huxley.wiitools.companyUtils.acce;


import com.huxley.wiitools.companyUtils.acce.model.bean.ResultBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by huxley on 2017/3/25.
 */
public interface AcceApi {

    @FormUrlEncoded
    @POST("api/auth")
    Observable<ResultBean<Object>> apiAuth(
            @Field("serviceCode") String serviceCode,
            @Field("business") String business
    );

    @FormUrlEncoded
    @POST("sms/message")
    Observable<ResultBean<Object>> smsMessage(
            @Field("serviceCode") String serviceCode,
            @Field("business") String business
    );

    @FormUrlEncoded
    @POST("api/iocar")
    Observable<ResultBean<Object>> apiIocar(
            @Field("serviceCode") String serviceCode,
            @Field("business") String business
    );
}
