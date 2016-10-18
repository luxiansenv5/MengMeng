package com.example.mengmeng.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.FriInfoBean;
import com.example.mengmeng.utils.HttpUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by admin on 2016/10/17.
 */
public class PetFriendsFragment extends BaseFragment {

    @InjectView(R.id.iv_search)
    ImageView ivSearch;
    @InjectView(R.id.iv_petfriend_add)
    ImageView ivPetfriendAdd;
    @InjectView(R.id.petfriend_bottom)
    RelativeLayout petfriendBottom;
    @InjectView(R.id.lv_petfriend)
    ListView lvPetfriend;
    private BaseAdapter adapter;

    final List<FriInfoBean.FriInfo> friinfoList = new ArrayList<FriInfoBean.FriInfo>();

    private ListView lv_petfriend;

    //compile 'com.jakewharton:butterknife:5.1.1'
    // compile 'com.jakewharton:butterknife-compiler:8.4.0'
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragent_layout_pet_friend, null);
        lv_petfriend = ((ListView) v.findViewById(R.id.lv_petfriend));
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void initView() {


        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return friinfoList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = View.inflate(getActivity(), R.layout.list_view_item_activity, null);
                ImageView iv_photo = ((ImageView) view.findViewById(R.id.iv_photo));
                TextView tv_friName = ((TextView) view.findViewById(R.id.tv_friName));
                TextView tv_address = ((TextView) view.findViewById(R.id.tv_address));
                TextView tv_petName = ((TextView) view.findViewById(R.id.tv_petName));
                TextView tv_petkind = ((TextView) view.findViewById(R.id.tv_petkind));

                FriInfoBean.FriInfo friInfo = friinfoList.get(position);
                iv_photo.setImageResource(R.drawable.add);
                tv_friName.setText(friInfo.friName);
                tv_address.setText(friInfo.address);
                tv_petName.setText(friInfo.petName);
                tv_petkind.setText(friInfo.petKind);

                return view;
            }
        };
        lv_petfriend.setAdapter(adapter);

    }

    @Override
    public void initData() {
        getFriInfoList();

    }

    @Override
    public void initEvent() {

    }

    private void getFriInfoList() {

        RequestParams params = new RequestParams(HttpUtils.HOST_COMMUNICATIE + "getfriinfobypage");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                FriInfoBean bean = gson.fromJson(result, FriInfoBean.class);

                System.out.println(bean.status);
                System.out.println(bean.friInfoList);

                friinfoList.addAll(bean.friInfoList);
                adapter.notifyDataSetChanged();
                System.out.println(bean.friInfoList.size() + "------");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString());
                Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_search, R.id.iv_petfriend_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                break;
            case R.id.iv_petfriend_add:
                break;
        }
    }
}
