package com.jiyun.kaiyuanzhongguo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.jiyun.kaiyuanzhongguo.App;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by dell on 2017/4/11.
 */

public class OkhttpUtils implements FactoryIn{
    private static OkhttpUtils okhttpUtils=null;
    private OkHttpClient okHttpClient=null;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public OkhttpUtils(){
        shared= App.activity.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor=shared.edit();
        cookie();
    }
    public static OkhttpUtils getInstance(){
        if(okhttpUtils==null){
            synchronized (OkhttpUtils.class){
                okhttpUtils=new OkhttpUtils();
                return okhttpUtils;
            }
        }
        return okhttpUtils;
    }
    public void get(String url, Map<String,String> map, final MyCallback callback){
        StringBuffer sb=new StringBuffer("?");
        if(map!=null&&map.size()>=1){
            int a=0;
            for(String key:map.keySet()){
                String value=map.get(key);
                if(a==0) {
                    sb.append(key + "=" + value);
                    a++;
                }else {
                    sb.append("&"+key+"="+value);
                }
            }
        }
        String cookie=map.get("cookie");
        url=url+sb.toString();
        Request request=null;
        if(cookie!=null) {
            request = new Request.Builder().url(url)
                    .addHeader("cookie", cookie)
                    .get().build();
        }else{
            request = new Request.Builder().url(url)
                    .get().build();
        }
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String jsondate=response.body().string();
                App.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(jsondate);
                    }
                });
            }
        });
    }
    private void cookie(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
        okHttpClient=builder.build();
    }
    public void post(String url, Map<String,String> map, final MyCallback callback){
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(map!=null&&map.size()>0) {
            for (String key : map.keySet()) {
                String value = map.get(key);
                if(value==null){

                }else {
                    builder.addFormDataPart(key, value);
                    if (value.endsWith(".jpg") || value.endsWith(".png")) {
                        String imgName = value.substring(value.lastIndexOf("/") + 1);
                        builder.addFormDataPart(key, value, MultipartBody.create(MediaType.parse("image/*"), new File(value)));
                    }
                }
            }
        }
        String cookie=map.get("cookie");
        Request request=null;
        if(cookie!=null) {
            request = new Request.Builder().url(url)
                    .addHeader("cookie", cookie)
                    .post(builder.build()).build();
        }else{
            request=new Request.Builder().url(url).post(builder.build()).build();
        }
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsondata=response.body().string();
                Headers headers = response.headers();
                String cookie=null;
                int code=shared.getInt("code",0);
                if(code==0) {
                    for (String key : headers.names()) {
                        List<String> value = headers.values(key);
                        if (key.contains("Set-Cookie")) {
                            cookie += value + ";";
                        }
                    }
                    if (cookie.length() > 0) {
                        cookie = cookie.substring(0, cookie.length() - 1);
                    }
                    if (cookie != null) {
                        editor.putString("cookie", cookie);
                        editor.commit();
                    }
                }
                App.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(jsondata);
                    }
                });
            }
        });
    }
    public void downLoadFile(String fileUrl, final String destFileDir, final MyCallback callBack) {
//        final String fileName = MD5.encode(fileUrl);
        final File file = new File(destFileDir, fileUrl);
        if (file.exists()) {
            callBack.onSuccess(file+"");
            return;
        }
        final Request request = new Request.Builder().url(fileUrl).build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
                callBack.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    Log.e(TAG, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        Log.e(TAG, "current------>" + current);
                    }
                    fos.flush();
                    callBack.onSuccess("下载成功");
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                    callBack.onError("下载失败");
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });
    }
}
