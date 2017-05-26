package com.jiyun.kaiyuanzhongguo.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/4/27.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.setting_finish)
    RadioButton settingFinish;
    @BindView(R.id.setting_title)
    TextView settingTitle;
    @BindView(R.id.setting_clear)
    RelativeLayout settingClear;
    @BindView(R.id.setting_backTwo)
    CheckBox settingBackTwo;
    @BindView(R.id.setting_back)
    RelativeLayout settingBack;
    @BindView(R.id.setting_Opinion)
    RelativeLayout settingOpinion;
    @BindView(R.id.setting_About)
    RelativeLayout settingAbout;
    @BindView(R.id.setting_inspect)
    RelativeLayout settingInspect;
    @BindView(R.id.setting_Cancellation)
    RelativeLayout settingCancellation;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @Override
    protected void initListener() {
        settingCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("code",0);
                editor.putString("cookie","");
                editor.putInt("uid",0);
                editor.putString("username",null);
                editor.putString("pwd",null);
                editor.commit();
                setResult(201);
                Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        settingFinish.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
