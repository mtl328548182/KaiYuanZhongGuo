package com.jiyun.kaiyuanzhongguo.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.BlogAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.Blog;
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
 * Created by dell on 2017/5/9.
 */

public class RecommendedFragment extends BaseFragment {
    @BindView(R.id.blog_Recycler)
    PullToRefreshRecyclerView blogRecycler;
    Unbinder unbinder;
    private int pageIndex=0;
    private int pageSize=20;
    private List<Blog.BlogBean> mList=new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.fragment_blog;
    }
    private void getList(final boolean boo){
        NewsModel model=new NewsModel();
        model.getBlog("recommend", pageIndex, pageSize, new MyCallback() {
            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(String xmldata) {
                Map<String,Class> map=new HashMap<String, Class>();
                map.put("oschina", Blog.class);
                map.put("blog", Blog.BlogBean.class);
                Blog blog= (Blog) XstreamUtils.getInstance().alias(map).fromXML(xmldata);
                List<Blog.BlogBean> list=blog.getBlogs();
                if(boo){
                    mList.addAll(list);
                    BlogAdapter adapter=new BlogAdapter(getContext(),R.layout.news_item,mList);
                    blogRecycler.setAdapter(adapter);
                    blogRecycler.setRefreshComplete();
                }else{
                    mList.addAll(list);
                    blogRecycler.setLoadMoreComplete();
                }
            }
        });
    }

    @Override
    protected void initView(View view) {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        blogRecycler.setLayoutManager(layoutManager);
        blogRecycler.setLoadingMoreEnabled(true);
        blogRecycler.setPullRefreshEnabled(true);
        blogRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        blogRecycler.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                pageIndex=0;
                getList(true);
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getList(false);
            }
        });
    }

    @Override
    protected void initData() {
        getList(true);
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
