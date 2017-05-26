package com.jiyun.kaiyuanzhongguo.model.bean;

import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by dell on 2017/5/9.
 */

public class Blog {

    private String pagesize;
    private List<BlogBean> blogs;

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public List<BlogBean> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<BlogBean> blogs) {
        this.blogs = blogs;
    }

    public static class BlogBean {
        private String id;
        private String title;
        private String body;
        private String url;
        private String pubDate;
        private String authoruid;
        private String authorname;
        private String commentCount;
        private String documentType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getAuthoruid() {
            return authoruid;
        }

        public void setAuthoruid(String authoruid) {
            this.authoruid = authoruid;
        }

        public String getAuthorname() {
            return authorname;
        }

        public void setAuthorname(String authorname) {
            this.authorname = authorname;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public String getDocumentType() {
            return documentType;
        }

        public void setDocumentType(String documentType) {
            this.documentType = documentType;
        }
    }
}
