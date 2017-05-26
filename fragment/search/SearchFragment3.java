package com.jiyun.kaiyuanzhongguo.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.SearchAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.Search;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by dell on 2017/4/6.
 */

public class SearchFragment3 extends BaseFragment {
    @BindView(R.id.blog_Recycler)
    PullToRefreshRecyclerView pulltorefreshBlog;
    Unbinder unbinder;
    private List<Search.ResultBean> mList = new ArrayList<>();
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
                getNews(false);
            }

            @Override
            public void onLoadMore() {
                getNews(true);
            }
        });
    }

    private void getNews(final boolean boo) {
        NewsModel model = new NewsModel();
        Intent in = getActivity().getIntent();
        String content = in.getStringExtra("content");
        model.getSearch(content, "news", new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                XStream xStream = new XStream();
                xStream.alias("oschina", Search.class);
                xStream.alias("result", Search.ResultBean.class);
                Search oschina = (Search) xStream.fromXML(jsondate);
                List<Search.ResultBean> list = oschina.getResults();
                if (boo == false) {
                    for (int i = 0; i < list.size(); i++) {
                        Search.ResultBean n = list.get(i);
                        mList.add(n);
                    }
                    SearchAdapter adapter = new SearchAdapter(getContext(), R.layout.news_moban, mList);
                    pulltorefreshBlog.setAdapter(adapter);
                    pulltorefreshBlog.setRefreshComplete();
                    pulltorefreshBlog.setPullRefreshEnabled(false);
                    pageIndex++;
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        Search.ResultBean n = list.get(i);
                        mList.add(n);
                    }
                    pulltorefreshBlog.setLoadMoreComplete();
                    pulltorefreshBlog.setPullRefreshEnabled(true);
                    pageIndex++;
                }
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    protected void initData() {
        mList.clear();
        getNews(false);
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
