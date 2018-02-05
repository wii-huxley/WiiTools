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
public class YscmResultBean implements Serializable {

    // 图片前缀地址
    public String httpImgDomain;

    // 返回代码0成功1失败 不跳到登录页面2失败跳到登陆页面
    public String code;

    // 提示信息，可直接弹出或者toast出msg
    public String msg;

    // code=0，返回成功值的类型0 Object 1 List 2 map 3字符串
    public String rtnType;

    // 系统时间戳，例如1440037096786
    public String sysTime;

    // code=0，返回成功的json字符串
    public String rtnValues;
}
