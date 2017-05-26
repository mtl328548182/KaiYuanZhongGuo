package com.jiyun.kaiyuanzhongguo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.jiyun.kaiyuanzhongguo.base.BaseFragment;

import java.util.List;

/**
 * Created by dell on 2017/5/9.
 */

public class ViewpagerAdapter extends FragmentPagerAdapter {
private List<BaseFragment> mList;
    private List<String> mNameList;

    public ViewpagerAdapter(FragmentManager fm, List<BaseFragment> mList, List<String> mNameList) {
        super(fm);
        this.mList = mList;
        this.mNameList = mNameList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNameList.get(position);
    }
}
