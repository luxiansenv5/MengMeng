package com.example.mengmeng.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mengmeng.activity.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 程和 on 2016/10/24.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener{
    private View headView;
    private View footerView;

    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView tvRefreshState;
    private TextView tvRefreshTime;


    private ProgressBar progressBarF;
    private TextView tvRefreshStateF;

    public static final int INIT=0;//初始状态（头部不显示）
    public static final int PREPAREREFRESHING=1;//准备刷新（头部全部显示）
    public static final int ISREFRESHING=2;//正在刷新

    //动画效果
    private RotateAnimation upAnimation;
    private RotateAnimation downAnimation;

    int higetHead=0;//头部的高度
    float downY=0;//点击是的坐标
    float moveY=0;

    int  headState;//头部的状态
    int firstVisibleItem;//第一条的位置

    private boolean isLoading = false; // 是否正在加载更多中

    public static boolean   tag=false;//默认为false，为ListView的点击事件



    public static boolean isTag(){
        return tag;
    }

    OnRefreshUploadChangeListener onRefreshUploadChangeListener;//接口


     public RefreshListView(Context context) {
         this(context,null);
     }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHead(context);
        initAnimation();
        changeFooter();
        this.setOnScrollListener(this);//这里如果不写的话，是不会监听的，即onScrollStateChanged和onScroll不会执行
    }


    //初始化头部控件
    public void initHead(Context context){

        //解析布局文件
        headView= LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_head,null);

        imageView= (ImageView) headView.findViewById(R.id.iv_refresher);
        progressBar=(ProgressBar) headView.findViewById(R.id.pb_refresher);
        tvRefreshState = (TextView) headView.findViewById(R.id.tv_refreshertext);
        tvRefreshTime = (TextView) headView.findViewById(R.id.tv_refreshtime);

        //获取headview的高度
        headView.measure(0,0);
        higetHead=headView.getMeasuredHeight();

        headView.setPadding(0,-higetHead,0,0);
        addHeaderView(headView);//添加头部控件

        //下拉加载的布局文件解析
        footerView=LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_footer,null);
        progressBarF=(ProgressBar) footerView.findViewById(R.id.footer_progressbar);
        tvRefreshStateF = (TextView) footerView.findViewById(R.id.footer_hint_textview);

        //footerView.setPadding(0, -footerViewHeight, 0, 0);
        addFooterView(footerView);

    }

    public void changeFooter(){

        if(isLoading){
            progressBarF.setVisibility(VISIBLE);
            tvRefreshStateF.setVisibility(GONE);
        }else {
            progressBarF.setVisibility(GONE);
            tvRefreshStateF.setVisibility(VISIBLE);
        }
    }

    //点击ListView会不断触发事件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                //当点击的时候
                downY=ev.getY();
                tag=false;

                break;
            case MotionEvent.ACTION_MOVE:
                //当点击移动的时候
                tag=true;
                moveY=ev.getY();
                //判断第一条记录可见&&下拉；跟着头部改变
                if(firstVisibleItem==0&&moveY-downY>0){
                    float  headPadding=-higetHead+(moveY-downY);//拉过之后，头部的padding
                    //下拉过程中，状态改变：头部全部显示出来==>准备刷新
                    if(headPadding>=0&&headState==INIT){
                        headState=PREPAREREFRESHING;
                        changeState();

                    }
                    if (headState==PREPAREREFRESHING&&headPadding<0){
                        headState=INIT;
                        changeState();
                    }

                    headView.setPadding(0,(int)headPadding,0,0);
                    return true;
                }

            case MotionEvent.ACTION_UP:
                //当触发结束的时候
                if(headState==PREPAREREFRESHING){
                    headState=ISREFRESHING;
                    changeState();
                    //更新数据源

                    //更新数据源
                    if(onRefreshUploadChangeListener!=null){
                        onRefreshUploadChangeListener.onRefresh();
                    }

                    //headview的padding变成0
                    headView.setPadding(0,0,0,0);
                }else if (headState==INIT){

                    headView.setPadding(0,-higetHead,0,0);

                }


                break;

        }


        return super.onTouchEvent(ev);
    }

    //headview不同状态，控件效果
    public void changeState(){
        switch(headState){
            case INIT:

                progressBar.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setAnimation(downAnimation);
                tvRefreshState.setText("下拉刷新");
                tvRefreshTime.setVisibility(View.INVISIBLE);


                break;
            case PREPAREREFRESHING:
                //准备刷新
                progressBar.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
                imageView.startAnimation(upAnimation);////设置箭头朝下
                tvRefreshState.setText("释放刷新");
                tvRefreshTime.setVisibility(View.INVISIBLE);
                break;
            case ISREFRESHING:
                //正在刷新

                progressBar.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                imageView.clearAnimation();//清除imagview的动画
                tvRefreshState.setText("正在刷新");
                tvRefreshTime.setVisibility(View.VISIBLE);
                tvRefreshTime.setText(getTime());//刷新时间
                break;
        }
    }

    //获取当前时间
    public String getTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time=format.format(new Date());
        return time;
    }

    public void initAnimation(){
        upAnimation=new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        upAnimation.setFillAfter(true);
        upAnimation.setDuration(500);
        downAnimation=new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setFillAfter(true);
        downAnimation.setDuration(500);
    }

    //滚动时候的回调事件，可以获取第一条的的位置
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (getLastVisiblePosition() == (getCount() - 1)&&isLoading==false) {
            if(scrollState==OnScrollListener.SCROLL_STATE_TOUCH_SCROLL||scrollState==OnScrollListener.SCROLL_STATE_IDLE) {
                isLoading = true;
                changeFooter();
                if(onRefreshUploadChangeListener!=null){
                    onRefreshUploadChangeListener.onPull();//加载更多数据
                }
            }

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem=firstVisibleItem;

    }


    //定义接口：下拉刷新，上拉加载；
    public interface  OnRefreshUploadChangeListener{
        //下拉刷新
        void onRefresh();
        void onPull();//上拉加载
    }

    //供其他类实现该接口
    public void setOnRefreshUploadChangeListener(OnRefreshUploadChangeListener onRefreshUploadChangeListener){
        this.onRefreshUploadChangeListener=onRefreshUploadChangeListener;


    }

    //刷新完成
    public void completeRefresh(){
        //padding返回去
        headView.setPadding(0,-higetHead,0,0);
        //状态改变：正在刷新-INIT
        headState=INIT;
        changeState();//控件初始化
    }



    //完成加载
    public void completeLoad(){
        isLoading=false;//加载完成
        changeFooter();

    }

}
