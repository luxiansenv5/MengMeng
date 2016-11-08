package com.example.mengmeng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mengmeng.activity.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 程和 on 2016/10/14.
 */
public class CommunicateFragment extends BaseFragment implements View.OnClickListener{


    Fragment[] fragments;
    PetFriendsFragment petFriendsFragment = new PetFriendsFragment();
    MessageFragment messageFragment = new MessageFragment();
    Button[] tabs;//按钮的数组，一开始第一个按钮被选中
    View v;

    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    @InjectView(R.id.btn_message)
    Button btnMessage;
    @InjectView(R.id.btn_container_message)
    RelativeLayout btnContainerMessage;
    @InjectView(R.id.btn_pet_friend)
    Button btnPetFriend;
    @InjectView(R.id.btn_container_pet_friend)
    RelativeLayout btnContainerPetFriend;
    @InjectView(R.id.main_top)
    LinearLayout mainTop;
    @InjectView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @InjectView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.communicate_fragment, null);
        ButterKnife.inject(this, v);
        return v;
    }


    public void swichFragment() {

        FragmentTransaction transaction;
        //如果选择的项不是当前选中项，则替换；否则，不做操作
        if (newIndex != oldIndex) {

            transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.hide(fragments[oldIndex]);//隐藏当前显示项


            //如果选中项没有加过，则添加
            if (!fragments[newIndex].isAdded()) {
                //添加fragment
                transaction.add(R.id.fragment_container, fragments[newIndex]);
            }
            //显示当前选择项
            transaction.show(fragments[newIndex]).commit();
        }
        //之前选中的项，取消选中
        tabs[oldIndex].setSelected(false);
        //当前选择项，按钮被选中
        tabs[newIndex].setSelected(true);


        //当前选择项变为选中项
        oldIndex = newIndex;

    }


    @Override
    public void initView() {

        btnMessage.setOnClickListener(this);
        btnPetFriend.setOnClickListener(this);

        fragments = new Fragment[]{petFriendsFragment, messageFragment};

        tabs = new Button[2];
        tabs[0] = ((Button) v.findViewById(R.id.btn_pet_friend));//petfriend的button
        tabs[1] = (Button) v.findViewById(R.id.btn_message);//message的button
        //界面初始显示第一个fragment;添加第一个fragment
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragments[0]).commit();
        //初始时，按钮1选中
        tabs[0].setSelected(true);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onClick(View v) {
        //点击按钮时，表示选中不同的项
        switch (v.getId()) {
            case R.id.btn_pet_friend:
                newIndex = 0;//选中petfriend
                break;
            case R.id.btn_message:
                newIndex = 1;//选中message
                break;

        }
        swichFragment();
    }
}


