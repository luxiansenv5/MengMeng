package com.example.mengmeng.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.activity.LoginInfo;
import com.example.mengmeng.activity.PersonDataActivity;
import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.ContactsInfoBean;
import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.HttpUtils;
import com.example.mengmeng.utils.NetUtil;
import com.example.mengmeng.utils.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;


/**
 * Created by admin on 2016/10/17.
 */
public class PetFriendsFragment extends BaseFragment {

    @InjectView(R.id.lv_petfriend)
    ListView lvPetfriend;
    private BaseAdapter adapter;

    String MyToken;
    User user;

    public static final Integer USERID = LoginInfo.userId;

    final List<ContactsInfoBean> contactsInfoBeanList = new ArrayList<ContactsInfoBean>();

    private ListView lv_petfriend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragent_layout_pet_friend, null);
        lv_petfriend = ((ListView) v.findViewById(R.id.lv_petfriend));
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void initView() {

    }


    @Override
    public void initData() {

        getFriInfoList();
        getTokenByUserId();
    }

    @Override
    public void initEvent() {

        lvPetfriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PersonDataActivity.class);
                intent.putExtra("contactsInfoBean",contactsInfoBeanList.get(position));
                Bundle bundle = new Bundle();
                bundle.putParcelable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);//传送数据到个人数据界面的activity
            }
        });

    }



    private void getTokenByUserId(){
        RequestParams params = new RequestParams(HttpUtils.HOST + "gettokenbyuserid");
        params.addBodyParameter("userId",LoginInfo.userId+"");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<User>(){}.getType();
                user = gson.fromJson(result,type);
                MyToken = user.getToken();

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

    private void getFriInfoList() {

        RequestParams params = new RequestParams(HttpUtils.HOST + "getcontactinfobypage");
        params.addBodyParameter("userId",LoginInfo.userId+"");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<ContactsInfoBean>>(){}.getType();
                List<ContactsInfoBean> newConList =new ArrayList<ContactsInfoBean>();

                newConList= gson.fromJson(result,type);//解析成list<ContactsInfoBean>
                contactsInfoBeanList.clear();
                contactsInfoBeanList.addAll(newConList);
                for (ContactsInfoBean contactsInfoBean : contactsInfoBeanList) {
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(contactsInfoBean.getUser().getUserId()+"",contactsInfoBean.getUser().getUserName(), Uri.parse(NetUtil.photo_url+contactsInfoBean.getUser().getUserPhoto())));
                    Log.e("fimg", "onSuccess: "+contactsInfoBean.getUser().getUserPhoto());
                }
                adapter=new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return contactsInfoBeanList.size();
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

//                        xUtilsImageUtils.display(iv_photo,HttpUtils.HOST_COMMUNICATIE+contactsInfoBeanList.get(position).getUser().getUserPhoto(),true);
                        xUtilsImageUtils.display(iv_photo, NetUtil.photo_url+contactsInfoBeanList.get(position).getUser().getUserPhoto(),true);

                        tv_petName.setText(contactsInfoBeanList.get(position).getPetInfo().petName);
                        tv_friName.setText(contactsInfoBeanList.get(position).getUser().getUserName());
                        tv_address.setText(contactsInfoBeanList.get(position).getUser().getAddress());
                        tv_petkind.setText(contactsInfoBeanList.get(position).getPetInfo().petKind);

                        return view;

                    }
                };
                    lvPetfriend.setAdapter(adapter);

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

    }

