package com.example.mengmeng.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import io.rong.imlib.model.Conversation;

public class ConversationActivity extends FragmentActivity {

    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    private TextView friendName;
    String name=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        initView();
        initDate();
    }
    private void initView(){
        friendName = ((TextView) findViewById(R.id.name));
    }
    private void initDate(){
        name=getIntent().getData().getQueryParameter("title");
        friendName.setText(name);
    }
}
