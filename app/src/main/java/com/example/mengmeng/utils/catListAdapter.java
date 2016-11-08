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
public class catListAdapter extends BaseAdapter {

    private Context mContext;

    public String[] img_text = { "埃及猫", "波斯猫", "布偶猫", "短腿猫","俄罗斯蓝猫","虎豹猫","浣熊猫","加菲猫","腊肠猫","兔猫"};
    public int[] imgs = {R.drawable.ajm,R.drawable.bsm,R.drawable.bom,R.drawable.dtm,R.drawable.elslm,R.drawable.hbm,R.drawable.hxm,R.drawable.jfm,R.drawable.lcm,R.drawable.tm};

    public catListAdapter(Context mContext) {
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
