package com.example.wanve4k.wanve.bean;

/**
 * Created by zhou on 2017/11/28.
 */

public class LoginBean {

    /**
     * Result : false
     * Message : 用户不存在或密码错误!
     */

    private boolean Result;
    private String Message;

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
