package com.example.mengmeng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.serviceactivity.AdoptActivity;

/**
 * Created by 程和 on 2016/10/14.
 */
public class ServiceFragement extends BaseFragment{

    private ImageView iv_lingyang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_service, null);

        iv_lingyang = ((ImageView) view.findViewById(R.id.iv_lingyang));
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

        iv_lingyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),AdoptActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

    }
}
