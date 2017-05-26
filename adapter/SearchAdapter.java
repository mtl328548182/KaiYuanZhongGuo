package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.Search;
import com.jiyun.kaiyuanzhongguo.view.NewsActivity;

import java.util.List;

/**
 * Created by dell on 2017/4/12.
 */

public class SearchAdapter extends BaseAdapter<Search.ResultBean> {
    public SearchAdapter(Context context, int layoutId, List<Search.ResultBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final Search.ResultBean newsBean) {
        holder.setText(R.id.news_title,newsBean.getTitle());
        holder.setText(R.id.news_body,newsBean.getDescription());
        holder.setText(R.id.news_author,newsBean.getAuthor());
        holder.setOnclickListener(R.id.news_linear, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NewsActivity.class);
                intent.putExtra("url",newsBean.getUrl());
                intent.putExtra("id",newsBean.getObjid());
                intent.putExtra("type",4);
                context.startActivity(intent);
            }
        });
    }
}
