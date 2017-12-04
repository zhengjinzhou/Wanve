package com.example.wanve4k.wanve.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.util.LoadDialog;
import com.example.wanve4k.wanve.util.ToastUtil;

import net.arraynetworks.vpn.VPNManager;

import butterknife.ButterKnife;

/**
 * Created by zhou on 2017/11/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int getLayout();

    public abstract void init();

    protected LoadDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        dialog = new LoadDialog(this,false,getString(R.string.loading));
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
