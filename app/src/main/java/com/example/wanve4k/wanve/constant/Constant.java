package com.example.wanve4k.wanve.constant;

/**
 * Created by zhou on 2017/11/28.
 */

public class Constant {
    //服务器地址
    public static final String BASE_URL = "http://121.15.203.82:9210";
    //登录路径
    public static final String HUAMBO_LOGIN_URL = "/DMS_Phone/Login/LoginHandler.ashx?Action=Login&cmd=";
    //主页地址
    public static final String HUAMBO_MAIN_URL = BASE_URL + "/DMS_Phone/Login/QuickLogin.aspx?cmd=";
    //通讯录地址
    public static final String HUAMBO_MAILLIST = "/DMS_Phone/Contact/ContactHandler.ashx?Action=GetContactByUserID&para=";
    //vpn地址
    public static final String VPN_UTL = "http://172.21.102.222:8000/dgjxj_touch/Login/Index.aspx";

    public static final String MAIN_HTTPURL = "main_httpurl";
    public static final String USER_SHAREPRE = "user_sharepre";
    public static final String USER_LOGINCACHE = "user_logincache";
    public static final String USER_VPN_LOGINCACHE = "user_vpn_logincache";
    public static final String DEPARTMENTLIST = "departmentlist";
    public static final String DEPARTMENTDEPARTMENT = "departmentdepartment";
    public static final String MAIL = "mail";

}
