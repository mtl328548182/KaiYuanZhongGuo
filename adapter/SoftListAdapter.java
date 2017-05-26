package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.SoftTag;
import com.jiyun.kaiyuanzhongguo.view.NewsActivity;


import java.util.List;

/**
 * Created by dell on 2017/4/12.
 */

public class SoftListAdapter extends BaseAdapter<SoftTag.SoftwareBean> {
    public SoftListAdapter(Context context, int layoutId, List<SoftTag.SoftwareBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final SoftTag.SoftwareBean newsBean) {
        holder.setText(R.id.Soft_text,newsBean.getName());
        holder.setOnclickListener(R.id.Soft_linear, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NewsActivity.class);
                intent.putExtra("type",4);
                intent.putExtra("id",newsBean.getId());
                intent.putExtra("url",newsBean.getUrl());
                context.startActivity(intent);
            }
        });
    }
}
