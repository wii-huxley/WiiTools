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
public class YscmUserBean implements Serializable {

    //登录ID
    public String loginId;
    //用户职位， 4-工程师，5-工人，6-巡刊员，7-物业关系员
    public String userPosition;
    //登录key
    public String loginKey;

}
