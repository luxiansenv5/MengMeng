package com.example.mengmeng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mengmeng.activity.R;

/**
 * Created by 程和 on 2016/10/14.
 */
public class CommunicateFragment extends BaseFragment {


    Fragment[] fragments ;
    PetFriendsFragment petFriendsFragment =new PetFriendsFragment();;
    MessageFragment messageFragment= new MessageFragment();;
    GroupFragment groupFragment = new GroupFragment();;
    //按钮的数组，一开始第一个按钮被选中
    Button[] tabs;
    View v;

    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.communicate_fragment, null);


        return v;
    }

    //按钮的点击事件:选中不同的按钮，不同的fragment显示
    public void onClick(View view) {

        //点击按钮时，表示选中不同的项
        switch(view.getId()){
            case R.id.btn_pet_friend:
                newIndex=0;//选中petfriend
                break;

            case R.id.btn_message:
                newIndex=1;//选中message
                break;
            case R.id.btn_group:
                newIndex=2;//选中group
                break;

        }
        swichFragment();
    }

    public void swichFragment(){

        FragmentTransaction transaction;
        //如果选择的项不是当前选中项，则替换；否则，不做操作
        if(newIndex!=oldIndex){

            transaction=getActivity().getSupportFragmentManager().beginTransaction();

            transaction.hide(fragments[oldIndex]);//隐藏当前显示项


            //如果选中项没有加过，则添加
            if(!fragments[newIndex].isAdded()){
                //添加fragment
                transaction.add(R.id.fragment_container,fragments[newIndex]);
            }
            //显示当前选择项
            transaction.show(fragments[newIndex]).commit();
        }
        //之前选中的项，取消选中
        tabs[oldIndex].setSelected(false);
        //当前选择项，按钮被选中
        tabs[newIndex].setSelected(true);


        //当前选择项变为选中项
        oldIndex=newIndex;

    }




    @Override
    public void initView() {

        fragments = new Fragment[]{petFriendsFragment, messageFragment, groupFragment};

        tabs = new Button[3];
        tabs[0] = ((Button) v.findViewById(R.id.btn_pet_friend));//petfriend的button
        tabs[1] = (Button) v.findViewById(R.id.btn_message);//message的button
        tabs[2] = (Button) v.findViewById(R.id.btn_group);//group的button
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
}


