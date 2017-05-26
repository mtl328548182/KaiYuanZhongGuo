package com.jiyun.kaiyuanzhongguo.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.GridAdapter;
import com.jiyun.kaiyuanzhongguo.adapter.ViewpagerAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.view.MyGridLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dell on 2017/5/9.
 */

public class ComprehensiveFragment extends BaseFragment {
    @BindView(R.id.info_CheckBox)
    ImageView infoCheckBox;
    @BindView(R.id.info_TabLayout)
    TabLayout infoTabLayout;
    @BindView(R.id.info_Viewpager)
    ViewPager infoViewpager;
    Unbinder unbinder;
    private List<BaseFragment> mList=new ArrayList<>();
    private List<String> mNameList=new ArrayList<>();
    private boolean check=false;
    private List<String> mGridOne=new ArrayList<>();
    private List<String> mGridTwo=new ArrayList<>();
    private int x=0;
    private int y=0;
    private int a=4;
    private ViewpagerAdapter pageradapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected int layoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected void initView(View view) {
        sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        x=infoCheckBox.getLeft();
        y=infoCheckBox.getBottom();
        OpenFragment openFragment=new OpenFragment();
        RecommendedFragment recommendedFragment=new RecommendedFragment();
        DailyFragment dailyFragment=new DailyFragment();
        TechnicalFragment technicalFragment=new TechnicalFragment();
        mList.add(openFragment);
        mList.add(recommendedFragment);
        mList.add(dailyFragment);
        mList.add(technicalFragment);
        mNameList.add("开源资讯");
        mNameList.add("推荐博客");
        mNameList.add("技术问答");
        mNameList.add("每日一博");
        pageradapter=new ViewpagerAdapter(getFragmentManager(),mList,mNameList);
        infoViewpager.setAdapter(pageradapter);
        infoTabLayout.setTag(infoTabLayout.newTab().setText(mNameList.get(0)));
        infoTabLayout.setTag(infoTabLayout.newTab().setText(mNameList.get(1)));
        infoTabLayout.setTag(infoTabLayout.newTab().setText(mNameList.get(2)));
        infoTabLayout.setTag(infoTabLayout.newTab().setText(mNameList.get(3)));
        infoTabLayout.setupWithViewPager(infoViewpager);
        infoTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        if(sharedPreferences.getStringSet("gridone",null)!=null) {
            mGridOne.addAll(sharedPreferences.getStringSet("gridone", null));
            mGridTwo.addAll(sharedPreferences.getStringSet("gridtwo", null));
        }
        if (mGridOne.size() == 0) {
                initList();
            }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        infoCheckBox.setImageResource(R.mipmap.image_add_sel);
        infoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check){
                    check=false;
                }else {
                    popupWindow();
                    check=true;
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void setParams(Bundle bundle) {

    }
    private void popupWindow(){
        View view=LayoutInflater.from(getContext()).inflate(R.layout.popup_moban,null);
        final ImageView image= (ImageView) view.findViewById(R.id.popup_btn);
        image.setImageResource(R.mipmap.image_add_sel);
        TextView text= (TextView) view.findViewById(R.id.popup_delete);
        GridView gridOne= (GridView) view.findViewById(R.id.popup_GridOne);
        GridView gridTwo= (GridView) view.findViewById(R.id.popup_GridTwo);
        final GridAdapter adapter=new GridAdapter(mGridOne,getContext());
        final GridAdapter adapterTwo=new GridAdapter(mGridTwo,getContext());
        gridOne.setAdapter(adapter);
        gridTwo.setAdapter(adapterTwo);
        final PopupWindow popup=new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                image.setImageResource(R.mipmap.ic_subscribe);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        gridOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        gridTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = mGridTwo.get(position);
                mGridTwo.remove(position);
                mGridOne.add(s);
                adapter.notifyDataSetChanged();
                adapterTwo.notifyDataSetChanged();
                mNameList.add(s);
                TestFragment test=new TestFragment();
                mList.add(test);
                infoTabLayout.setTag(infoTabLayout.newTab().setText(mNameList.get(a++)));
                pageradapter.notifyDataSetChanged();
            }
        });
        popup.setTouchable(true);
// 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        popup.setBackgroundDrawable(new ColorDrawable());
// 设置好参数之后再show
        popup.showAtLocation(view, LinearLayoutManager.VERTICAL,x,y);
    }
    private void initList(){
        mGridOne.add("开源资讯");
        mGridOne.add("推荐博客");
        mGridOne.add("技术问答");
        mGridOne.add("每日一博");
        mGridTwo.add("码云推荐");
        mGridTwo.add("最新翻译");
        mGridTwo.add("移动开发");
        mGridTwo.add("开源硬件");
        mGridTwo.add("云计算");
        mGridTwo.add("软件工程");
        mGridTwo.add("系统运维");
        mGridTwo.add("图像多媒体");
        mGridTwo.add("企业开发");
        mGridTwo.add("数据库");
        mGridTwo.add("编程语言");
        mGridTwo.add("游戏开发");
        mGridTwo.add("服务端开发");
        mGridTwo.add("前端开发");
        mGridTwo.add("源创会");
        mGridTwo.add("最新博客");
        mGridTwo.add("热门博客");
        mGridTwo.add("站务建议");
        mGridTwo.add("现实生涯");
        mGridTwo.add("行业杂烩");
        mGridTwo.add("技术分享");
        mGridTwo.add("开源访谈");
        mGridTwo.add("高手问答");
        mGridTwo.add("最新软件");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HashSet<String> set=new HashSet();
        set.addAll(mGridOne);
        HashSet<String> set1=new HashSet();
        set.addAll(mGridTwo);
        editor.putStringSet("gridone",set);
        editor.putStringSet("gridtwo",set1);
        editor.commit();
    }
}
