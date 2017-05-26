package com.jiyun.kaiyuanzhongguo.model.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dell on 2017/5/19.
 */

public class Version {

    /**
     * versionName : 1.0.1
     * versionCode : 2
     * contentDesc : 1.0.2版本的apk修复了综合模块中数据刷新卡顿的问题
     * newApkUrl : http://192.168.137.117:8889/new.apk
     */

    private String versionName;
    private int versionCode;
    private String contentDesc;
    private String newApkUrl;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getNewApkUrl() {
        return newApkUrl;
    }

    public void setNewApkUrl(String newApkUrl) {
        this.newApkUrl = newApkUrl;
    }
}
