package com.jiyun.kaiyuanzhongguo.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.ViewpagerAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.fragment.soft.SoftFragment;
import com.jiyun.kaiyuanzhongguo.fragment.soft.SoftListFragment;
import com.jiyun.kaiyuanzhongguo.fragment.soft.SoftListFragment1;
import com.jiyun.kaiyuanzhongguo.fragment.soft.SoftListFragment2;
import com.jiyun.kaiyuanzhongguo.fragment.soft.SoftListFragment3;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dell on 2017/4/17.
 */

public class SoftActivity extends BaseActivity {
    @BindView(R.id.move_tab)
    TabLayout moveTab;
    @BindView(R.id.move_pager)
    ViewPager movePager;
    private List<BaseFragment> mList=new ArrayList<>();
    private List<String> mNameList=new ArrayList<>();
    private Fragment fg;
    private ViewpagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_move;
    }

    @Override
    protected void init() {
        SoftFragment softFragment=new SoftFragment();
        SoftListFragment fragment=new SoftListFragment();
        SoftListFragment1 fragment1=new SoftListFragment1();
        SoftListFragment2 fragment2=new SoftListFragment2();
        SoftListFragment3 fragment3=new SoftListFragment3();
        mList.add(softFragment);
        mList.add(fragment);
        mList.add(fragment1);
        mList.add(fragment2);
        mList.add(fragment3);
        mNameList.add("分类");
        mNameList.add("推荐");
        mNameList.add("最新");
        mNameList.add("热门");
        mNameList.add("国产");
        adapter=new ViewpagerAdapter(getSupportFragmentManager(),mList,mNameList);
        movePager.setAdapter(adapter);
        moveTab.addTab(moveTab.newTab().setText(mNameList.get(0)));
        moveTab.addTab(moveTab.newTab().setText(mNameList.get(1)));
        moveTab.addTab(moveTab.newTab().setText(mNameList.get(2)));
        moveTab.addTab(moveTab.newTab().setText(mNameList.get(3)));
        moveTab.addTab(moveTab.newTab().setText(mNameList.get(4)));
        moveTab.setupWithViewPager(movePager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
    }


    @Override
    public TextView getTitletv() {
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        fg=adapter.getItem(movePager.getCurrentItem());
        if(fg instanceof SoftFragment){
           return ((SoftFragment)fg).onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode,event);
    }
}
