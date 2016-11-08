package com.example.mengmeng.utils;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 陆猛 on 2016/11/7.
 */
public class ViewPagerAdapter extends PagerAdapter {

    List<View> viewLists;

    public ViewPagerAdapter(List<View> lists)
    {
        viewLists = lists;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(viewLists.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(viewLists.get(position),0);
        return viewLists.get(position);
    }
}
