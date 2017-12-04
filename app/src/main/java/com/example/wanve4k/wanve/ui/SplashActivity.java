package com.example.wanve4k.wanve.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.bean.UserBean;
import com.example.wanve4k.wanve.constant.Constant;
import com.example.wanve4k.wanve.util.ProxySettings;
import com.example.wanve4k.wanve.util.SpUtil;
import com.example.wanve4k.wanve.util.ToastUtil;

import net.arraynetworks.vpn.Common;
import net.arraynetworks.vpn.VPNManager;
import net.arraynetworks.vpn.Version;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    public String main_url;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Common.VpnMsg.MSG_VPN_CONNECTING:
                    Log.d("MSG_VPN_CONNECTING", "handleMessage: ");
                    Toast.makeText(getApplicationContext(), "正在连接vpn服务器", Toast.LENGTH_SHORT).show();
                    break;
                case Common.VpnMsg.MSG_VPN_CONNECTED:
                    Log.d("MSG_VPN_CONNECTED", "handleMessage: ");

                    Toast.makeText(getApplicationContext(), "成功连接，正在跳转", Toast.LENGTH_SHORT).show();

                    ProxySettings.setProxy(getApplicationContext(), ProxySettings.DefaultHost,
                            VPNManager.getInstance().getHttpProxyPort());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.MAIN_HTTPURL, String.format("http://172.21.102.222:8000/dgjxj_touch/Login/Index.aspx"));
                    startActivity(intent);
                    finish();
                    break;
                case Common.VpnMsg.MSG_VPN_DISCONNECTING:
                    Log.d("MSG_VPN_DISCONNECTING", "handleMessage: ");
                    break;
                case Common.VpnMsg.MSG_VPN_DISCONNECTED:
                    Log.d("MSG_VPN_DISCONNECTED", "handleMessage: ");
                    ToastUtil.show(getApplicationContext(), "连接断开，Session结束");
                    break;
                case Common.VpnMsg.MSG_VPN_CONNECT_FAILED:
                    Log.d("MSG_VPN_CONNECT_FAILED", "handleMessage: ");
                    ToastUtil.show(getApplicationContext(), "正在登出");
                    break;
                case Common.VpnMsg.MSG_VPN_RECONNECTING:
                    Log.d("MSG_VPN_RECONNECTING", "handleMessage: ");
                    ToastUtil.show(getApplicationContext(), "连接断开，正在进行重连");
                    break;
                case Common.VpnMsg.MSG_VPN_LOGIN:
                    Log.d("MSG_VPN_LOGIN", "handleMessage: ");
                    VPNManager.AAAMethod[] mMethods = (VPNManager.AAAMethod[]) (msg.obj);
                    if (0 == mMethods.length) {
                        VPNManager.getInstance().cancelLogin();
                    }
                    ToastUtil.show(getApplicationContext(), "vpn账号或密码有误-------这里有问题呢------");
                    break;
                case Common.VpnMsg.MSG_VPN_DEVREG:
                    Log.d("MSG_VPN_DEVREG", "handleMessage: ");
                    ToastUtil.show(getApplicationContext(), "需要用户输入认证凭证及设备名称。");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
    private UserBean userVpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //判断是否存在缓存数据
        UserBean userBean = (UserBean) SpUtil.getObject(getApplicationContext(), Constant.USER_SHAREPRE, UserBean.class);
        userVpn = (UserBean) SpUtil.getObject(getApplicationContext(), Constant.USER_VPN_LOGINCACHE, UserBean.class);


        if (userBean != null) {
            Log.d(TAG, "onCreate: ------------------------------");
            if (userBean.isAutomitic()) {
                if (userVpn != null && userVpn.isVpn()){
                    ToastUtil.show(getApplicationContext(),"vpn登录");
                    vpnToLogin();
                }else {
                    main_url = Constant.HUAMBO_MAIN_URL + "{UserID:'" + userBean.getUsername() + "'" + ",UserPsw:'" + userBean.getPassword() + "'}";
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constant.MAIN_HTTPURL, String.format(main_url));
                    startActivity(intent);
                    finish();
                }
            }else {
                startToLogin();
            }
        }else{
            startToLogin();
        }
    }

    /**
     * vpn登录
     */
    private void vpnToLogin() {
        //启动vpn
        Log.d(TAG, "onResponse: ---------------");
        VPNManager.getInstance().setHandler(mHandler);
        VPNManager.getInstance().setLogLevel(Common.LogLevel.LOG_DEBUG, 0);
        Log.i("", "SDK version " + Version.VERSION + " " + Version.BUILD_DATE);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                VPNManager.getInstance().startVPN("mobile.dg.cn", 443, userVpn.getVpnUsername(),
                        userVpn.getVpnPassword(),"", "",VPNManager.VpnFlag.VPN_FLAG_HTTP_PROXY
                                | VPNManager.VpnFlag.VPN_FLAG_SOCK_PROXY);
            }
        });
        t.start();
    }

    /**
     * 正常登陆
     */
    private void startToLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 1800);
    }
}
