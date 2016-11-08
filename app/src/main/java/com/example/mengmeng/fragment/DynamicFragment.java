package com.example.mengmeng.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.Dynamic;
import com.example.mengmeng.pojo.Remark;
import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.CommentAdapter;
import com.example.mengmeng.utils.CommonAdapter;
import com.example.mengmeng.utils.NetUtil;
import com.example.mengmeng.utils.RefreshListView;
import com.example.mengmeng.widget.NoTouchLinearLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 陆猛 on 2016/11/4.
 */
public class DynamicFragment extends BaseFragment implements RefreshListView.OnRefreshUploadChangeListener {

//    CommonAdapter<Dynamic> dynamicsAdapter;
    List<Dynamic> dynamics = new ArrayList<>();//存放动态信息
    List<Dynamic> newDynamic ;

//    @InjectView(R.id.lv_dynamics)
//    RefreshListView lvDynamics;

    RefreshListView lvDynamics;
    private List<Integer> choice = new ArrayList<Integer>();//点赞
    private List<Integer> follow = new ArrayList<Integer>();//关注

    int pageNo = 1;
    int pageSize = 5;

//    Handler handler = new Handler();

    private RadioButton rbFriends;
    private ViewPager dynamic_vp;

    private  boolean flag11=true;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences  sharedPreferences1;
    SharedPreferences.Editor editor1;


    private CommentAdapter dynamicsAdapter;

    private boolean isReply = false;
    private int commentPosition = -1;
    private int replayPosition = -1;

    private EditText mCommentEdittext;//评论输入框
    private NoTouchLinearLayout editVgLyt;
    private Remark r1;
    private  Remark r2;
    private Button mSendBut;//评论按钮
    private EditText edit_comment;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 10:
                    Toast.makeText(getActivity(),msg.obj+"评论信息",Toast.LENGTH_LONG).show();
                    isReply = false;//评论信息
                    editVgLyt.setVisibility(View.VISIBLE);
                    onFocusChange(true);
                    commentPosition= (int)msg.obj;

                    break;
                case 11:
                    Toast.makeText(getActivity(),msg.obj+"回复信息",Toast.LENGTH_LONG).show();
                    isReply = true;//回复信息
                    editVgLyt.setVisibility(View.VISIBLE);
                    onFocusChange(true);
                    String[] attrs = ((String)msg.obj).split(" ");// dongtai   huifu  0 2
                    commentPosition = Integer.parseInt(attrs[0]);
                    replayPosition = Integer.parseInt(attrs[1]);
                    break;
            }

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dynamic_all, null);
        lvDynamics= (RefreshListView)v.findViewById(R.id.lv_dynamics);
        sharedPreferences = getActivity().getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sharedPreferences1 = getActivity().getSharedPreferences("dianzan_sp", Context.MODE_PRIVATE);
        editor1 = sharedPreferences1.edit();

        editVgLyt = ((NoTouchLinearLayout) v.findViewById(R.id.edit_vg_lyt));
        mSendBut = (Button) v.findViewById(R.id.but_comment_send);
        ClickListener cl = new ClickListener();
        mSendBut.setOnClickListener(cl);

        //处理返回键
        editVgLyt.setOnResizeListener(new NoTouchLinearLayout.OnResizeListener() {
            @Override
            public void OnResize() {
                editVgLyt.setVisibility(View.GONE);//输入框消息
                onFocusChange(false);//软键盘消息
            }
        });
        mCommentEdittext = (EditText) v.findViewById(R.id.edit_comment);
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
        RequestParams requestParams = new RequestParams(NetUtil.url + "QueryDynamicByUserIdServlet");
        requestParams.addQueryStringParameter("userId",1+"");
        requestParams.addQueryStringParameter("pageNo", pageNo + "");
        requestParams.addQueryStringParameter("pageSize", pageSize + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Dynamic>>() {
                }.getType();
                newDynamic = new ArrayList<Dynamic>();
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
                    dynamicsAdapter = new CommentAdapter(getActivity(),handler,dynamics) {
//                        @Override
//                        public void convert(ViewHolder viewHolder, final Dynamic dynamic, final int position) {
//
//                            //取件赋值
//                            ImageView ivHead = viewHolder.getViewById(R.id.im_dynamic_head);
//                            xUtilsImageUtils.display(ivHead,NetUtil.photo_url + dynamic.getUser().getUserPhoto(),true);
//
//                            final TextView tvName = viewHolder.getViewById(R.id.tv_dynamic_name);
//                            tvName.setText(dynamic.getUser().getUserName());
//
//                            TextView tvTime = viewHolder.getViewById(R.id.tv_dynamic_time);
//                            tvTime.setText(dynamic.getReleaseTime() + "");
//
//
//                            TextView tvContent = viewHolder.getViewById(R.id.tv_dynamic_content);
//                            tvContent.setText(dynamic.getReleaseText());
//                            strContent=dynamic.getReleaseText();
//
//                            ImageView ivImag = viewHolder.getViewById(R.id.iv_dynamic_imag);
//                            x.image().bind(ivImag, NetUtil.picture_url + dynamic.getPicture());
//                            final String str=NetUtil.picture_url + dynamic.getPicture();
//                            ivImag.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent=new Intent(getActivity(), PictureActivity.class);
//                                    intent.putExtra("picture",str);
//                                    startActivity(intent);
//                                }
//                            });
//
//                            TextView tvPlace = viewHolder.getViewById(R.id.tv_dynamic_place);
//                            tvPlace.setText(dynamic.getPlace());
//
//                            final TextView tvZan=viewHolder.getViewById(R.id.tv_zan);
//                            if (dynamic.zan.size()>0&&dynamic.zan!=null){
//                                System.out.println("dynamic.zan.size():"+dynamic.zan.size());
//                                StringBuilder sb=new StringBuilder();
//                                tvZan.setVisibility(View.VISIBLE);
//                                for (Zan zan1:dynamic.zan) {
//                                    sb.append(zan1.user.getUserName()+"、");
//                                }
//                                String users=sb.substring(0,sb.lastIndexOf("、"));
//                                tvZan.setMovementMethod(LinkMovementMethod.getInstance());
//                                tvZan.setText(addClickPart(users), TextView.BufferType.SPANNABLE);
//                            }else{
//                                tvZan.setVisibility(View.GONE);
//                            }
//
//
//
//                            //设置头像点击事件
//                            ivHead.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
//                                    intent.putExtra("userId", dynamic.getUser().getUserId() + "");
//                                    startActivity(intent);
//                                }
//                            });
//
//                            //设置分享点击事件
//                            viewHolder.getViewById(R.id.im_fenxiang).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    showShare();
//                                }
//                            });
//
//                            //设置点赞点击事件
//                            final ImageButton imZan = viewHolder.getViewById(R.id.im_zan);
////
//                            imZan.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if(sharedPreferences1.contains(position+"")){//选中状态
//                                        imZan.setImageResource(R.drawable.zan);
//                                        int user_Id = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
//                                        int dynamic_Id = dynamic.getDynamicId();
//                                        removeZan(dynamic_Id, user_Id);
//                                        Toast.makeText(getActivity(),"取消点赞",Toast.LENGTH_SHORT).show();
//                                        editor1.remove(position+"");
//
//                                        String zanTime = String.valueOf(System.currentTimeMillis());
//                                        Timestamp zanTime1=new Timestamp(Long.parseLong(zanTime));
//                                        Zan zan1=new Zan(newUser,user_Id,dynamic_Id,zanTime1);
//                                        dynamic.zan.remove(zan1);
//                                        dynamicsAdapter.notifyDataSetChanged();
//
//                                    }else{
//                                        tvZan.setVisibility(View.VISIBLE);
//                                        imZan.setImageResource(R.drawable.zan1);
//                                        //增加动画效果
//                                        imZan.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.shake));
//
//                                        int user_Id = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
//                                        int dynamic_Id = dynamic.getDynamicId();                                                  //动态Id
//                                        String zanTime = String.valueOf(System.currentTimeMillis());                             //点赞时间
//                                        addZan(dynamic_Id, user_Id, zanTime);
//                                        Toast.makeText(getActivity(),"点赞成功",Toast.LENGTH_SHORT).show();
//                                        editor1.putInt(position+"",(Integer)imZan.getTag());
//
//                                        tvZan.setText("萌萌");
//                                        Timestamp zanTime1=new Timestamp(Long.parseLong(zanTime));
//                                        Zan zan=new Zan(newUser,user_Id,dynamic_Id,zanTime1);
//                                        dynamic.zan.add(zan);
//                                        dynamicsAdapter.notifyDataSetChanged();
//                                    }
//                                    editor1.commit();
//                                }
//                            });
//                            imZan.setTag(dynamic.getDynamicId());
//                            if(sharedPreferences1.contains(position+"")){
//                                imZan.setImageResource(R.drawable.zan1);
//                            }else{
//                                imZan.setImageResource(R.drawable.zan );
//                            }
//
//
////                            设置关注点击事件
//                            final ImageButton imConcern = viewHolder.getViewById(R.id.ib_dynamic_concern);
//                            imConcern.setTag(position);
//                            SharedPreferences sharedPreferences = PetringAllFragment.this.getActivity().getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            String guanzhu = sharedPreferences.getString("guanzhu", "");
//                            String[] ids = guanzhu.split("&");
//
//                            int userId = dynamic.getUser().getUserId();
//                            boolean flag111 = false;
//                            for (String id : ids) {
//                                if (id.equals(userId + "")) {
//                                    flag111 = true;
//                                    break;
//                                }
//                            }
//                            System.out.println("flag111:" + flag111);
//                            if (flag111) {
//                                imConcern.setImageResource(R.drawable.already_concern);
//                            } else {
//                                imConcern.setImageResource(R.drawable.add_concern);
//                            }
//
//
//                            imConcern.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    SharedPreferences sharedPreferences = PetringAllFragment.this.getActivity().getSharedPreferences("guanzhu_sp", Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    String guanzhu = sharedPreferences.getString("guanzhu", "-1&");
//                                    String[] ids = guanzhu.split("&");
//                                    boolean f = false;
//
//                                    int userId = dynamic.getUser().getUserId();
//                                    for (String id : ids) {
//                                        if(id.equals(userId+"")){
//                                            f = true;
//                                            break;
//                                        }
//                                    }
//
//                                    if (f) {
//                                        ((ImageView) v).setImageResource(R.drawable.add_concern);
//                                        follow.remove((Integer) (((ImageView) v).getTag()));
//
//                                        int followUserId = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
//                                        int beFollowUserId = dynamic.getUser().getUserId();
//                                        removeFollow(followUserId, beFollowUserId);
//
//                                        String s = "";
//                                        for (String id : ids) {
//                                            if (!id.equals(userId + "")) {
//                                                s += id + "&";
//                                            }
//                                        }
//                                        editor.putString("guanzhu", s.substring(0, s.length() - 1));
//
//                                    } else {
//                                        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.already_concern);
//                                        ((ImageView) v).setImageBitmap(bitmap);
//                                        follow.add((Integer) (((ImageView) v).getTag()));
//
//                                        int followUserId = ((MyApplication) getActivity().getApplication()).getUser().getUserId();    //点赞人Id
//                                        int beFollowUserId = dynamic.getUser().getUserId();
//                                        addFollow(followUserId, beFollowUserId);
//                                        String s = "";
//                                        for (String id : ids) {
//                                            if (!id.equals(userId + "")) {
//                                                s += id + "&";
//                                            }
//                                        }
//                                        s = s + userId;
//
//
//                                        editor.putString("guanzhu", s.substring(0, s.length()));
//                                    }
//                                    editor.commit();
//                                }
//                            });
//                        }

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

//    //获取user对象
//    public void  getUser(){
//        RequestParams requestParams1 = new RequestParams(NetUtil.url + "UserQueryServlet");
//        requestParams1.addQueryStringParameter("userId", 1+"");
//
//        x.http().get(requestParams1, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Gson gson = new Gson();
//                Type type = new TypeToken<User>() {
//                }.getType();
//                 User newUser1 = gson.fromJson(result, type);
//                newUser=newUser1;
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
//
//    //增加点赞
//    public void addZan(int a, int b, String c) {
//        RequestParams params = new RequestParams(NetUtil.url + "AddZanServlet");
//
//        params.addBodyParameter("dynamicId", String.valueOf(a));
//        params.addBodyParameter("userId", String.valueOf(b));
//        params.addBodyParameter("zanTime", c);
//
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//
//    }
//
//    //消除点赞
//    public void removeZan(int a, int b) {
//        RequestParams params = new RequestParams(NetUtil.url + "RemoveZanServlet");
//
//        params.addBodyParameter("dynamicId", String.valueOf(a));
//        params.addBodyParameter("userId", String.valueOf(b));
//
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
//
//    //加关注
//    public void addFollow(int a, int b) {
//        RequestParams params = new RequestParams(NetUtil.url + "AddFollowServlet");
//        params.addBodyParameter("followUserId", String.valueOf(a));
//        params.addBodyParameter("beFollowUserId", String.valueOf(b));
//
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
//
//    //取消关注
//    public void removeFollow(int a, int b) {
//        RequestParams params = new RequestParams(NetUtil.url + "RemoveFollowServlet");
//        params.addBodyParameter("followUserId", String.valueOf(a));
//        params.addBodyParameter("beFollowUserId", String.valueOf(b));
//
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
//
//    //分享
//    private void showShare() {
//        ShareSDK.initSDK(getActivity());
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("来自萌萌的分享");
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(strContent);
//        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl(str);
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("ShareSDK");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//
//        // 启动分享GUI
//        oks.show(getActivity());
//    }

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

    private final class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.but_comment_send:
                    if (isReply) {
                        replyComment();
                    } else {
                        publishComment();
                    }
                    editVgLyt.setVisibility(View.GONE);
                    onFocusChange(false);
                    break;
            }
        }
    }

    public void replyComment(){

        User fatheruser= dynamics.get(commentPosition).remarklist.get(replayPosition-1).user;
        User user=new User(1,"萌萌");

        String remarkContent= mCommentEdittext.getText().toString().trim();
        long remarkTime = System.currentTimeMillis();
        Timestamp timestamp=new Timestamp(remarkTime);
        r2= new Remark(user,remarkContent,timestamp,fatheruser);

        newDynamic.get(commentPosition).remarklist.add(replayPosition,r2);
        dynamicsAdapter.notifyDataSetChanged();

        //插入到数据库
        RequestParams params = new RequestParams(NetUtil.url + "AddReplyCommentServlet");

        params.addBodyParameter("dynamicId",dynamics.get(commentPosition).dynamicId+"");
        params.addBodyParameter("userId",1+"");
        Long time=System.currentTimeMillis();
        params.addBodyParameter("remarkTime",time+"");
        params.addBodyParameter("fatherUserId",fatheruser.getUserId()+"");
        try {
            params.addBodyParameter("remarkContent", URLEncoder.encode(remarkContent,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void publishComment(){
        User fatheruser= dynamics.get(commentPosition).getUser();
        User user=new User(1,"萌萌");
        String remarkContent= mCommentEdittext.getText().toString().trim();
        long remarkTime = System.currentTimeMillis();
        Timestamp timestamp=new Timestamp(remarkTime);
        r1= new Remark(user,timestamp,remarkContent);
        newDynamic.get(commentPosition).remarklist.add(r1);
        dynamicsAdapter.notifyDataSetChanged();

        //插入到数据库
        insertPublishComment();
    }

    public void insertPublishComment() {
        String remarkContent= mCommentEdittext.getText().toString().trim();
        RequestParams params = new RequestParams(NetUtil.url + "AddPublishCommentServlet");

        params.addBodyParameter("dynamicId",dynamics.get(commentPosition).dynamicId+"");
        params.addBodyParameter("userId",1+"");
        Long time=System.currentTimeMillis();
        params.addBodyParameter("remarkTime",time+"");
        try {
            params.addBodyParameter("remarkContent", URLEncoder.encode(remarkContent,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

//

    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        mCommentEdittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    mCommentEdittext.requestFocus();//获取焦点
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(mCommentEdittext.getWindowToken(), 0);
                    editVgLyt.setVisibility(View.GONE);
                }
            }
        }, 100);
    }



    /**
     * 点击屏幕其他地方收起输入法
     */

    public boolean  dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getActivity().getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                onFocusChange(false);

            }
            return super.getActivity().dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getActivity().getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return getActivity().onTouchEvent(ev);
    }

    /**
     * 隐藏或者显示输入框
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            /**
             *这堆数值是算我的下边输入区域的布局的，
             * 规避点击输入区域也会隐藏输入区域
             */

            v.getLocationInWindow(leftTop);
            int left = leftTop[0] - 50;
            int top = leftTop[1] - 50;
            int bottom = top + v.getHeight() + 300;
            int right = left + v.getWidth() + 120;
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

//    private SpannableStringBuilder addClickPart(String users) {
//        SpannableString spanStr=new SpannableString("i");//任意文字 主要是实现效果
//        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 前后都不包括
//        Drawable d = getResources().getDrawable(R.drawable.zan1);
//        d.setBounds(0, 0, 50, 50);
//        spanStr.setSpan(new  ImageSpan(d),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        //创建一个ssb 存储总的用户
//        SpannableStringBuilder ssb=new SpannableStringBuilder(spanStr);
//        ssb.append(users);
//
//        //为每段数据创建点击 事件
//        String[] users_array=users.split("、");
//        if(users_array.length>0){
//            for (int i = 0; i < users_array.length; i++) {
//                final String user_name = users_array[i];//好友0
//                int start=users.indexOf(user_name)+spanStr.length();
//
//                //为每段数据增加点击事件
//                ssb.setSpan(new ClickableSpan() {
//                    @Override
//                    public void onClick(View widget) {
//                        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
//                        intent.putExtra("userName",user_name);
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void updateDrawState(TextPaint ds) {
//                        super.updateDrawState(ds);
//                        ds.setColor(Color.BLUE);
//                        ds.setUnderlineText(false);
//                    }
//                },start,start+user_name.length(),0);
//            }
//        }
//
//        return ssb.append("等"+users_array.length+"人觉得很赞");
//    }
}
