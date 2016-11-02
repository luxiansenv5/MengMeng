package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 程和 on 2016/10/28.
 */
public class PictureActivity extends AppCompatActivity {

    @InjectView(R.id.iv_picture)
    ImageView ivPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.inject(this);
        getData();
    }


    public void getData() {
        Intent intent = getIntent();
        String str = intent.getStringExtra("picture");
        x.image().bind(ivPicture, str);
    }

    @OnClick(R.id.iv_picture)
    public void onClick() {
        finish();
    }
}
