package com.example.wanve4k.wanve.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.base.BaseActivity;
import com.example.wanve4k.wanve.bean.MailBean;
import com.example.wanve4k.wanve.bean.UserListBean;
import com.example.wanve4k.wanve.constant.Constant;
import com.example.wanve4k.wanve.util.ToastUtil;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DepartmentInfoActivity extends BaseActivity {

    private static final String TAG = "DepartmentInfoActivity";
    RxPermissions mRxPermissions;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_department)
    TextView tv_department;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_shortnumber)
    TextView tv_shortnumber;
    @BindView(R.id.tv_office)
    TextView tv_office;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_address)
    TextView tv_address;
    private UserListBean userListBean;

    /**
     * 页面跳转，并带参数
     *
     * @param context
     * @param userListBean
     */
    public static void newInstance(Context context, String department, String userListBean) {
        Intent inetent = new Intent(context, DepartmentInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.DEPARTMENTLIST, userListBean);
        bundle.putString(Constant.DEPARTMENTDEPARTMENT, department);
        inetent.putExtras(bundle);
        context.startActivity(inetent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_department_info;
    }

    @Override
    public void init() {
        mRxPermissions = new RxPermissions(this);
        txtTitle.setText("通讯录");
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            String username = bundle.getString(Constant.DEPARTMENTDEPARTMENT);
            String string1 = bundle.getString(Constant.DEPARTMENTLIST);
            Gson gson = new Gson();
            userListBean = gson.fromJson(string1, UserListBean.class);
            tv_department.setText(userListBean.getUserName());
            tv_phone.setText(userListBean.getMobileNumber());
            tv_shortnumber.setText(userListBean.getShortNumber());
            tv_office.setText(userListBean.getOfficeNumber());
            tv_email.setText(userListBean.getEmail());
            tv_address.setText(userListBean.getAddress());
            tv_username.setText(username);

        } else {
            ToastUtil.show(getApplicationContext(), "请重试");
        }
    }

    @OnClick({R.id.rl_call, R.id.rl_message})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_call:
                Call(userListBean.getMobileNumber());
                break;
            case R.id.rl_message:
                SendMessage(userListBean.getMobileNumber());
                break;
        }
    }

    /**
     * 打电话
     * <p>
     * 动态权限
     */
    public void Call(String tel) {
        mRxPermissions
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + tel);
                        intent.setData(data);
                        startActivity(intent);
                    } else {
                        // Oups permission denied
                        ToastUtil.show(getApplicationContext(), "未授权");
                    }
                });

    }

    /**
     * 发短信
     */
    public void SendMessage(String tel) {
        mRxPermissions
                .request(Manifest.permission.SEND_SMS)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        if (PhoneNumberUtils.isGlobalPhoneNumber(tel)) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));
                            intent.putExtra("sms_body", "");
                            startActivity(intent);
                        }
                    } else {
                        ToastUtil.show(getApplicationContext(), "未授权");
                    }
                });

    }
}
