package com.huxley.wiitools.companyUtils.yscm.model;

import com.huxley.wiitools.WiiException;
import com.huxley.wiitools.companyUtils.acce.AcceSubscriber;
import com.huxley.wiitools.companyUtils.acce.model.bean.ResultBean;
import com.huxley.wiitools.companyUtils.acce.model.bean.UrlBean;
import com.huxley.wiitools.companyUtils.yscm.YscmApi;
import com.huxley.wiitools.companyUtils.yscm.YscmHttpClient;
import com.huxley.wiitools.companyUtils.yscm.YscmSubscriber;
import com.huxley.wiitools.companyUtils.yscm.model.bean.YscmCommonParamsBean;
import com.huxley.wiitools.companyUtils.yscm.model.bean.YscmResultBean;
import com.huxley.wiitools.companyUtils.yscm.model.bean.YsmcParamsBean;
import com.huxley.wiitools.retrofitUtils.RxHelper;
import com.huxley.wiitools.utils.GsonUtils;
import com.huxley.wiitools.utils.Tools;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huxley on 2017/8/17.
 */
public class YscmHttpModel {

    private static YscmHttpModel instance;
    private HashMap<String, YscmApi> mApi;

    public UrlBean defaultUrl;

    public UrlBean currentUrl;
    public String currentAction;
    public YsmcParamsBean business;


    private YscmHttpModel() {
        mApi = new HashMap<>();
        business = new YsmcParamsBean();
    }


    public static YscmHttpModel getInstance() {
        if (instance == null) {
            synchronized (YscmHttpModel.class) {
                if (instance == null) {
                    instance = new YscmHttpModel();
                }
            }
        }
        return instance;
    }


    public YscmHttpModel setDefaultUrl(UrlBean urlBean) {
        defaultUrl = urlBean;
        return this;
    }


    public YscmHttpModel setDefaultUrl(String url, String path) {
        defaultUrl = new UrlBean(url, path);
        return this;
    }


    public YscmHttpModel setUrl(UrlBean urlBean) {
        currentUrl = urlBean;
        return this;
    }


    public YscmHttpModel setUrl(String url, String path) {
        currentUrl = new UrlBean(url, path);
        return this;
    }


    public YscmHttpModel setAction(String action) {
        this.currentAction = action;
        return this;
    }


    public YscmHttpModel addParam(String key, String value) {
        business.add(key, value);
        return this;
    }


    public synchronized <T> void post(YscmSubscriber<T> subscriber) {
        post().subscribe(subscriber);
    }


    public synchronized Observable<String> post() {
        initPost();
        Observable<String> post = post(currentUrl, currentAction, business);
        clearPost();
        return post;
    }


    private void clearPost() {
        currentUrl = defaultUrl;
        currentAction = null;
        business = new YsmcParamsBean();
    }


    private void initPost() {
        if (currentUrl == null) currentUrl = defaultUrl;
        if (business == null) business = new YsmcParamsBean();
        if (currentUrl == null) {
            throw new WiiException("202", "请设置默认url");
        }
        if (currentAction == null) {
            throw new WiiException("202", "请设置serviceCode");
        }
    }


    public synchronized Observable<String> post(UrlBean urlBean, String action, YsmcParamsBean businessBean) {
        if (!mApi.containsKey(urlBean.url)) {
            mApi.put(urlBean.url, new YscmHttpClient().getApi(urlBean.url));
        }
        return mApi.get(urlBean.url)
            .yscmMobileErpAppService(action, businessBean.build())
            .compose(RxHelper.<YscmResultBean>io_main())
            .compose(this.handleResult());
    }


    private Observable.Transformer<YscmResultBean, String> handleResult() {
        return new Observable.Transformer<YscmResultBean, String>() {
            @Override
            public Observable<String> call(Observable<YscmResultBean> tObservable) {
                return tObservable.flatMap(
                    new Func1<YscmResultBean, Observable<String>>() {
                        @Override
                        public Observable<String> call(YscmResultBean result) {
                            if ("0".equals(result.code)) {
                                return Observable.just(result.rtnValues);
                            } else {
                                return Observable.error(new WiiException(result.code, result.msg));
                            }
                        }
                    }
                );
            }
        };
    }

    // public synchronized Observable<ResponseBody> uploadPics(List<File> files) {
    //
    //     RequestBody requestFile =
    //         RequestBody.create(MediaType.parse("multipart/form-data"), file);
    //
    //     // MultipartBody.Part is used to send also the actual file name
    //     MultipartBody.Part body =
    //         MultipartBody.Part.createFormData("image", file.getName(), requestFile);
    //
    //     // 添加描述
    //     String descriptionString = "hello, 这是文件描述";
    //     RequestBody description =
    //         RequestBody.create(
    //             MediaType.parse("multipart/form-data"), descriptionString);
    //
    //
    //
    //     if (file == null || !file.exists() || file.length() == 0) {
    //         return Observable.empty();
    //     }
    //     boolean isPng = file.getName().lastIndexOf("png") > 0 ||
    //         file.getName().lastIndexOf("PNG") > 0;
    //     boolean isJpg = file.getName().lastIndexOf("jpg") > 0 ||
    //         file.getName().lastIndexOf("JPG") > 0
    //         || file.getName().lastIndexOf("jpeg") > 0 || file.getName().lastIndexOf("JPEG") > 0;
    //     String mediaType = null;
    //     if (isPng) {
    //         mediaType = "image/png; charset=UTF-8";
    //     } else if (isJpg) {
    //         mediaType = "image/jpeg; charset=UTF-8";
    //     }
    //     if (!mApi.containsKey(urlBean.url)) {
    //         mApi.put(urlBean.url, new YscmHttpClient().getApi(urlBean.url));
    //     }
    //     return mApi.get(urlBean.url)
    //         .uploadFile("uploadImg", RequestBody.create(MediaType.parse(mediaType), file))
    //         .compose(RxHelper.<ResponseBody>io_main());
    // }


    public synchronized Observable<String> uploadPic(UrlBean urlBean, File file) {
        if (file == null || !file.exists() || file.length() == 0) {
            return Observable.empty();
        }

        MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(),
            RequestBody.create(null, file));

        YscmCommonParamsBean yscmCommonParamsBean = new YscmCommonParamsBean();
        yscmCommonParamsBean.loginId = YscmUserModel.getInstance().getLoginId();
        yscmCommonParamsBean.loginKey = YscmUserModel.getInstance().getLoginKey();
        yscmCommonParamsBean.deviceId = Tools.getSzImei();
        yscmCommonParamsBean.version = Tools.getVersionName();
        yscmCommonParamsBean.deviceType = "1";

        if (!mApi.containsKey(urlBean.url + "loadPic")) {
            mApi.put(urlBean.url + "loadPic", new YscmHttpClient().getUploadApi(urlBean.url));
        }
        return mApi.get(urlBean.url + "loadPic")
            .uploadFile(part, RequestBody.create(null, "uploadImg"), RequestBody.create(null, GsonUtils.toJson(yscmCommonParamsBean)))
            .compose(RxHelper.<YscmResultBean>io_main())
            .compose(this.handleResult());
    }


    public synchronized Observable<String> uploadPic(File file) {
        setAction("uploadImg");
        initPost();
        Observable<String> post = uploadPic(currentUrl, file);
        clearPost();
        return post;
    }
}