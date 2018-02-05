package com.huxley.wiitools.companyUtils.yscm;

import com.huxley.wiitools.companyUtils.yscm.model.bean.YscmResultBean;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by huxley on 2017/3/25.
 */
public interface YscmApi {

    @FormUrlEncoded
    @POST("yscm/mobile/erpAppService")
    Observable<YscmResultBean> yscmMobileErpAppService(
        @Field("action") String action,
        @Field("params") String params
    );
    //
    // @Multipart
    // @POST("yscm/mobile/erpAppService")
    // Observable<ResponseBody> uploadFiles(
    //     @Field("action") String action,
    //     @PartMap() Map<String, RequestBody> maps
    // );
    //
    // @Multipart
    // @POST("yscm/mobile/erpAppService")
    // Observable<ResponseBody> uploadFiles(
    //     @Field("action") String action,
    //     @Part("filename") String description,
    //     @PartMap() Map<String, RequestBody> maps
    // );

    @Multipart
    @POST("yscm/mobile/erpAppService")
    Observable<YscmResultBean> uploadFile(
        @Part MultipartBody.Part file,
        @Part("action") RequestBody action,
        @Part("commonParams") RequestBody commonParams
    );
}
