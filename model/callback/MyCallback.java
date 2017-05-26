package com.jiyun.kaiyuanzhongguo.model.callback;

/**
 * Created by dell on 2017/5/8.
 */

public interface MyCallback {
    void onError(String error);

    void onSuccess(String xmldata);
}
