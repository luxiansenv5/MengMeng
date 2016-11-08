package com.example.mengmeng.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.activity.PictureActivity;
import com.example.mengmeng.activity.R;
import com.example.mengmeng.activity.UserInfoActivity;
import com.example.mengmeng.fragment.PetringAllFragment;
import com.example.mengmeng.pojo.Dynamic;
import com.example.mengmeng.pojo.User;
import com.example.mengmeng.pojo.Zan;
import com.example.mengmeng.widget.NoScrollListView;
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
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/11/2.
 */
public class CommentAdapter extends BaseAdapter {

    private Context context;
    private  List<Dynamic> list;
    private Handler handler;
    private User newUser;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences  sharedPreferences1;
    SharedPreferences.Editor editor1;

    private List<Integer> follow = new ArrayList<Integer>();//关注
    String strContent="";

    public CommentAdapter(Context context, Handler handler, List<Dynamic> list){
        this.context = context;
        this.list = list;
        this.handler = handler;
        sharedPreferences = context.getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sharedPreferences1 = context.getSharedPreferences("dianzan_sp", Context.MODE_PRIVATE);
        editor1 = sharedPreferences1.edit();
    }

    //获取user对象
    public void  getUser(){
        RequestParams requestParams1 = new RequestParams(NetUtil.url + "UserQueryServlet");
        requestParams1.addQueryStringParameter("userId", 1+"");

        x.http().get(requestParams1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<User>() {
                }.getType();
                User newUser1 = gson.fromJson(result, type);
                newUser=newUser1;
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

    //增加点赞
    public void addZan(int a, int b, String c) {
        RequestParams params = new RequestParams(NetUtil.url + "AddZanServlet");

        params.addBodyParameter("dynamicId", String.valueOf(a));
        params.addBodyParameter("userId", String.valueOf(b));
        params.addBodyParameter("zanTime", c);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
    public void removeZan(int a, int b) {
        RequestParams params = new RequestParams(NetUtil.url + "RemoveZanServlet");

        params.addBodyParameter("dynamicId", String.valueOf(a));
        params.addBodyParameter("userId", String.valueOf(b));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
    public void addFollow(int a, int b) {
        RequestParams params = new RequestParams(NetUtil.url + "AddFollowServlet");
        params.addBodyParameter("followUserId", String.valueOf(a));
        params.addBodyParameter("beFollowUserId", String.valueOf(b));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
    public void removeFollow(int a, int b) {
        RequestParams params = new RequestParams(NetUtil.url + "RemoveFollowServlet");
        params.addBodyParameter("followUserId", String.valueOf(a));
        params.addBodyParameter("beFollowUserId", String.valueOf(b));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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

    private SpannableStringBuilder addClickPart(String users) {
        SpannableString spanStr=new SpannableString("i");//任意文字 主要是实现效果
        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 前后都不包括
        Drawable d = context.getResources().getDrawable(R.drawable.zan1);
        d.setBounds(0, 0, 50, 50);
        spanStr.setSpan(new ImageSpan(d),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //创建一个ssb 存储总的用户
        SpannableStringBuilder ssb=new SpannableStringBuilder(spanStr);
        ssb.append(users);

        //为每段数据创建点击 事件
        String[] users_array=users.split("、");
        if(users_array.length>0){
            for (int i = 0; i < users_array.length; i++) {
                final String user_name = users_array[i];//好友0
                int start=users.indexOf(user_name)+spanStr.length();

                //为每段数据增加点击事件
                ssb.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Intent intent = new Intent(context, UserInfoActivity.class);
                        intent.putExtra("userName",user_name);
                        context.startActivity(intent);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.BLUE);
                        ds.setUnderlineText(false);
                    }
                },start,start+user_name.length(),0);
            }
        }

        return ssb.append("等"+users_array.length+"人觉得很赞");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_dynamic,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.imgHead = (ImageView) convertView
                    .findViewById(R.id.im_dynamic_head);
            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.tv_dynamic_name);
            viewHolder.tvDate = (TextView) convertView
                    .findViewById(R.id.tv_dynamic_time);
            viewHolder.ibConcern = (ImageButton) convertView
                    .findViewById(R.id.ib_dynamic_concern);
            viewHolder.tvContent = (TextView) convertView
                    .findViewById(R.id.tv_dynamic_content);
            viewHolder.ivPhoto = (ImageView) convertView
                    .findViewById(R.id.iv_dynamic_imag);
            viewHolder.ivAddress = (ImageView) convertView
                    .findViewById(R.id.iv_dynamic_place);
            viewHolder.tvAddress = (TextView) convertView
                    .findViewById(R.id.tv_dynamic_place);
            viewHolder.ibZan = (ImageButton) convertView
                    .findViewById(R.id.im_zan);
            viewHolder.ibPinglun = (ImageButton) convertView
                    .findViewById(R.id.im_pinglun);
            viewHolder.ibFenxiang = (ImageButton) convertView
                    .findViewById(R.id.im_fenxiang);
            viewHolder.tvZan = (TextView) convertView
                    .findViewById(R.id.tv_zan);
            viewHolder.lv_remarks = (NoScrollListView) convertView
                    .findViewById(R.id.lv_remarks);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Dynamic dynamic = list.get(position);

        //头像
        xUtilsImageUtils.display(viewHolder.imgHead,NetUtil.photo_url + dynamic.getUser().getUserPhoto(),true);
        viewHolder.imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra("userId", dynamic.getUser().getUserId() + "");
                context.startActivity(intent);
            }
        });

        viewHolder.tvName.setText(dynamic.getUser().getUserName());
        viewHolder.tvDate.setText(dynamic.getReleaseTime()+"");
        viewHolder.tvContent.setText(dynamic.getReleaseText());
        strContent=dynamic.getReleaseText();
        viewHolder.tvAddress.setText(dynamic.getPlace());
        //图片
        x.image().bind(viewHolder.ivPhoto, NetUtil.picture_url + dynamic.getPicture());
        final String str=NetUtil.picture_url + dynamic.getPicture();
        viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PictureActivity.class);
                intent.putExtra("picture",str);
                context.startActivity(intent);
            }
        });


        final TextView tvZan=viewHolder.tvZan;
        if (dynamic.zan.size()>0&&dynamic.zan!=null){
            System.out.println("dynamic.zan.size():"+dynamic.zan.size());
            StringBuilder sb=new StringBuilder();
            tvZan.setVisibility(View.VISIBLE);
            for (Zan zan1:dynamic.zan) {
                sb.append(zan1.user.getUserName()+"、");
            }
            String users=sb.substring(0,sb.lastIndexOf("、"));
            tvZan.setMovementMethod(LinkMovementMethod.getInstance());
            tvZan.setText(addClickPart(users), TextView.BufferType.SPANNABLE);
        }else{
            tvZan.setVisibility(View.GONE);
        }
        //点赞事件
        final ImageButton imZan = viewHolder.ibZan;
        imZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferences1.contains(dynamic.dynamicId+"")){//选中状态
                    imZan.setImageResource(R.drawable.zan);
                    int user_Id = 1;    //点赞人Id
                    int dynamic_Id = dynamic.getDynamicId();
                    removeZan(dynamic_Id, user_Id);
                    Toast.makeText(context,"取消点赞",Toast.LENGTH_SHORT).show();
                    editor1.remove(dynamic.dynamicId+"");

                    String zanTime = String.valueOf(System.currentTimeMillis());
                    Timestamp zanTime1=new Timestamp(Long.parseLong(zanTime));
                    Zan zan1=new Zan(newUser,user_Id,dynamic_Id,zanTime1);
                    dynamic.zan.remove(zan1);
//                    dynamicsAdapter.notifyDataSetChanged();

                }else{
                    tvZan.setVisibility(View.VISIBLE);
                    imZan.setImageResource(R.drawable.zan1);
                    //增加动画效果
                    imZan.startAnimation(AnimationUtils.loadAnimation(context,R.anim.shake));

                    int user_Id = 1 ; //点赞人Id
                    int dynamic_Id = dynamic.getDynamicId();                                                  //动态Id
                    String zanTime = String.valueOf(System.currentTimeMillis());                             //点赞时间
                    addZan(dynamic_Id, user_Id, zanTime);
                    Toast.makeText(context,"点赞成功",Toast.LENGTH_SHORT).show();
                    editor1.putInt(dynamic.dynamicId+"",(Integer)imZan.getTag());

                    tvZan.setText("萌萌");
                    Timestamp zanTime1=new Timestamp(Long.parseLong(zanTime));
                    Zan zan=new Zan(newUser,user_Id,dynamic_Id,zanTime1);
                    dynamic.zan.add(zan);
//                    dynamicsAdapter.notifyDataSetChanged();
                }
                editor1.commit();
            }
        });
        imZan.setTag(dynamic.getDynamicId());
        if(sharedPreferences1.contains(position+"")){
            imZan.setImageResource(R.drawable.zan1);
        }else{
            imZan.setImageResource(R.drawable.zan );
        }

//        设置关注点击事件
        final ImageButton imConcern =  viewHolder.ibConcern;
        imConcern.setTag(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String guanzhu = sharedPreferences.getString("guanzhu", "");
        String[] ids = guanzhu.split("&");

        int userId = dynamic.getUser().getUserId();
        boolean flag111 = false;
        for (String id : ids) {
            if (id.equals(userId + "")) {
                flag111 = true;
                break;
            }
        }
        System.out.println("flag111:" + flag111);
        if (flag111) {
            imConcern.setImageResource(R.drawable.already_concern);
        } else {
            imConcern.setImageResource(R.drawable.add_concern);
        }


        imConcern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String guanzhu = sharedPreferences.getString("guanzhu", "-1&");
                String[] ids = guanzhu.split("&");
                boolean f = false;

                int userId = dynamic.getUser().getUserId();
                for (String id : ids) {
                    if(id.equals(userId+"")){
                        f = true;
                        break;
                    }
                }

                if (f) {
                    ((ImageView) v).setImageResource(R.drawable.add_concern);
                    follow.remove((Integer) (((ImageView) v).getTag()));

                    int followUserId = 1;    //点赞人Id
                    int beFollowUserId = dynamic.getUser().getUserId();
                    removeFollow(followUserId, beFollowUserId);

                    String s = "";
                    for (String id : ids) {
                        if (!id.equals(userId + "")) {
                            s += id + "&";
                        }
                    }
                    editor.putString("guanzhu", s.substring(0, s.length() - 1));

                } else {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.already_concern);
                    ((ImageView) v).setImageBitmap(bitmap);
                    follow.add((Integer) (((ImageView) v).getTag()));

                    int followUserId = 1;    //点赞人Id
                    int beFollowUserId = dynamic.getUser().getUserId();
                    addFollow(followUserId, beFollowUserId);
                    String s = "";
                    for (String id : ids) {
                        if (!id.equals(userId + "")) {
                            s += id + "&";
                        }
                    }
                    s = s + userId;


                    editor.putString("guanzhu", s.substring(0, s.length()));
                }
                editor.commit();
            }
        });

        viewHolder.ibFenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });




        viewHolder.ibPinglun
                .setOnClickListener(new ListViewButtonOnClickListener(position));
        viewHolder.ibPinglun.setFocusable(false);

        if (dynamic!=null&&dynamic.remarklist!=null){
        if(dynamic.remarklist.size()>0){
            viewHolder.lv_remarks.setVisibility(View.VISIBLE);
            ReplayAdapter replayAdapter = new ReplayAdapter(context,handler,dynamic.remarklist,position);
            viewHolder.lv_remarks.setAdapter(replayAdapter);
        }
        }else{
            viewHolder.lv_remarks.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView imgHead;
        TextView tvName;
        TextView tvDate;
        TextView tvContent;
        ImageView ivPhoto;
        ImageView ivAddress;
        TextView tvAddress;
        ImageButton ibConcern;
        ImageButton ibZan;
        ImageButton ibPinglun;
        ImageButton ibFenxiang;
        TextView tvZan;
        NoScrollListView lv_remarks;
    }

    private class ListViewButtonOnClickListener implements View.OnClickListener{
        private int position;
        public ListViewButtonOnClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.im_pinglun:
                    handler.sendMessage(handler.obtainMessage(10,position));
                    break;
            }

        }
    }
//
//    public void add(Dynamic dynamic){
//        this.list.add(dynamic);
//        this.notifyDataSetChanged();
//    }

    //分享
    private void showShare() {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("来自萌萌的分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(strContent);
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
        oks.show(context);
    }

}