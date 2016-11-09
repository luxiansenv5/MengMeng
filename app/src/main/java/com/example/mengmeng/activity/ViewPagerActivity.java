package com.example.mengmeng.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager vp;
    int previoutsPosition_vp = 0;
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_view_pager);

        final int[] imgs = {R.id.iv_iv1,R.id.iv_iv2,R.id.iv_iv3};

        vp = ((ViewPager) findViewById(R.id.vp));
        enter = ((Button) findViewById(R.id.bt_enter));

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        List<Integer> imgsrc=new ArrayList<Integer>();
        imgsrc.add(0,R.drawable.start_i1);
        imgsrc.add(1,R.drawable.start_i2);
        imgsrc.add(2,R.drawable.start_i3);
        MyPageAdapter pageAdapter=new MyPageAdapter(imgsrc);
        vp.setAdapter(pageAdapter);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((ImageView) findViewById(imgs[position])).setImageResource(R.drawable.point_red);
                ((ImageView) findViewById(imgs[previoutsPosition_vp])).setImageResource(R.drawable.point_gray1);
                previoutsPosition_vp = position;

                if(position==imgs.length-1){
                    enter.setVisibility(View.VISIBLE);
                }else{
                    enter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class MyPageAdapter extends PagerAdapter{

        List<Integer> imgsrc;

        public  MyPageAdapter( List<Integer> imgsrc){
            this.imgsrc=imgsrc;
        }
        @Override
        public int getCount() {
            return imgsrc.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
           View view  = View.inflate(getApplicationContext(),R.layout.vp_item,null);
            ImageView iv_vp_item = ((ImageView) view.findViewById(R.id.iv_vp_item));
            iv_vp_item.setImageResource(imgsrc.get(position));
            container.addView(view);

            return view;
        }
    }



}
