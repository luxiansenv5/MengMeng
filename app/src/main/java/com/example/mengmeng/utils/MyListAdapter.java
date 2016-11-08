package com.example.mengmeng.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;

/**
 * Created by 陆猛 on 2016/11/7.
 */
public class MyListAdapter extends BaseAdapter {

    private Context mContext;

    public String[] img_text = { "阿拉斯加雪橇犬", "澳大利亚牧牛犬", "巴哥犬", "比熊犬","博美犬","茶杯犬","柴犬","哈士奇","金毛犬","柯基犬"};
    public int[] imgs = {R.drawable.alsj,R.drawable.adly,R.drawable.bgq,R.drawable.bxq,R.drawable.bmq,R.drawable.cbq,R.drawable.cq,R.drawable.hsq,R.drawable.jmq,R.drawable.kjq};

    public MyListAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.list_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_bake);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_bake);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        iv.setImageResource(imgs[position]);
        return convertView;
    }
}
