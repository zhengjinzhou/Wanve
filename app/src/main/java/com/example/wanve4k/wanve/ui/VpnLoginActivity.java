package com.example.wanve4k.wanve.ui;


import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.base.BaseActivity;
import com.example.wanve4k.wanve.bean.UserBean;
import com.example.wanve4k.wanve.constant.Constant;
import com.example.wanve4k.wanve.util.SpUtil;
import com.example.wanve4k.wanve.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class VpnLoginActivity extends BaseActivity {

    private static final String TAG = "VpnLoginActivity";
    @BindView(R.id.rg_vpn) RadioGroup rg_vpn;
    @BindView(R.id.et_username) EditText et_username;
    @BindView(R.id.et_password) EditText et_password;
    @BindView(R.id.ll_vpn) LinearLayout ll_vpn;
    @BindView(R.id.iv_show) ImageView iv_show;
    @BindView(R.id.rb_yes) RadioButton rb_yes;
    @BindView(R.id.rb_no) RadioButton rb_no;
    public boolean isVpnLogin;
    private UserBean userBean;

    @Override
    public int getLayout() {
        return R.layout.activity_vpn_login;
    }

    @Override
    public void init() {

        iv_show.setBackgroundResource(R.drawable.login__user_pass_visible);
        userBean = new UserBean();

        //记住当前是否勾选过状态以及vpn账号密码框
        UserBean user  = (UserBean) SpUtil.getObject(this, Constant.USER_VPN_LOGINCACHE, UserBean.class);
        if (user != null){
            et_username.setText(user.getVpnUsername());
            et_password.setText(user.getVpnPassword());
            if (user.isVpn()){
                rb_yes.setChecked(true);
                isVpnLogin = true;
            }else {
                rb_no.setChecked(true);
                isVpnLogin = false;
            }
        }

        rg_vpn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yes:
                        isVpnLogin = true;
                        break;
                    case R.id.rb_no:
                        isVpnLogin = false;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.iv_clear, R.id.iv_show, R.id.lyLeftContainer})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.lyLeftContainer:
                finish();
                break;
            case R.id.iv_clear:
                et_username.setText("");
                break;
            case R.id.iv_show:
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
        }
    }

    @Override
    public void onBackPressed() {
        userBean.setVpn(isVpnLogin);
        userBean.setVpnUsername(et_username.getText().toString().trim());
        userBean.setVpnPassword(et_password.getText().toString().trim());
        SpUtil.putObject(getApplicationContext(), Constant.USER_VPN_LOGINCACHE, userBean);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        userBean.setVpn(isVpnLogin);
        userBean.setVpnUsername(et_username.getText().toString().trim());
        userBean.setVpnPassword(et_password.getText().toString().trim());
        SpUtil.putObject(getApplicationContext(), Constant.USER_VPN_LOGINCACHE, userBean);
        super.onDestroy();
    }
}
