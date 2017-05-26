package com.jiyun.kaiyuanzhongguo.fragment.soft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.SoftAdapter;
import com.jiyun.kaiyuanzhongguo.adapter.SoftTagAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.Soft;
import com.jiyun.kaiyuanzhongguo.model.bean.SoftTag;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.jiyun.kaiyuanzhongguo.view.NewsActivity;
import com.thoughtworks.xstream.XStream;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by dell on 2017/4/17.
 */

public class SoftFragment extends BaseFragment {
    private ListView softList;
    private List<Soft.SoftwareTypeBean> mList;
    private List<SoftTag.SoftwareBean> softwareBeen;
    private SoftAdapter adapter;
    private int a = 0;
    private String tag1;

    @Override
    protected int layoutId() {
        return R.layout.activity_soft;
    }

    @Override
    protected void initView(View view) {
        softList= (ListView) view.findViewById(R.id.soft_list);
    }

    private void getNew() {
        NewsModel model = new NewsModel();
        model.getSoftType("0", new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                XStream xStream = new XStream();
                xStream.alias("oschina", Soft.class);
                xStream.alias("softwareType", Soft.SoftwareTypeBean.class);
                Soft oschina = (Soft) xStream.fromXML(jsondate);
                mList = oschina.getSoftwareTypes();
                adapter = new SoftAdapter(mList, getContext());
                softList.setAdapter(adapter);
                a = 1;
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void getNews(String tag) {
        NewsModel model = new NewsModel();
        model.getSoftTypes(tag, new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                XStream xStream = new XStream();
                xStream.alias("oschina", Soft.class);
                xStream.alias("softwareType", Soft.SoftwareTypeBean.class);
                Soft oschina = (Soft) xStream.fromXML(jsondate);
                mList = oschina.getSoftwareTypes();
                adapter.setmList(mList);
                adapter.notifyDataSetChanged();
                a = 2;
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void getTags(String tag, String pageIndex, String pageSize) {
        NewsModel model = new NewsModel();
        model.getTags(tag, pageIndex, pageSize, new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                XStream xStream = new XStream();
                xStream.alias("oschina", SoftTag.class);
                xStream.alias("software", SoftTag.SoftwareBean.class);
                SoftTag oschina = (SoftTag) xStream.fromXML(jsondate);
                softwareBeen = oschina.getSoftwares();
                SoftTagAdapter softTagAdapter = new SoftTagAdapter(softwareBeen, getContext());
                softList.setAdapter(softTagAdapter);
                a = 3;
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    protected void initData() {
        getNew();
    }

    @Override
    protected void initListener() {
        softList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (a == 1) {
                    tag1 = mList.get(position).getTag();
                    getNews(mList.get(position).getTag());
                } else if (a == 2) {
                    getTags(mList.get(position).getTag(), "0", "40");
                } else if (a == 3) {
                    Intent intent = new Intent(getActivity(), NewsActivity.class);
                    intent.putExtra("url", softwareBeen.get(position).getUrl());
                    intent.putExtra("type", 4);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void setParams(Bundle bundle) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            if(a==3){
                softList.setAdapter(adapter);
                a=2;
                return false;
            }else if(a==2){
                getNew();
                return false;
            }else if(a==1){
                Log.d("cccc","aaaaaaa");
                getActivity().finish();
            }
        }
        return true;
    }
}
