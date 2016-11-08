package com.example.mengmeng.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Mine_SetActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView count_set;
    private TextView psd_set;
    private TextView address;
    private TextView photo_set;
    private TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine__set);

        count_set = ((TextView) findViewById(R.id.count_set));
        count_set.setOnClickListener(this);

        psd_set = ((TextView) findViewById(R.id.psd_set));
        psd_set.setOnClickListener(this);

        address = ((TextView) findViewById(R.id.address));
        address.setOnClickListener(this);

        photo_set = ((TextView) findViewById(R.id.message_photo_set));
        photo_set.setOnClickListener(this);

        about = ((TextView) findViewById(R.id.about));
        about.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.count_set:
                Intent intent = new Intent(getApplication(), Count_setActivity.class);
                startActivity(intent);
                break;
            case R.id.psd_set:
                Intent intent1 = new Intent(getApplication(), Psd_setActivity.class);
                startActivity(intent1);
                break;
            case  R.id.message_photo_set:
                Intent intent2=new Intent(this,SetQianmingActivity.class);
                intent2.putExtra("userId",1+"");
                startActivity(intent2);
                break;
            case R.id.address:
                Intent intent3=new Intent(this,SetAddressActivity.class);
                intent3.putExtra("userId",1+"");
                startActivity(intent3);
                break;
            case  R.id.about:
                Intent intent4=new Intent(this,AboutActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
