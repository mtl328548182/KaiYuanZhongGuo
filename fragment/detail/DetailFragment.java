package com.jiyun.kaiyuanzhongguo.fragment.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.CommentAdapter;
import com.jiyun.kaiyuanzhongguo.adapter.LikeAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.Comment;
import com.jiyun.kaiyuanzhongguo.model.bean.Tweet;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.jiyun.kaiyuanzhongguo.utils.XstreamUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dell on 2017/5/16.
 */

public class DetailFragment extends BaseFragment {
    @BindView(R.id.blog_text)
    TextView blogText;
    @BindView(R.id.blog_Recycler)
    PullToRefreshRecyclerView blogRecycler;
    Unbinder unbinder;
    private int type;
    private int pageIndex=0;
    private int pageSize=20;

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initView(View view) {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        blogRecycler.setLayoutManager(layoutManager);
        blogRecycler.setLoadingMoreEnabled(false);
        blogRecycler.setPullRefreshEnabled(false);
        getNews();
    }
private void getNews(){
    if(type==1){
        Intent intent=getActivity().getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        Tweet.TweetBean tweet= (Tweet.TweetBean) bundle.getSerializable("tweet");
        List<Tweet.TweetBean.User> likeList = tweet.getLikeList();
        LikeAdapter adapter=new LikeAdapter(getContext(),likeList);
        blogRecycler.setAdapter(adapter);
    }else if(type==2){
        Intent intent=getActivity().getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        Tweet.TweetBean tweet= (Tweet.TweetBean) bundle.getSerializable("tweet");
        int id = Integer.parseInt(tweet.getId());
        NewsModel model=new NewsModel();
        model.getComment(3, id, pageIndex, pageSize, new MyCallback() {
            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(String xmldata) {
                Map<String,Class> map=new HashMap<String, Class>();
                map.put("oschina", Comment.class);
                map.put("comment", Comment.CommentBean.class);
                Comment comment= (Comment) XstreamUtils.getInstance().alias(map).fromXML(xmldata);
                List<Comment.CommentBean> comments = comment.getComments();
                CommentAdapter adapter=new CommentAdapter(getContext(),comments);
                blogRecycler.setAdapter(adapter);
            }
        });
    }
}
    @Override
    protected void initData() {
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
}
