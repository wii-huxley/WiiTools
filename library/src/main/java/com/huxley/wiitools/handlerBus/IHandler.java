package com.huxley.wiitools.handlerBus;

import android.os.Message;

/**
 * handler回调更新接口
 * Created by huxley on 2017/4/21.
 */
public interface IHandler {

    /**
     * handler回调接口
     */
    void handleMessage(Message msg);
}
