package com.jiyun.kaiyuanzhongguo.model.http;

import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.urls.Urls;
import com.jiyun.kaiyuanzhongguo.utils.Factory;
import com.jiyun.kaiyuanzhongguo.utils.FactoryIn;
import com.jiyun.kaiyuanzhongguo.utils.OkhttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/5/8.
 */

public class NewsModel implements INewsModel {
    @Override
    public void getNews(int catalog, int pageIndex, int pageSize, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("catalog",catalog+"");
        map.put("pageIndex",pageIndex+"");
        map.put("pageSize",pageSize+"");
        OkhttpUtils.getInstance().get(Urls.NEWS_LIST,map,callback);
    }

    @Override
    public void getBlog(String type, int pageIndex, int pageSize, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("type",type+"");
        map.put("pageIndex",pageIndex+"");
        map.put("pageSize",pageSize+"");
        OkhttpUtils.getInstance().get(Urls.BLOG_LIST,map,callback);
    }

    @Override
    public void getPost(int catalog, int pageIndex, int pageSize, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("catalog",catalog+"");
        map.put("pageIndex",pageIndex+"");
        map.put("pageSize",pageSize+"");
        OkhttpUtils.getInstance().get(Urls.POST_LIST,map,callback);
    }
    @Override
    public void login(String username, String pwd, String keep_login, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("pwd",pwd);
        map.put("keep_login",keep_login);
        OkhttpUtils.getInstance().post(Urls.LOGIN,map,callback);
    }
    @Override
    public void getSoftType(String type, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("type",type);
        OkhttpUtils.getInstance().get(Urls.SOFTWARECATALOG,map,callback);
    }

    @Override
    public void getSoftTypes(String tag, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("tag",tag);
        OkhttpUtils.getInstance().get(Urls.SOFTWARECATALOG,map,callback);
    }

    @Override
    public void getTags(String tag, String pageIndex, String pageSize, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("searchTag",tag);
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        OkhttpUtils.getInstance().get(Urls.SOFTWARETAG,map,callback);
    }

    @Override
    public void getSoftList(String searchTag, String pageIndex, String pageSize, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("searchTag",searchTag);
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        OkhttpUtils.getInstance().get(Urls.SOFTWARE_LIST,map,callback);
    }
    @Override
    public void getSearch(String content, String catalog, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("content",content);
        map.put("catalog",catalog);
        OkhttpUtils.getInstance().get(Urls.SEARCH_LIST,map,callback);
    }

    @Override
    public void getUser(String name,int pageIndex,MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("name",name);
        map.put("pageIndex",pageIndex+"");
        OkhttpUtils.getInstance().get(Urls.FIND_USER,map,callback);
    }

    @Override
    public void getEvent(int pageIndex, int uid, int pageSize, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid+"");
        map.put("pageIndex",pageIndex+"");
        map.put("pageSize",pageSize+"");
        Factory.create().get(Urls.EVENT_LIST,map,callback);
    }

    @Override
    public void getRock(MyCallback callback) {
        Factory.create().get(Urls.ROCK_ROCK,null,callback);
    }

    @Override
    public void getUser(int Uid,String cookie, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("uid",Uid+"");
        map.put("cookie",cookie);
        OkhttpUtils.getInstance().get(Urls.MY_INFORMATION,map,callback);
    }
    @Override
    public void getTweet(String uid, String pageIndex, String pageSize, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid);
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        OkhttpUtils.getInstance().get(Urls.TWEET_LIST,map,callback);
    }
    @Override
    public void getFavorite(int uid,String cookie, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid+"");
        map.put("cookie",cookie);
        OkhttpUtils.getInstance().get(Urls.FARORITE_LIST,map,callback);
    }
    @Override
    public void getLike(String cookie,int uid, int tweetid, int ownerOfTweet, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("cookie",cookie);
        map.put("uid",uid+"");
        map.put("tweetid",tweetid+"");
        map.put("ownerOfTweet",ownerOfTweet+"");
        OkhttpUtils.getInstance().get(Urls.TWEET_LIKE,map,callback);
    }

    @Override
    public void getUnLike(String cookie,int uid, int tweetid, int ownerOfTweet, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("cookie",cookie);
        map.put("uid",uid+"");
        map.put("tweetid",tweetid+"");
        map.put("ownerOfTweet",ownerOfTweet+"");
        OkhttpUtils.getInstance().get(Urls.TWEET_UNLIKE,map,callback);
    }
    @Override
    public void deleteTweet(int uid, int tweetid,String cookie,MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid+"");
        map.put("tweetid",tweetid+"");
        map.put("cookie",cookie);
        OkhttpUtils.getInstance().post(Urls.TWEET_DELETE,map,callback);
    }
    @Override
    public void getNewsDetail(String id, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        OkhttpUtils.getInstance().get(Urls.NEWS_DETAIL,map,callback);
    }

    @Override
    public void getBlogDetail(String id, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        OkhttpUtils.getInstance().get(Urls.BLOG_DETAIL,map,callback);
    }

    @Override
    public void getPostDetail(String id, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("id",id);
        OkhttpUtils.getInstance().get(Urls.POST_DETAIL,map,callback);
    }

    @Override
    public void getComment(int catalog, int id, int pageIndex, int pageSize,MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("catalog",catalog+"");
        map.put("id",id+"");
        map.put("pageIndex",pageIndex+"");
        map.put("pageSize",pageSize+"");
        OkhttpUtils.getInstance().get(Urls.COMMENT_LIST,map,callback);
    }
    @Override
    public void pubTweet(int uid, String msg,String cookie,String img, String amr, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid+"");
        map.put("msg",msg);
        map.put("img",img);
        map.put("amr",amr);
        map.put("cookie",cookie);
        OkhttpUtils.getInstance().post(Urls.TWEET_PUB,map,callback);
    }

    @Override
    public void portrait_pub(int uid, String portrait, String cookie, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid+"");
        map.put("portrait",portrait);
        map.put("cookie",cookie);
        Factory.create().post(Urls.PORTRAIT_UPDATE,map,callback);
    }

    @Override
    public void getApkVersion(MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        Factory.create().get(Urls.APKVERSION,map,callback);
    }

    @Override
    public void comment_Pub(int catalog, int id, int uid, String content, int isPostToMyZone,String cookie,MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("catalog",catalog+"");
        map.put("id",id+"");
        map.put("uid",uid+"");
        map.put("content",content);
        map.put("isPostToMyZone",isPostToMyZone+"");
        map.put("cookie",cookie);
        Factory.create().post(Urls.COMMENT_PUB,map,callback);
    }
    @Override
    public void getFriends(int uid, int relation, int pageIndex, int pageSize, MyCallback callback) {
        Map<String,String> map=new HashMap<>();
        map.put("uid",uid+"");
        map.put("relation",relation+"");
        map.put("pageIndex",pageIndex+"");
        map.put("pageSize",pageSize+"");
        OkhttpUtils.getInstance().get(Urls.FRIENDS_LIST,map,callback);
    }
}
