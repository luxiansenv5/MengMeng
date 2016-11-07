package com.example.mengmeng.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.activity.PictureActivity;
import com.example.mengmeng.activity.R;
import com.example.mengmeng.activity.UserInfoActivity;
import com.example.mengmeng.pojo.Dynamic;
import com.example.mengmeng.pojo.User;
import com.example.mengmeng.pojo.Zan;
import com.example.mengmeng.utils.CommonAdapter;
import com.example.mengmeng.utils.NetUtil;
import com.example.mengmeng.utils.RefreshListView;
import com.example.mengmeng.utils.ViewHolder;
import com.example.mengmeng.utils.xUtilsImageUtils;
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
public class PetringAllFragment extends BaseFragment implements RefreshListView.OnRefreshUploadChangeListener {

    CommonAdapter<Dynamic> dynamicsAdapter;
    List<Dynamic> dynamics = new ArrayList<>();//存放动态信息

    @InjectView(R.id.lv_dynamics)
    RefreshListView lvDynamics;

    private List<Integer> choice = new ArrayList<Integer>();//点赞
    private List<Integer> follow = new ArrayList<Integer>();//关注

    private List<String> lists;//点赞人集合


    int pageNo = 1;
    int pageSize = 5;

    Handler handler = new Handler();

    private RadioButton rbFriends;
    private ViewPager dynamic_vp;

    private  boolean flag11=true;
    SharedPreferences  sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences  sharedPreferences1;
    SharedPreferences.Editor editor1;
    User newUser;

    String str="";
    String  strContent="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dynamic_all, null);
        ButterKnife.inject(this, v);
        sharedPreferences = PetringAllFragment.this.getActivity().getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sharedPreferences1 = PetringAllFragment.this.getActivity().getSharedPreferences("dianzan_sp", Context.MODE_PRIVATE);
        editor1 = sharedPreferences1.edit();
        getUser();
        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
//        lvDynamics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (RefreshListView.isTag() == false && position != 0 && position <= dynamics.size()) {
//                    Intent intent = new Intent(getActivity(), DynamicInfoActivity.class);
//                    intent.putExtra("dynamicInfo", dynamics.get(position - 1));
//                    startActivityForResult(intent, 1);
//                }
//            }
//        });
        lvDynamics.setOnRefreshUploadChangeListener(this);
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
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Dynamic>>() {
                }.getType();
                List<Dynamic> newDynamic = new ArrayList<Dynamic>();
                newDynamic = gson.fromJson(result, type);//解析成List<Dynamic>
                if (flag11) {
                    dynamics.clear();// 清空原来数据
                } else {
                    if (newDynamic.size() == 0) {//服务器没有返回新的数据
                        pageNo--; //下一次继续加载这一页
                        Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                        lvDynamics.completeLoad();//没获取到数据也要改变界面
                        return;
                    }
                }
                dynamics.addAll(newDynamic);
                //设置listview的adpter
                if (dynamicsAdapter == null) {
                    dynamicsAdapter = new CommonAdapter<Dynamic>(getActivity(), dynamics, R.layout.layout_dynamic) {
                        @Override
                        public void convert(ViewHolder viewHolder, final Dynamic dynamic, final int position) {
                            boolean flag1 = false;
                            //取件赋值
                            ImageView ivHead = viewHolder.getViewById(R.id.im_dynamic_head);
                            xUtilsImageUtils.display(ivHead,NetUtil.photo_url + dynamic.getUser().getUserPhoto(),true);

                            final TextView tvName = viewHolder.getViewById(R.id.tv_dynamic_name);
                            tvName.setText(dynamic.getUser().getUserName());

                            TextView tvTime = viewHolder.getViewById(R.id.tv_dynamic_time);
                            tvTime.setText(dynamic.getReleaseTime() + "");


                            TextView tvContent = viewHolder.getViewById(R.id.tv_dynamic_content);
                            tvContent.setText(dynamic.getReleaseText());
                            strContent=dynamic.getReleaseText();

                            ImageView ivImag = viewHolder.getViewById(R.id.iv_dynamic_imag);
                            x.image().bind(ivImag, NetUtil.picture_url + dynamic.getPicture());
                            final String str=NetUtil.picture_url + dynamic.getPicture();
                            ivImag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getActivity(), PictureActivity.class);
                                    intent.putExtra("picture",str);
                                    startActivity(intent);
                                }
                            });


                            TextView tvPlace = viewHolder.getViewById(R.id.tv_dynamic_place);
                            tvPlace.setText(dynamic.getPlace());

                            final TextView tvZan=viewHolder.getViewById(R.id.tv_zan);

                            String zan3="";
                            if (dynamic.zan.size()>0&&dynamic.zan!=null){
                                System.out.println("dynamic.zan.size():"+dynamic.zan.size());
                                String zan2="";
                                tvZan.setVisibility(View.VISIBLE);
                                for (Zan zan1:dynamic.zan) {
                                    zan2=zan1.getUser().getUserName();
                                    zan2+="、";
                                }
                                zan2=zan2.substring(0,zan2.length()-1);
                                zan2="我 "+zan2;
                                SpannableString ss = new SpannableString(zan2);
                                Drawable d=getResources().getDrawable(R.drawable.zan);
                                d.setBounds(0,0,30,30);
                                ss.setSpan(new ImageSpan(d),0,1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                ss.setSpan(new ForegroundColorSpan(Color.BLUE),2,zan2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                zan3=zan2;
                                tvZan.setText(ss);
                            }else{
                                tvZan.setVisibility(View.GONE);
                            }



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
                            final ImageButton imZan = viewHolder.getViewById(R.id.im_zan);
//
                            final String finalZan = zan3;
                            imZan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(sharedPreferences1.contains(position+"")){//选中状态
                                        imZan.setImageResource(R.drawable.zan);
                                        String zan5=finalZan;
                                        System.out.println("zan5:"+zan5);
                                        zan5=zan5.substring(4,zan5.length());
                                        if (zan5.length()>0){
                                            SpannableString ss = new SpannableString(zan5);
                                            Drawable d=getResources().getDrawable(R.drawable.zan);
                                            d.setBounds(0,0,30,30);
                                            ss.setSpan(new ImageSpan(d),0,1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                            ss.setSpan(new ForegroundColorSpan(Color.BLUE),2,zan5.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            tvZan.setText(ss);
                                        }else {
                                            PetringAllFragment.this.getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    tvZan.setText("");
                                                }
                                            });

                                        }

                                        int user_Id = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
                                        int dynamic_Id = dynamic.getDynamicId();
                                        removeZan(dynamic_Id, user_Id);
                                        Toast.makeText(getActivity(),"取消点赞",Toast.LENGTH_SHORT).show();
                                        editor1.remove(position+"");

                                        String zanTime = String.valueOf(System.currentTimeMillis());
                                        Timestamp zanTime1=new Timestamp(Long.parseLong(zanTime));
                                        Zan zan1=new Zan(newUser,user_Id,dynamic_Id,zanTime1);
                                        dynamic.zan.remove(zan1);
                                        dynamicsAdapter.notifyDataSetChanged();

                                    }else{
                                        tvZan.setVisibility(View.VISIBLE);
                                        imZan.setImageResource(R.drawable.zan1);
                                        //增加动画效果
                                        imZan.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.shake));

                                        String zan4="我"+"萌萌"+finalZan;
                                        System.out.println("zan4:"+zan4);
                                        SpannableString ss = new SpannableString(zan4);
                                        Drawable d=getResources().getDrawable(R.drawable.zan);
                                        d.setBounds(0,0,30,30);
                                        ss.setSpan(new ImageSpan(d),0,1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                        ss.setSpan(new ForegroundColorSpan(Color.BLUE),2,zan4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        tvZan.setText(ss);

                                        int user_Id = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
                                        int dynamic_Id = dynamic.getDynamicId();                                                  //动态Id
                                        String zanTime = String.valueOf(System.currentTimeMillis());                             //点赞时间
                                        addZan(dynamic_Id, user_Id, zanTime);
                                        Toast.makeText(getActivity(),"点赞成功",Toast.LENGTH_SHORT).show();
                                        editor1.putInt(position+"",(Integer)imZan.getTag());

                                        Timestamp zanTime1=new Timestamp(Long.parseLong(zanTime));
                                        Zan zan=new Zan(newUser,user_Id,dynamic_Id,zanTime1);

                                        dynamic.zan.add(zan);
                                        dynamicsAdapter.notifyDataSetChanged();
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


//                            设置关注点击事件
                            final ImageButton imConcern = viewHolder.getViewById(R.id.ib_dynamic_concern);
                            imConcern.setTag(position);
                            SharedPreferences sharedPreferences = PetringAllFragment.this.getActivity().getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
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
                                    SharedPreferences sharedPreferences = PetringAllFragment.this.getActivity().getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
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

                                        int followUserId = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
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
                                        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.already_concern);
                                        ((ImageView) v).setImageBitmap(bitmap);
                                        follow.add((Integer) (((ImageView) v).getTag()));

                                        int followUserId = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
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

                            //点击评论按钮事件
                            ImageButton imPinlun = viewHolder.getViewById(R.id.im_pinglun);
                            final EditText editText=viewHolder.getViewById(R.id.et_pinglun);
                            imPinlun.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    editText.requestFocus();
                                    InputMethodManager imm= (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
                                }
                            });

                            //评论列表

                        }
                    };
                    lvDynamics.setAdapter(dynamicsAdapter);
                } else {
                        dynamicsAdapter.notifyDataSetChanged();
                   }
                if (!flag11){
                    lvDynamics.completeLoad();//获取完数据后在改变界面
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "无法获取网络数据，请检查网络连接", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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

    //分享
    private void showShare() {
        ShareSDK.initSDK(getActivity());
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
        oks.setImageUrl(str);
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


    //下拉刷新
    @Override
    public void onRefresh() {
        pageNo = 1; //每次刷新，让pageNo变成初始值1
        //1秒后发一个消息
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag11 = true;
                getData();  //再次获取数据
                lvDynamics.completeRefresh();  //刷新数据后，改变页面为初始页面：隐藏头部
                Toast.makeText(getActivity(), "已是最新数据", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }


    //上拉加载
    @Override
    public void onPull() {
        pageNo++;
        //原来数据基础上增加
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag11 = false;
                getData();
            }
        }, 1000);
    }
}
