package com.jiyun.kaiyuanzhongguo.fragment.move;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.ViewpagerAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dell on 2017/4/11.
 */

public class MoveFragment extends BaseFragment {
    @BindView(R.id.move_tab)
    TabLayout moveTab;
    @BindView(R.id.move_pager)
    ViewPager movePager;
    Unbinder unbinder;
    private List<BaseFragment> mList=new ArrayList<>();
    private List<String> mNameList=new ArrayList<>();
    private MoveOne moveOne;
    private MoveTwo moveTwo;
    private MoveThree moveThree;
    private MoveFour moveFour;

    @Override
    protected int layoutId() {
        return R.layout.activity_move;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        moveOne=new MoveOne();
        moveTwo=new MoveTwo();
        moveThree=new MoveThree();
        moveFour=new MoveFour();
        mList.add(moveOne);
        mList.add(moveTwo);
        mList.add(moveThree);
        mList.add(moveFour);
        mNameList.add("最新动弹");
        mNameList.add("热门动弹");
        mNameList.add("每日乱弹");
        mNameList.add("我的动弹");
        ViewpagerAdapter adapter=new ViewpagerAdapter(getFragmentManager(),mList,mNameList);
        movePager.setAdapter(adapter);
        moveTab.addTab(moveTab.newTab().setText(mNameList.get(0)));
        moveTab.addTab(moveTab.newTab().setText(mNameList.get(1)));
        moveTab.addTab(moveTab.newTab().setText(mNameList.get(2)));
        moveTab.addTab(moveTab.newTab().setText(mNameList.get(3)));
        moveTab.setupWithViewPager(movePager);
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
