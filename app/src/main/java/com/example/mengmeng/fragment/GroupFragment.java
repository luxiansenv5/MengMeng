package com.example.mengmeng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.mengmeng.activity.CommunicateGroupActivity;
import com.example.mengmeng.activity.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by admin on 2016/10/17.
 */
public class GroupFragment extends BaseFragment {


    @InjectView(R.id.group_add)
    ImageView groupAdd;
    @InjectView(R.id.group_bottom)
    RelativeLayout groupBottom;
    @InjectView(R.id.lv_group)
    ListView lvGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout_group, null);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.group_add)
    public void onClick() {

        Intent intent = new Intent(getActivity(), CommunicateGroupActivity.class);

        startActivity(intent);

    }
}
