package com.example.mengmeng.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.HttpUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_userName;
    private EditText et_userPsd;
    private CheckBox cb_remeber;
    private TextView register;
    private TextView sign;
    public boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一次启动APP判断
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun)
        {
            Intent intent=new Intent(LoginActivity.this,ViewPagerActivity.class);
            startActivity(intent);
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        }



        setContentView(R.layout.activity_login);

        ((Button) findViewById(R.id.btn_login)).setOnClickListener(this);
        et_userName = ((EditText) findViewById(R.id.et_userName));
        et_userPsd = ((EditText) findViewById(R.id.et_userPsd));
        cb_remeber = ((CheckBox) findViewById(R.id.cb_remeber));
        register = ((TextView) findViewById(R.id.register));
        sign = ((TextView) findViewById(R.id.sign));


        SharedPreferences shared_prefs = getSharedPreferences("userinfo_shared_prefs", Context.MODE_PRIVATE);
        String loginName = shared_prefs.getString("loginName","");
        boolean remeberName = shared_prefs.getBoolean("remeberName",false);

        et_userName.setText(loginName);
        cb_remeber.setChecked(remeberName);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                checkLogin();
                break;
        }
    }
    private void checkLogin(){
        //访问网络  checklogin
        //RequestParams params = new RequestParams("http://10.40.5.102:8080/Mengmeng/CheckLogin");
        getLoginInfoList();

    }

    public void getLoginInfoList() {

        String name = null;
        String psd = null;
        try {
            name = URLEncoder.encode(et_userName.getText().toString().trim(),"utf-8");
            psd = URLEncoder.encode(et_userPsd.getText().toString().trim(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RequestParams params = new RequestParams(HttpUtils.HOST+"checkLogin");
        final String finalName = name;
        params.addParameter("name",name);
        params.addParameter("psd",psd);
        Log.e("parameter", "getLoginInfoList: "+params );
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if (result.equals("Error")) {
                    //返回值为零 表示不匹配 传会的result值就是psd
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                } else {

                    Gson gson=new Gson();
                    User user=gson.fromJson(result,User.class);
//                    Toast.makeText(LoginActivity.this, "result="+result, Toast.LENGTH_LONG).show();
//                    SharedPreferences shared_prefs = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = shared_prefs.edit();
//
//                    editor.putInt("userId",user.getUserId());
//                    editor.putString("userPhoto",user.getUserPhoto());
//                    editor.putString("address",user.getAddress());
//                    editor.putBoolean("sex",user.isUserSex());
//                    editor.putString("underWrite",user.getUserWrite());
//                    editor.putString("token",user.getToken());
//                    editor.putString("userPsd",user.getUserPsd());
//
//                    editor.commit();
                    LoginInfo.userId=user.getUserId();
                    LoginInfo.address=user.getAddress();
                    LoginInfo.name = user.getUserName();
                    LoginInfo.sex = user.isUserSex();
                    LoginInfo.token = user.getToken();
                    LoginInfo.underWrite = user.getUserWrite();
                    LoginInfo.userPhoto = user.getUserPhoto();
                    LoginInfo.userPsd = user.getUserPsd();

                    et_userName.setText(finalName);
                    et_userPsd.setText(result);
                    Intent intent = new Intent(LoginActivity.this, DynamicMainActivity.class);
                    startActivity(intent);
//                    Toast.makeText(LoginActivity.this,"lognin-result=="+result,Toast.LENGTH_SHORT).show();
                    System.out.println("LoginActivity---result==="+result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString()+"+=============+===");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        }



    }

