package com.example.mengmeng.serviceactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.PetInfo;
import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.CommonAdapter;
import com.example.mengmeng.utils.HttpUtils;
import com.example.mengmeng.utils.ViewHolder;
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

public class SelectPet extends AppCompatActivity {

    private ListView lv_publish;
    private CommonAdapter<PetInfo> adapter;
    private User user;
    private List<PetInfo> petList=new ArrayList<PetInfo>();
    private ImageView msback;
    Integer flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pet);

        initView();

        initData();

        initEvent();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        finish();
    }

    private void initEvent() {

        lv_publish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SelectPet.this,TakePhotoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("petInfo",petList.get(position));
                bundle.putInt("flag",flag);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        msback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void initView() {

        lv_publish = ((ListView) findViewById(R.id.lv_publish));
        msback = ((ImageView) findViewById(R.id.msback));
    }

    private void initData() {

        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);
        System.out.println("SelectPet-flag======="+flag);
        user=((MyApplication)getApplication()).getUser();
        getPersonalPet();
    }

    private void getPersonalPet() {

        RequestParams params = new RequestParams(HttpUtils.HOST + "querypersonalpet");
        params.addQueryStringParameter("userId",user.getUserId()+"");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                Type type=new TypeToken<List<PetInfo>>(){}.getType();
                petList=gson.fromJson(result,type);

                if (adapter==null){

                    adapter=new CommonAdapter<PetInfo>(SelectPet.this,petList,R.layout.activity_selectpet_listview_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, PetInfo petInfo, int position) {

                            TextView tv_petName = viewHolder.getViewById(R.id.tv_petName);
                            TextView tv_petType = viewHolder.getViewById(R.id.tv_petType);
                            TextView tv_petAge = viewHolder.getViewById(R.id.tv_petAge);
                            ImageView iv_petPhoto = viewHolder.getViewById(R.id.iv_petPhoto);

                            tv_petName.setText(petInfo.petName);
                            tv_petType.setText(petInfo.petType);
                            tv_petAge.setText(petInfo.petAge.toString().trim() + "岁");
                            xUtilsImageUtils.display(iv_petPhoto, HttpUtils.HOST_PIC + petInfo.petPhoto, true);

                        }
                    };
                    lv_publish.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                System.out.println(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
