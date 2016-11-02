package com.example.mengmeng.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Mine_SetActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView count_set;
    private TextView psd_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine__set);

        count_set = ((TextView) findViewById(R.id.count_set));
        count_set.setOnClickListener(this);

        psd_set = ((TextView) findViewById(R.id.psd_set));
        psd_set.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.count_set:
                //已经测试，可以跳转
                Intent intent = new Intent(getApplication(), count_setActivity.class);
                startActivity(intent);
                break;
            case R.id.psd_set:
                Intent intent1 = new Intent(getApplication(), psd_setActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
