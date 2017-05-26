package com.jiyun.kaiyuanzhongguo.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.EventAdapter;
import com.jiyun.kaiyuanzhongguo.adapter.TestNormalAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.model.bean.Event;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.jiyun.kaiyuanzhongguo.utils.XstreamUtils;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.list;

/**
 * Created by dell on 2017/5/10.
 */

public class EventActivity extends BaseActivity {
    @BindView(R.id.event_finish)
    ImageView eventFinish;
    @BindView(R.id.event_title)
    TextView eventTitle;
    @BindView(R.id.bullet_relative)
    RelativeLayout bulletRelative;
    @BindView(R.id.event_RecyclerView)
    PullToRefreshRecyclerView eventRecyclerView;
    private List<Event.EventBean> mList=new ArrayList<>();
    private int pageIndex=0;
    private int pageSize=20;
    private RollPagerView rollPagerView;
    private List<String> mImgList=new ArrayList<>();
    private TestNormalAdapter testAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event;
    }

    @Override
    protected void init() {
        getimg();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        eventRecyclerView.setLayoutManager(layoutManager);
        eventRecyclerView.setLoadingMoreEnabled(true);
        eventRecyclerView.setPullRefreshEnabled(true);
        View v= LayoutInflater.from(this).inflate(R.layout.viewpager_moban,null);
        RollPagerView rollPagerView= (RollPagerView) v.findViewById(R.id.roll_view_pager);
        //设置播放时间间隔
        rollPagerView.setPlayDelay(3000);
        //设置透明度
        rollPagerView.setAnimationDurtion(500);
        testAdapter = new TestNormalAdapter(mImgList);
        rollPagerView.setAdapter(testAdapter);
        testAdapter.notifyDataSetChanged();
        rollPagerView.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
        eventRecyclerView.addHeaderView(v);
        eventRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        eventRecyclerView.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                pageIndex=0;
                getList(true);
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getList(false);
            }
        });
    }
    private void getList(final boolean boo){
        NewsModel model=new NewsModel();
        model.getEvent(pageIndex, 0, pageSize, new MyCallback() {
            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(String xmldata) {
                Map<String,Class> map=new HashMap<String, Class>();
                map.put("oschina",Event.class);
                map.put("event", Event.EventBean.class);
                Event event= (Event) XstreamUtils.getInstance().alias(map).fromXML(xmldata);
                List<Event.EventBean> list=event.getEvents();
                if(boo){
                    mList.addAll(list);
                    EventAdapter adapter=new EventAdapter(EventActivity.this,R.layout.event_item,mList);
                    eventRecyclerView.setAdapter(adapter);
                    eventRecyclerView.setRefreshComplete();
                }else{
                    mList.addAll(list);
                    eventRecyclerView.setLoadMoreComplete();
                }
            }
        });
    }
    private void getimg(){
        NewsModel model=new NewsModel();
        model.getEvent(pageIndex, 0, pageSize, new MyCallback() {
            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(String xmldata) {
                Map<String,Class> map=new HashMap<String, Class>();
                map.put("oschina",Event.class);
                map.put("event", Event.EventBean.class);
                Event event= (Event) XstreamUtils.getInstance().alias(map).fromXML(xmldata);
                List<Event.EventBean> list=event.getEvents();
                for(int i=0;i<5;i++){
                    Log.e("aaa",list.get(i).getCover());
                    mImgList.add(list.get(i).getCover());
                }
                testAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initListener() {
        eventFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        getList(true);
    }

    @Override
    public TextView getTitletv() {
        return null;
    }

}
