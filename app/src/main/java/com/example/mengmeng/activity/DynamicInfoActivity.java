package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.Dynamic;
import com.example.mengmeng.utils.NetUtil;

import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 程和 on 2016/10/15.
 */
public class DynamicInfoActivity extends AppCompatActivity {


    Dynamic dynamic;//动态信息
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.ib_dynamic_info_photo)
    ImageView ibDynamicInfoPhoto;
    @InjectView(R.id.tv_dynamic_info_name)
    TextView tvDynamicInfoName;
    @InjectView(R.id.tv_dynamic_info_time)
    TextView tvDynamicInfoTime;
    @InjectView(R.id.ib_dynamic_info_concern)
    ImageView ibDynamicInfoConcern;
    @InjectView(R.id.tv_dynamic_info_content)
    TextView tvDynamicInfoContent;
    @InjectView(R.id.iv_dynamic_info_picture)
    ImageView ivDynamicInfoPicture;
    @InjectView(R.id.iv_dynamic_info_place)
    ImageView ivDynamicInfoPlace;
    @InjectView(R.id.tv_dynamic_info_place)
    TextView tvDynamicInfoPlace;
    @InjectView(R.id.ib_dynamic_info_zan)
    ImageView ibDynamicInfoZan;
    @InjectView(R.id.ib_dynamic_info_pinlun)
    ImageView ibDynamicInfoPinlun;
    @InjectView(R.id.ib_dynamic_info_fenxiang)
    ImageView ibDynamicInfoFenxiang;
    @InjectView(R.id.v_zhixian1)
    View vZhixian1;
    @InjectView(R.id.tv_zan)
    TextView tvZan;
    @InjectView(R.id.et_dynamic_info_pinlun)
    EditText etDynamicInfoPinlun;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_info);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        dynamic = intent.getParcelableExtra("dynamicInfo");
        if (dynamic != null) {
            x.image().bind(ibDynamicInfoPhoto, NetUtil.photo_url+dynamic.getUser().getUserPhoto());
            tvDynamicInfoName.setText(dynamic.getUser().getUserName());
            tvDynamicInfoTime.setText(dynamic.getReleaseTime()+"");
            tvDynamicInfoContent.setText(dynamic.getReleaseText());
            x.image().bind(ivDynamicInfoPicture,NetUtil.picture_url+dynamic.getPicture());
            tvDynamicInfoPlace.setText(dynamic.getPlace());
        }

    }


    @OnClick({R.id.iv_back, R.id.ib_dynamic_info_photo, R.id.ib_dynamic_info_concern, R.id.ib_dynamic_info_zan, R.id.ib_dynamic_info_pinlun, R.id.ib_dynamic_info_fenxiang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                Intent intent=new Intent(this,DynamicMainActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_dynamic_info_photo:
                break;
            case R.id.ib_dynamic_info_concern:
                break;
            case R.id.ib_dynamic_info_zan:
                break;
            case R.id.ib_dynamic_info_pinlun:
                break;
            case R.id.ib_dynamic_info_fenxiang:
                break;
        }
    }
}
