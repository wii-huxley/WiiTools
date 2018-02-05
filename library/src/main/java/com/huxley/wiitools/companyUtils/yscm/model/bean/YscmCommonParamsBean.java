package com.huxley.wiitools.companyUtils.yscm.model.bean;

import java.io.Serializable;

//////////////////////////////////////////////////////////
//
//      我们的征途是星辰大海
//
//		...．．∵ ∴★．∴∵∴ ╭ ╯╭ ╯╭ ╯╭ ╯∴∵∴∵∴ 
//		．☆．∵∴∵．∴∵∴▍▍ ▍▍ ▍▍ ▍▍☆ ★∵∴ 
//		▍．∴∵∴∵．∴▅███████████☆ ★∵ 
//		◥█▅▅▅▅███▅█▅█▅█▅█▅█▅███◤ 
//		． ◥███████████████████◤
//		.．.．◥████████████████■◤
//      
//      Created by huxley on 2017/10/12.
//
//////////////////////////////////////////////////////////
public class YscmCommonParamsBean implements Serializable {

    public String loginId;
    public String loginKey;
    public String deviceId;
    public String version;
    public String deviceType;


    public YscmCommonParamsBean() {
    }



    public YscmCommonParamsBean(String loginId, String loginKey, String deviceId, String version, String deviceType) {
        this.loginId = loginId;
        this.loginKey = loginKey;
        this.deviceId = deviceId;
        this.version = version;
        this.deviceType = deviceType;
    }
}
