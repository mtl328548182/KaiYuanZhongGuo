package com.jiyun.kaiyuanzhongguo.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.base.BaseActivity;
import com.jiyun.kaiyuanzhongguo.model.bean.Login;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;
import com.thoughtworks.xstream.XStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2017/4/16.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_button)
    Button loginButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @Override
    protected void initListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=loginUsername.getText().toString();
                String pwd=loginPassword.getText().toString();
                login(username,pwd);
            }
        });
    }
    private void login(final String username, final String pwd){
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
                    setResult(101);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(String message) {

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
