package com.example.mengmeng.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mengmeng.activity.R;
import com.example.mengmeng.activity.UserInfoActivity;
import com.example.mengmeng.pojo.Remark;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class ReplayAdapter extends BaseAdapter {

    private Context context;
    private List<Remark> list;
    private Handler handler;
    private int commentPosition;
    public ReplayAdapter(Context context, Handler handler, List<Remark> list, int commentPosition){
        this.context = context;
        this.list = list;
        this.handler = handler;
        this.commentPosition = commentPosition;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Remark replay = list.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_remarks_item,
                    null);
            viewHolder = new ViewHolder();

            viewHolder.txt_comment = (TextView) convertView
                    .findViewById(R.id.txt_comment);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // item赋值
        //给相应位置的文字赋内容
        if (replay!=null) {
            StringBuilder actionText = new StringBuilder();

            //谁回复
            actionText.append("<a style=\"text-decoration:none;\" href='name' ><font color='#1468a3'>"
                    + replay.getUser().getUserName() + "</font> </a>");

            // 回复谁，被回复的人可能不存在。
            if(replay.getFatherUser()!=null && replay.getFatherUser().getUserName()!=null&&replay.getFatherUser().getUserName()!=replay.user.getUserName()) {
                actionText.append("回复");
                actionText.append("<font color='#1468a3'><a style=\"text-decoration:none;\" href='toName'>"
                        + replay.getFatherUser().getUserName()+ " " + " </a></font>");
            }
            // 内容
            actionText.append("<font color='#484848'><a style=\"text-decoration:none;\" href='content'>"
                    + ":" + replay.getRemarkContent()+ " " + " </a></font>");

            viewHolder.txt_comment.setText(Html.fromHtml(actionText.toString()));
            viewHolder.txt_comment.setMovementMethod(LinkMovementMethod
                    .getInstance());
            CharSequence text = viewHolder.txt_comment.getText();
            int ends = text.length();
            Spannable spannable = (Spannable) viewHolder.txt_comment.getText();
            URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);
            SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
            stylesBuilder.clearSpans();

            for (URLSpan url : urlspan) {
                if (replay.getFatherUser()!=null) {
                    FeedTextViewURLSpan myURLSpan = new FeedTextViewURLSpan(url.getURL(),
                            context, replay.getUser().getUserName(), replay.getFatherUser().getUserName(), replay.getRemarkContent(), position);
                    stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
                            spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }else {
                    FeedTextViewURLSpan myURLSpan = new FeedTextViewURLSpan(url.getURL(),
                            context,replay.user.getUserName(),replay.remarkContent,position);
                    stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
                            spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            viewHolder.txt_comment.setText(stylesBuilder);
            viewHolder.txt_comment.setFocusable(false);
            viewHolder.txt_comment.setClickable(false);
            viewHolder.txt_comment.setLongClickable(false);

        }

        return convertView;
    }

    class FeedTextViewURLSpan extends ClickableSpan {
        private String clickString;
        private Context context;
        // 回复人的名字
        private String name;
        // 被回复人的名字
        private String toName;
        // 评论内容
        private String content;

        private int position;

        public FeedTextViewURLSpan(String clickString, Context context, String name, String toName, String content,int position) {
            this.clickString = clickString;
            this.context = context;
            this.name = name;
            this.toName = toName;
            this.content = content;
            this.position = position;
        }

        public FeedTextViewURLSpan(String clickString, Context context, String name, String content, int position) {
            this.clickString = clickString;
            this.context = context;
            this.name = name;
            this.content = content;
            this.position = position;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
            //给标记的部分 的文字 添加颜色
            if(clickString.equals("toName")){
                ds.setColor(Color.BLUE);
            }else if(clickString.equals("name")){
                ds.setColor(Color.BLUE);
            }
        }

        @Override
        public void onClick(View widget) {
            // 根据文字的标记 来进行相应的 响应事件
            if (clickString.equals("toName")) {
                //可以再次进行跳转activity的操作
                Toast.makeText(context,"点击了"+toName,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, UserInfoActivity.class);
                intent.putExtra("userName",toName);
                context.startActivity(intent);
                //跳闸
            } else if (clickString.equals("name")) {
                //可以再次进行跳转activity的操作
                Toast.makeText(context,"点击了"+name,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, UserInfoActivity.class);
                intent.putExtra("userName",name);
                context.startActivity(intent);
            } else if(clickString.equals("content")){
                //可以再次进去回复评论的操作
                Toast.makeText(context,"点击了"+content,Toast.LENGTH_SHORT).show();
                handler.sendMessage(handler.obtainMessage(11,commentPosition+" "+(position+1)));
            }

        }
    }

    class ViewHolder{
            TextView txt_comment;
    }
}
