package com.example.mengmeng.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PersonDataActivity extends AppCompatActivity {

    @InjectView(R.id.person_photo)
    ImageView personPhoto;
    @InjectView(R.id.person_name)
    TextView personName;
    @InjectView(R.id.person_address)
    TextView personAddress;
    @InjectView(R.id.person_sex)
    TextView personSex;
    @InjectView(R.id.qianming)
    TextView qianming;
    @InjectView(R.id.person_underwrite)
    TextView personUnderwrite;
    @InjectView(R.id.vLine)
    View vLine;
    @InjectView(R.id.pet_photo)
    ImageView petPhoto;
    @InjectView(R.id.pet_name)
    TextView petName;
    @InjectView(R.id.pet_type)
    TextView petType;
    @InjectView(R.id.pet_sex)
    TextView petSex;
    @InjectView(R.id.pet_age)
    TextView petAge;
    @InjectView(R.id.pet_kind)
    TextView petKind;
    @InjectView(R.id.show_person_data)
    RelativeLayout showPersonData;
    @InjectView(R.id.sendMessage)
    Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        ButterKnife.inject(this);
    }



    @OnClick(R.id.sendMessage)
    public void onClick() {
    }
}
