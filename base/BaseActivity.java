package com.jiyun.kaiyuanzhongguo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.App;
import com.jiyun.kaiyuanzhongguo.R;

import butterknife.ButterKnife;

/**
 * Created by dell on 2017/5/8.
 */

public abstract class BaseActivity extends AppCompatActivity {


    //记录上一个Fragment  记录要隐藏的Fragment
    private BaseFragment lastFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init();
        initListener();
        loadData();

    }

    /**
     * 用于fragment切换
     * @param targetFragment 要跳转的目标Fragment
     * @param params 跳转页面携带的参数
     * @param isBack 是否通过back键返回
     */
    public void startFragment(BaseFragment targetFragment,Bundle params,boolean isBack){

        if(targetFragment == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //传递参数
        if(params != null)
            targetFragment.setParams(params);

        //隐藏上一个fragment
        if(lastFragment != null)
            transaction.hide(lastFragment);

        //判断是否已经添加 如果没有添加过就添加
        if(!targetFragment.isAdded())
            transaction.add(R.id.frameLayout,targetFragment, targetFragment.getClass().getSimpleName());

        //已经添加就调用show方法显示
        transaction.show(targetFragment);

        //添加到回退栈
        if (isBack)
            transaction.addToBackStack(null);

        transaction.commit();

        //记录上一个fragment
        lastFragment = targetFragment;
    }

    //加载布局
    protected abstract int getLayoutId();

    //初始化
    protected abstract void init();

    //初始化监听
    protected abstract void initListener();

    //加载数据
    protected abstract void loadData();
    public abstract TextView getTitletv();

    @Override
    protected void onResume() {
        super.onResume();
        App.activity=this;
    }
}
