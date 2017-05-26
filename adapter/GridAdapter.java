package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.R;

import java.util.List;

/**
 * Created by dell on 2017/5/22.
 */

public class GridAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;

    public GridAdapter(List<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
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
        holder holder=null;
        if(convertView==null){
            holder=new holder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.grid_item,null);
            holder.textView= (TextView) convertView.findViewById(R.id.grid_text);
            convertView.setTag(holder);
        }else{
            holder= (GridAdapter.holder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position));
        return convertView;
    }
    class holder{
        private TextView textView;
    }
}
