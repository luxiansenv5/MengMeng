package com.example.mengmeng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.AdoaptInfo;
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

/**
 * Created by 陆猛 on 2016/11/4.
 */
public class PetFragment extends BaseFragment {

    private CommonAdapter<AdoaptInfo> adapter;
    private ListView myrelease_petlist;
    private List<AdoaptInfo> adoaptInfos =new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_myrelease_pet,null);

        myrelease_petlist = ((ListView) view.findViewById(R.id.myrelease_petlist));
//        button_left = ((Button) view.findViewById(R.id.button_left));
//        button_right = ((Button) view.findViewById(R.id.button_right));

        return view;
    }

    @Override
    public void initView() {


    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

        RequestParams requestParams=new RequestParams(HttpUtils.HOST+"querymyreleasepet");
        requestParams.addBodyParameter("publisherId", (((MyApplication)getActivity().getApplication())).getUser().getUserId()+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson=new Gson();
                List<AdoaptInfo> newAdoaptInfoList=new ArrayList<AdoaptInfo>();
                Type type=new TypeToken<List<AdoaptInfo>>(){}.getType();
                newAdoaptInfoList=gson.fromJson(result,type);

                adoaptInfos.clear();
                adoaptInfos.addAll(newAdoaptInfoList);

                if (adapter==null){

                    adapter=new CommonAdapter<AdoaptInfo>(getActivity(),adoaptInfos,R.layout.myrelease_pet_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, AdoaptInfo adoaptInfo, int position) {

                            initItemView(viewHolder,adoaptInfo,position);


                        }
                    };
                    myrelease_petlist.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
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

    public void initItemView(ViewHolder viewHolder, AdoaptInfo adoaptInfo, int position){

        ImageView petImage=viewHolder.getViewById(R.id.myrelease_petimage);
        TextView describe=viewHolder.getViewById(R.id.myrelease_desc);
        TextView releaseTime=viewHolder.getViewById(R.id.myrelease_time);
        Button button_left=viewHolder.getViewById(R.id.button_left);
        Button button_right=viewHolder.getViewById(R.id.button_right);

        xUtilsImageUtils.display(petImage,HttpUtils.HOST_PIC+adoaptInfo.getPetImage());
        describe.setText(adoaptInfo.getDescribe());
        releaseTime.setText(adoaptInfo.getReleaseTime()+"");

        btnShow(adoaptInfo.isState(),button_left,button_right);

        if (!adoaptInfo.isState()) {
            btnClick(adoaptInfo, button_left, button_right, position);
        }
    }

    public void btnShow(boolean petState,Button btnLeft,Button btnRight){

        if (petState){

            btnLeft.setVisibility(View.GONE);
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText("结束");

        }else {

            btnLeft.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);

            btnLeft.setText("取消");
            btnRight.setText("确认");

        }
    }

    public void btnClick(final AdoaptInfo adoaptInfo, Button button_left, Button button_right, final int position){

        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                changeState(adoaptInfo.getPublishId(),adoaptInfo.getType(),position);
            }
        });

        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelRelease(adoaptInfo.getType(),adoaptInfo.getPublishId(),adoaptInfo.getPetId(),position);
            }
        });
    }

    public void changeState(Integer publishId, Integer type, final int position){

        RequestParams requestParams=new RequestParams(HttpUtils.HOST+"updaterelease");

        requestParams.addBodyParameter("publishId",publishId+"");
        requestParams.addBodyParameter("type",type+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                adoaptInfos.get(position).setState(true);
                adapter.notifyDataSetChanged();
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

    public void cancelRelease(Integer type, Integer publisherId, Integer petId, final int position){

        RequestParams requestParams=new RequestParams(HttpUtils.HOST+"cancelrelease");

        requestParams.addBodyParameter("type",type+"");
        requestParams.addBodyParameter("publisherId",publisherId+"");
        requestParams.addBodyParameter("petId",petId+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                adoaptInfos.remove(position);
                adapter.notifyDataSetChanged();
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
}
