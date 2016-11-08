package com.example.mengmeng.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mengmeng.pojo.PetInfo;
import com.example.mengmeng.utils.CommonAdapter;
import com.example.mengmeng.utils.NetUtil;
import com.example.mengmeng.utils.ViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class My_PetActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private ImageView addpet;
    private ListView petListview;

    CommonAdapter<PetInfo> petinfoAdapter;

    List<PetInfo> PetInfos=new ArrayList<PetInfo>();//存放宠物信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypet);
        initView();
        intData();
    }

    private void initView() {
        back = ((ImageView) findViewById(R.id.iv_pet_back));
        back.setOnClickListener(this);
        addpet = ((ImageView) findViewById(R.id.iv_addpet));
        addpet.setOnClickListener(this);
        petListview = ((ListView) findViewById(R.id.listview_mypet));


    }


    private void intData() {
//        http://10.40.5.17:8080/MengmengWeb/querypersonalpet
        Intent intent = getIntent();
        RequestParams requestParams = new RequestParams(NetUtil.url + "querypersonalpet");
        if(intent.getStringExtra("userId")!=null) {
            String userId = intent.getStringExtra("userId");
            requestParams.addQueryStringParameter("userId", userId);
            int userID=Integer.parseInt(userId);
            if(userID!=1){
                addpet.setVisibility(View.GONE);
            }
        }
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson=new Gson();
                Type type=new TypeToken<List<PetInfo>>(){}.getType();
                List<PetInfo> newPetInfo=new ArrayList<PetInfo>();
                newPetInfo=gson.fromJson(result, type);
                PetInfos.clear();
                PetInfos.addAll(newPetInfo);

                if(petinfoAdapter==null){
                    petinfoAdapter=new CommonAdapter<PetInfo>(My_PetActivity.this,PetInfos,R.layout.activity_my__pet) {
                        @Override
                        public void convert(ViewHolder viewHolder, PetInfo petInfo, int position) {
                            ImageView petPhoto=viewHolder.getViewById(R.id.iv_petPhoto);
                            x.image().bind(petPhoto,NetUtil.url+"imgs/pet/"+petInfo.petPhoto);

                            TextView petName=viewHolder.getViewById(R.id.tv_petName);
                            petName.setText(petInfo.petName);

                            TextView petType=viewHolder.getViewById(R.id.tv_petType);
                            petType.setText(petInfo.petType);

                            TextView petAge=viewHolder.getViewById(R.id.tv_petAge);
                            petAge.setText(petInfo.petAge+"岁");
                        }
                    };
                    petListview.setAdapter(petinfoAdapter);
                }else{
                    petinfoAdapter.notifyDataSetChanged();
                }

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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_pet_back:
                finish();
                break;
            case R.id.iv_addpet:
                Intent intent=new Intent(this,AddPetActivity.class);
                intent.putExtra("userId",1+"");
                startActivity(intent);
                break;
        }
    }
}
