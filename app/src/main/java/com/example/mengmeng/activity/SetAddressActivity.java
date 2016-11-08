package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SetAddressActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.new_address)
    EditText newAddress;
    @InjectView(R.id.count_confirm)
    Button countConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address);
        ButterKnife.inject(this);
        countConfirm.setOnClickListener(this);
    }



    private void intData() {
        if (newAddress.getText().length()>3){
            Intent intent = getIntent();
            RequestParams requestParams = new RequestParams(NetUtil.url + "ModifyAddressByUserIdServlet");
            if(intent.getStringExtra("userId")!=null) {
                String userId = intent.getStringExtra("userId");
                String newAddress1=newAddress.getText().toString().trim();
                requestParams.addQueryStringParameter("userId", userId);
                try {
                    requestParams.addQueryStringParameter("newAddress", URLEncoder.encode(newAddress1,"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(SetAddressActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(SetAddressActivity.this, "修改失败，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }else{
            Toast.makeText(SetAddressActivity.this, "请输入新的地址(长度最少为3位)", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.count_confirm:
                intData();
                break;
        }
    }
}
