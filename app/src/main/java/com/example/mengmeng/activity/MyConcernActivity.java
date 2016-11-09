package com.example.mengmeng.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.pojo.User;
import com.example.mengmeng.utils.CommonAdapter;
import com.example.mengmeng.utils.NetUtil;
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

public class MyConcernActivity extends AppCompatActivity  implements View.OnClickListener{

    private ImageView back;
    private ListView listView;

    CommonAdapter<User> userAdapter;
    List<User> Users= new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_concern);
        initView();
        intData();
    }

    private void initView() {
        back = ((ImageView) findViewById(R.id.iv_concern_back));
        back.setOnClickListener(this);

        listView = ((ListView) findViewById(R.id.listview_myconcern));
    }

    private void intData() {
//        http://10.40.5.17:8080/MengmengWeb/QueryFollowByUserIdServlet?userId=1
        RequestParams requestParams = new RequestParams(NetUtil.url + "QueryFollowByUserIdServlet");
        requestParams.addBodyParameter("userId",LoginInfo.userId+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<User>>(){}.getType();
                List<User> newUser=new ArrayList<User>();
                newUser=gson.fromJson(result, type);
                Users.clear();
                Users.addAll(newUser);

                if(userAdapter==null){
                    userAdapter=new CommonAdapter<User>(MyConcernActivity.this,Users,R.layout.listview_myconcern_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, final User user, int position) {
                           ImageView userPhoto= viewHolder.getViewById(R.id.iv_userPhoto);
//                            x.image().bind(userPhoto,NetUtil.photo_url+user.getUserPhoto());
                            xUtilsImageUtils.display(userPhoto,NetUtil.photo_url+user.getUserPhoto(),true);
                            userPhoto.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(MyConcernActivity.this,UserInfoActivity.class);
                                    intent.putExtra("userId",user.getUserId()+"");
                                    startActivity(intent);
                                }
                            });

                            TextView userName=viewHolder.getViewById(R.id.tv_userName);
                            userName.setText(user.getUserName());

                           TextView userWrite= viewHolder.getViewById(R.id.tv_userWrite);
                            userWrite.setText(user.getUserWrite());

                        }
                    };
                    listView.setAdapter(userAdapter);
                }else {
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MyConcernActivity.this, "获取数据失败，请检查网络连接", Toast.LENGTH_SHORT).show();
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
            case R.id.iv_concern_back:
                finish();
                break;
        }
    }
}
