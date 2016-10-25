package com.example.mengmeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mengmeng.pojo.ContactsInfoBean;
import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.HttpUtils;
import com.example.mengmeng.utils.xUtilsImageUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

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


    ContactsInfoBean contactsInfoBean;
    User user;
    String MyToken;
    Integer MyId;
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
        //拿到用户的token和id
        user = intent.getParcelableExtra("user");
        MyToken = user.getToken();
        MyId =user.getUserId();
        System.out.println(MyId);

        //拿到联系人的token和id
        ContactsId = contactsInfoBean.getUser().getUserId();
        ContactsToken = contactsInfoBean.getUser().getToken();

        //获取用户的个人资料
        xUtilsImageUtils.display(personPhoto, HttpUtils.HOST_COMMUNICATIE + contactsInfoBean.getUser().getUserPhoto(), true);
        personName.setText(contactsInfoBean.getUser().getUserName());
        personAddress.setText(contactsInfoBean.getUser().getAddress());
        personSex.setText(contactsInfoBean.getUser().isSex() == true ? "man" : "woman");
        personUnderwrite.setText(contactsInfoBean.getUser().getUserWrite());
        //获得宠物的个人资料
        xUtilsImageUtils.display(petPhoto, HttpUtils.HOST_COMMUNICATIE + contactsInfoBean.getPetInfo().petPhoto, true);
        petName.setText(contactsInfoBean.getPetInfo().petName);
        petType.setText(contactsInfoBean.getPetInfo().petType);
        petSex.setText(contactsInfoBean.getPetInfo().petSex == true ? "man" : "woman");
        petAge.setText((contactsInfoBean.getPetInfo().petAge) + "岁");
        petKind.setText(contactsInfoBean.getPetInfo().petKind);

    }


    @OnClick(R.id.sendMessage)
    public void onClick() {
        if(RongIM.getInstance()!=null) {
            RongIM.getInstance().startPrivateChat(PersonDataActivity.this, ContactsId+"", "聊天");
        }
    }


    public void connect(String MyToken) {
        RongIM.connect(MyToken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }


}
