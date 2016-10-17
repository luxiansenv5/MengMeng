package com.example.mengmeng;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.mengmeng.fragment.CommunicateFragment;
import com.example.mengmeng.fragment.PetringFragement;
import com.example.mengmeng.fragment.ServiceFragement;

public class DynamicMainActivity extends AppCompatActivity {

    Fragment[] fragments;
    PetringFragement petringFragement;//主页
    CommunicateFragment communicateFragment;//好友聊天
    ServiceFragement serviceFragement;//服务中心
    //按钮的数组，一开始第一个按钮被选中
    RadioButton[] tabs;

    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_main);

        petringFragement=new PetringFragement();
        communicateFragment=new CommunicateFragment();
        serviceFragement=new ServiceFragement();

        fragments=new Fragment[]{petringFragement,communicateFragment,serviceFragement};

        tabs=new RadioButton[3];
        tabs[0]=(RadioButton) findViewById(R.id.rb_main_pet_ring);//主页的button
        tabs[1]=(RadioButton) findViewById(R.id.rb_main_communicate);//主页的button
        tabs[2]=(RadioButton) findViewById(R.id.rb_main_service);//主页的button
        //界面初始显示第一个fragment;添加第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragments[0]).commit();
        //初始时，按钮1选中
        tabs[0].setSelected(true);
    }


    //按钮的点击事件:选中不同的按钮，不同的fragment显示
    public void onTabClicked(View view) {
        //点击按钮时，表示选中不同的项
        switch(view.getId()) {
            case R.id.rb_main_pet_ring:
                newIndex = 0;//选中第一项
                break;
            case R.id.rb_main_communicate:
                newIndex = 1;//选中第二项
                break;
            case R.id.rb_main_service:
                newIndex = 2;//选中第三项
                break;
        }
        switchFragment();
    }

    public void switchFragment(){
        FragmentTransaction transaction;
        //如果选择的项不是当前选中项，则替换；否则，不做操作
        if(newIndex!=oldIndex){
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.hide(fragments[oldIndex]);//隐藏当前显示项
            //如果选中项没有加过，则添加
            if(!fragments[newIndex].isAdded()){
                //添加fragment
                transaction.add(R.id.fl_content,fragments[newIndex]);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        newIndex=1;
        switchFragment();
    }

}
