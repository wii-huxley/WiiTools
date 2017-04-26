package com.huxley.wiitools.wiiHandler;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by huxley on 2017/4/21.
 */
public class WiiHandler extends Handler {

    private static WiiHandler instance;

    private SparseArray<SoftReference<IHandler>> mHandlerList;
    private HandlerBus                           mHandlerBus;

    private HashMap<String, Object> tempMap = new HashMap<>();

    private WiiHandler() {
        mHandlerList = new SparseArray<>();
    }

    public synchronized static WiiHandler getInstance() {
        if (instance == null) {
            synchronized (WiiHandler.class) {
                if (instance == null) {
                    instance = new WiiHandler();
                }
            }
        }
        return instance;
    }

    @Override
    public void handleMessage(Message msg) {
        if (mHandlerList != null && mHandlerList.size() > 0) {
            if (mHandlerBus != null) {
                SoftReference<IHandler> reference = mHandlerList.get(mHandlerBus.getHandlerId());
                if (reference != null) {
                    IHandler ihandler = reference.get();
                    if (ihandler != null) {
                        ihandler.handleMessage(msg);
                    }
                }
            } else {
                System.out.println("handleMessage>>>>handler获取Key接口为空");
            }
        }
    }

    public void register(Class<?> clazz, IHandler iHandler) {
        if (mHandlerBus != null && clazz != null) {
            mHandlerList.put(mHandlerBus.getHandlerId(), new SoftReference<>(iHandler));
            for (String s : tempMap.keySet()) {
                if (s.contains(clazz.getName())) {
                    int tag = Integer.parseInt(s.split(clazz.getName())[1]);
                    Message message = this.obtainMessage();
                    message.what = tag;
                    message.obj = tempMap.get(s);
                    this.sendMessage(message);
                    tempMap.remove(s);
                    break;
                }
            }
        } else {
            System.out.println("register>>>handler获取Key接口为空");
        }
    }


    /**
     * 发送message信息个handler
     */
    public void post(Class clazz, int tag, Object obj) {
        if (mHandlerList.get(mHandlerBus.getHandlerId()) == null) {
            tempMap.put(clazz.getName() + tag, obj);
        } else {
            Message message = this.obtainMessage();
            message.what = tag;
            message.obj = obj;
            this.sendMessage(message);
        }
    }

    /**
     * 清除SparseArray集合中指定键的软引用的数据 (!--Key是通过接口获取的，只需要调用此方法即可)
     */
    public void unregister() {
        if (mHandlerList != null && mHandlerList.size() > 0) {
            if (mHandlerBus != null) {
                SoftReference<IHandler> reference = mHandlerList.get(mHandlerBus.getHandlerId());
                if (reference != null) {
                    reference.clear();
                    mHandlerList.remove(mHandlerBus.getHandlerId());
                }
            } else {
                System.out.println("unregister>>>handler获取Key接口为空");
            }
        }
    }

    /**
     * 清除SparseArray集合里面所有值
     */
    public void unregisterAll() {
        if (mHandlerList != null && mHandlerList.size() > 0) {
            for (int i = 0; i < mHandlerList.size(); i++) {
                mHandlerList.valueAt(i).clear();
            }
            mHandlerList.clear();
        }
    }

    public void setHandlerBus(HandlerBus handlerBus) {
        this.mHandlerBus = handlerBus;
    }
}