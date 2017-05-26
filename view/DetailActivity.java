package com.jiyun.kaiyuanzhongguo.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.adapter.ViewpagerAdapter;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.base.BaseFragment;
import com.jiyun.kaiyuanzhongguo.fragment.detail.DetailFragment;
import com.jiyun.kaiyuanzhongguo.model.bean.Tweet;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.jiyun.kaiyuanzhongguo.utils.GlideCircleTransform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/5/16.
 */

public class DetailActivity extends BaseActivity {
    @BindView(R.id.movede_image)
    ImageView movedeImage;
    @BindView(R.id.movede_title)
    TextView movedeTitle;
    @BindView(R.id.movede_body)
    TextView movedeBody;
    @BindView(R.id.movede_Images)
    ImageView movedeImages;
    @BindView(R.id.movede_author)
    TextView movedeAuthor;
    @BindView(R.id.de_Fabulous)
    CheckBox deFabulous;
    @BindView(R.id.de_fabulous_count)
    TextView deFabulousCount;
    @BindView(R.id.movede_linear)
    LinearLayout movedeLinear;
    @BindView(R.id.movede_relative)
    RelativeLayout movedeRelative;
    @BindView(R.id.movede_tab)
    TabLayout movedeTab;
    @BindView(R.id.movede_viewpager)
    ViewPager movedeViewpager;
    @BindView(R.id.alertDialog_et)
    EditText alertDialogEt;
    private List<BaseFragment> mList = new ArrayList<>();
    private List<String> mNameList = new ArrayList<>();
    private int id=0;
    private SharedPreferences sharedPreferences;

    @Override
    protected int getLayoutId() {
        return R.layout.detail_moban;
    }

    @Override
    protected void init() {
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        Tweet.TweetBean tweet = (Tweet.TweetBean) bundle.getSerializable("tweet");
        initViews(tweet);
        initList(tweet);
    }

    private void initViews(Tweet.TweetBean tweet) {
        id= Integer.parseInt(tweet.getId());
        movedeTitle.setText(tweet.getAuthor());
        movedeBody.setText(tweet.getBody());
        deFabulousCount.setText(tweet.getLikeCount());
        final String time = tweet.getPubDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long pub = d.getTime();
        Long One = System.currentTimeMillis();
        long timer = (One - pub) / 1000 / 60 / 60 / 24;
        if (timer > 0) {
            movedeAuthor.setText(timer + "天前");
        } else {
            long timeOne = (One - pub) / 1000 / 60 / 60;
            if (timeOne >= 1) {
                movedeAuthor.setText(timeOne + "小时前");
            } else {
                long timeTwo = (One - pub) / 1000 / 60;
                movedeAuthor.setText(timeTwo + "分钟前");
            }
        }
        if (tweet.getPortrait() == "") {
            Glide.with(this).load(R.mipmap.widget_dface).transform(new GlideCircleTransform(this)).into(movedeImage);
        } else {
            Glide.with(this).load(tweet.getPortrait()).transform(new GlideCircleTransform(this)).into(movedeImage);
        }
        if (tweet.getImgBig() != "") {
            Glide.with(this).load(tweet.getImgBig()).into(movedeImages);
        } else {
            Glide.with(this).load("").into(movedeImages);
        }
    }

    private void initList(Tweet.TweetBean tweet) {
        mNameList.add("赞" + "(" + tweet.getLikeCount() + ")");
        mNameList.add("评论" + "(" + tweet.getCommentCount() + ")");
        DetailFragment detail = new DetailFragment();
        detail.setType(1);
        DetailFragment detailone = new DetailFragment();
        detailone.setType(2);
        mList.add(detail);
        mList.add(detailone);
        ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager(), mList, mNameList);
        movedeViewpager.setAdapter(adapter);
        movedeTab.setTag(movedeTab.newTab().setText(mNameList.get(0)));
        movedeTab.setTag(movedeTab.newTab().setText(mNameList.get(1)));
        movedeTab.setupWithViewPager(movedeViewpager);
    }

    @Override
    protected void initListener() {
        alertDialogEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public TextView getTitletv() {
        return null;
    }

    private void diaLog(){
        final AlertDialog dialog=new AlertDialog.Builder(DetailActivity.this)
                .create();
        View view= LayoutInflater.from(DetailActivity.this).inflate(R.layout.detail_dialog_moban,null);
        final EditText editText= (EditText) view.findViewById(R.id.Detail_Dialog_Order);
        ImageView imageView= (ImageView) view.findViewById(R.id.Detail_Dialog_image);
        ImageView imageView1= (ImageView) view.findViewById(R.id.Detail_Dialog_image2);
        Button button= (Button) view.findViewById(R.id.Detail_Dialog_Btn);
        dialog.setView(view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=editText.getText().toString().trim();
                pub(content);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void pub(String content){
        NewsModel model=new NewsModel();
        int uid=sharedPreferences.getInt("uid",0);
        String cookie=sharedPreferences.getString("cookie","");
        model.comment_Pub(3, id, uid, content, 0,cookie, new MyCallback() {
            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(String xmldata) {
                Log.e("ddd",xmldata);
                DetailFragment detail = new DetailFragment();
                detail.setType(1);
                DetailFragment detailone = new DetailFragment();
                detailone.setType(2);
                mList.add(detail);
                mList.add(detailone);
                ViewpagerAdapter adapter = new ViewpagerAdapter(getSupportFragmentManager(), mList, mNameList);
                movedeViewpager.setAdapter(adapter);
            }
        });
    }
}
