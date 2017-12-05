package com.example.wanve4k.wanve.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.base.BaseActivity;
import com.example.wanve4k.wanve.bean.MailBean;
import com.example.wanve4k.wanve.constant.Constant;
import com.example.wanve4k.wanve.util.ToastUtil;
import com.google.gson.Gson;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class DepartmentActivity extends BaseActivity {

    private static final String TAG = "DepartmentActivity";
    static List<MailBean.UserListBean> mUserListBeans;
    static List<MailBean.UserListBean> mTempUserListBeans ;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.txtTitle) TextView txtTitle;

    MailBean mailBean;
    private DepartmentAdapter mDepartmentAdapter;

    public static void newInstance(Context context, String str) {
        Intent intent = new Intent(context, DepartmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.DEPARTMENTLIST, str);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_department;
    }
    @Override
    public void init() {
        txtTitle.setText("通讯录");
        mDepartmentAdapter = new DepartmentAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mDepartmentAdapter);

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            String string = bundle.getString(Constant.DEPARTMENTLIST);
            Log.d(TAG, "init: "+string);
            Gson gson = new Gson();
            mailBean = gson.fromJson(string, MailBean.class);
            tv_title.setText(mailBean.getKSName());
            mTempUserListBeans = mailBean.getUserList();
            mUserListBeans = mailBean.getUserList();

        }else{
            ToastUtil.show(getApplicationContext(),"请重试");
        }
    }

    @OnClick({R.id.lyLeftContainer}) void onClick(View view){
        switch (view.getId()){
            case R.id.lyLeftContainer:
                finish();
                break;
        }
    }

    class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    DepartmentActivity.this).inflate(R.layout.child_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mTempUserListBeans.get(position).getUserName());
            holder.itemView.setOnClickListener(arg0 -> {
                DepartmentInfoActivity.newInstance(DepartmentActivity.this,mailBean.getKSName(),mTempUserListBeans.get(position).toString());
            });
        }

        @Override
        public int getItemCount() {
            return mTempUserListBeans == null ? 0 : mTempUserListBeans.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = view.findViewById(R.id.child_title);
            }
        }
    }
}
