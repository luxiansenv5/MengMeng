package com.example.mengmeng.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class count_setActivity extends AppCompatActivity {

    private TextView new_count_set;
    private Button count_confirm;

    String new_count = null;
    private String old_count;
    private EditText et_userName;
    String loginName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_set);

        new_count_set = ((TextView) findViewById(R.id.new_count_set));

        //拿到原来的登录名
//        et_userName = ((EditText) findViewById(R.id.et_userName));
//        old_count = et_userName.getText().toString();
        SharedPreferences shared_prefs = getSharedPreferences("userinfo_shared_prefs", Context.MODE_PRIVATE);
        loginName = shared_prefs.getString("loginName","");

        count_confirm = ((Button) findViewById(R.id.count_confirm));
        count_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_count = new_count_set.getText().toString();//success
                //Toast.makeText(getApplication(),loginName,Toast.LENGTH_SHORT).show();
                System.out.println("new_count:"+new_count);
                System.out.println("loginName:"+loginName);

                RequestParams params = new RequestParams("http://10.40.5.41:8080/Mengmeng/UpdateCount");
                params.addBodyParameter("new_count",new_count);
                params.addBodyParameter("loginName",loginName);

                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.length()>=0){
                            Toast.makeText(getApplication(),"修改用户名成功！",Toast.LENGTH_SHORT).show();

                            SharedPreferences shared_prefs = getSharedPreferences("userinfo_shared_prefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared_prefs.edit();
                            editor.putString("loginName","");
                            editor.putString("loginName", new_count.toString().trim());

                            editor.commit();
                        }else{
                            Toast.makeText(getApplication(),"修改用户名失败！",Toast.LENGTH_SHORT).show();
                        }



                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("ex:"+ex);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }




}
