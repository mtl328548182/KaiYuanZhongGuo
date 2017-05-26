package com.jiyun.kaiyuanzhongguo.fragment.move;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.MoveAdapter;
import com.jiyun.kaiyuanzhongguo.adapter.MoveTwoAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.Tweet;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dell on 2017/4/16.
 */

public class MoveFour extends BaseFragment {
    @BindView(R.id.blog_Recycler)
    PullToRefreshRecyclerView pulltorefreshBlog;
    Unbinder unbinder;
    @BindView(R.id.blog_text)
    TextView blogText;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<Tweet.TweetBean> mList = new ArrayList<>();
    private int load = 10;
    private int pageIndex = 0;
    private MoveTwoAdapter adapter;
    private AdapterView.AdapterContextMenuInfo menuinfo;

    @Override
    protected int layoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initView(View view) {
        sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pulltorefreshBlog.setLayoutManager(linearLayoutManager);
        //是否开启下拉刷新功能
        pulltorefreshBlog.setPullRefreshEnabled(false);
//是否开启上拉加载功能
        pulltorefreshBlog.setLoadingMoreEnabled(true);
        //设置是否显示上次刷新的时间
        pulltorefreshBlog.displayLastRefreshTime(true);
        pulltorefreshBlog.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        pulltorefreshBlog.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 0;
                getNews(false);
            }

            @Override
            public void onLoadMore() {
                mList.clear();
                getNews(true);
            }
        });

    }

    private void getNews(final boolean boo) {
        NewsModel model = new NewsModel();
        int uid=sharedPreferences.getInt("uid",0);
        if(uid==0){
            blogText.setVisibility(View.VISIBLE);
        }else {
            model.getTweet(uid + "", pageIndex + "", load + "", new MyCallback() {
                @Override
                public void onSuccess(String jsondate) {
                    XStream xStream = new XStream();
                    xStream.alias("oschina", Tweet.class);
                    xStream.alias("tweet", Tweet.TweetBean.class);
                    xStream.alias("user", Tweet.TweetBean.User.class);
                    Tweet oschina = (Tweet) xStream.fromXML(jsondate);
                    List<Tweet.TweetBean> listTweet = oschina.getTweets();
                    if (!oschina.getTweetCount().equals("0")) {
                        blogText.setVisibility(View.GONE);
                        pulltorefreshBlog.setVisibility(View.VISIBLE);
                        if (boo == false) {
                            for (int i = 0; i < listTweet.size(); i++) {
                                Tweet.TweetBean n = listTweet.get(i);
                                mList.add(n);
                            }
                            adapter = new MoveTwoAdapter(getContext(), R.layout.move_moban, mList);
                            adapter.init(mList);
                            pulltorefreshBlog.setAdapter(adapter);
                            pulltorefreshBlog.setRefreshComplete();
                            pulltorefreshBlog.setPullRefreshEnabled(false);
                            pageIndex++;
                        } else {
                            for (int i = 0; i < listTweet.size(); i++) {
                                Tweet.TweetBean n = listTweet.get(i);
                                mList.add(n);
                            }
                            adapter.init(mList);
                            adapter.notifyDataSetChanged();
                            pulltorefreshBlog.setLoadMoreComplete();
                            pulltorefreshBlog.setPullRefreshEnabled(false);
                            pageIndex++;
                        }
                    } else {
                        blogText.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onError(String message) {

                }
            });
        }
    }

    @Override
    protected void initData() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

}
