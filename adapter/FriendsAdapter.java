package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.Friends;
import com.jiyun.kaiyuanzhongguo.utils.GlideCircleTransform;

import java.util.List;

/**
 * Created by dell on 2017/4/12.
 */

public class FriendsAdapter extends BaseAdapter<Friends.FriendBean> {
    public FriendsAdapter(Context context, int layoutId, List<Friends.FriendBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final Friends.FriendBean newsBean) {
        holder.setText(R.id.technology_title,newsBean.getName());
        holder.setText(R.id.technology_body,newsBean.getFrom());
        ImageView imageView=holder.getView(R.id.technology_image);
        if(newsBean.getPortrait()==""){
            Glide.with(context).load(R.mipmap.widget_dface).transform(new GlideCircleTransform(context)).into(imageView);
        }else {
            Glide.with(context).load(newsBean.getPortrait()).transform(new GlideCircleTransform(context)).into(imageView);
        }
        holder.setOnclickListener(R.id.technology_linear, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
