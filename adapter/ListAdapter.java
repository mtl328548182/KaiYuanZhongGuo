package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.SearchDetail;

import java.util.List;

/**
 * Created by dell on 2017/4/14.
 */

public class ListAdapter extends BaseAdapter {
    private List<SearchDetail> mList;
    private Context context;

    public ListAdapter(List<SearchDetail> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.list_moban,null);
            holder.textView= (TextView) convertView.findViewById(R.id.List_text);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position).getNumber());
        return convertView;
    }
    class ViewHolder{
        private TextView textView;
    }
}
