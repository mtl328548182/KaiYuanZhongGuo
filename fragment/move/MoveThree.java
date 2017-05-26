package com.jiyun.kaiyuanzhongguo.fragment.move;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dell on 2017/4/16.
 */

public class MoveThree extends BaseFragment {
    @BindView(R.id.blog_Recycler)
    PullToRefreshRecyclerView pulltorefreshBlog;
    Unbinder unbinder;
    @BindView(R.id.blog_text)
    TextView blogText;

    @Override
    protected int layoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    protected void initView(View view) {
        blogText.setVisibility(View.VISIBLE);
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
