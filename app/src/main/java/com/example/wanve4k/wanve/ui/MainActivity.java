package com.example.wanve4k.wanve.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.base.BaseActivity;
import com.example.wanve4k.wanve.bean.TitleBarBean;
import com.example.wanve4k.wanve.bean.UserBean;
import com.example.wanve4k.wanve.constant.Constant;
import com.example.wanve4k.wanve.util.FileDataUtil;
import com.example.wanve4k.wanve.util.JsonConvert;
import com.example.wanve4k.wanve.util.SpUtil;
import com.example.wanve4k.wanve.util.ToastUtil;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.web_main) WebView mWebView;
    @BindView(R.id.iv_titleBar_right_img) ImageView iv_titleBar_right_img;
    @BindView(R.id.v_top) View v_top;
    @BindView(R.id.tv_sum) TextView tv_sum;
    @BindView(R.id.tv_center) TextView tv_center;

    private String mHttpUrl;
    TitleBarBean mTitleBarBean;
    static PopupWindow mPopupWindow;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void init() {
        mTitleBarBean = new TitleBarBean();
        mTitleBarBean.setLeftIsVisible(true);
        //绑定  TitleBarBean   mActivityMainBinding.titleBar.setTitle(mTitleBarBean);
        //设置菜单按钮
        iv_titleBar_right_img.setImageResource(R.drawable.main_title_popup);
        //弹出菜单点击事件
        iv_titleBar_right_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {
                    mPopupWindow.showAsDropDown(iv_titleBar_right_img);
                }
            }
        });
        //获取路径
        if (!TextUtils.isEmpty(getIntent().getStringExtra(Constant.MAIN_HTTPURL))) {
            mHttpUrl = getIntent().getStringExtra(Constant.MAIN_HTTPURL);
        }
        //适配
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置跨域问题
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebView.addJavascriptInterface(new HuamboJsInterface(), "contact");
        //WebViewClient
        mWebView.setWebViewClient(mWebViewClient);
        //WebChromeClient
        mWebView.setWebChromeClient(mWebChromeClient);
        //加载并获取添加头部信息
        mWebView.loadUrl(mHttpUrl);
        getAddressBook();
    }

    private void getAddressBook() {
        //获取下一个界面的通讯录
        UserBean userBean = (UserBean) SpUtil.getObject(getApplicationContext(), Constant.USER_SHAREPRE, UserBean.class);
        if (userBean != null) {
            final String url = Constant.BASE_URL + Constant.HUAMBO_MAILLIST + "{UserID:'" + userBean.getUsername() + "'}";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    result(response.body().string());
                }
            });
        }
    }

    private void result(String result) {
        FileDataUtil.saveDataToFile(getApplicationContext(), result, "AddressBook");
    }

    /**
     * JS 交互
     */
    public final class HuamboJsInterface {
        @JavascriptInterface
        public void clickAndroid(String json) {
            Log.e("", "clickAndroid: " + json);
            String mType = JsonConvert.analysisJson(json, new TypeToken<String>() {
            }.getType(), "type");
            if (!TextUtils.isEmpty(mType)) {
                switch (mType) {
                    case "phonebook":
                        startActivity(new Intent(getApplicationContext(), MailActivity.class));
                        break;
                    case "logout":
                        SpUtil.clear();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        MainActivity.this.startActivity(intent);
                        finish();
                        break;
                    case "quit":
                        finish();
                        break;
                    default:
                        showPopupWindow();
                        break;
                }
            }
        }
    }

    /**
     * 显示popup
     */
    private void showPopupWindow() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mPopupWindow.showAsDropDown(v_top);
            }

        }, 200L);
    }

    //ChromeClient
    WebChromeClient mWebChromeClient = new WebChromeClient() {
        //监听网页加载
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            result.confirm();
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    };

    //WebViewClient
    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
            // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
            mWebView.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }
    };

    @OnClick({R.id.lyLeftContainer, R.id.tv_sum, R.id.tv_index, R.id.tv_map, R.id.tv_center})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.lyLeftContainer:
                if (null != mWebView) {
                    mWebView.goBack();
                }
                break;
            case R.id.tv_index:
                ToastUtil.show(getApplicationContext(), "待续");
                break;

            case R.id.tv_map:
                ToastUtil.show(getApplicationContext(), "待续");
                break;

            case R.id.tv_sum:
                showPopupSum();
                break;
            case R.id.tv_center:
                showPopupCenter();
                break;

        }
    }

    private void showPopupCenter() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_center, null);
        PopupWindow pop = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setContentView(contentView);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.anim.mypop_anim);
        pop.showAtLocation(tv_center, Gravity.BOTTOM, 220, 112);
        contentView.findViewById(R.id.tv_data).setOnClickListener(v -> {
            ToastUtil.show(getApplicationContext(), "待续");
            pop.dismiss();
        });
        contentView.findViewById(R.id.tv_table).setOnClickListener(v -> {
            ToastUtil.show(getApplicationContext(), "待续");
            pop.dismiss();
        });
        contentView.findViewById(R.id.tv_rules).setOnClickListener(v -> {
            ToastUtil.show(getApplicationContext(), "待续");
            pop.dismiss();
        });
        contentView.findViewById(R.id.tv_standard).setOnClickListener(v -> {
            ToastUtil.show(getApplicationContext(), "待续");
            pop.dismiss();
        });
    }

    private void showPopupSum() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_sum, null);
        PopupWindow pop = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setContentView(contentView);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.anim.mypop_anim);
        pop.showAtLocation(tv_sum, Gravity.BOTTOM, 20, 112);
        contentView.findViewById(R.id.tv_table).setOnClickListener(v -> {
            ToastUtil.show(getApplicationContext(), "待续");
            pop.dismiss();
        });
        contentView.findViewById(R.id.tv_distribution).setOnClickListener(v -> {
            ToastUtil.show(getApplicationContext(), "待续");
            pop.dismiss();
        });
        contentView.findViewById(R.id.tv_project).setOnClickListener(v -> {
            ToastUtil.show(getApplicationContext(), "待续");
            pop.dismiss();
        });
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtil.show(getApplicationContext(), "再按一次退出程序");
                firstTime = secondTime;
            } else {
                finish();
            }
        }
        return false;
    }
}
