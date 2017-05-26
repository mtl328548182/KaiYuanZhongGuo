package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.Find;
import com.jiyun.kaiyuanzhongguo.utils.GlideCircleTransform;

import java.util.List;

/**
 * Created by dell on 2017/5/10.
 */

public class FindAdapter extends BaseAdapter<Find.UserBean> {

    public FindAdapter(Context context, int layoutId, List<Find.UserBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, Find.UserBean userBean) {
        holder.setText(R.id.find_name,userBean.getName());
        holder.setText(R.id.find_order,userBean.getFrom());
        holder.setText(R.id.find_text,userBean.getGender());
        ImageView view=holder.getView(R.id.find_image);
        String url=userBean.getPortrait();
        if(url.equals("")){
            Glide.with(context).load(R.mipmap.widget_dface).transform(new GlideCircleTransform(context)).into(view);
        }else{
            Glide.with(context).load(url).transform(new GlideCircleTransform(context)).into(view);
        }
    }
}
