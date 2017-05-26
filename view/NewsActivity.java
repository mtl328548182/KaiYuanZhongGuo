package com.jiyun.kaiyuanzhongguo.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.model.bean.BlogDetail;
import com.jiyun.kaiyuanzhongguo.model.bean.NewsDetail;
import com.jiyun.kaiyuanzhongguo.model.bean.PostDetail;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.thoughtworks.xstream.XStream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.id;
import static android.R.attr.type;

/**
 * Created by dell on 2017/5/15.
 */

public class NewsActivity extends BaseActivity {
    @BindView(R.id.news_web)
    WebView newsWeb;
    private String id;
    private int type;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void init() {
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        type=intent.getIntExtra("type",0);
        url=intent.getStringExtra("url");
        if(url!=null) {
            if (!url.substring(0, 4).equals("http")) {
                url = "https://city.oschina.net/beijing/" + url;
            }
        }
        newsWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        NewsModel model=new NewsModel();
        if(type==1) {
            model.getNewsDetail(id, new MyCallback() {
                @Override
                public void onSuccess(String jsondate) {
                    Log.e("aa",jsondate);
                    XStream xStream = new XStream();
                    xStream.alias("oschina", NewsDetail.class);
                    xStream.alias("news", NewsDetail.NewsBean.class);
                    xStream.alias("relative", NewsDetail.NewsBean.RelativeBean.class);
                    NewsDetail newsDetail = (NewsDetail) xStream.fromXML(jsondate);
                    NewsDetail.NewsBean news = newsDetail.getNews();
                    if(news.getUrl()!=null) {
                        newsWeb.loadUrl(news.getUrl());
                    }
                }

                @Override
                public void onError(String message) {

                }
            });
        }else if(type==2){
            model.getBlogDetail(id, new MyCallback() {
                @Override
                public void onSuccess(String jsondate) {
                    XStream xStream = new XStream();
                    xStream.alias("oschina", BlogDetail.class);
                    xStream.alias("blog", BlogDetail.BlogBean.class);
                    BlogDetail oschina= (BlogDetail) xStream.fromXML(jsondate);
                    BlogDetail.BlogBean blog=oschina.getBlog();
                    newsWeb.loadUrl(blog.getUrl());
                }

                @Override
                public void onError(String message) {

                }
            });
        }else if(type==3){
            model.getPostDetail(id, new MyCallback() {
                @Override
                public void onSuccess(String jsondate) {
                    XStream xStream = new XStream();
                    xStream.alias("oschina", PostDetail.class);
                    xStream.alias("post", PostDetail.PostBean.class);
                    PostDetail oschina= (PostDetail) xStream.fromXML(jsondate);
                    PostDetail.PostBean post=oschina.getPost();
                    newsWeb.loadUrl(post.getUrl());
                }

                @Override
                public void onError(String message) {

                }
            });
        }else if(type==4){
            newsWeb.loadUrl(url);
        }
    }

    @Override
    public TextView getTitletv() {
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK && newsWeb.canGoBack()) {  // goBack()表示返回WebView的上一页面

            newsWeb.goBack();

            return true;

        } else {

            //结束当前页
            return super.onKeyDown(keyCode, event);
        }

    }
}
