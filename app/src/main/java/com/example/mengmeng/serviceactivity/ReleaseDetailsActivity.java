package com.example.mengmeng.serviceactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.DetailsBean;
import com.example.mengmeng.pojo.GetAdoptBean;
import com.example.mengmeng.pojo.SingleComment;
import com.example.mengmeng.utils.SingleCommentAdapter;
import com.example.mengmeng.utils.HttpUtils;
import com.example.mengmeng.utils.viewpageAdapter;
import com.example.mengmeng.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import application.MyApplication;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ReleaseDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_petImage;
    private TextView tv_ApetName;
    private TextView tv_ApetType;
    private TextView tv_realDesc;
    private TextView tv_publisherName;
    private ImageView iv_publisherPhoto;
    private GetAdoptBean petInfo;
    private Integer userId;
    private List<DetailsBean> detailList;
    private ImageView iv_ApetPhoto;
    private Integer petId;
    private ImageView share;
    private Integer flag;

    private ViewPager list_pager;
    private List<View> list_view;
    private int currentItem;
    private viewpageAdapter adpter;
    private ImageView mxback;

    private ImageView comment;
    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;

    private LinearLayout rl_enroll;
    private RelativeLayout rl_comment;

    private SingleCommentAdapter singleCommentAdapter;
    private List<ListView> listViews;
    private List<SingleCommentAdapter> singleCommentAdapterList = new ArrayList<SingleCommentAdapter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_details);

        initView();

        initData();

        initEvent();

    }

    public void initView(){

        list_pager = (ViewPager)findViewById(R.id.list_pager);
        list_view = new ArrayList<>();

        share = ((ImageView) findViewById(R.id.share));
        mxback = ((ImageView) findViewById(R.id.mxback));

        comment = (ImageView) findViewById(R.id.comment);
        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_send = (Button) findViewById(R.id.comment_send);

        rl_enroll = (LinearLayout) findViewById(R.id.rl_enroll);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
    }

    public void initData(){

        Intent intent=getIntent();
        petInfo=intent.getExtras().getParcelable("petInfo");
        flag=intent.getExtras().getInt("flag");

        userId=petInfo.getUserId();
        petId=petInfo.getPetId();

        currentItem=intent.getExtras().getInt("position");

        getAdoptDetails();

    }

    public void initEvent(){

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

        mxback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        comment.setOnClickListener(this);

        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
    }

    public void getAdoptDetails(){

        RequestParams requestParams=null;

        if (flag==1){

            requestParams=new RequestParams(HttpUtils.HOST+"queryalldetails");
        }else if (flag==2){

            requestParams=new RequestParams(HttpUtils.HOST+"pairpet");
        }else if (flag==3){
            requestParams=new RequestParams(HttpUtils.HOST+"searchdetails");
        }

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson=new Gson();
                Type type=new TypeToken<List<DetailsBean>>(){}.getType();
                detailList=gson.fromJson(result, type);

                list_pager.setOffscreenPageLimit(2);

                for (DetailsBean detailsBean : detailList) {

                    View view= LayoutInflater.from(ReleaseDetailsActivity.this).inflate(R.layout.fragment_viewpage,null);

                    iv_petImage = ((ImageView) view.findViewById(R.id.iv_petImage));
                    iv_ApetPhoto = ((ImageView) view.findViewById(R.id.iv_ApetPhoto));
                    tv_ApetName = ((TextView) view.findViewById(R.id.tv_ApetName));
                    tv_ApetType = ((TextView) view.findViewById(R.id.tv_ApetType));
                    tv_realDesc = ((TextView) view.findViewById(R.id.tv_realDesc));
                    tv_publisherName = ((TextView) view.findViewById(R.id.tv_publisherName));
                    iv_publisherPhoto = ((ImageView) view.findViewById(R.id.iv_publisherPhoto));
                    // 初始化评论列表
                    ListView comment_list = (ListView) view.findViewById(R.id.comment_list);
                    getComment(comment_list,detailsBean);

                    xUtilsImageUtils.display(iv_petImage,HttpUtils.HOST_PIC+detailsBean.getPetImage());
                    xUtilsImageUtils.display(iv_ApetPhoto,HttpUtils.HOST_PIC+detailsBean.getPetPhoto(),true);
                    xUtilsImageUtils.display(iv_publisherPhoto,HttpUtils.HOST_PIC+detailsBean.getPublisherPhoto(),true);

                    tv_ApetName.setText(detailsBean.getPetName());
                    tv_ApetType.setText(detailsBean.getPetType());
                    tv_realDesc.setText(detailsBean.getDescribe());
                    tv_publisherName.setText(detailsBean.getPublisherName());

                    list_view.add(view);
                }

                adpter = new viewpageAdapter(list_view);
                list_pager.setAdapter(adpter);

                list_pager.setCurrentItem(currentItem);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                System.out.println("Error"+ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void getComment(final ListView comment_list,DetailsBean detailsBean){

        final List<SingleComment> data=new ArrayList<SingleComment>();

        RequestParams requestParams=new RequestParams(HttpUtils.HOST+"querycomment");

        requestParams.addBodyParameter("publisherId",detailsBean.getPublisherId()+"");
        requestParams.addBodyParameter("releaseId",detailsBean.getReleaseId()+"");
        requestParams.addBodyParameter("commentType",flag+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println("getComment-result===="+result);

                List<SingleComment> newData=new ArrayList<SingleComment>();
                Gson gson=new Gson();
                Type type=new TypeToken<List<SingleComment>>(){}.getType();
                newData=gson.fromJson(result,type);

                data.clear();

                data.addAll(newData);

                // 初始化适配器
                SingleCommentAdapter singleCommentAdapter = new SingleCommentAdapter(ReleaseDetailsActivity.this, data);
                // 为评论列表设置适配器
                singleCommentAdapterList.add(singleCommentAdapter);
                comment_list.setAdapter(singleCommentAdapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                System.out.println("Error======="+ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public void sendComment(){

        if(comment_content.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();

        }else{

            // 生成评论数据
            SingleComment comment = new SingleComment(detailList.get(currentItem).getPublisherId(),((MyApplication)getApplication()).getUser().getUserId(),
                    detailList.get(currentItem).getReleaseId(),comment_content.getText().toString(),
                    flag);

            SingleCommentAdapter cp = singleCommentAdapterList.get(list_pager.getCurrentItem());
            if(cp!=null){
                cp.addComment(comment);
            }

            Gson gson=new Gson();
            String commentStr=gson.toJson(comment);

            RequestParams requestParams=new RequestParams(HttpUtils.HOST+"sendcomment");
            requestParams.addBodyParameter("comment",commentStr);

            x.http().get(requestParams, new Callback.CommonCallback<String>() {
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
            // 发送完，清空输入框
            comment_content.setText("");

            Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
        }
    }

    private void showShare() {
        ShareSDK.initSDK(ReleaseDetailsActivity.this);
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
        oks.show(ReleaseDetailsActivity.this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.comment:
                // 弹出输入法
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // 显示评论框
                rl_enroll.setVisibility(View.GONE);
                rl_comment.setVisibility(View.VISIBLE);
                break;
            case R.id.hide_down:
                // 隐藏评论框
                rl_enroll.setVisibility(View.VISIBLE);
                rl_comment.setVisibility(View.GONE);
                // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                break;
            case R.id.comment_send:

                sendComment();
                break;
            default:
                break;
        }
    }

}
