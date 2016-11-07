package com.example.mengmeng.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.pojo.SingleComment;

import java.util.List;

/**
 * Created by 陆猛 on 2016/10/31.
 */
public class CommentAdapter extends BaseAdapter {

    Context context;
    List<SingleComment> data;

    public CommentAdapter(Context c, List<SingleComment> data){
        this.context = c;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // 重用convertView
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            holder.commentatorName = (TextView) convertView.findViewById(R.id.commentatorName);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.commentatorPhoto= (ImageView) convertView.findViewById(R.id.commentatorPhoto);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        // 适配数据
        holder.commentatorName.setText(data.get(position).getCommentatorName());
        System.out.println("commentatorName===="+data.get(position).getCommentatorName());
        holder.content.setText(data.get(position).getContent());
        xUtilsImageUtils.display(holder.commentatorPhoto,HttpUtils.HOST+data.get(position).getCommentatorPhoto(),true);


        return convertView;
    }

    /**
     * 添加一条评论,刷新列表
     * @param comment
     */
    public void addComment(SingleComment comment){
        data.add(comment);
        notifyDataSetChanged();
    }

    /**
     * 静态类，便于GC回收
     */
    public static class ViewHolder{
        TextView commentatorName;
        TextView content;
        ImageView commentatorPhoto;
    }
}
