package com.jiyun.kaiyuanzhongguo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jiyun.kaiyuanzhongguo.view.MainActivity;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * Created by dell on 2017/5/9.
 */

public class TestNormalAdapter extends StaticPagerAdapter {
    private List<String> mList;

    public TestNormalAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        Glide.with(container.getContext()).load(mList.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
