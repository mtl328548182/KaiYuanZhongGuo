package com.jiyun.kaiyuanzhongguo.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.FindAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.Find;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.jiyun.kaiyuanzhongguo.utils.XstreamUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by dell on 2017/5/10.
 */

public class SearchFragment4 extends BaseFragment {
    @BindView(R.id.blog_Recycler)
    PullToRefreshRecyclerView pulltorefreshBlog;
    Unbinder unbinder;
    private List<Find.UserBean> mList = new ArrayList<>();
    private int load = 10;
    private int pageIndex = 0;
    private String content;

    @Override
    protected int layoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initView(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pulltorefreshBlog.setLayoutManager(linearLayoutManager);
        //是否开启下拉刷新功能
        pulltorefreshBlog.setPullRefreshEnabled(true);
//是否开启上拉加载功能
        pulltorefreshBlog.setLoadingMoreEnabled(true);
        //设置是否显示上次刷新的时间
        pulltorefreshBlog.displayLastRefreshTime(true);
        pulltorefreshBlog.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        pulltorefreshBlog.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                pageIndex = 0;
                getList(false);
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getList(true);
            }
        });
    }
    private void getList(final boolean boo){
        NewsModel model = new NewsModel();
        Intent in = getActivity().getIntent();
        String content = in.getStringExtra("content");
        model.getUser(content,pageIndex,new MyCallback() {
            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(String xmldata) {
                Map<String,Class> map=new HashMap<String, Class>();
                map.put("oschina", Find.class);
                map.put("user", Find.UserBean.class);
                Find find= (Find) XstreamUtils.getInstance().alias(map).fromXML(xmldata);
                List<Find.UserBean> list=find.getUsers();
                if(boo){
                    mList.addAll(list);
                    pulltorefreshBlog.setLoadMoreComplete();
                }else{
                    mList.addAll(list);
                    FindAdapter adapter=new FindAdapter(getContext(),R.layout.find_item,mList);
                    pulltorefreshBlog.setAdapter(adapter);
                    pulltorefreshBlog.setRefreshComplete();
                }
            }
        });
    }

    @Override
    protected void initData() {
        getList(false);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void setParams(Bundle bundle) {

    }

}
