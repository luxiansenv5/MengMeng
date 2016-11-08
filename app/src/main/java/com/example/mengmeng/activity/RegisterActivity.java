package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText reg_name;
    private EditText reg_psd;
    private EditText reg_psd_again;
    private RadioGroup check_sex;
    private Button btn_register;
    private Button btn_cancel;
    private int sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_name = ((EditText) findViewById(R.id.reg_name));
        reg_psd = ((EditText) findViewById(R.id.reg_psd));
        reg_psd_again = ((EditText) findViewById(R.id.reg_psd_again));
        check_sex = ((RadioGroup) findViewById(R.id.check_sex));

        btn_register = ((Button)findViewById(R.id.btn_register));
        btn_cancel = ((Button)findViewById(R.id.btn_cancel));
        btn_cancel.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }

    }

    private void cancel(){

        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    private void register(){
        String name=null;
        String psd=null;
        String psd_again=null;


        try {
            name = URLEncoder.encode(reg_name.getText().toString().trim(),"utf-8");
            psd = URLEncoder.encode(reg_psd.getText().toString().trim(),"utf-8");
            psd_again=URLEncoder.encode(reg_psd_again.getText().toString().trim(),"utf-8");


            check_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.female:
                                sex=0;
                            break;
                        case R.id.male:
                            sex=1;
                            break;
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(psd_again.equals(psd)==false){
            Toast.makeText(getApplicationContext(),"两次密码不同，请重新输入", Toast.LENGTH_SHORT).show();
            reg_psd_again.setText("");
        }else{
            RequestParams params = new RequestParams("http://10.0.2.2:8080/MMAPP/checkLogin?name="+name+"&psd="+psd+"");

            x.http().get(params, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    //服务端传一个大于零的值，表示用户名已经存在
                    if (result.length() > 0) {
                        Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_LONG).show();
                    }else{
                        RequestParams p = new RequestParams("http://10.0.2.2:8080/MMAPP/regist");
                        p.addBodyParameter("name",reg_name.getText()+"");
                        p.addBodyParameter("psd",reg_psd.getText()+"");
                        p.addBodyParameter("sex",sex+"");
                        x.http().get(p, new Callback.CacheCallback<String>() {
                            @Override
                            public void onSuccess(String result) {


                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);


                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
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






    }




}
