package com.huxley.wiitools.companyUtils.acce.model.bean;

import java.io.Serializable;

/**
 * Created by huxley on 2017/8/17.
 */

public class UserBean implements Serializable {

    public String atUserId;
    public String phoneNum;
    public String realName;
    public String token;
    public String thumbnail;
    public String nickName;
    public String sex;
    public String companyName;
    public String companyId;
    public String departmentId;
    public String departmentName;
    public String companyProvince;
    public String companyCity;
    public String companyDistrict;
    public String companyAuthority;
    public String qrCode;

    public UserBean() {
        token = "411e7bc272a421b44802a9f608a740e5ca74872a";
    }

    public UserBean(String phoneNum) {
        token = "411e7bc272a421b44802a9f608a740e5ca74872a";
        atUserId = phoneNum;
    }
}
