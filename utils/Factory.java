package com.jiyun.kaiyuanzhongguo.utils;

import static android.R.attr.type;

/**
 * Created by dell on 2017/5/10.
 */

public class Factory {
    private static final int OKHTTP=1;
    private static final int TYPE=OKHTTP;
    public static FactoryIn create(){
        switch (TYPE){
            case 1:
                return OkhttpUtils.getInstance();

        }
        return null;
    }
}
