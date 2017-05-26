package com.jiyun.kaiyuanzhongguo.fragment.find;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.view.EventActivity;
import com.jiyun.kaiyuanzhongguo.Sao1SaoActivity;
import com.jiyun.kaiyuanzhongguo.view.ShakeActivity;
import com.jiyun.kaiyuanzhongguo.view.SoftActivity;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by dell on 2017/5/10.
 */

public class FindFragment extends BaseFragment {
    @BindView(R.id.Yards_cloud_recommend)
    RelativeLayout YardsCloudRecommend;
    @BindView(R.id.scan)
    RelativeLayout scan;
    @BindView(R.id.shake)
    RelativeLayout shake;
    @BindView(R.id.Open_source_software)
    RelativeLayout OpenSourceSoftware;
    @BindView(R.id.Near_the_programmer)
    RelativeLayout NearTheProgrammer;
    @BindView(R.id.Offline_activities)
    RelativeLayout OfflineActivities;
    Unbinder unbinder;

    @Override
    protected int layoutId() {
        return R.layout.activity_find;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SoftActivity.class);
                startActivity(intent);
            }
        });
        OfflineActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), EventActivity.class);
                startActivity(intent);

            }
        });
        OpenSourceSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ShakeActivity.class);
                startActivity(intent);
            }
        });
        shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Sao1SaoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void setParams(Bundle bundle) {

    }

}
