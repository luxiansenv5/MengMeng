package com.example.mengmeng.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class psd_setActivity extends AppCompatActivity {


    private EditText old_psd_set;
    private EditText new_psd_set;
    String old_psd= null;
    String new_psd= null;
    String loginName = null;
    private Button psd_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pad_set);
        //拿到原来的用户名，取出对应密码
        SharedPreferences shared_prefs = getSharedPreferences("userinfo_shared_prefs", Context.MODE_PRIVATE);
        loginName = shared_prefs.getString("loginName","");

        old_psd_set = ((EditText) findViewById(R.id.old_psd_set));
        new_psd_set = ((EditText) findViewById(R.id.new_psd_set));


        //确认按钮
        psd_confirm = ((Button) findViewById(R.id.psd_confirm));
        psd_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                old_psd = old_psd_set.getText().toString();//输入的旧密码
                new_psd = new_psd_set.getText().toString();//输入的新密码

                //Toast.makeText(getApplication(),old_psd,Toast.LENGTH_SHORT).show();
                RequestParams params = new RequestParams("http://10.40.5.41:8080/Mengmeng/GetLoginInfo");
                params.addBodyParameter("loginName",loginName);

                x.http().get(params, new Callback.CacheCallback<String>() {
                    @Override
                    public void onSuccess(String result) {



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

                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }
                });
            }
        });




    }
}
