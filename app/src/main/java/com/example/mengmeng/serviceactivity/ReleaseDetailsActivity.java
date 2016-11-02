package com.example.mengmeng.serviceactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.DetailsBean;
import com.example.mengmeng.pojo.GetAdoptBean;
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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ReleaseDetailsActivity extends AppCompatActivity {

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
    }

    public void initData(){

        Intent intent=getIntent();
        petInfo=intent.getExtras().getParcelable("petInfo");
        flag=intent.getExtras().getInt("flag");

        userId=petInfo.getUserId();
        petId=petInfo.getPetId();

        currentItem=intent.getExtras().getInt("position");

//        if (flag==1){
//            getAdoptDetails();
//        }else if (flag==2){
//            getPairDetails();
//        }else if(flag==3){
//            getSearchDetails();
//        }

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
    }

    //获取寻宠详情界面信息
    public void getSearchDetails(){

        RequestParams requestParams=new RequestParams(HttpUtils.HOST+"searchdetails");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<DetailsBean>>(){}.getType();
                detailList=gson.fromJson(result, type);


                for (DetailsBean detailsBean : detailList) {

                    View view= LayoutInflater.from(ReleaseDetailsActivity.this).inflate(R.layout.fragment_viewpage,null);


                    iv_petImage = ((ImageView) view.findViewById(R.id.iv_petImage));
                    iv_ApetPhoto = ((ImageView) view.findViewById(R.id.iv_ApetPhoto));
                    tv_ApetName = ((TextView) view.findViewById(R.id.tv_ApetName));
                    tv_ApetType = ((TextView) view.findViewById(R.id.tv_ApetType));
                    tv_realDesc = ((TextView) view.findViewById(R.id.tv_realDesc));
                    tv_publisherName = ((TextView) view.findViewById(R.id.tv_publisherName));
                    iv_publisherPhoto = ((ImageView) view.findViewById(R.id.iv_publisherPhoto));

                    xUtilsImageUtils.display(iv_petImage,HttpUtils.HOST+detailsBean.getPetImage());
                    xUtilsImageUtils.display(iv_ApetPhoto,HttpUtils.HOST+detailsBean.getPetPhoto(),true);
                    xUtilsImageUtils.display(iv_publisherPhoto,HttpUtils.HOST+detailsBean.getUserPhoto(),true);

                    tv_ApetName.setText(detailsBean.getPetName());
                    tv_ApetType.setText(detailsBean.getPetType());
                    tv_realDesc.setText(detailsBean.getDescribe());
                    tv_publisherName.setText(detailsBean.getUserName());

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
                System.out.println("ReleaseDetails-result===="+result);

                Gson gson=new Gson();
                Type type=new TypeToken<List<DetailsBean>>(){}.getType();
                detailList=gson.fromJson(result, type);


                for (DetailsBean detailsBean : detailList) {

                    View view= LayoutInflater.from(ReleaseDetailsActivity.this).inflate(R.layout.fragment_viewpage,null);


                    iv_petImage = ((ImageView) view.findViewById(R.id.iv_petImage));
                    iv_ApetPhoto = ((ImageView) view.findViewById(R.id.iv_ApetPhoto));
                    tv_ApetName = ((TextView) view.findViewById(R.id.tv_ApetName));
                    tv_ApetType = ((TextView) view.findViewById(R.id.tv_ApetType));
                    tv_realDesc = ((TextView) view.findViewById(R.id.tv_realDesc));
                    tv_publisherName = ((TextView) view.findViewById(R.id.tv_publisherName));
                    iv_publisherPhoto = ((ImageView) view.findViewById(R.id.iv_publisherPhoto));

                    xUtilsImageUtils.display(iv_petImage,HttpUtils.HOST+detailsBean.getPetImage());
                    xUtilsImageUtils.display(iv_ApetPhoto,HttpUtils.HOST+detailsBean.getPetPhoto(),true);
                    xUtilsImageUtils.display(iv_publisherPhoto,HttpUtils.HOST+detailsBean.getUserPhoto(),true);

                    tv_ApetName.setText(detailsBean.getPetName());
                    tv_ApetType.setText(detailsBean.getPetType());
                    tv_realDesc.setText(detailsBean.getDescribe());
                    tv_publisherName.setText(detailsBean.getUserName());

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
}
