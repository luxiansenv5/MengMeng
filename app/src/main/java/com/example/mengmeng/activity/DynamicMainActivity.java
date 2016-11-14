package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mengmeng.fragment.CommunicateFragment;
import com.example.mengmeng.fragment.PetringFragement;
import com.example.mengmeng.fragment.ServiceFragement;
import com.nineoldandroids.view.ViewHelper;

public class DynamicMainActivity extends AppCompatActivity implements View.OnClickListener  {

    Fragment[] fragments;
    PetringFragement petringFragement;//主页
    CommunicateFragment communicateFragment;//好友聊天
    ServiceFragement serviceFragement;//服务中心
    //按钮的数组，一开始第一个按钮被选中
    RadioButton[] tabs;

    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    //private DrawerLayout drawer;

    private DrawerLayout mDrawerLayout;
    private TextView sign;
    int userId;

    //private TextView register;


    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public void setmDrawerLayout(DrawerLayout mDrawerLayout) {
        this.mDrawerLayout = mDrawerLayout;
    }

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
        //drawer = ((DrawerLayout) findViewById(R.id.drawer));

        //界面初始显示第一个fragment;添加第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragments[0]).commit();
        //初始时，按钮1选中
        tabs[0].setSelected(true);

//        ((Button) findViewById(R.id.OpenLeftMenu)).setOnClickListener(this);
//        OpenRightMenu = ((Button) findViewById(R.id.OpenRightMenu));
//        OpenRightMenu.setOnClickListener(this);
        sign = ((TextView) findViewById(R.id.sign));
        sign.setOnClickListener(this);
//        register = ((TextView) findViewById(R.id.register));
//        register.setOnClickListener(this);

        //ib_mine.setOnClickListener(this);


        initView();
        initEvents();

//        initData();
    }

    private void initData() {

        Intent intent=getIntent();
        userId=intent.getIntExtra("userId",0);

        if (userId!=0){
            newIndex=1;
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            Bundle bundle=new Bundle();
            bundle.putInt("userId",userId);

            communicateFragment.setArguments(bundle);

            transaction.add(R.id.fl_content,communicateFragment,"communicateFragment");
            transaction.commit();

            tabs[oldIndex].setSelected(false);
            //当前选择项，按钮被选中
            tabs[newIndex].setSelected(true);

            //当前选择项变为选中项
            oldIndex=newIndex;
        }
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


    private void initEvents() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerStateChanged(int newState)
            {

            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT"))
                {
                    float leftScale = 1 - 0.3f * scale;
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                } else
                {
                    ViewHelper.setTranslationX(mContent,
                            -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.OpenRightMenu:
//                mDrawerLayout.openDrawer(Gravity.RIGHT);
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
//                        Gravity.RIGHT);
//                break;

//            case R.id.OpenLeftMenu:
//                mDrawerLayout.openDrawer(Gravity.LEFT);
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
//                        Gravity.LEFT);
//                break;
            case R.id.sign:
                Intent intent = new Intent(DynamicMainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
