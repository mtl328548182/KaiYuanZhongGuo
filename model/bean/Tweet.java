package com.jiyun.kaiyuanzhongguo.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2017/4/16.
 */

public class Tweet implements Serializable{
    private String tweetCount;
    private String pagesize;
    private List<TweetBean> tweets;

    public String getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(String tweetCount) {
        this.tweetCount = tweetCount;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public List<TweetBean> getTweets() {
        return tweets;
    }

    public void setTweets(List<TweetBean> tweets) {
        this.tweets = tweets;
    }

    public static class TweetBean implements Serializable{
        private String id;
        private String portrait;
        private String author;
        private String authorid;
        private String body;
        private String attach;
        private String appclient;
        private String commentCount;
        private String pubDate;
        private String imgSmall;
        private String imgBig;
        private String likeCount;
        private String isLike;
        private List<User> likeList;

        public List<User> getLikeList() {
            return likeList;
        }

        public void setLikeList(List<User> likeList) {
            this.likeList = likeList;
        }

        public class User implements Serializable{
            private String name;
            private String uid;
            private String portrait;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthorid() {
            return authorid;
        }

        public void setAuthorid(String authorid) {
            this.authorid = authorid;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public String getAppclient() {
            return appclient;
        }

        public void setAppclient(String appclient) {
            this.appclient = appclient;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getImgSmall() {
            return imgSmall;
        }

        public void setImgSmall(String imgSmall) {
            this.imgSmall = imgSmall;
        }

        public String getImgBig() {
            return imgBig;
        }

        public void setImgBig(String imgBig) {
            this.imgBig = imgBig;
        }

        public String getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(String likeCount) {
            this.likeCount = likeCount;
        }

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }


    }
}
