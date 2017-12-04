package com.example.wanve4k.wanve.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.base.BaseActivity;
import com.example.wanve4k.wanve.bean.LoginBean;
import com.example.wanve4k.wanve.bean.UserBean;
import com.example.wanve4k.wanve.constant.Constant;
import com.example.wanve4k.wanve.util.ProxySettings;
import com.example.wanve4k.wanve.util.SpUtil;
import com.example.wanve4k.wanve.util.ToastUtil;
import com.google.gson.Gson;

import net.arraynetworks.vpn.Common;
import net.arraynetworks.vpn.VPNManager;
import net.arraynetworks.vpn.Version;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_username) EditText et_username;
    @BindView(R.id.et_password) EditText et_password;
    @BindView(R.id.cb_remember) CheckBox cb_remember;
    @BindView(R.id.cb_automatic) CheckBox cb_automatic;
    @BindView(R.id.iv_show) ImageView iv_show;

    boolean isRemenber = false;
    boolean isAutomatic = false;
    private UserBean userBean;

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
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        userBean = new UserBean();
        //userVpn = (UserBean) SpUtil.getObject(this, Constant.USER_VPN_LOGINCACHE, UserBean.class);
        iv_show.setBackgroundResource(R.drawable.login__user_pass_visible);
        setInfo();
        getCheckBox();
    }

    @Override
    protected void onResume() {
        userVpn = (UserBean) SpUtil.getObject(this, Constant.USER_VPN_LOGINCACHE, UserBean.class);
        super.onResume();
    }

    private void setInfo() {
        UserBean userBean = (UserBean) SpUtil.getObject(getApplicationContext(), Constant.USER_SHAREPRE, UserBean.class);
        if (userBean != null){
            Log.d(TAG, "setInfo: "+"空的吗？");
            et_username.setText(userBean.getUsername());
            et_password.setText(userBean.getPassword());
            cb_remember.setChecked(userBean.isRemenber());
            cb_automatic.setChecked(userBean.isAutomitic());
        }
    }

    private void getCheckBox() {
        cb_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "--------------------------------onCheckedChanged: "+isChecked);
                isRemenber = isChecked;
            }
        });

        cb_automatic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "---------------------------------onCheckedChanged: "+isChecked);
                isAutomatic = isChecked;
                isRemenber = isChecked;
            }
        });
    }

    @OnClick({R.id.iv_clear, R.id.iv_show, R.id.bt_login, R.id.tv_vpn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                et_username.setText("");
                break;
            case R.id.iv_show:
                //这是一段很有意思的代码，根据点击来显示与隐藏密码
                int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                if (et_password.getInputType() == type) {
                    iv_show.setBackgroundResource(R.drawable.login_user_pass_show);
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    et_password.setSelection(et_password.getText().length());     //把光标设置到当前文本末尾
                } else {
                    iv_show.setBackgroundResource(R.drawable.login__user_pass_visible);
                    et_password.setInputType(type);
                    et_password.setSelection(et_password.getText().length());
                }
                break;
            case R.id.bt_login:
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    ToastUtil.show(getApplicationContext(), "用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.show(getApplicationContext(), "密码不能为空");
                    return;
                }
                final String login = Constant.BASE_URL + Constant.HUAMBO_LOGIN_URL + "{UserID:'" + username + "'" + ",UserPsw:'" + password + "'}";//登录验证地址
                login(login,username,password);
                break;
            case R.id.tv_vpn:
                startActivity(new Intent(getApplicationContext(), VpnLoginActivity.class));
                break;
        }
    }

    /**
     * 正常登陆
     *
     * vpn登陆
     *
     * @param login
     * @param user
     * @param psw
     */
    private void login(final String login, final String user, final String psw) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(login)
                .build();
        dialog.show();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                String news = response.body().string();
                Gson gson = new Gson();
                final LoginBean loginBean = gson.fromJson(news, LoginBean.class);
                //判断是否成功登录
                if (loginBean.isResult()) {
                    if (userVpn != null && userVpn.isVpn()){
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
                        userBean.setUsername(user);
                        userBean.setPassword(psw);
                        userBean.setAutomitic(isAutomatic);
                        userBean.setRemenber(isRemenber);
                        SpUtil.putObject(getApplication(), Constant.USER_SHAREPRE, userBean);
                    }else{
                        String main_url = Constant.HUAMBO_MAIN_URL + "{UserID:'" + user + "'" + ",UserPsw:'" + psw + "'}";//首页地址
                        userBean.setUsername(user);
                        userBean.setPassword(psw);
                        userBean.setAutomitic(isAutomatic);
                        userBean.setRemenber(isRemenber);
                        SpUtil.putObject(getApplication(), Constant.USER_SHAREPRE, userBean);
                        Log.d(TAG, "onResponse: " + main_url);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(Constant.MAIN_HTTPURL, String.format(main_url));
                        startActivity(intent);
                        finish();
                    }

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show(getApplicationContext(), loginBean.getMessage());
                        }
                    });
                }
            }
        });
    }
}
