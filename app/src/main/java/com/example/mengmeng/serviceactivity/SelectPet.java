package com.example.mengmeng.serviceactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;

public class SelectPet extends AppCompatActivity {

    private ListView lv_publish;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pet);

        lv_publish = ((ListView) findViewById(R.id.lv_publish));

        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return 1;
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

                View view=View.inflate(getApplicationContext(),R.layout.activity_selectpet_listview_item,null);
                ImageView iv_petPhoto=((ImageView) view.findViewById(R.id.iv_petPhoto));
                TextView tv_petName=((TextView) view.findViewById(R.id.tv_petName));
                TextView tv_petType=((TextView) view.findViewById(R.id.tv_petType));
                TextView tv_petAge=((TextView) view.findViewById(R.id.tv_petAge));

                tv_petName.setText("二哈");
                tv_petType.setText("哈士奇");
                tv_petAge.setText("2岁");

                return view;
            }
        };

        lv_publish.setAdapter((ListAdapter) adapter);

        lv_publish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

}
