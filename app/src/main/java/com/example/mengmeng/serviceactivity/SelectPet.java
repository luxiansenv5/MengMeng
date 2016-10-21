package com.example.mengmeng.serviceactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.PetInfo;
import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.HttpUtils;
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
    private BaseAdapter adapter;
    private User user;
    private List<PetInfo> petList=new ArrayList<PetInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pet);

        initView();

        initData();

        initEvent();
    }

    private void initEvent() {

        lv_publish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SelectPet.this,TakePhotoActivity.class);

                startActivity(intent);
            }
        });
    }

    private void initView() {

        lv_publish = ((ListView) findViewById(R.id.lv_publish));
    }

    private void initData() {

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
                    adapter = new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return petList.size();
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

                            View view = View.inflate(SelectPet.this, R.layout.activity_selectpet_listview_item, null);
                            TextView tv_petName = ((TextView) view.findViewById(R.id.tv_petName));
                            TextView tv_petType = ((TextView) view.findViewById(R.id.tv_petType));
                            TextView tv_petAge = ((TextView) view.findViewById(R.id.tv_petAge));
                            ImageView iv_petPhoto = ((ImageView) view.findViewById(R.id.iv_petPhoto));

                            PetInfo petInfo = petList.get(position);
                            tv_petName.setText(petInfo.petName);
                            tv_petType.setText(petInfo.petType);
                            tv_petAge.setText(petInfo.petAge.toString().trim() + "岁");
                            xUtilsImageUtils.display(iv_petPhoto, HttpUtils.HOST + petInfo.petPhoto, true);

                            return view;
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
