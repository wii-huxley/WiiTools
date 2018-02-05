package com.huxley.wiitools.companyUtils.yscm.model;

import com.huxley.wiitools.companyUtils.acce.model.bean.UserBean;
import com.huxley.wiitools.companyUtils.yscm.model.bean.YscmUserBean;
import com.huxley.wiitools.utils.GsonUtils;
import com.huxley.wiitools.utils.StringUtils;

/**
 * Created by huxley on 2017/8/17.
 */
public class YscmUserModel {

    private static YscmUserModel instance;
    private YscmUserBean mUser;

    private YscmUserModel() {
    }

    public static YscmUserModel getInstance() {
        if (instance == null) {
            synchronized (YscmUserModel.class) {
                if (instance == null) {
                    instance = new YscmUserModel();
                }
            }
        }
        return instance;
    }

    public YscmUserBean getUser() {
        if (mUser == null) {
            synchronized (UserBean.class) {
                if (mUser == null) {
                    mUser = new YscmUserBean();
                    String read = (String) YscmSPModel.User.read(YscmSPModel.User.KEY_USER, "");
                    if (!StringUtils.isEmpty(read)) {
                        mUser = GsonUtils.fromJson(read, YscmUserBean.class);
                    }
                }
            }
        }
        return mUser;
    }

    public void setUser(YscmUserBean user) {
        YscmSPModel.User.save(YscmSPModel.User.KEY_USER, user);
        mUser = user;
    }

    public String getLoginId() {
        if (StringUtils.isEmpty(getUser().loginId)) {
            return "";
        }
        return getUser().loginId;
    }


    public String getLoginKey() {
        if (StringUtils.isEmpty(getUser().loginKey)) {
            return "";
        }
        return getUser().loginKey;
    }

    public
}