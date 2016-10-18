package com.example.mengmeng.serviceactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.AdoaptActivityBean;
import com.example.mengmeng.pojo.PetInfo;
import com.example.mengmeng.utils.HttpUtils;
import com.example.mengmeng.utils.xUtilsImageUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class AdoptActivity extends AppCompatActivity {

    private ListView lv_petInfo;
    private BaseAdapter adapter;
    List<PetInfo> petInfoList=new ArrayList<PetInfo>();
    private Button btn_publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);

        lv_petInfo = ((ListView) findViewById(R.id.lv_petInfo));
        btn_publish = ((Button) findViewById(R.id.btn_publish));

        Log.i("adoptAvtibity","===========onCreate");

        initData();

        initEvent();
    }


    public void initData(){
        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return petInfoList.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view=View.inflate(AdoptActivity.this,R.layout.activity_adopt_listview_item,null);
                TextView tv_petName=((TextView) view.findViewById(R.id.tv_petName));
                TextView tv_petType=((TextView) view.findViewById(R.id.tv_petType));
                TextView tv_petAge=((TextView) view.findViewById(R.id.tv_petAge));
                ImageView iv_petPhoto=((ImageView) view.findViewById(R.id.iv_petPhoto));

                PetInfo petInfo=petInfoList.get(position);
                tv_petName.setText(petInfo.petName);
                tv_petType.setText(petInfo.petType);
                tv_petAge.setText(petInfo.petAge.toString().trim()+"岁");
                xUtilsImageUtils.display(iv_petPhoto, HttpUtils.HOST+petInfo.petPhoto,true);


                return view;
            }
        };

        lv_petInfo.setAdapter(adapter);

        getPetInfoList();
    }

    public void initEvent(){

        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdoptActivity.this,SelectPet.class);
                startActivity(intent);
            }
        });
    }

    private void getPetInfoList() {

        Log.i("adoptAvtibity","===========getPetInfoList");

        RequestParams params=new RequestParams(HttpUtils.HOST+"getpetInfobypage");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                AdoaptActivityBean bean=gson.fromJson(result, AdoaptActivityBean.class);
                petInfoList.addAll(bean.petList);
                adapter.notifyDataSetChanged();
                System.out.println(bean.petList.get(0).petPhoto+"============");
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
