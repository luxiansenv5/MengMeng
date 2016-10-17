package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.NetUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 程和 on 2016/10/15.
 */
public class UserInfoActivity extends AppCompatActivity {
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.iv_userInfo_back)
    ImageView ivUserInfoBack;
    @InjectView(R.id.iv_userInfo_background)
    ImageView ivUserInfoBackground;
    @InjectView(R.id.iv_userInfo_photo)
    ImageView ivUserInfoPhoto;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.tv_userInfo_concern)
    TextView tvUserInfoConcern;
    @InjectView(R.id.tv_userInfo_name)
    TextView tvUserInfoName;
    @InjectView(R.id.tv_userInfo_name_v)
    View tvUserInfoNameV;
    @InjectView(R.id.tv_userInfo_sex)
    TextView tvUserInfoSex;
    @InjectView(R.id.tv_userInfo_sex_v)
    View tvUserInfoSexV;
    @InjectView(R.id.iv_show_pet)
    ImageView ivShowPet;
    @InjectView(R.id.rl_userInfo_pet)
    RelativeLayout rlUserInfoPet;
    @InjectView(R.id.tv_userInfo_adduser)
    TextView tvUserInfoAdduser;

    List<User> users = new ArrayList<User>();
    User user = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        //从数据库获取数据
        RequestParams requestParams = new RequestParams(NetUtil.url + "UserQueryServlet");
        requestParams.addQueryStringParameter("userId", userId);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<User>() {
                }.getType();
                User newUser = gson.fromJson(result, type);

                x.image().bind(ivUserInfoPhoto, NetUtil.photo_url + newUser.getUserPhoto());
                tvUsername.setText(newUser.getUserName());
                tvUserInfoName.setText("昵称：" + newUser.getUserName());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex + "===================");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @OnClick({R.id.iv_userInfo_back, R.id.tv_userInfo_adduser})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_userInfo_back:
                    finish();
                break;
            case R.id.tv_userInfo_adduser:

                break;
        }
    }
}
