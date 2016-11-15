package com.example.mengmeng.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mengmeng.pojo.ContactsInfoBean;
import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.NetUtil;
import com.example.mengmeng.utils.xUtilsImageUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class PersonDataActivity extends AppCompatActivity {


    private ImageView personPhoto;
    private TextView personName;
    private TextView personAddress;
    private TextView personUnderwrite;
    private TextView personSex;

    private ImageView petPhoto;
    private TextView petName;
    private TextView petType;
    private TextView petAge;
    private TextView petKind;
    private TextView petSex;
    private Button deleteFri;


    ContactsInfoBean contactsInfoBean;
    Integer userId;
    User user;
    String MyToken;
    Integer MyId;
    String MyName;
    String ContactsToken;
    Integer ContactsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        initData();
        initView();
        connect(MyToken);
        ButterKnife.inject(this);
    }

    public void initView() {

    }

    public void initData() {


        personPhoto = ((ImageView) findViewById(R.id.person_photo));
        personName = ((TextView) findViewById(R.id.person_name));
        personAddress = ((TextView) findViewById(R.id.person_address));
        personUnderwrite = ((TextView) findViewById(R.id.person_underwrite));

        petPhoto = ((ImageView) findViewById(R.id.pet_photo));
        petName = ((TextView) findViewById(R.id.pet_name));
        petType = ((TextView) findViewById(R.id.pet_type));
        petAge = ((TextView) findViewById(R.id.pet_age));
        petKind = ((TextView) findViewById(R.id.pet_kind));
        personSex = ((TextView) findViewById(R.id.person_sex));
        petSex = ((TextView) findViewById(R.id.pet_sex));

        Intent intent = getIntent();
        contactsInfoBean = intent.getParcelableExtra("contactsInfoBean");
        System.out.println("userId"+contactsInfoBean.getUser().getUserId());
        MyToken = LoginInfo.token;
        MyId =LoginInfo.userId;
        MyName =LoginInfo.name;
        System.out.println("已进入PersonDataActivity");
        //拿到联系人的token和id
        ContactsId = contactsInfoBean.getUser().getUserId();
        ContactsToken = contactsInfoBean.getUser().getToken();

        //获取用户的个人资料
        xUtilsImageUtils.display(personPhoto, NetUtil.photo_url+ contactsInfoBean.getUser().getUserPhoto(), true);
        personName.setText(contactsInfoBean.getUser().getUserName());
        personAddress.setText(contactsInfoBean.getUser().getAddress());
        personSex.setText(contactsInfoBean.getUser().isUserSex() == true ? "man" : "woman");
        personUnderwrite.setText(contactsInfoBean.getUser().getUserWrite());
        //获得宠物的个人资料
        xUtilsImageUtils.display(petPhoto, NetUtil.photo_url+ contactsInfoBean.getPetInfo().petPhoto, true);
        petName.setText(contactsInfoBean.getPetInfo().petName);
        petType.setText(contactsInfoBean.getPetInfo().petType);
        petSex.setText(contactsInfoBean.getPetInfo().petSex == true ? "man" : "woman");
        petAge.setText((contactsInfoBean.getPetInfo().petAge) + "岁");
        petKind.setText(contactsInfoBean.getPetInfo().petKind);
    }

    @OnClick(R.id.sendMessage)
    public void onClick() {
        if(RongIM.getInstance()!=null) {
            RongIM.getInstance().startPrivateChat(PersonDataActivity.this, ContactsId+"",contactsInfoBean.getUser().getUserName() );
        }
    }


    public void connect(String MyToken) {
        RongIM.connect(MyToken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                Log.e("e", "onSuccess: "+s );
                /**
                 * 刷新用户缓存数据。
                 *
                 * @param userInfo 需要更新的用户缓存数据。
                 */
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(MyId+"",MyName, Uri.parse( NetUtil.photo_url+ LoginInfo.userPhoto)));
                Log.e("img", "onSuccess: "+NetUtil.photo_url + contactsInfoBean.getUser().getUserPhoto());
                Log.e("img", "onSuccess: "+MyId );
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("img", "onError: "+errorCode );
            }
        });
    }
    /**
     * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
     *
     * @param userInfoProvider 用户信息提供者。
     * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
     *                         如果 App 提供的 UserInfoProvider
     *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
     *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
     * @see UserInfoProvider
     */





}
