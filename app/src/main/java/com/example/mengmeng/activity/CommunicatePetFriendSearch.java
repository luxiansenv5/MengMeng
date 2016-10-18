package com.example.mengmeng.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CommunicatePetFriendSearch extends AppCompatActivity {

    @InjectView(R.id.tv_petfriend_input)
    TextView tvPetfriendInput;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    @InjectView(R.id.petsearch_bottom)
    RelativeLayout petsearchBottom;
    @InjectView(R.id.tv_searchhistory)
    TextView tvSearchhistory;
    @InjectView(R.id.lv_history)
    ListView lvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_petfriend_search);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.tv_cancel)
    public void onClick() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
