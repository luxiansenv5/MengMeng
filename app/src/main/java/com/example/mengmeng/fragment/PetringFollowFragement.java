package com.example.mengmeng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mengmeng.activity.DynamicInfoActivity;
import com.example.mengmeng.activity.R;
import com.example.mengmeng.activity.UserInfoActivity;
import com.example.mengmeng.pojo.Dynamic;
import com.example.mengmeng.utils.CommonAdapter;
import com.example.mengmeng.utils.NetUtil;
import com.example.mengmeng.utils.ViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 程和 on 2016/10/17.
 */
public class PetringFollowFragement extends BaseFragment{

    CommonAdapter<Dynamic> dynamicsAdapter;
    List<Dynamic> dynamics = new ArrayList<>();//存放动态信息
    @InjectView(R.id.lv_dynamics)
    ListView lvDynamics;



    private RadioButton rbFriends;
    private ViewPager dynamic_vp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dynamic_all, null);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        lvDynamics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DynamicInfoActivity.class);
                intent.putExtra("dynamicInfo", dynamics.get(position));
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //获取网络数据
    public void getData() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "DynamicQueryServlet");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Dynamic>>() {
                }.getType();
                List<Dynamic> newDynamic = new ArrayList<Dynamic>();
                newDynamic = gson.fromJson(result, type);//解析成List<Dynamic>
                dynamics.clear();// 清空原来数据
                dynamics.addAll(newDynamic);

                //设置listview的adpter
                if (dynamicsAdapter == null) {
                    dynamicsAdapter = new CommonAdapter<Dynamic>(getActivity(), dynamics, R.layout.layout_dynamic) {
                        @Override
                        public void convert(ViewHolder viewHolder, final Dynamic dynamic, int position) {
                            //取件赋值
                            ImageView ivHead = viewHolder.getViewById(R.id.im_dynamic_head);

                            x.image().bind(ivHead, NetUtil.photo_url + dynamic.getUser().getUserPhoto());

                            final TextView tvName = viewHolder.getViewById(R.id.tv_dynamic_name);
                            tvName.setText(dynamic.getUser().getUserName());

                            TextView tvTime = viewHolder.getViewById(R.id.tv_dynamic_time);
                            tvTime.setText(dynamic.getReleaseTime() + "");

                            TextView tvContent = viewHolder.getViewById(R.id.tv_dynamic_content);
                            tvContent.setText(dynamic.getReleaseText());

                            ImageView ivImag = viewHolder.getViewById(R.id.iv_dynamic_imag);
                            x.image().bind(ivImag, NetUtil.picture_url + dynamic.getPicture());

                            TextView tvPlace = viewHolder.getViewById(R.id.tv_dynamic_place);
                            tvPlace.setText(dynamic.getPlace());

                            //设置头像点击事件
                            ivHead.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                                    intent.putExtra("userId", dynamic.getUser().getUserId() + "");
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    lvDynamics.setAdapter(dynamicsAdapter);
                } else {
                    dynamicsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
