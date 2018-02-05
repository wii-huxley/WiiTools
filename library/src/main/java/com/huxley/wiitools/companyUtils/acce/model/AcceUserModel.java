package com.huxley.wiitools.companyUtils.acce.model;

import com.huxley.wiitools.companyUtils.acce.model.bean.UserBean;
import com.huxley.wiitools.utils.GsonUtils;
import com.huxley.wiitools.utils.StringUtils;

/**
 * Created by huxley on 2017/8/17.
 */
public class AcceUserModel {

    private static AcceUserModel instance;
    private        UserBean      mUser;

    private AcceUserModel() {
    }

    public static AcceUserModel getInstance() {
        if (instance == null) {
            synchronized (AcceUserModel.class) {
                if (instance == null) {
                    instance = new AcceUserModel();
                }
            }
        }
        return instance;
    }

    public UserBean getUser() {
        if (mUser == null) {
            synchronized (UserBean.class) {
                if (mUser == null) {
                    mUser = new UserBean();
                    String read = (String) AcceSPModel.User.read(AcceSPModel.User.KEY_USER, "");
                    if (!StringUtils.isEmpty(read)) {
                        mUser = GsonUtils.fromJson(read, UserBean.class);
                    }
                }
            }
        }
        return mUser;
    }

    public void setUser(UserBean user) {
        AcceSPModel.User.save(AcceSPModel.User.KEY_USER, user);
        mUser = user;
    }

    public String getAtUserId() {
        if (StringUtils.isEmpty(mUser.atUserId)) {
            return "";
        } else {
            return getUser().atUserId;
        }
    }

    public String getDepartmentName() {
        if (StringUtils.isEmpty(mUser.departmentName)) {
            return "";
        } else {
            return getUser().departmentName;
        }
    }

    public String getDepartmentId() {
        if (StringUtils.isEmpty(mUser.departmentId)) {
            return "";
        } else {
            return getUser().departmentId;
        }
    }

    public String getOwerName() {
        if (StringUtils.isEmpty(mUser.companyName)) {
            return "";
        } else {
            return getUser().companyName;
        }
    }

    public String getCompanyId() {
        if (StringUtils.isEmpty(mUser.companyId)) {
            return "";
        } else {
            return getUser().companyId;
        }
    }

    public String getRealName() {
        if (StringUtils.isEmpty(mUser.realName)) {
            return "";
        } else {
            return getUser().realName;
        }
    }


    public void setAtUserId(String phoneNum) {
        getUser().atUserId = phoneNum;
    }
}