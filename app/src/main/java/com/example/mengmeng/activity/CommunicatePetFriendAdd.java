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

public class CommunicatePetFriendAdd extends AppCompatActivity {

    @InjectView(R.id.tv_back)
    ImageView tvBack;
    @InjectView(R.id.tv_input)
    TextView tvInput;
    @InjectView(R.id.pet_friend_add)
    TextView petFriendAdd;
    @InjectView(R.id.petfriend_add_bottom)
    RelativeLayout petfriendAddBottom;
    @InjectView(R.id.tv_recommend_friend)
    TextView tvRecommendFriend;
    @InjectView(R.id.lv_addfriend)
    ListView lvAddfriend;
    @InjectView(R.id.recommend_friend)
    RelativeLayout recommendFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_petfriend_add);
        ButterKnife.inject(this);
    }

    public void back(){
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick({R.id.tv_back, R.id.pet_friend_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                back();
                break;
            case R.id.pet_friend_add:
                break;
        }
    }
}
