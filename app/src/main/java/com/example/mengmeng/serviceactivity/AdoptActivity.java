package com.example.mengmeng.serviceactivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.PetInfo;
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

public class AdoptActivity extends AppCompatActivity {

    List<String> kindContents = new ArrayList<String>();
    private Integer queryFlag=1;
//    private Integer pageNo;
//    private Integer pageSize;

    private ListView lv_petInfo;
    private CommonAdapter<PetInfo> adapter;
    List<PetInfo> petLists = new ArrayList<PetInfo>();
    private Button btn_publish;
    private TextView tv_allkind;
    Integer flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);

        initView();

        initData();

        initEvent();
    }

    public void initView() {

        lv_petInfo = ((ListView) findViewById(R.id.lv_petInfo));
        btn_publish = ((Button) findViewById(R.id.btn_publish));
        tv_allkind = ((TextView) findViewById(R.id.tv_allkind));

    }

    public void initData() {

        kindContents.add("狗");
        kindContents.add("猫");
        kindContents.add("其他");

        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);

//        if (flag==1){
//            getAdoaptInfo();
//        }else if(flag==3){
//            getSearchInfo();
//        }
        getAdoaptInfo();

    }

    public void initEvent() {

        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdoptActivity.this, SelectPet.class);
                startActivity(intent);
            }
        });

        tv_allkind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupWindow(v);
            }
        });

        lv_petInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(AdoptActivity.this, ReleaseDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("petInfo",petLists.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getAdoaptInfo() {

        RequestParams params = new RequestParams(HttpUtils.HOST + "queryadoapt");
        params.addQueryStringParameter("queryFlag",queryFlag+"");//排序标记
//        params.addQueryStringParameter("pageNo",pageNo+"");
//        params.addQueryStringParameter("pageSize",pageSize+"");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();

                List<PetInfo> newAdoaptLists=new ArrayList<PetInfo>();

                Type type=new TypeToken<List<PetInfo>>(){}.getType();
                newAdoaptLists=gson.fromJson(result,type);

                petLists.clear();
                petLists.addAll(newAdoaptLists);

                if (adapter==null){

                    adapter=new CommonAdapter<PetInfo>(AdoptActivity.this,petLists,R.layout.activity_adopt_listview_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, PetInfo petInfo, int position) {

                            TextView tv_petName =viewHolder.getViewById(R.id.tv_petName);
                            TextView tv_petType = viewHolder.getViewById(R.id.tv_petType);
                            TextView tv_petAge = viewHolder.getViewById(R.id.tv_petAge);
                            ImageView iv_petPhoto = viewHolder.getViewById(R.id.iv_petPhoto);

                            tv_petName.setText(petInfo.petName);
                            tv_petType.setText(petInfo.petType);
                            tv_petAge.setText(petInfo.petAge.toString().trim() + "岁");
                            xUtilsImageUtils.display(iv_petPhoto, HttpUtils.HOST + petInfo.petPhoto, true);
                        }
                    };
                    lv_petInfo.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                System.out.println("Error==="+ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getSearchInfo(){}

    public void initPopupWindow(View v) {

        View view = LayoutInflater.from(AdoptActivity.this).inflate(R.layout.all_kind, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 200);

        ListView lv = (ListView) view.findViewById(R.id.lv_allkind);

        ArrayAdapter adapter = new ArrayAdapter(AdoptActivity.this, R.layout.all_kind_item, kindContents);
        lv.setAdapter(adapter);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.showAsDropDown(v);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                popupWindow.dismiss();

                if (position == 0) {
                    queryFlag = 2;
                } else if (position == 1) {
                    queryFlag = 3;
                } else if (position == 2) {
                    queryFlag = 4;
                }

                getAdoaptInfo();
            }
        });

    }

}
