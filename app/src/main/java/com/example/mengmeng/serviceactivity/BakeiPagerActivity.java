package com.example.mengmeng.serviceactivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.utils.MyListAdapter;
import com.example.mengmeng.utils.PetEncyclopedia;
import com.example.mengmeng.utils.ViewPagerAdapter;
import com.example.mengmeng.utils.catListAdapter;

import java.util.ArrayList;
import java.util.List;

public class BakeiPagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ImageView imageView;
    private List<View> lists = new ArrayList<View>();
    private ViewPagerAdapter adapter;
    private Bitmap cursor;
    private int offSet;
    private int currentItem;
    private Matrix matrix = new Matrix();
    private int bmWidth;
    private Animation animation;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView alsj;
    private ListView dogList;
    private ListView catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakei_pager);

        initView();

        initEvent();

        initData();

        initeCursor();

    }

    private void initView(){


        imageView = (ImageView) findViewById(R.id.cursor);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        viewPager=(ViewPager) findViewById(R.id.viewPager2);

        View dogView=getLayoutInflater().inflate(R.layout.fragment_dog, null);
        View catView=getLayoutInflater().inflate(R.layout.fragment_cat, null);
        View otherView=getLayoutInflater().inflate(R.layout.fragment_other,null);

        dogList= ((ListView) dogView.findViewById(R.id.dog_list));
        dogList.setAdapter(new MyListAdapter(BakeiPagerActivity.this));

        catList = ((ListView) catView.findViewById(R.id.cat_list));
        catList.setAdapter(new catListAdapter(BakeiPagerActivity.this));

        lists.add(dogView);
        lists.add(catView);
        lists.add(otherView);
    }

    private void initEvent(){


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        if (currentItem == 1) {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth, 0, 0, 0);
                        } else if (currentItem == 2) {
                            animation = new TranslateAnimation(offSet * 4 + 2
                                    * bmWidth, 0, 0, 0);
                        }
                        break;
                    case 1:
                        if (currentItem == 0) {
                            animation = new TranslateAnimation(0, offSet * 2
                                    + bmWidth, 0, 0);
                        } else if (currentItem == 2) {
                            animation = new TranslateAnimation(4 * offSet + 2
                                    * bmWidth, offSet * 2 + bmWidth, 0, 0);
                        }
                        break;
                    case 2:
                        if (currentItem == 0) {
                            animation = new TranslateAnimation(0, 4 * offSet + 2
                                    * bmWidth, 0, 0);
                        } else if (currentItem == 1) {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth,
                                    0, 0);
                        }
                }
                currentItem = position;
                animation.setDuration(150); // 光标滑动速度
                animation.setFillAfter(true);
                imageView.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        textView1.setOnClickListener(this);

        textView2.setOnClickListener(this);

        textView3.setOnClickListener(this);

        dogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog(position);
            }
        });

        catList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog2(position);
            }
        });

    }

    private void initData(){

        adapter = new ViewPagerAdapter(lists);

        viewPager.setAdapter(adapter);

    }

    private void initeCursor(){

        cursor = BitmapFactory.decodeResource(getResources(), R.drawable.cursor);
        bmWidth = cursor.getWidth();

        DisplayMetrics dm;
        dm = getResources().getDisplayMetrics();

        offSet = (dm.widthPixels - 3 * bmWidth) / 6;
        matrix.setTranslate(offSet, 0);
        imageView.setImageMatrix(matrix); // 需要imageView的scaleType为matrix
        currentItem = 0;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.textView1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.textView2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.textView3:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    private void dialog(int position) {

        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("简介"); //设置标题

        switch (position){

            case 0:
                builder.setIcon(R.drawable.alsj);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.alsjxqq);
                break;
            case 1:
                builder.setIcon(R.drawable.adly);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.adlymyq);
                break;
            case 2:
                builder.setIcon(R.drawable.bgq);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.bgq);
                break;
            case 3:
                builder.setIcon(R.drawable.bxq);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.bxq);
                break;
            case 4:
                builder.setIcon(R.drawable.bmq);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.bmq);
                break;
            case 5:
                builder.setIcon(R.drawable.cbq);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.cbq);
                break;
            case 6:
                builder.setIcon(R.drawable.cq);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.cq);
                break;
            case 7:
                builder.setIcon(R.drawable.hsq);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.hsq);
                break;
            case 8:
                builder.setIcon(R.drawable.jmq);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.jmq);
                break;
            case 9:
                builder.setIcon(R.drawable.kjq);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.kjq);
                break;
        }
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
              }
        });
        builder.create().show();
    }

    private void dialog2(int position) {


        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("简介"); //设置标题

        switch (position){

            case 0:
                builder.setIcon(R.drawable.ajm);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.ajm);
                break;
            case 1:
                builder.setIcon(R.drawable.bsm);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.bsm);
                break;
            case 2:
                builder.setIcon(R.drawable.bom);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.bom);
                break;
            case 3:
                builder.setIcon(R.drawable.dtm);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.dtm);
                break;
            case 4:
                builder.setIcon(R.drawable.elslm);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.elslm);
                break;
            case 5:
                builder.setIcon(R.drawable.hbm);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.hbm);
                break;
            case 6:
                builder.setIcon(R.drawable.hxm);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.hxm);
                break;
            case 7:
                builder.setIcon(R.drawable.jfm);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.jfm);
                break;
            case 8:
                builder.setIcon(R.drawable.lcm);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.lcm);
                break;
            case 9:
                builder.setIcon(R.drawable.tm);//设置图标，图片id即可
                builder.setMessage(PetEncyclopedia.tm);
                break;
        }
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
