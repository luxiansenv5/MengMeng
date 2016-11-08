package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.mengmeng.fragment.DynamicFragment;
import com.example.mengmeng.fragment.PetFragment;

public class MyReleaseActivity extends AppCompatActivity {

    Fragment[] fragments;
    RadioButton[] tabs;

    DynamicFragment dynamicFragment;
    PetFragment petFragment;

    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_release);

        dynamicFragment=new DynamicFragment();
        petFragment=new PetFragment();

        fragments=new Fragment[]{dynamicFragment,petFragment};

        tabs=new RadioButton[2];

        tabs[0]= ((RadioButton) findViewById(R.id.rb_dynamic));
        tabs[1]= ((RadioButton) findViewById(R.id.rb_pet));


        getSupportFragmentManager().beginTransaction().add(R.id.mfl_content,fragments[0]).show(fragments[0]).commit();
        tabs[0].setSelected(true);


    }

    public void onTabClicked(View view) {
        //点击按钮时，表示选中不同的项
        switch(view.getId()) {
            case R.id.rb_dynamic:
                newIndex = 0;//选中第一项
                break;
            case R.id.rb_pet:
                newIndex = 1;//选中第二项
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
                transaction.add(R.id.mfl_content,fragments[newIndex]);
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
