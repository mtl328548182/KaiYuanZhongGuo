package com.jiyun.kaiyuanzhongguo.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.FavoriteAdapter;
import com.jiyun.kaiyuanzhongguo.adapter.FriendsAdapter;
import com.jiyun.kaiyuanzhongguo.adapter.MoveAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.model.Friends;
import com.jiyun.kaiyuanzhongguo.model.bean.Favorite;
import com.jiyun.kaiyuanzhongguo.model.bean.Tweet;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/4/23.
 */

public class FriendsActivity extends BaseActivity {
    @BindView(R.id.myOne_finish)
    RadioButton myOneFinish;
    @BindView(R.id.myOne_RecyclerView)
    PullToRefreshRecyclerView myOneRecyclerView;
    @BindView(R.id.myOne_title)
    TextView myOneTitle;
    private int load = 10;
    private int pageIndex = 0;
    private int type = 0;
    private int uid = 0;
    private List<Friends.FriendBean> mList = new ArrayList<>();
    private List<Tweet.TweetBean> mListOne = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Favorite oschina;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myone;
    }

    @Override
    protected void init() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOneRecyclerView.setLayoutManager(layoutManager);
        myOneRecyclerView.setPullRefreshEnabled(true);
        myOneRecyclerView.setLoadingMoreEnabled(true);
        myOneRecyclerView.displayLastRefreshTime(true);
        myOneRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            oschina = (Favorite) bundle.getSerializable("favorite");
        }
        uid = intent.getIntExtra("uid", 0);
        type = intent.getIntExtra("type", 0);
        myOneRecyclerView.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                mListOne.clear();
                pageIndex = 0;
                getNews(uid, type, false);
            }

            @Override
            public void onLoadMore() {
                getNews(uid, type, true);
            }
        });
    }

    private void getNews(final int uid, int relation, final Boolean boo) {
        NewsModel model = new NewsModel();
        if (type == 3) {
            model.getTweet(uid + "", "0", "500", new MyCallback() {
                @Override
                public void onSuccess(String jsondate) {
                    XStream xStream = new XStream();
                    xStream.alias("oschina", Tweet.class);
                    xStream.alias("tweet", Tweet.TweetBean.class);
                    xStream.alias("user", Tweet.TweetBean.User.class);
                    Tweet oschina = (Tweet) xStream.fromXML(jsondate);
                    mListOne = oschina.getTweets();
                    if (boo == false) {
                        MoveAdapter adapter = new MoveAdapter(FriendsActivity.this, R.layout.move_moban, mListOne);
                        adapter.init(mListOne);
                        myOneRecyclerView.setAdapter(adapter);
                        myOneRecyclerView.setRefreshComplete();
                        myOneRecyclerView.setPullRefreshEnabled(false);
                        pageIndex++;
                    } else {
                        myOneRecyclerView.setLoadMoreComplete();
                        myOneRecyclerView.setPullRefreshEnabled(true);
                        pageIndex++;
                    }
                }

                @Override
                public void onError(String message) {

                }
            });
        } else if (type == 2) {
            myOneTitle.setText("收藏列表");
            List<Favorite.FavoriteBean> list = oschina.getFavorites();
            if (boo == false) {
                FavoriteAdapter adapter = new FavoriteAdapter(FriendsActivity.this, R.layout.technology_moban, list);
                myOneRecyclerView.setAdapter(adapter);
                myOneRecyclerView.setRefreshComplete();
                myOneRecyclerView.setPullRefreshEnabled(false);
                pageIndex++;
            } else {
                myOneRecyclerView.setLoadMoreComplete();
                myOneRecyclerView.setPullRefreshEnabled(true);
                pageIndex++;
            }
        } else {
            if(relation==0) {
                myOneTitle.setText("我的关注");
            }else{
                myOneTitle.setText("我的粉丝");
            }
            model.getFriends(uid, relation, pageIndex, load, new MyCallback() {
                @Override
                public void onSuccess(String jsondate) {
                    XStream xStream = new XStream();
                    xStream.alias("oschina", Friends.class);
                    xStream.alias("friend", Friends.FriendBean.class);
                    Friends oschina = (Friends) xStream.fromXML(jsondate);
                    List<Friends.FriendBean> list = oschina.getFriends();
                    if (boo == false) {
                        for (int i = 0; i < list.size(); i++) {
                            Friends.FriendBean n = list.get(i);
                            mList.add(n);
                        }
                        FriendsAdapter adapter = new FriendsAdapter(FriendsActivity.this, R.layout.technology_moban, mList);
                        myOneRecyclerView.setAdapter(adapter);
                        myOneRecyclerView.setRefreshComplete();
                        myOneRecyclerView.setPullRefreshEnabled(false);
                        pageIndex++;
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            Friends.FriendBean n = list.get(i);
                            mList.add(n);
                        }
                        myOneRecyclerView.setLoadMoreComplete();
                        myOneRecyclerView.setPullRefreshEnabled(true);
                        pageIndex++;
                    }
                }

                @Override
                public void onError(String message) {

                }
            });
        }
    }

    @Override
    protected void initListener() {
        myOneFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        getNews(uid, type, false);
    }

    @Override
    public TextView getTitletv() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
