package com.example.mengmeng.serviceactivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.GetAdoptBean;
import com.example.mengmeng.utils.CommonAdapter;
import com.example.mengmeng.utils.HttpUtils;
import com.example.mengmeng.utils.RefreshListView;
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

public class AdoptActivity extends AppCompatActivity implements RefreshListView.OnRefreshUploadChangeListener {

    List<String> kindContents = new ArrayList<String>();
    private Integer queryFlag=1;
    private Integer pageNo=1;
    private Integer pageSize=4;
    Handler handler = new Handler();

    private RefreshListView lv_petInfo;
    private CommonAdapter<GetAdoptBean> adapter;
    List<GetAdoptBean> petLists = new ArrayList<GetAdoptBean>();
    private Button btn_publish;
    private TextView tv_allkind;
    Integer flag;
    boolean flag11=true;
    private TextView adopt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);

        initView();

        initData();

        initEvent();
    }

    public void initView() {

        lv_petInfo = ((RefreshListView) findViewById(R.id.lv_petInfo));
        btn_publish = ((Button) findViewById(R.id.btn_publish));
        tv_allkind = ((TextView) findViewById(R.id.tv_allkind));
        adopt = ((TextView) findViewById(R.id.adopt));

    }

    public void initData() {

        kindContents.add("狗");
        kindContents.add("猫");
        kindContents.add("其他");

        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);

        if (flag==1){
            getAdoaptInfo();
        }else if(flag==3){
            adopt.setText("寻宠");
            getSearchInfo();
        }

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
                if (RefreshListView.isTag() == false && position != 0 && position <= petLists.size()){

                    Intent intent=new Intent(AdoptActivity.this, ReleaseDetailsActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("petInfo",petLists.get(position-1));
                    bundle.putInt("flag",flag);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

        lv_petInfo.setOnRefreshUploadChangeListener(this);
    }

    private void getAdoaptInfo() {

        RequestParams params = new RequestParams(HttpUtils.HOST + "queryadoapt");
        params.addQueryStringParameter("queryFlag",queryFlag+"");//排序标记
        params.addQueryStringParameter("pageNo",pageNo+"");
        params.addQueryStringParameter("pageSize",pageSize+"");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();

                List<GetAdoptBean> newAdoaptLists=new ArrayList<GetAdoptBean>();

                Type type=new TypeToken<List<GetAdoptBean>>(){}.getType();

                newAdoaptLists=gson.fromJson(result,type);

                if (flag11) {
                    petLists.clear();// 清空原来数据
                } else {
                    if (newAdoaptLists.size() == 0) {//服务器没有返回新的数据
                        pageNo--; //下一次继续加载这一页
                        Toast.makeText(AdoptActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                        lv_petInfo.completeLoad();//没获取到数据也要改变界面
                        return;
                    }
                }
                petLists.addAll(newAdoaptLists);

                if (adapter==null){

                    adapter=new CommonAdapter<GetAdoptBean>(AdoptActivity.this,petLists,R.layout.activity_adopt_listview_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, GetAdoptBean petInfo, int position) {

                            TextView tv_petName =viewHolder.getViewById(R.id.tv_petName);
                            TextView tv_petType = viewHolder.getViewById(R.id.tv_petType);
                            TextView tv_petAge = viewHolder.getViewById(R.id.tv_petAge);
                            ImageView iv_petPhoto = viewHolder.getViewById(R.id.iv_petPhoto);
                            TextView tv_releasetime=viewHolder.getViewById(R.id.tv_releasetime);

                            tv_petName.setText(petInfo.getPetName());
                            tv_petType.setText(petInfo.getPetType());
                            tv_petAge.setText(petInfo.getPetAge().toString().trim() + "岁");
                            tv_releasetime.setText(petInfo.getReleaseTime()+"");
                            xUtilsImageUtils.display(iv_petPhoto, HttpUtils.HOST + petInfo.getPetPhoto(), true);
                        }
                    };
                    lv_petInfo.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }
                if (!flag11){
                    lv_petInfo.completeLoad();
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

    private void getSearchInfo(){

        RequestParams params = new RequestParams(HttpUtils.HOST + "searchpet");
        params.addQueryStringParameter("queryFlag",queryFlag+"");//排序标记
        params.addQueryStringParameter("pageNo",pageNo+"");
        params.addQueryStringParameter("pageSize",pageSize+"");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();

                List<GetAdoptBean> newAdoaptLists=new ArrayList<GetAdoptBean>();

                Type type=new TypeToken<List<GetAdoptBean>>(){}.getType();

                newAdoaptLists=gson.fromJson(result,type);

                if (flag11) {
                    petLists.clear();// 清空原来数据
                } else {
                    System.out.println("newAdoaptLists.size===="+newAdoaptLists.size());
                    if (newAdoaptLists.size() == 0) {//服务器没有返回新的数据
                        pageNo--; //下一次继续加载这一页
                        Toast.makeText(AdoptActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                        lv_petInfo.completeLoad();//没获取到数据也要改变界面
                        return;
                    }
                }
                petLists.addAll(newAdoaptLists);

                if (adapter==null){

                    adapter=new CommonAdapter<GetAdoptBean>(AdoptActivity.this,petLists,R.layout.activity_adopt_listview_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, GetAdoptBean petInfo, int position) {

                            TextView tv_petName =viewHolder.getViewById(R.id.tv_petName);
                            TextView tv_petType = viewHolder.getViewById(R.id.tv_petType);
                            TextView tv_petAge = viewHolder.getViewById(R.id.tv_petAge);
                            ImageView iv_petPhoto = viewHolder.getViewById(R.id.iv_petPhoto);
                            TextView tv_releasetime=viewHolder.getViewById(R.id.tv_releasetime);

                            tv_petName.setText(petInfo.getPetName());
                            tv_petType.setText(petInfo.getPetType());
                            tv_petAge.setText(petInfo.getPetAge().toString().trim() + "岁");
                            tv_releasetime.setText(petInfo.getReleaseTime()+"");
                            xUtilsImageUtils.display(iv_petPhoto, HttpUtils.HOST + petInfo.getPetPhoto(), true);
                        }
                    };
                    lv_petInfo.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }
                if (!flag11){
                    lv_petInfo.completeLoad();
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

    @Override
    public void onRefresh() {

        pageNo = 1; //每次刷新，让pageNo变成初始值1
        //1秒后发一个消息
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag11 = true;

                //再次获取数据
                if(flag==1){
                    getAdoaptInfo();
                }else if (flag==3){
                    getSearchInfo();
                }
                lv_petInfo.completeRefresh();  //刷新数据后，改变页面为初始页面：隐藏头部
            }
        }, 1000);
    }

    @Override
    public void onPull() {

        pageNo++;
        //原来数据基础上增加
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag11 = false;
                if(flag==1){
                    getAdoaptInfo();
                }else if (flag==3){
                    getSearchInfo();
                }
            }
        }, 1000);
    }
}
