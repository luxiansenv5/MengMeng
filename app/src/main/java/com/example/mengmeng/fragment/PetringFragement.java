package com.example.mengmeng.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.mengmeng.activity.DynamicMainActivity;
import com.example.mengmeng.activity.LoginInfo;
import com.example.mengmeng.activity.R;
import com.example.mengmeng.activity.ReleaseActivity;
import com.example.mengmeng.utils.NetUtil;
import com.example.mengmeng.utils.xUtilsImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 程和 on 2016/10/14.
 */
public class PetringFragement extends BaseFragment implements ViewPager.OnPageChangeListener {

    List<BaseFragment> lists = new ArrayList<BaseFragment>();
    private RadioButton rbFriends;
    private RadioButton rbHot;
    private RadioButton rbConcern;
    private ImageButton ibPaizhao;

    private ImageButton ibMine;
    private ViewPager dynamic_vp;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dynamic_all, null);
        dynamic_vp = ((ViewPager) v.findViewById(R.id.dynamic_fragment_viewpager));
//        rbConcern = ((RadioButton) v.findViewById(R.id.rb_concern));
        rbHot = ((RadioButton) v.findViewById(R.id.rb_hot));
        rbFriends = ((RadioButton) v.findViewById(R.id.rb_friends));
        ibPaizhao=(ImageButton)v.findViewById(R.id.ib_paizhao);
        ibMine=(ImageButton)v.findViewById(R.id.ib_mine);

        onPageSelected(0);
        xUtilsImageUtils.display(ibMine, NetUtil.photo_url+ LoginInfo.userPhoto,true);

        ibMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DynamicMainActivity)getActivity()).getmDrawerLayout().openDrawer(Gravity.LEFT);
                ((DynamicMainActivity)getActivity()).getmDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                        Gravity.LEFT);
            }
        });

        ibPaizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ReleaseActivity.class);
                startActivity(intent);
            }
        });

        rbFriends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dynamic_vp.setCurrentItem(1);
                }
            }
        });
        rbHot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    dynamic_vp.setCurrentItem(0);
                }
            }
        });
//        rbConcern.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    dynamic_vp.setCurrentItem(2);
//                }
//            }
//        });


        lists.add(new PetringAllFragment());
        lists.add(new PetringFriendFragement());
//        lists.add(new PetringFollowFragement());



        dynamic_vp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lists.get(position);

            }
            @Override
            public int getCount() {
                return lists.size();
            }
        });

        dynamic_vp.setOnPageChangeListener(this);


        return v;
    }

    @Override
    public void initView() {
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
                    case 0:
                        rbHot.setTextColor(Color.WHITE);
                        rbFriends.setTextColor(Color.BLACK);
//                        rbConcern.setTextColor(Color.BLACK);
                        break;
                    case 1:
                        rbHot.setTextColor(Color.BLACK);
                        rbFriends.setTextColor(Color.WHITE);
//                        rbConcern.setTextColor(Color.BLACK);
                        break;
//                    case 2:
//                        rbHot.setTextColor(Color.BLACK);
//                        rbFriends.setTextColor(Color.BLACK);
//                        rbConcern.setTextColor(Color.WHITE);
//                        break;
                    }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
