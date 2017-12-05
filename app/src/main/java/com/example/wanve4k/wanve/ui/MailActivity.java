package com.example.wanve4k.wanve.ui;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wanve4k.wanve.R;
import com.example.wanve4k.wanve.base.BaseActivity;
import com.example.wanve4k.wanve.bean.MailBean;
import com.example.wanve4k.wanve.bean.UserBean;
import com.example.wanve4k.wanve.constant.Constant;
import com.example.wanve4k.wanve.util.FileDataUtil;
import com.example.wanve4k.wanve.util.SpUtil;
import com.example.wanve4k.wanve.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MailActivity extends BaseActivity {

    private static final String TAG = "MailActivity";
    private String mailStr;
    private List<MailBean> mUserListBeen;
    private List<MailBean> mTempUserListBeen;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.txtTitle) TextView txtTitle;
    HomeAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_mail;
    }

    @Override
    public void init() {
        txtTitle.setText("通讯录");
        //获取联系人列表
        String addressBook = FileDataUtil.loadDataFile(getApplicationContext(), "AddressBook");
        Gson gson = new Gson();

        mUserListBeen = gson.fromJson(addressBook, new TypeToken<List<MailBean>>(){}.getType());
        mTempUserListBeen = gson.fromJson(addressBook, new TypeToken<List<MailBean>>(){}.getType());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);
    }
    @OnClick({R.id.lyLeftContainer}) void onClick(View view){
        switch (view.getId()){
            case R.id.lyLeftContainer:
                finish();
                break;
        }
    }

    @OnTextChanged(value = R.id.et_search,callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChange(CharSequence s, int start, int before, int count){
        if (!TextUtils.isEmpty(s)){
            mUserListBeen.clear();
            for (MailBean user : mTempUserListBeen){
                if (user.getKSName().indexOf(s.toString()) != -1){
                    mUserListBeen.add(user);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MailActivity.this).inflate(R.layout.parent_item, parent,
                    false));
            return holder;
        }

        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv.setText(mUserListBeen.get(position).getKSName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: "+mUserListBeen.get(position));
                    DepartmentActivity.newInstance(MailActivity.this, mUserListBeen.get(position).toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUserListBeen == null ? 0 : mUserListBeen.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            public MyViewHolder(View view) {
                super(view);
                tv = view.findViewById(R.id.parent_title);
            }
        }
    }
}
