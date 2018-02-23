package com.example.wanve4k.wanve.constant;

/**
 * Created by zhou on 2017/11/28.
 */

public class Constant {
    /***
     * http://120.86.117.106/DMS_Phone

     http://www.dgluqiao.com/DMS_Phone

     bgs 123456
     */
    //服务器地址
    public static final String BASE_URL = "http://121.15.203.82:9210";
    //public static final String BASE_URL = "http://19.108.192.125";
    //登录路径
    public static final String HUAMBO_LOGIN_URL = "/DMS_Phone_SLJ/Login/LoginHandler.ashx?Action=Login&cmd=";
    //主页地址
    public static final String HUAMBO_MAIN_URL = BASE_URL + "/DMS_Phone_SLJ/Login/QuickLogin.aspx?cmd=";
    //通讯录地址
    public static final String HUAMBO_MAILLIST = "/DMS_Phone_SLJ/Contact/ContactHandler.ashx?Action=GetContactByUserID&para=";
    //vpn地址
    public static final String VPN_UTL = "http://19.108.192.125/DMS_Phone/Login/LoginIndex.aspx?Quit=&JumpPage=&JumpPagePara=";

    public static final String MAIN_HTTPURL = "main_httpurl";
    public static final String USER_SHAREPRE = "user_sharepre";
    public static final String USER_LOGINCACHE = "user_logincache";
    public static final String USER_VPN_LOGINCACHE = "user_vpn_logincache";
    public static final String DEPARTMENTLIST = "departmentlist";
    public static final String DEPARTMENTDEPARTMENT = "departmentdepartment";
    public static final String MAIL = "mail";

}
