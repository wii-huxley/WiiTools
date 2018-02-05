package com.huxley.wiitools.companyUtils.acce.model.bean;

import com.google.gson.JsonObject;
import com.huxley.wiitools.companyUtils.acce.model.AcceUserModel;
import com.huxley.wiitools.utils.GsonUtils;

/**
 * Created by huxley on 2017/8/17.
 */

public class BusinessBean {

    private JsonObject mBusinessObject = new JsonObject();

    public BusinessBean() {
        this.add("atUserId", AcceUserModel.getInstance().getUser());
    }

    public BusinessBean add(String key, Object value) {
        if (value == null) {
            mBusinessObject.addProperty(key, "");
        } else if (value instanceof String) {
            mBusinessObject.addProperty(key, (String) value);
        } else {
            mBusinessObject.addProperty(key, GsonUtils.toJson(value));
        }
        return this;
    }

    public String build() {
        return mBusinessObject.toString();
    }
}