package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.Favorite;
import com.jiyun.kaiyuanzhongguo.view.NewsActivity;

import java.util.List;

/**
 * Created by dell on 2017/4/12.
 */

public class FavoriteAdapter extends BaseAdapter<Favorite.FavoriteBean> {
    public FavoriteAdapter(Context context, int layoutId, List<Favorite.FavoriteBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final Favorite.FavoriteBean newsBean) {
        String type=newsBean.getType();
        if(type.equals("1"))
        holder.setText(R.id.technology_title,"软件    "+newsBean.getTitle());
        else if(type.equals("2"))
            holder.setText(R.id.technology_title,"话题    "+newsBean.getTitle());
        else if(type.equals("3"))
            holder.setText(R.id.technology_title,"博客    "+newsBean.getTitle());
        else if(type.equals("4"))
            holder.setText(R.id.technology_title,"新闻    "+newsBean.getTitle());
        else if(type.equals("5"))
            holder.setText(R.id.technology_title,"代码    "+newsBean.getTitle());
        holder.setViewVisiable(R.id.technology_image,View.GONE);
        holder.setOnclickListener(R.id.technology_linear, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NewsActivity.class);
                intent.putExtra("url",newsBean.getUrl());
                intent.putExtra("type",4);
                context.startActivity(intent);
            }
        });
    }
}
