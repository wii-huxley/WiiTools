package com.huxley.wiitools.companyUtils.acce.model.bean;

import java.io.Serializable;

/**
 * Created by huxley on 2017/8/9.
 */
public class ResultBean<T> implements Serializable {

    public String cacheTime;
    public String checkTime;
    public String returnCode;
    public String returnMsg;
    public String serviceCode;
    public T      result;
}