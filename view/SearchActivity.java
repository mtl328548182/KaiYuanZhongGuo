package com.jiyun.kaiyuanzhongguo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.App;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.ViewpagerAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.fragment.search.SearchFragment;
import com.jiyun.kaiyuanzhongguo.fragment.search.SearchFragment1;
import com.jiyun.kaiyuanzhongguo.fragment.search.SearchFragment2;
import com.jiyun.kaiyuanzhongguo.fragment.search.SearchFragment3;
import com.jiyun.kaiyuanzhongguo.fragment.search.SearchFragment4;
import com.jiyun.kaiyuanzhongguo.model.bean.SearchDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by dell on 2017/4/14.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.search_text)
    TextView searchText;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_list)
    ListView searchList;
    @BindView(R.id.search_tab)
    TabLayout searchTab;
    @BindView(R.id.search_pager)
    ViewPager searchPager;
    @BindView(R.id.search_linear)
    LinearLayout searchLinear;
    private ArrayList<String> mNameList = new ArrayList<>();
    private ArrayList<BaseFragment> mList = new ArrayList<>();
    private ViewpagerAdapter adapter;
    private com.jiyun.kaiyuanzhongguo.adapter.ListAdapter listAdapter;
    private SearchFragment fragment1;
    private SearchFragment1 fragment2;
    private SearchFragment2 fragment3;
    private SearchFragment3 fragment4;
    private SearchFragment4 fragment5;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        final List<SearchDetail> list = App.getDaoInstant().getSearchDetailDao().queryBuilder().build().list();
        listAdapter=new com.jiyun.kaiyuanzhongguo.adapter.ListAdapter(list,this);
        searchList.setAdapter(listAdapter);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fragment1=new SearchFragment();
        fragment2=new SearchFragment1();
        fragment3=new SearchFragment2();
        fragment4=new SearchFragment3();
        fragment5=new SearchFragment4();
        mList.add(fragment1);
        mList.add(fragment2);
        mList.add(fragment3);
        mList.add(fragment4);
        mList.add(fragment5);
        mNameList.add("软件");
        mNameList.add("博客");
        mNameList.add("资讯");
        mNameList.add("问答");
        mNameList.add("找人");
        searchTab.setTabMode(TabLayout.MODE_FIXED);
        adapter = new ViewpagerAdapter(getSupportFragmentManager(),mList,mNameList);
        searchPager.setAdapter(adapter);
        searchTab.addTab(searchTab.newTab().setText(mNameList.get(0)));
        searchTab.addTab(searchTab.newTab().setText(mNameList.get(1)));
        searchTab.addTab(searchTab.newTab().setText(mNameList.get(2)));
        searchTab.addTab(searchTab.newTab().setText(mNameList.get(3)));
        searchTab.addTab(searchTab.newTab().setText(mNameList.get(4)));
        searchTab.setupWithViewPager(searchPager);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=getIntent();
                in.putExtra("content",searchEdit.getText().toString()+"");
                App.getDaoInstant().getSearchDetailDao().insert(new SearchDetail(null,searchEdit.getText().toString()+""));
                searchList.setVisibility(View.GONE);
                searchLinear.setVisibility(View.VISIBLE);
                adapter = new ViewpagerAdapter(getSupportFragmentManager(),mList,mNameList);
                searchPager.setAdapter(adapter);
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    searchList.setVisibility(View.VISIBLE);
                    searchLinear.setVisibility(View.GONE);
                    final List<SearchDetail> list = App.getDaoInstant().getSearchDetailDao().queryBuilder().build().list();
                    listAdapter=new com.jiyun.kaiyuanzhongguo.adapter.ListAdapter(list,SearchActivity.this);
                    searchList.setAdapter(listAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=getIntent();
                in.putExtra("content",list.get(position).getNumber()+"");
                searchList.setVisibility(View.GONE);
                searchLinear.setVisibility(View.VISIBLE);
                adapter = new ViewpagerAdapter(getSupportFragmentManager(),mList,mNameList);
                searchPager.setAdapter(adapter);
            }
        });
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
