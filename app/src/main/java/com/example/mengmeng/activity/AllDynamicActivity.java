package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.fragment.BaseFragment;
import com.example.mengmeng.fragment.PetringFollowFragement;
import com.example.mengmeng.fragment.PetringFragement;
import com.example.mengmeng.fragment.PetringFriendFragement;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 程和 on 2016/10/17.
 */
public class AllDynamicActivity extends AppCompatActivity {
    //fragement的集合
    List<BaseFragment> lists = new ArrayList<BaseFragment>();

    @InjectView(R.id.rb_hot)
    RadioButton rbHot;
    @InjectView(R.id.rb_friends)
    RadioButton rbFriends;
    @InjectView(R.id.rb_concern)
    RadioButton rbConcern;
    @InjectView(R.id.rg_tab1)
    RadioGroup rgTab1;
    @InjectView(R.id.ib_mine)
    ImageButton ibMine;
    @InjectView(R.id.ib_paizhao)
    ImageButton ibPaizhao;
    @InjectView(R.id.id_prod_list_sort_line1)
    View idProdListSortLine1;
    @InjectView(R.id.Dynamic_fragment_viewpager)
    ViewPager DynamicFragmentViewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragement_pet_ring);
        ButterKnife.inject(this);
        Intent intent=getIntent();
        intent.getStringExtra("flag");
        initData();
    }

    private void initData() {
        lists.add(new PetringFragement());
        lists.add(new PetringFriendFragement());
        lists.add(new PetringFollowFragement());

        DynamicFragmentViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                System.out.println(position+"==============");
                return lists.get(position);
            }

            @Override
            public int getCount() {
                return lists.size();
            }
        });
    }

}
