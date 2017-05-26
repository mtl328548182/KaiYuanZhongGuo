package com.jiyun.kaiyuanzhongguo.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.fragment.ComprehensiveFragment;
import com.jiyun.kaiyuanzhongguo.fragment.OpenFragment;
import com.jiyun.kaiyuanzhongguo.fragment.find.FindFragment;
import com.jiyun.kaiyuanzhongguo.fragment.move.MoveFragment;
import com.jiyun.kaiyuanzhongguo.fragment.my.MyFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.Login;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.path;
import static com.jiyun.kaiyuanzhongguo.view.BulletActivity.REQUEST_IMAGE_CAPTURE;

public class MainActivity extends BaseActivity{

    @BindView(R.id.comprehensive)
    RadioButton comprehensive;
    @BindView(R.id.move)
    RadioButton move;
    @BindView(R.id.Bullet)
    ImageView Bullet;
    @BindView(R.id.find)
    RadioButton find;
    @BindView(R.id.My)
    RadioButton My;
    @BindView(R.id.main_tab)
    RadioGroup mainTab;
    @BindView(R.id.main_text)
    TextView mainText;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.main_imageView)
    ImageView mainImageView;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    private ComprehensiveFragment comprehensiveFragment;
    private FindFragment findFragment;
    private MoveFragment moveFragment;
    private MyFragment myFragment;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int code=0;
    private String path;
    private String cookie;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        cookie=sharedPreferences.getString("cookie","");
        if(!cookie.equals("")){
            final String username=sharedPreferences.getString("username","");
            final String pwd=sharedPreferences.getString("pwd","");
            NewsModel model=new NewsModel();
            model.login(username, pwd, "1", new MyCallback() {
                @Override
                public void onSuccess(String jsondate) {
                    XStream xStream=new XStream();
                    xStream.alias("oschina", Login.class);
                    xStream.alias("result", Login.ResultBean.class);
                    xStream.alias("user", Login.UserBean.class);
                    Login oschina= (Login) xStream.fromXML(jsondate);
                    Login.ResultBean resultBean=oschina.getResult();
                    int code=Integer.parseInt(resultBean.getErrorCode());
                    if(code==1){
                        editor.putString("name",oschina.getUser().getName());
                        editor.putString("username",username);
                        editor.putString("pwd",pwd);
                        editor.putInt("uid",Integer.parseInt(oschina.getUser().getUid()));
                        editor.putInt("code",code);
                        editor.commit();
                    }
                }

                @Override
                public void onError(String message) {

                }
            });
        }
        if(comprehensiveFragment==null){
            comprehensiveFragment=new ComprehensiveFragment();
        }
        startFragment(comprehensiveFragment,null,false);
        login();
    }

    @Override
    protected void initListener() {
        start();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public TextView getTitletv() {
        return mainText;
    }

    private void start() {
        mainTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.comprehensive:
                        startFragment(comprehensiveFragment,null,false);
                        mainText.setText("综合");
                        mainText.setVisibility(View.VISIBLE);
                        mainImageView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.move:
                        if(moveFragment==null){
                            moveFragment=new MoveFragment();
                        }
                        startFragment(moveFragment,null,false);
                        mainText.setText("动弹");
                        mainText.setVisibility(View.VISIBLE);
                        mainImageView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.find:
                        if(findFragment==null){
                            findFragment=new FindFragment();
                        }
                        startFragment(findFragment,null,false);
                        mainText.setText("发现");
                        mainText.setVisibility(View.VISIBLE);
                        mainImageView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.My:
                        if(myFragment==null){
                            myFragment=new MyFragment();
                        }
                        startFragment(myFragment,null,false);
                        mainText.setVisibility(View.GONE);
                        mainImageView.setVisibility(View.GONE);
                        break;
                }
            }
        });
        mainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        Bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cookie.equals("")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 100);
                }else{
                    Intent intent = new Intent(MainActivity.this, BulletActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void login() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==101){
            finish();
        }else if(requestCode==200&&resultCode==201){
            finish();
        }
            if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
                return;
            }
            Bitmap bm = null;

            //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
            ContentResolver resolver = getContentResolver();

            //此处的用于判断接收的Activity是不是你想要的那个
            if (requestCode == 10) {
                try {
                    Uri originalUri = data.getData();        //获得图片的uri

                    bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片

                    //获取图片的路径：

                    String[] proj = {MediaStore.Images.Media.DATA};

                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    path = cursor.getString(column_index);
                    Intent intent=getIntent();
                    intent.putExtra("path",path);
                } catch (IOException e) {
                }
            }
    }
private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                break;
        }
    }
};
    private int click=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            if(click==0) {
                Toast.makeText(MainActivity.this, "再次点击退出开源中国", Toast.LENGTH_SHORT).show();
                click=1;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        click=0;
                    }
                },2000);
            }else{
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putInt("code",0);
        editor.commit();
    }
}
