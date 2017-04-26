package com.huxley.wiitools.wiiHandler;

import java.util.Hashtable;

/**
 * WiiHandler操作对象（核心）
 * Created by huxley on 2017/4/21.
 */
public class HandlerBus {

    private static HandlerBus instance;

    protected Class<?>   clazz;

    private int                          mHandlerId  = 0X8;
    private Hashtable<Class<?>, Integer> mHandlerIds = null;

    /**
     * 获取hBaseHandler操作对象
     */
    public synchronized static HandlerBus getInstance() {
        if (instance == null) {
            synchronized (HandlerBus.class) {
                if (instance == null) {
                    instance = new HandlerBus();
                }
            }
        }
        return instance;
    }

    private HandlerBus() {
        WiiHandler.getInstance().setHandlerBus(this);
        mHandlerIds = new Hashtable<>();
    }

    /**
     * 把当前对象对象添加到指定键里面
     */
    public HandlerBus register(Class<?> clazz, IHandler iHandler) {
        if (clazz != null) {
            this.clazz = clazz;
            WiiHandler.getInstance().register(clazz, iHandler);
        }
        return this;
    }

    /**
     * 给指定的handler发送message
     */
    public HandlerBus post(Class<?> clazz, int tag, Object obj) {
        if (clazz != null) {
            this.clazz = clazz;
            WiiHandler.getInstance().post(clazz, tag, obj);
        }
        return this;
    }


    /**
     * 移除BaseHandler里面的指定key对象
     */
    public HandlerBus unregister(Class<?> clazz) {
        if (clazz != null) {
            this.clazz = clazz;
            WiiHandler.getInstance().unregister();
            mHandlerIds.remove(clazz);
        }
        return this;
    }

    /**
     * 移除所有的handler里面的对象
     */
    public HandlerBus unregisterAll() {
        WiiHandler.getInstance().unregisterAll();
        mHandlerIds.clear();
        return this;
    }

    public synchronized int getHandlerId() {
        if (mHandlerIds.containsKey(clazz)) {
            return mHandlerIds.get(clazz);
        } else {
            int handlerId = createHandlerId();
            mHandlerIds.put(clazz, handlerId);
            return handlerId;
        }
    }

    /**
     * 保证容器里面的ID值唯一性
     */
    private int createHandlerId() {
        if (mHandlerIds.containsValue(++mHandlerId)) {
            createHandlerId();
        }
        return mHandlerId;
    }
}
