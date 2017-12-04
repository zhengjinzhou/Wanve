package com.example.wanve4k.wanve.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.bean.UserBean;
import com.example.wanve4k.wanve.constant.Constant;
import com.example.wanve4k.wanve.util.SpUtil;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    public String main_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        UserBean userBean = (UserBean) SpUtil.getObject(getApplicationContext(), Constant.USER_SHAREPRE, UserBean.class);
        if (userBean != null) {
            Log.d(TAG, "onCreate: ------------------------------");
            if (userBean.isAutomitic()) {
                main_url = Constant.HUAMBO_MAIN_URL + "{UserID:'" + userBean.getUsername() + "'" + ",UserPsw:'" + userBean.getPassword() + "'}";
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constant.MAIN_HTTPURL, String.format(main_url));
                startActivity(intent);
                finish();
            }else {
                startToLogin();
            }
        }else{
            startToLogin();
        }
    }

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
