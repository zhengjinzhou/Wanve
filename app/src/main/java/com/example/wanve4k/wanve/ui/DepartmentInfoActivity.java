package com.example.wanve4k.wanve.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.bean.UserListBean;
import com.example.wanve4k.wanve.constant.Constant;
import com.example.wanve4k.wanve.util.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class DepartmentInfoActivity extends AppCompatActivity {

    RxPermissions mRxPermissions;
    /**
     * 页面跳转，并带参数
     * @param context
     * @param department
     * @param userListBean
     */
    public static void newInstance(Context context, String department, UserListBean userListBean) {
        Intent inetent = new Intent(context, DepartmentInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DEPARTMENTLIST, userListBean);
        bundle.putString(Constant.DEPARTMENTDEPARTMENT, department);
        inetent.putExtras(bundle);
        context.startActivity(inetent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_info);
    }

    /**
     * 弹出联系人点击事件
     */
    public class PopupContactsInfoControl {

        /**
         * 打电话
         */
        public void Call(String tel) {
            //动态权限
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
            // dialogHidden();
            //    ToolToast.success(tel);
            mRxPermissions
                    .request(Manifest.permission.SEND_SMS)
                    .subscribe(granted -> {
                        if (granted) { // Always true pre-M
                            // I can control the camera now

                            if (PhoneNumberUtils.isGlobalPhoneNumber(tel)) {
                                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));
                                intent.putExtra("sms_body", "");
                                startActivity(intent);
                            }
                        } else {
                            // Oups permission denied
                            ToastUtil.show(getApplicationContext(), "未授权");
                        }
                    });

        }
    }
}
