package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.NetUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;

/**
 * Created by 程和 on 2016/11/7.
 */
public class MyActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView address;
    private ImageView photo;
    private TextView name;
    private TextView concern;
    private TextView username;
    private TextView sex;
    private TextView qianming;
    private RelativeLayout pet;
    private TextView adduser;
    private ImageView back;

    String userSex="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initData();
    }


    private void initView() {
        address = ((TextView) findViewById(R.id.tv_userInfo_address));
        photo = ((ImageView) findViewById(R.id.iv_userInfo_photo));
        name = ((TextView) findViewById(R.id.tv_username));
        concern = ((TextView) findViewById(R.id.tv_userInfo_concern));
        username = ((TextView) findViewById(R.id.tv_userInfo_name));
        sex = ((TextView) findViewById(R.id.tv_userInfo_sex));
        qianming = ((TextView) findViewById(R.id.tv_userInfo_qianming));
        pet = ((RelativeLayout) findViewById(R.id.rl_userInfo_pet));
        pet.setOnClickListener(this);
        back = ((ImageView) findViewById(R.id.iv_userInfo_back));
        back.setOnClickListener(this);
        adduser = ((TextView) findViewById(R.id.tv_userInfo_adduser));
        adduser.setVisibility(View.GONE);
    }


    private void initData() {
        Intent intent = getIntent();
        RequestParams requestParams = new RequestParams(NetUtil.url + "UserQueryServlet");
        if(intent.getStringExtra("userId")!=null) {
            String userId = intent.getStringExtra("userId");
            requestParams.addQueryStringParameter("userId", userId);
        }

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<User>() {
                }.getType();
                User newUser = gson.fromJson(result, type);

                x.image().bind(photo, NetUtil.photo_url + newUser.getUserPhoto());
                name.setText(newUser.getUserName());

                concern.setText("关注数："+newUser.getFollowCount());
                username.setText("昵称：" + newUser.getUserName());

                if (newUser.userSex){
                    userSex="男";
                }else {
                    userSex="女";
                }
                sex.setText("性别："+userSex);
                address.setText("地址："+newUser.getAddress());
                qianming.setText("个性签名："+newUser.getUserWrite());

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_userInfo_back:
                finish();
                break;
            case R.id.rl_userInfo_pet:
                Intent intent=new Intent(this,My_PetActivity.class);
                intent.putExtra("userId",1+"");
                startActivity(intent);
                break;
        }
    }
}
