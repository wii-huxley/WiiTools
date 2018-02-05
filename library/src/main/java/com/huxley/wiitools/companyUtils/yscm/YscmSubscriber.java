package com.huxley.wiitools.companyUtils.yscm;

import com.huxley.wiitools.WiiException;
import com.huxley.wiitools.utils.GsonUtils;
import com.huxley.wiitools.utils.reflect.ClassTypeReflect;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import rx.Subscriber;

/**
 * Created by huxley on 2017/8/18.
 */

public abstract class YscmSubscriber<T> extends Subscriber<String> {

    private Type type;

    public YscmSubscriber() {
        super();
        type = ClassTypeReflect.getGenericType(this);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        String msg;
        if (e instanceof WiiException) {
            WiiException we = (WiiException) e;
            msg = we.getMessage();
            switch (we.getCode()) {
                case "1":

                    break;
                case "2":

                    return;
                default:

                    break;
            }
        } else if (e instanceof UnknownHostException) {
            msg = "没有网络...";
        } else if (e instanceof SocketTimeoutException) {
            msg = "超时...";
        } else {
            msg = "请求失败，请稍后重试...";
        }
        onError(msg);
    }

    @Override
    public void onNext(String obj) {
        try {
            T t = GsonUtils.fromJson(obj, type);
            onSuccess(t);
        } catch (Exception e) {
            if ("class java.lang.String".equals(type.toString())) {
                onSuccess((T) obj);
            } else {
                onError("数据解析失败！");
            }
        }
    }

    @Override
    public void onCompleted() {

    }

    public abstract void onSuccess(T t);

    public abstract void onError(String msg);
}
