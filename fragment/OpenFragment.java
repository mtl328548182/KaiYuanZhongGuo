package com.jiyun.kaiyuanzhongguo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.NewsAdapter;
import com.jiyun.kaiyuanzhongguo.adapter.TestNormalAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.News;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.INewsModel;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.jiyun.kaiyuanzhongguo.utils.XstreamUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static java.security.AccessController.getContext;

/**
 * Created by dell on 2017/5/9.
 */

public class OpenFragment extends BaseFragment {
    @BindView(R.id.News_Recycler)
    PullToRefreshRecyclerView NewsRecycler;
    Unbinder unbinder;
    private List<String> mImgList;
    private List<News.NewsBean> mList=new ArrayList();
    private int pageIndex=0;
    private int pageSize=20;

    @Override
    protected int layoutId() {
        return R.layout.recyclerview_moban;
    }

    @Override
    protected void initView(View view) {
        mImgList=new ArrayList<>();
        for(int i=0;i<5;i++){
            mImgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2592175618,2686141055&fm=23&gp=0.jpg");
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        NewsRecycler.setLayoutManager(layoutManager);
        NewsRecycler.setLoadingMoreEnabled(true);
        NewsRecycler.setPullRefreshEnabled(true);
        View v=LayoutInflater.from(getContext()).inflate(R.layout.viewpager_moban,null);
        RollPagerView rollPagerView= (RollPagerView) v.findViewById(R.id.roll_view_pager);
        //设置播放时间间隔
        rollPagerView.setPlayDelay(3000);
        //设置透明度
        rollPagerView.setAnimationDurtion(500);
        TestNormalAdapter adapter=new TestNormalAdapter(mImgList);
        rollPagerView.setAdapter(adapter);
        rollPagerView.setHintView(new ColorPointHintView(getContext(), Color.YELLOW, Color.WHITE));
        NewsRecycler.addHeaderView(v);
        NewsRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        NewsRecycler.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                pageIndex=0;
                getNews(true);
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getNews(false);
            }
        });
    }
    private void getNews(final boolean boo){
        NewsModel model=new NewsModel();
        model.getNews(1, pageIndex, pageSize, new MyCallback() {
            @Override
            public void onError(String error) {
                Log.e("error",error);
            }

            @Override
            public void onSuccess(String xmldata) {
                Map<String,Class> map=new HashMap<String, Class>();
                map.put("oschina", News.class);
                map.put("news", News.NewsBean.class);
                map.put("newstype", News.NewsBean.NewstypeBean.class);
                News news= (News) XstreamUtils.getInstance().alias(map).fromXML(xmldata);
                List<News.NewsBean> list=news.getNewslist();
                if(boo){
                    mList.addAll(list);
                    NewsAdapter adapter=new NewsAdapter(getContext(),R.layout.news_item,mList);
                    NewsRecycler.setAdapter(adapter);
                    NewsRecycler.setRefreshComplete();
                }else{
                    mList.addAll(list);
                    NewsRecycler.setLoadMoreComplete();
                }
            }
        });
    }
    @Override
    protected void initData() {
        getNews(true);
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
