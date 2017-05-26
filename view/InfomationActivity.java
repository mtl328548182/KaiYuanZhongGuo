package com.jiyun.kaiyuanzhongguo.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.model.bean.User;
import com.jiyun.kaiyuanzhongguo.utils.GlideCircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/4/26.
 */

public class InfomationActivity extends BaseActivity {

    @BindView(R.id.info_finish)
    ImageView infoFinish;
    @BindView(R.id.info_title)
    TextView infoTitle;
    @BindView(R.id.info_image)
    ImageView infoImage;
    @BindView(R.id.info_text_name)
    TextView infoTextName;
    @BindView(R.id.info_name)
    LinearLayout infoName;
    @BindView(R.id.my_relative)
    RelativeLayout myRelative;
    @BindView(R.id.info_add)
    TextView infoAdd;
    @BindView(R.id.info_from)
    TextView infoFrom;
    @BindView(R.id.info_devplatform)
    TextView infoDevplatform;
    @BindView(R.id.info_expertise)
    TextView infoExpertise;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_infor;
    }

    @Override
    protected void init() {
        User user = (User) getIntent().getBundleExtra("bundle").getSerializable("user");
        infoTextName.setText(user.getUser().getName());
        infoAdd.setText(user.getUser().getJointime());
        infoFrom.setText(user.getUser().getFrom());
        infoDevplatform.setText(user.getUser().getDevplatform());
        infoExpertise.setText(user.getUser().getExpertise());
        String url = user.getUser().getPortrait();
        if (!url .equals("")) {
            Glide.with(this).load(url).transform(new GlideCircleTransform(this)).into(infoImage);
        } else {
            Glide.with(this).load(R.mipmap.widget_dface).transform(new GlideCircleTransform(this)).into(infoImage);
        }
    }
    @Override
    protected void initListener() {
        infoFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

}
