package com.jiyun.kaiyuanzhongguo.view;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.path;

/**
 * Created by dell on 2017/5/10.
 */

public class BulletActivity extends BaseActivity {
    @BindView(R.id.bullet_finish)
    ImageView bulletFinish;
    @BindView(R.id.bullet_title)
    TextView bulletTitle;
    @BindView(R.id.bullet_pub)
    TextView bulletPub;
    @BindView(R.id.bullet_relative)
    RelativeLayout bulletRelative;
    @BindView(R.id.Bullet_edit)
    EditText BulletEdit;
    @BindView(R.id.bullet_image)
    ImageView bulletImage;
    @BindView(R.id.Bullet_pubimg)
    ImageView BulletPubimg;
    @BindView(R.id.Bullet_pubtwo)
    ImageView BulletPubtwo;
    @BindView(R.id.Bullet_pubthree)
    ImageView BulletPubthree;
    @BindView(R.id.Bullet_pubfour)
    ImageView BulletPubfour;
    private String path;
    public final static int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CROP_REQUEST_CODE = 4;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bullet;
    }

    @Override
    protected void init() {
        mSharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    @Override
    protected void initListener() {
        bulletFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final String cookie = mSharedPreferences.getString("cookie", "");
        bulletPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = BulletEdit.getText().toString().trim();
                int uid = mSharedPreferences.getInt("uid", 0);
                if (msg.isEmpty() && uid != 0) {

                } else {
                    pubTweet(uid, msg, cookie, path, "");
                }
            }
        });
        BulletPubimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    @Override
    protected void loadData() {

    }
    private void pubTweet(int uid, String msg, String cookie, String img, String amr) {
        NewsModel model = new NewsModel();
        model.pubTweet(uid, msg, cookie, img, amr, new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                Log.e("aaa", jsondate);
                finish();
            }

            @Override
            public void onError(String message) {
                Log.e("error",message);
            }
        });
    }
    @Override
    public TextView getTitletv() {
        return null;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            return;
        }
        Bitmap bm = null;

        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();

        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
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
//                startCrop(originalUri);
                Glide.with(this).load(originalUri).into(bulletImage);
            } catch (IOException e) {
            }
        }else if(requestCode==CROP_REQUEST_CODE) {
            if (data != null){
                return;
            }
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                bulletImage.setImageBitmap(photo); //把图片显示在ImageView控件上
            }
        }
    }
    /**
     * 开始裁剪
     *
     * @param uri
     */
    private void startCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    /**
     * 判断sdcard卡是否可用
     *
     * @return 布尔类型 true 可用 false 不可用
     */
    private boolean isSDCardCanUser() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
