package com.example.wanve4k.wanve.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.util.LoadDialog;

import butterknife.ButterKnife;


/**
 * Created by zhou on 2017/11/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int getLayout();

    public abstract void init();

    protected LoadDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
