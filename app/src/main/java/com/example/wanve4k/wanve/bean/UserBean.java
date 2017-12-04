package com.example.wanve4k.wanve.bean;

/**
 * Created by zhou on 2017/11/28.
 *
 * 用户信息与vpn登录信息
 *
 */

public class UserBean {
    private String username;
    private String password;
    private boolean isVpn;
    private String vpnUsername;
    private String vpnPassword;
    private boolean isRemenber;
    private boolean isAutomitic;

    public boolean isRemenber() {
        return isRemenber;
    }

    public void setRemenber(boolean remenber) {
        isRemenber = remenber;
    }

    public boolean isAutomitic() {
        return isAutomitic;
    }

    public void setAutomitic(boolean automitic) {
        isAutomitic = automitic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVpn() {
        return isVpn;
    }

    public void setVpn(boolean vpn) {
        isVpn = vpn;
    }

    public String getVpnUsername() {
        return vpnUsername;
    }

    public void setVpnUsername(String vpnUsername) {
        this.vpnUsername = vpnUsername;
    }

    public String getVpnPassword() {
        return vpnPassword;
    }

    public void setVpnPassword(String vpnPassword) {
        this.vpnPassword = vpnPassword;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isVpn=" + isVpn +
                ", vpnUsername='" + vpnUsername + '\'' +
                ", vpnPassword='" + vpnPassword + '\'' +
                ", isRemenber=" + isRemenber +
                ", isAutomitic=" + isAutomitic +
                '}';
    }
}
