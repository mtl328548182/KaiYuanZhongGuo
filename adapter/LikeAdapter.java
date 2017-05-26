package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.Tweet;
import com.jiyun.kaiyuanzhongguo.utils.GlideCircleTransform;

import java.util.List;

/**
 * Created by dell on 2017/5/16.
 */

public class LikeAdapter extends BaseAdapter<Tweet.TweetBean.User> {
    public LikeAdapter(Context context, List<Tweet.TweetBean.User> datas) {
        super(context, R.layout.like_item, datas);
    }

    @Override
    public void convert(ViewHolder holder, Tweet.TweetBean.User user) {
        holder.setText(R.id.like_name,user.getName());
        ImageView imageView=holder.getView(R.id.like_image);
        String url=user.getPortrait();
        if(url.equals("")){
            Glide.with(context).load(R.mipmap.widget_dface).transform(new GlideCircleTransform(context)).into(imageView);
        }else{
            Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(imageView);
        }
    }
}
