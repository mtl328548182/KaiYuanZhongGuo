package com.jiyun.kaiyuanzhongguo.model.http;

import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;

/**
 * Created by dell on 2017/5/8.
 */

public interface INewsModel {
    void getNews(int catalog, int pageIndex, int pageSize,MyCallback callback);
    void getBlog(String type,int pageIndex,int pageSize,MyCallback callback);
    void getPost(int catalog,int pageIndex,int pageSize,MyCallback callback);
    void login(String username,String pwd,String keep_login,MyCallback callback);
    void getSoftType(String type,MyCallback callback);
    void getSoftTypes(String tag,MyCallback callback);
    void getTags(String tag,String pageIndex,String pageSize,MyCallback callback);
    void getSoftList(String searchTag,String pageIndex,String pageSize,MyCallback callback);
    void getSearch(String content,String catalog,MyCallback callback);
    void getUser(String name,int pageIndex,MyCallback callback);
    void getEvent(int pageIndex,int uid,int pageSize,MyCallback callback);
    void getRock(MyCallback callback);
    void getUser(int Uid,String cookie,MyCallback callback);
    void getTweet(String uid,String pageIndex,String pageSize,MyCallback callback);
    void getFavorite(int uid,String cookie,MyCallback callback);
    void getLike(String cookie,int uid,int tweetid,int ownerOfTweet,MyCallback callback);
    void getUnLike(String cookie,int uid,int tweetid,int ownerOfTweet,MyCallback callback);
    void deleteTweet(int uid,int tweetid,String cookie,MyCallback callback);
    void getNewsDetail(String id,MyCallback callback);
    void getBlogDetail(String id,MyCallback callback);
    void getPostDetail(String id,MyCallback callback);
    void getComment(int catalog,int id,int pageIndex,int pageSize,MyCallback callback);
    void pubTweet(int uid,String msg,String img,String cookie,String amr,MyCallback callback);
    void portrait_pub(int uid,String portrait,String cookie,MyCallback callback);
    void getApkVersion(MyCallback callback);
    void comment_Pub(int catalog,int id,int uid,String content,int isPostToMyZone,String cookie,MyCallback callback);
    void getFriends(int uid,int relation,int pageIndex,int pageSize,MyCallback callback);
}
