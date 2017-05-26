package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.Event;
import com.jiyun.kaiyuanzhongguo.view.NewsActivity;

import java.util.List;

/**
 * Created by dell on 2017/5/10.
 */

public class EventAdapter extends BaseAdapter<Event.EventBean> {
    public EventAdapter(Context context, int layoutId, List<Event.EventBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final Event.EventBean eventBean) {
        holder.setText(R.id.event_title,eventBean.getTitle());
        holder.setText(R.id.event_time,eventBean.getStartTime());
        ImageView view=holder.getView(R.id.event_image);
        String ulr=eventBean.getCover();
        Glide.with(context).load(ulr).into(view);
        holder.setOnclickListener(R.id.event_Linear, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NewsActivity.class);
                intent.putExtra("id",eventBean.getId());
                intent.putExtra("url",eventBean.getUrl());
                intent.putExtra("type",4);
                context.startActivity(intent);
            }
        });
    }
}
