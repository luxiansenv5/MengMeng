package com.example.mengmeng.serviceactivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
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
    private ImageView mlback;
    private ImageView right_trangle;
    private ProgressBar progressBar;


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private TextView city;

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
        mlback = ((ImageView) findViewById(R.id.mlback));
        adopt = ((TextView) findViewById(R.id.adopt));
        right_trangle = ((ImageView) findViewById(R.id.right_trangle));
        progressBar = ((ProgressBar) findViewById(R.id.progressBar));
        city = ((TextView) findViewById(R.id.first_city));

    }

    public void initData() {

        getLocation();

        kindContents.add("狗");
        kindContents.add("猫");
        kindContents.add("其他");

        Intent intent=getIntent();
        flag=intent.getIntExtra("flag",0);

        if (flag==2) {

            adopt.setText("配对");

        }else if(flag==3){

            adopt.setText("寻宠");
        }

        getAdoaptInfo();
    }

    public void initEvent() {

        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdoptActivity.this, SelectPet.class);
                intent.putExtra("flag",flag);
                startActivity(intent);
            }
        });

        right_trangle.setOnClickListener(new View.OnClickListener() {
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
                    bundle.putInt("position",position-1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });

        mlback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        lv_petInfo.setOnRefreshUploadChangeListener(this);

        tv_allkind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryFlag=1;
                getAdoaptInfo();
            }
        });
    }

    private void getAdoaptInfo() {

        progressBar.setVisibility(View.VISIBLE);
        RequestParams params = null;

        if (flag==1){

            params=new RequestParams(HttpUtils.HOST + "queryadoapt");
        }else if (flag==2){

            params=new RequestParams(HttpUtils.HOST + "petpair");
        }else if (flag==3){
            params=new RequestParams(HttpUtils.HOST + "searchpet");
        }

        params.addQueryStringParameter("queryFlag",queryFlag+"");//排序标记
        params.addQueryStringParameter("pageNo",pageNo+"");
        params.addQueryStringParameter("pageSize",pageSize+"");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                progressBar.setVisibility(View.GONE);
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
                            xUtilsImageUtils.display(iv_petPhoto, HttpUtils.HOST_PIC + petInfo.getPetPhoto(), true);

                            System.out.println("adoptActivity-pic==="+HttpUtils.HOST_PIC + petInfo.getPetPhoto());
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
        final PopupWindow popupWindow = new PopupWindow(view, 200, 200);

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
                getAdoaptInfo();
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
                getAdoaptInfo();
            }
        }, 1000);
    }

    public void getLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        mLocationOption.setOnceLocation(true);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        //启动定位
        mLocationClient.startLocation();
    }
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {

            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息

                    city.setText(amapLocation.getCity());
                    Toast.makeText(AdoptActivity.this,"城市为："+amapLocation.getCity(),Toast.LENGTH_SHORT).show();
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
}
