package com.jiyun.kaiyuanzhongguo.fragment.my;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.Favorite;
import com.jiyun.kaiyuanzhongguo.model.bean.Tweet;
import com.jiyun.kaiyuanzhongguo.model.bean.User;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.jiyun.kaiyuanzhongguo.utils.GlideCircleTransform;
import com.jiyun.kaiyuanzhongguo.view.FriendsActivity;
import com.jiyun.kaiyuanzhongguo.view.InfomationActivity;
import com.jiyun.kaiyuanzhongguo.view.LoginActivity;
import com.jiyun.kaiyuanzhongguo.view.SettingActivity;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;

import static android.R.attr.id;
import static android.app.Activity.RESULT_OK;
import static com.jiyun.kaiyuanzhongguo.App.activity;
import static com.jiyun.kaiyuanzhongguo.view.BulletActivity.REQUEST_IMAGE_CAPTURE;


/**
 * Created by dell on 2017/4/11.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.my_Set_up)
    ImageButton mySetUp;
    @BindView(R.id.my_QR_code)
    ImageButton myQRCode;
    @BindView(R.id.my_image)
    ImageView myImage;
    @BindView(R.id.my_text_name)
    TextView myTextName;
    @BindView(R.id.my_text_jifen)
    TextView myTextJifen;
    @BindView(R.id.my_name)
    LinearLayout myName;
    @BindView(R.id.One_title)
    TextView OneTitle;
    @BindView(R.id.One_order)
    TextView OneOrder;
    @BindView(R.id.my_one)
    LinearLayout myOne;
    @BindView(R.id.Two_title)
    TextView TwoTitle;
    @BindView(R.id.Two_order)
    TextView TwoOrder;
    @BindView(R.id.my_two)
    LinearLayout myTwo;
    @BindView(R.id.Three_title)
    TextView ThreeTitle;
    @BindView(R.id.Three_order)
    TextView ThreeOrder;
    @BindView(R.id.my_three)
    LinearLayout myThree;
    @BindView(R.id.Four_title)
    TextView FourTitle;
    @BindView(R.id.Four_order)
    TextView FourOrder;
    @BindView(R.id.my_four)
    LinearLayout myFour;
    @BindView(R.id.my_radiogroup)
    LinearLayout myRadiogroup;
    @BindView(R.id.My_Message)
    RelativeLayout MyMessage;
    @BindView(R.id.My_Blog)
    RelativeLayout MyBlog;
    @BindView(R.id.My_Post)
    RelativeLayout MyPost;
    @BindView(R.id.My_activity)
    RelativeLayout MyActivity;
    @BindView(R.id.My_Team)
    RelativeLayout MyTeam;
    @BindView(R.id.my_relative)
    RelativeLayout myRelative;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int uid = 0;
    private List<Tweet.TweetBean> mList;
    private Favorite oschina;
    private User user;
    private String path;
    private String url;

    @Override
    protected int layoutId() {
        return R.layout.activity_my;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        Glide.with(getContext()).load(R.mipmap.widget_dface).transform(new GlideCircleTransform(getContext())).into(myImage);
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        uid = sharedPreferences.getInt("uid", 0);
        int code = sharedPreferences.getInt("code", 0);
        String cookie = sharedPreferences.getString("cookie", "");
        if (!cookie.equals("")) {
            getUser(uid, cookie);
        }else{
            myRadiogroup.setVisibility(View.GONE);
            myTextName.setText("点击头像登录");
        }
    }

    private void getUser(int uid, String cookie) {
        NewsModel model = new NewsModel();
        model.getUser(uid, cookie, new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                Log.e("json",jsondate);
                XStream xStream = new XStream();
                xStream.alias("oschina", User.class);
                xStream.alias("user", User.UserBean.class);
                user = (User) xStream.fromXML(jsondate);
                myTextName.setText(user.getUser().getName());
                myTextJifen.setText("积分" + "  " + user.getUser().getScore());
                FourTitle.setText(user.getUser().getFans());
                ThreeTitle.setText(user.getUser().getFollowers());
                TwoTitle.setText(user.getUser().getFavoritecount());
                url = user.getUser().getPortrait();
                if (url != "") {
                    Glide.with(getContext()).load(url).transform(new GlideCircleTransform(getContext())).into(myImage);
                } else {
                    Glide.with(getContext()).load(R.mipmap.widget_dface).transform(new GlideCircleTransform(getContext())).into(myImage);
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        model.getTweet(uid + "", "0", "500", new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                Log.e("jsondate",jsondate);
                XStream xStream = new XStream();
                xStream.alias("oschina", Tweet.class);
                xStream.alias("tweet", Tweet.TweetBean.class);
                xStream.alias("user", Tweet.TweetBean.User.class);
                Tweet oschina = (Tweet) xStream.fromXML(jsondate);
                OneTitle.setText(oschina.getPagesize());
                mList = oschina.getTweets();
            }

            @Override
            public void onError(String message) {

            }
        });
        model.getFavorite(uid, sharedPreferences.getString("cookie", ""), new MyCallback() {
            @Override
            public void onSuccess(String jsondate) {
                XStream xStream = new XStream();
                xStream.alias("oschina", Favorite.class);
                xStream.alias("favorite", Favorite.FavoriteBean.class);
                oschina = (Favorite) xStream.fromXML(jsondate);
                TwoTitle.setText(oschina.getPagesize());
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    @Override
    protected void initListener() {
//        MyPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int code = sharedPreferences.getInt("code", 0);
//                if (code == 0) {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    startActivity(intent);
//
//                } else {
//                }
//            }
//        });
        myThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
        myFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        myOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("type", 3);
                startActivity(intent);
            }
        });
        myTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                intent.putExtra("uid", uid);
                Bundle bundle = new Bundle();
                bundle.putSerializable("favorite", oschina);
                intent.putExtra("bundle", bundle);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });
//        MyBlog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int code = sharedPreferences.getInt("code", 0);
//                if (code == 0) {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getContext(), BlogActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
        myRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int code = sharedPreferences.getInt("code", 0);
                if (code == 0) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    Intent intent = new Intent(getContext(),InfomationActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("user",user);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }
            }
        });
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int code = sharedPreferences.getInt("code", 0);
                if (code == 0) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent,100);
                } else {
                    dialog();
                }
            }
        });
        mySetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SettingActivity.class);
                startActivityForResult(intent,200);
            }
        });
    }

    @Override
    protected void loadData() {
        activity.getTitletv().setText("我的");
    }

    @Override
    public void setParams(Bundle bundle) {

    }

    private void dialog(){
        final AlertDialog myDialog =new AlertDialog.Builder(getContext()).create();
        myDialog.show();
        myDialog.getWindow().setContentView(R.layout.dialogimg_moban);
        myDialog.getWindow().findViewById(R.id.dialog_textOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        myDialog.getWindow().findViewById(R.id.dialog_textTwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 10);

            }
        });
        myDialog.getWindow().findViewById(R.id.dialog_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(getContext()).create();
                dialog.show();
                dialog.getWindow().setContentView(R.layout.da_image);
                ImageView imageView= (ImageView) dialog.getWindow().findViewById(R.id.da_image);
                Glide.with(getContext()).load(url).into(imageView);
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().findViewById(R.id.dialog_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            return;
        }
        Bitmap bm = null;

        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getActivity().getContentResolver();

        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == 10) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri

                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片

                //获取图片的路径：

                String[] proj = {MediaStore.Images.Media.DATA};

                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = getActivity().managedQuery(originalUri, proj, null, null, null);
                //按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                //最后根据索引值获取图片路径
                path = cursor.getString(column_index);
                final String cookie=sharedPreferences.getString("cookie","");
                NewsModel model=new NewsModel();
                model.portrait_pub(uid, path, cookie, new MyCallback() {
                    @Override
                    public void onError(String error) {

                    }

                    @Override
                    public void onSuccess(String xmldata) {
                        Log.e("aa",xmldata);
                        getUser(uid,cookie);
                    }
                });
            } catch (IOException e) {
            }
        }
    }
}
