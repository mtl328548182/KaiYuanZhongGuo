package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.Comment;
import com.jiyun.kaiyuanzhongguo.view.NewsActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/4/12.
 */

public class CommentAdapter extends BaseAdapter<Comment.CommentBean> {
    public CommentAdapter(Context context, List<Comment.CommentBean> datas) {
        super(context,R.layout.technology_moban, datas);
    }

    @Override
    public void convert(ViewHolder holder, final Comment.CommentBean newsBean) {
        holder.setText(R.id.technology_title,newsBean.getAuthor());
        holder.setText(R.id.technology_body,newsBean.getContent());
        ImageView view=holder.getView(R.id.technology_image);
        if(newsBean.getPortrait()=="") {
            Glide.with(context).load(R.mipmap.widget_dface).into(view);
        }else{
            Glide.with(context).load(newsBean.getPortrait()).into(view);
        }
        String time=newsBean.getPubDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d=null;
        try {
             d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long pub=d.getTime();
        Long One=System.currentTimeMillis();
        long timer=(One-pub)/1000/60/60/24;
        if(timer>0) {
            holder.setText(R.id.technology_author, newsBean.getAuthor() + "  " + timer + "天前");
        }else{
            long timeOne=(One-pub)/1000/60/60;
            if(timeOne>=1) {
                holder.setText(R.id.technology_author, newsBean.getAuthor() + "  " + timeOne + "小时前");
            }else{
                long timeTwo=(One-pub)/1000/60;
                holder.setText(R.id.technology_author, newsBean.getAuthor() + "  " + timeTwo + "分钟前");
            }
        }
        holder.setOnclickListener(R.id.technology_linear, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NewsActivity.class);
                intent.putExtra("id",newsBean.getId());
                intent.putExtra("type",1);
                context.startActivity(intent);
            }
        });
    }
}
