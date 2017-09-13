package com.huxley.wiitools.companyUtils.acce.model;

import com.huxley.wiitools.WiiException;
import com.huxley.wiitools.companyUtils.acce.AcceApi;
import com.huxley.wiitools.companyUtils.acce.AcceHttpClient;
import com.huxley.wiitools.companyUtils.acce.AcceSubscriber;
import com.huxley.wiitools.companyUtils.acce.model.bean.BusinessBean;
import com.huxley.wiitools.companyUtils.acce.model.bean.ResultBean;
import com.huxley.wiitools.companyUtils.acce.model.bean.UrlBean;

import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huxley on 2017/8/17.
 */
public class AcceHttpModel {

    private static AcceHttpModel            instance;
    private        HashMap<String, AcceApi> mApi;

    public UrlBean defaultUrl;

    public UrlBean      currentUrl;
    public String       currentCode;
    public BusinessBean business;

    private AcceHttpModel() {
        mApi = new HashMap<>();
        business = new BusinessBean();
    }

    public static AcceHttpModel getInstance() {
        if (instance == null) {
            synchronized (AcceHttpModel.class) {
                if (instance == null) {
                    instance = new AcceHttpModel();
                }
            }
        }
        return instance;
    }

    public AcceHttpModel setDefaultUrl(UrlBean urlBean) {
        defaultUrl = urlBean;
        return this;
    }

    public AcceHttpModel setDefaultUrl(String url, String path) {
        defaultUrl = new UrlBean(url, path);
        return this;
    }

    public AcceHttpModel setUrl(UrlBean urlBean) {
        currentUrl = urlBean;
        return this;
    }

    public AcceHttpModel setUrl(String url, String path) {
        currentUrl = new UrlBean(url, path);
        return this;
    }

    public AcceHttpModel setServiceCode(String serviceCode) {
        this.currentCode = serviceCode;
        return this;
    }

    public AcceHttpModel addBusiness(String key, String value) {
        business.add(key, value);
        return this;
    }

    public synchronized <T> void post(AcceSubscriber<T> subscriber) {
        post().subscribe(subscriber);
    }

    public synchronized Observable<Object> post() {
        initPost();
        Observable<Object> post = post(currentUrl, currentCode, business);
        clearPost();
        return post;
    }

    private void clearPost() {
        currentUrl = defaultUrl;
        currentCode = null;
        business = new BusinessBean();
    }

    private void initPost() {
        if (currentUrl == null) currentUrl = defaultUrl;
        if (business == null) business = new BusinessBean();
        if (currentUrl == null) {
            throw new WiiException("202", "请设置默认url");
        }
        if (currentCode == null) {
            throw new WiiException("202", "请设置serviceCode");
        }
    }


    public synchronized Observable<Object> post(UrlBean urlBean, String serviceCode, BusinessBean businessBean) {
        if (!mApi.containsKey(urlBean.url)) {
            mApi.put(urlBean.url, new AcceHttpClient().getAcceApi(urlBean.url));
        }
        AcceApi acceApi = mApi.get(urlBean.url);
        Observable<ResultBean<Object>> observable;
        switch (urlBean.path) {
            case "api/auth":
                observable = acceApi.apiAuth(serviceCode, businessBean.build());
                break;
            case "sms/message":
                observable = acceApi.smsMessage(serviceCode, businessBean.build());
                break;
            case "api/iocar":
                observable = acceApi.apiIocar(serviceCode, businessBean.build());
                break;
            default:
                observable = Observable.empty();
                break;
        }
        return observable.compose(io_main())
                .compose(handleResult());
    }

    private Observable.Transformer<ResultBean<Object>, Object> handleResult() {
        return new Observable.Transformer<ResultBean<Object>, Object>() {
            @Override
            public Observable<Object> call(Observable<ResultBean<Object>> tObservable) {
                return tObservable.flatMap(
                        new Func1<ResultBean<Object>, Observable<Object>>() {
                            @Override
                            public Observable<Object> call(ResultBean<Object> result) {
                                if ("200".equals(result.returnCode)) {
                                    return Observable.just(result.result);
                                } else {
                                    return Observable.error(new WiiException(result.returnCode, result.returnMsg));
                                }
                            }
                        }
                );
            }
        };
    }


    private Observable.Transformer<ResultBean<Object>, ResultBean<Object>> io_main() {
        return new Observable.Transformer<ResultBean<Object>, ResultBean<Object>>() {
            @Override
            public Observable<ResultBean<Object>> call(Observable<ResultBean<Object>> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}