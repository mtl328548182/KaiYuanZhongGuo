package com.jiyun.kaiyuanzhongguo.utils;

import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;

import java.util.Map;

/**
 * Created by dell on 2017/5/10.
 */

public interface FactoryIn {
    void get(String url, Map<String,String> map, MyCallback callback);
    void post(String url, Map<String,String> map, MyCallback callback);
    void downLoadFile(String fileUrl, final String destFileDir,MyCallback callBack);
}
