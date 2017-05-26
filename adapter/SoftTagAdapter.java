package com.jiyun.kaiyuanzhongguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.bean.SoftTag;

import java.util.List;

/**
 * Created by dell on 2017/4/17.
 */

public class SoftTagAdapter extends BaseAdapter {
    private List<SoftTag.SoftwareBean> mList;

    public void setmList(List<SoftTag.SoftwareBean> mList) {
        this.mList = mList;
    }

    private Context context;

    public SoftTagAdapter(List<SoftTag.SoftwareBean> mList, Context context) {
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
        ViewHondler hondler=null;
        if(convertView==null){
            hondler=new ViewHondler();
            convertView= LayoutInflater.from(context).inflate(R.layout.soft_moban,null);
            hondler.text= (TextView) convertView.findViewById(R.id.Soft_text);
            convertView.setTag(hondler);
        }else{
            hondler= (ViewHondler) convertView.getTag();
        }
        hondler.text.setText(mList.get(position).getName());
        return convertView;
    }
    class ViewHondler{
        private TextView text;
    }
}
