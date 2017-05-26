package com.jiyun.kaiyuanzhongguo.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.model.bean.Version;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.jiyun.kaiyuanzhongguo.utils.Factory;

/**
 * Created by dell on 2017/5/8.
 */

public class AnimActivity extends BaseActivity {
    private Handler handler=new Handler();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_anim;
    }

    @Override
    protected void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(AnimActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
//                getAPK();
            }
        },2000);
    }
    private void getAPK(){
        NewsModel model=new NewsModel();
        model.getApkVersion(new MyCallback() {
            @Override
            public void onError(String error) {
                Log.e("error",error);
                Intent intent=new Intent(AnimActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSuccess(String xmldata) {
                Log.e("aa",xmldata);
                Gson gson=new Gson();
                if(xmldata!=null){
                  Version version=gson.fromJson(xmldata, Version.class);
                    int APKVersion=version.getVersionCode();
                    String url=version.getNewApkUrl();
                    int MyVersion=getVerSionCode();
                    if(MyVersion< APKVersion){
                        dialog(url);
                    }else{
                        Intent intent=new Intent(AnimActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public TextView getTitletv() {
        return null;
    }

    private int getVerSionCode(){
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int version = packInfo.versionCode;
        return version;
    }
    private void dialog(final String url){
        Dialog dialog=new AlertDialog.Builder(AnimActivity.this)
        .setTitle("更新")
        .setMessage("检测到有新的版本，是否需要更新")
        .setPositiveButton("开始更新",new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Factory.create().downLoadFile(url, null, new MyCallback() {
                    @Override
                    public void onError(String error) {

                    }

                    @Override
                    public void onSuccess(String xmldata) {
                        Intent intent=new Intent();
                        Toast.makeText(AnimActivity.this,xmldata,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("下次再说", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(AnimActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create();
        dialog.show();
    }
}
