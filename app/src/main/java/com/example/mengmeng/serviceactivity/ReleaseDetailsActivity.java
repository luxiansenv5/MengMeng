package com.example.mengmeng.serviceactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.DetailsBean;
import com.example.mengmeng.pojo.GetAdoptBean;
import com.example.mengmeng.utils.HttpUtils;
import com.example.mengmeng.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
    private DetailsBean detailsBean;
    private ImageView iv_ApetPhoto;
    private Integer petId;
    private ImageView share;
    private Integer flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_details);

        initView();

        initData();

        initEvent();

    }

    public void initView(){

        iv_petImage = ((ImageView) findViewById(R.id.iv_petImage));
        iv_ApetPhoto = ((ImageView) findViewById(R.id.iv_ApetPhoto));
        tv_ApetName = ((TextView) findViewById(R.id.tv_ApetName));
        tv_ApetType = ((TextView) findViewById(R.id.tv_ApetType));
        tv_realDesc = ((TextView) findViewById(R.id.tv_realDesc));
        tv_publisherName = ((TextView) findViewById(R.id.tv_publisherName));
        iv_publisherPhoto = ((ImageView) findViewById(R.id.iv_publisherPhoto));
        share = ((ImageView) findViewById(R.id.share));
    }

    public void initData(){

        Intent intent=getIntent();
        petInfo=intent.getExtras().getParcelable("petInfo");
        flag=intent.getExtras().getInt("flag");
        System.out.println("flag==="+flag);
        userId=petInfo.getUserId();
        petId=petInfo.getPetId();

        if (flag==1){
            getAdoptDetails();
        }else if(flag==3){
            getSearchDetails();
        }
    }

    public void initEvent(){

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }

    //获取详情界面信息
    public void getAdoptDetails(){

        RequestParams requestParams=new RequestParams(HttpUtils.HOST+"querydetails");
        requestParams.addQueryStringParameter("userId",userId+"");
        requestParams.addQueryStringParameter("petId",petId+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                detailsBean=gson.fromJson(result, DetailsBean.class);

                System.out.println("petImage===="+detailsBean.getPetImage());
                System.out.println("userPhoto===="+detailsBean.getUserPhoto());
                xUtilsImageUtils.display(iv_petImage,HttpUtils.HOST+detailsBean.getPetImage());
                xUtilsImageUtils.display(iv_ApetPhoto,HttpUtils.HOST+detailsBean.getPetPhoto(),true);
                xUtilsImageUtils.display(iv_publisherPhoto,HttpUtils.HOST+detailsBean.getUserPhoto(),true);

                tv_ApetName.setText(detailsBean.getPetName());
                tv_ApetType.setText(detailsBean.getPetType());
                tv_realDesc.setText(detailsBean.getDescribe());
                tv_publisherName.setText(detailsBean.getUserName());
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

    public void getSearchDetails(){

        RequestParams requestParams=new RequestParams(HttpUtils.HOST+"searchdetails");
        requestParams.addQueryStringParameter("userId",userId+"");
        requestParams.addQueryStringParameter("petId",petId+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("getSearchDetails"+result);
                Gson gson=new Gson();
                detailsBean=gson.fromJson(result, DetailsBean.class);

                System.out.println("petImage===="+detailsBean.getPetImage());
                System.out.println("userPhoto===="+detailsBean.getUserPhoto());
                xUtilsImageUtils.display(iv_petImage,HttpUtils.HOST+detailsBean.getPetImage());
                xUtilsImageUtils.display(iv_ApetPhoto,HttpUtils.HOST+detailsBean.getPetPhoto(),true);
                xUtilsImageUtils.display(iv_publisherPhoto,HttpUtils.HOST+detailsBean.getUserPhoto(),true);

                tv_ApetName.setText(detailsBean.getPetName());
                tv_ApetType.setText(detailsBean.getPetType());
                tv_realDesc.setText(detailsBean.getDescribe());
                tv_publisherName.setText(detailsBean.getUserName());
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
