package com.example.mengmeng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.serviceactivity.AdoptActivity;
import com.example.mengmeng.utils.MyGridAdapter;
import com.example.mengmeng.utils.MyGridView;

/**
 * Created by 程和 on 2016/10/14.
 */
public class ServiceFragement extends BaseFragment{

    private MyGridView gridview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_service, null);

        gridview=(MyGridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new MyGridAdapter(getActivity()));

        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(getActivity(),"这是第一个item",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:

                        Intent intent=new Intent(getActivity(), AdoptActivity.class);
                        startActivity(intent);

                        break;
                    case 2:
                        Toast.makeText(getActivity(),"这是第三个item",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(),"这是第四个item",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }

    @Override
    public void initData() {

    }
}
