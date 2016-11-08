package com.example.mengmeng.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mengmeng.utils.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class Psd_setActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText old_psd_set;
    private EditText new_psd_set;
    String old_psd = null;
    String new_psd = null;
    String loginName = null;
    private Button psd_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad_set);
        //拿到原来的用户名，取出对应密码
//        SharedPreferences shared_prefs = getSharedPreferences("userinfo_shared_prefs", Context.MODE_PRIVATE);
//        loginName = shared_prefs.getString("loginName","");

        old_psd_set = ((EditText) findViewById(R.id.old_psd_set));
        new_psd_set = ((EditText) findViewById(R.id.new_psd_set));


        //确认按钮
        psd_confirm = ((Button) findViewById(R.id.psd_confirm));
        psd_confirm.setOnClickListener(this);
    }

    private void intData() {
        if (old_psd_set.getText().length() != 0 && new_psd_set.getText().length() != 0 && old_psd_set.getText().toString().trim().equals(new_psd_set.getText().toString().trim())) {
            Intent intent = getIntent();
            RequestParams requestParams = new RequestParams(NetUtil.url + "ModifyPsdByUserIdServlet");
            if (intent.getStringExtra("userId") != null) {
                String userId = intent.getStringExtra("userId");
                String newPsd = new_psd_set.getText().toString().trim();
                requestParams.addQueryStringParameter("userId", userId);
                try {
                    requestParams.addQueryStringParameter("newPsd", URLEncoder.encode(newPsd, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(Psd_setActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(Psd_setActivity.this, "修改密码失败，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }else {
            Toast.makeText(Psd_setActivity.this, "密码不能为空，密码需相等", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.psd_confirm:
                intData();
                break;
        }
    }
}