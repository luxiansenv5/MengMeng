package com.example.mengmeng.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.activity.DynamicInfoActivity;
import com.example.mengmeng.activity.R;
import com.example.mengmeng.activity.UserInfoActivity;
import com.example.mengmeng.pojo.Dynamic;
import com.example.mengmeng.utils.CommonAdapter;
import com.example.mengmeng.utils.NetUtil;
import com.example.mengmeng.utils.ViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 程和 on 2016/10/18.
 */
public class PetringAllFragment extends BaseFragment implements AbsListView.OnScrollListener {

    CommonAdapter<Dynamic> dynamicsAdapter;
    List<Dynamic> dynamics = new ArrayList<>();//存放动态信息
    private  List<Integer> choice=new ArrayList<Integer>();//点赞
    private  List<Integer> follow=new ArrayList<Integer>();//关注

    @InjectView(R.id.lv_dynamics)
    ListView lvDynamics;


    private RadioButton rbFriends;
    private ViewPager dynamic_vp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dynamic_all, null);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

        lvDynamics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DynamicInfoActivity.class);
                intent.putExtra("dynamicInfo", dynamics.get(position));
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //获取网络数据
    public void getData() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "DynamicQueryServlet");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Dynamic>>() {
                }.getType();
                List<Dynamic> newDynamic = new ArrayList<Dynamic>();
                newDynamic = gson.fromJson(result, type);//解析成List<Dynamic>
                dynamics.clear();// 清空原来数据
                dynamics.addAll(newDynamic);

                //设置listview的adpter
                if (dynamicsAdapter == null) {
                    dynamicsAdapter = new CommonAdapter<Dynamic>(getActivity(), dynamics, R.layout.layout_dynamic) {
                        @Override
                        public void convert(ViewHolder viewHolder, final Dynamic dynamic, int position) {


                            boolean flag1 = false;

                            //取件赋值
                            ImageView ivHead = viewHolder.getViewById(R.id.im_dynamic_head);
                            x.image().bind(ivHead, NetUtil.photo_url + dynamic.getUser().getUserPhoto());

                            final TextView tvName = viewHolder.getViewById(R.id.tv_dynamic_name);
                            tvName.setText(dynamic.getUser().getUserName());

                            TextView tvTime = viewHolder.getViewById(R.id.tv_dynamic_time);
                            tvTime.setText(dynamic.getReleaseTime() + "");

                            TextView tvContent = viewHolder.getViewById(R.id.tv_dynamic_content);
                            tvContent.setText(dynamic.getReleaseText());

                            ImageView ivImag = viewHolder.getViewById(R.id.iv_dynamic_imag);
                            x.image().bind(ivImag, NetUtil.picture_url + dynamic.getPicture());

                            TextView tvPlace = viewHolder.getViewById(R.id.tv_dynamic_place);
                            tvPlace.setText(dynamic.getPlace());

                            //设置头像点击事件
                            ivHead.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                                    intent.putExtra("userId", dynamic.getUser().getUserId() + "");
                                    startActivity(intent);
                                }
                            });

                            //设置分享点击事件
                            viewHolder.getViewById(R.id.im_fenxiang).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showShare();
                                }
                            });

                            //设置点赞点击事件
                           ImageButton imZan= viewHolder.getViewById(R.id.im_zan);
                            imZan.setTag(position);
                            imZan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(choice.contains((Integer)(((ImageView)v).getTag()))){
                                        ((ImageView) v).setImageResource(R.drawable.zan);
                                        choice.remove((Integer)(((ImageView)v).getTag()));

                                        int user_Id=((MyApplication)getActivity().getApplication()).getUser().getUserId();    //点赞人Id
                                        int dynamic_Id=dynamic.getDynamicId();
                                        removeZan(dynamic_Id,user_Id);

                                    }else {
                                        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.zan1);
                                        ((ImageView) v).setImageBitmap(bitmap);
                                        choice.add((Integer) (((ImageView) v).getTag()));

                                        int user_Id=((MyApplication)getActivity().getApplication()).getUser().getUserId();    //点赞人Id
                                        int dynamic_Id=dynamic.getDynamicId();                                                  //动态Id
                                        String zanTime=String.valueOf(System.currentTimeMillis());                             //点赞时间
                                        addZan(dynamic_Id,user_Id,zanTime);
                                    }

                                }
                            });

                            if(choice.contains(position)){
                               imZan.setImageResource(R.drawable.zan1);
                            }else{
                                imZan.setImageResource(R.drawable.zan);
                            }

                            //设置关注点击事件
                            ImageButton imConcern= viewHolder.getViewById(R.id.ib_dynamic_concern);
                            imConcern.setTag(position);
                            SharedPreferences sharedPreferences=PetringAllFragment.this.getActivity().getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            String guanzhu = sharedPreferences.getString("guanzhu","");
                            String[] ids = guanzhu.split("&");
                            System.out.println(ids+"1==========");
                            int userId= dynamic.getUser().getUserId();
                            boolean flag111 = false;
                            for(String id:ids){
                                if(id.equals(userId+"")){
                                    flag111 = true;
                                    return;
                                }
                            }
                            System.out.println("flag1:"+flag111);
                            if (flag111){
                                imConcern.setImageResource(R.drawable.already_concern);
                            }else{
                                imConcern.setImageResource(R.drawable.add_concern);
                            }

                            final boolean f = flag111;

                            imConcern.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    SharedPreferences sharedPreferences=PetringAllFragment.this.getActivity().getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    String guanzhu = sharedPreferences.getString("guanzhu","");
                                    String[] ids = guanzhu.split("&");
                                    for(String id:ids){
                                        System.out.println(id);
                                    }
                                    int userId= dynamic.getUser().getUserId();
                                    System.out.println(ids.length+"================2==========");

                                    if(follow.contains((Integer)(((ImageView)v).getTag()))&&f){
                                        ((ImageView) v).setImageResource(R.drawable.add_concern);
                                        follow.remove((Integer)(((ImageView)v).getTag()));

                                        int followUserId=((MyApplication)getActivity().getApplication()).getUser().getUserId();    //点赞人Id
                                        int beFollowUserId=dynamic.getUser().getUserId();
                                        removeFollow(followUserId,beFollowUserId);

                                        String s = "";
                                        for(String id:ids){
                                            if(!id.equals(userId+"")){
                                                s+=id+"&";
                                            }
                                        }
                                        editor.putString("guanzhu",s.substring(0,s.length()-1));

                                    }else {
                                        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.already_concern);
                                        ((ImageView) v).setImageBitmap(bitmap);
                                        follow.add((Integer) (((ImageView) v).getTag()));

                                        int followUserId=((MyApplication)getActivity().getApplication()).getUser().getUserId();    //点赞人Id
                                        int beFollowUserId=dynamic.getUser().getUserId();
                                        addFollow(followUserId,beFollowUserId);
                                        String s = "";
                                        for (String id:ids){
                                            if (!id.equals(userId+"")){
                                                s+=id+"&";
                                            }
                                            s=s+userId;
                                        }
                                        System.out.println("s:"+s);

                                        editor.putString("guanzhu",s.substring(0,s.length()));
                                    }
                                    editor.commit();
                                }
                            });

                            if(follow.contains(position)){
                                imConcern.setImageResource(R.drawable.already_concern);
                            }else{
                                imConcern.setImageResource(R.drawable.add_concern);
                            }
                        }
                    };
                    lvDynamics.setAdapter(dynamicsAdapter);
                } else {
                    dynamicsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    //增加点赞
    public void addZan(int a,int b,String c){
        RequestParams params = new RequestParams(NetUtil.url + "AddZanServlet");

        params.addBodyParameter("dynamicId",String.valueOf(a));
        params.addBodyParameter("userId",String.valueOf(b));
        params.addBodyParameter("zanTime",c);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getActivity(), "点赞成功", Toast.LENGTH_SHORT).show();
                System.out.println("点赞成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    //消除点赞
    public void removeZan(int a,int b){
        RequestParams params = new RequestParams(NetUtil.url + "RemoveZanServlet");

        params.addBodyParameter("dynamicId",String.valueOf(a));
        params.addBodyParameter("userId",String.valueOf(b));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getActivity(), "取消点赞成功", Toast.LENGTH_SHORT).show();
                System.out.println("取消点赞成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    //加关注
    public void addFollow(int a,int b){
        RequestParams params = new RequestParams(NetUtil.url + "AddFollowServlet");
        params.addBodyParameter("followUserId",String.valueOf(a));
        params.addBodyParameter("beFollowUserId",String.valueOf(b));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getActivity(), "关注成功", Toast.LENGTH_SHORT).show();
                System.out.println("关注成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    //取消关注
    public void removeFollow(int a,int b){
        RequestParams params = new RequestParams(NetUtil.url + "RemoveFollowServlet");
        params.addBodyParameter("followUserId",String.valueOf(a));
        params.addBodyParameter("beFollowUserId",String.valueOf(b));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getActivity(), "取消关注成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void showShare() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

           // 启动分享GUI
        oks.show(getActivity());
    }
}
