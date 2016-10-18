package com.example.mengmeng.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CommunicateGroupActivity extends AppCompatActivity {

    @InjectView(R.id.tv_back)
    ImageView tvBack;
    @InjectView(R.id.tv_input)
    TextView tvInput;
    @InjectView(R.id.add_group)
    TextView addGroup;
    @InjectView(R.id.group_add_bottom)
    RelativeLayout groupAddBottom;
    @InjectView(R.id.tv_recommend_group)
    TextView tvRecommendGroup;
    @InjectView(R.id.lv_addgroup)
    ListView lvAddgroup;
    @InjectView(R.id.recommend_group)
    RelativeLayout recommendGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_group_add);
        ButterKnife.inject(this);

    }

    public void back(){
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();//关闭当前的activity
            }
        });
    }


    @OnClick({R.id.tv_back, R.id.add_group})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                back();
                break;
            case R.id.add_group:
                break;
        }
    }
}
