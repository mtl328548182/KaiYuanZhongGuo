package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.News;
import com.jiyun.kaiyuanzhongguo.view.NewsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/5/9.
 */

public class NewsAdapter extends BaseAdapter<News.NewsBean> {

    public NewsAdapter(Context context, int layoutId, List<News.NewsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final News.NewsBean newsBean) {
        holder.setText(R.id.news_title,newsBean.getTitle());
        holder.setText(R.id.news_order,newsBean.getBody());
        holder.setText(R.id.news_tatle,newsBean.getCommentCount());
        holder.setViewVisiable(R.id.news_topimage, View.GONE);
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
            holder.setViewVisiable(R.id.news_imageThree, View.GONE);
            holder.setText(R.id.news_name,  newsBean.getAuthor()+"  "+timer + "天前");
        }else{
            holder.setViewVisiable(R.id.news_imageThree, View.VISIBLE);
            long timeOne=(One-pub)/1000/60/60;
            if(timeOne>=1) {
                holder.setText(R.id.news_name,  newsBean.getAuthor()+"  "+timeOne + "小时前");
            }else{
                long timeTwo=(One-pub)/1000/60;
                holder.setText(R.id.news_name,  newsBean.getAuthor()+"  "+timeTwo + "分钟前");
            }
        }
        holder.setOnclickListener(R.id.news_Linear, new View.OnClickListener() {
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
